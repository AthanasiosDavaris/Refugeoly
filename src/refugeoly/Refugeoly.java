package refugeoly;

import java.util.*;
import java.io.*;

public class Refugeoly {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        Board board = new Board();
        Refugee refugee[];
        GiverEntity NGOBank = new GiverEntity();
        ReceiverEntity MafiaBank = new ReceiverEntity();
        String playerName, option;
        int playersAlive;
        int turn = 0, j=0, i=0;
        int menu;
        PrintWriter outputStream = null;
        Scanner inputStream = null;
        Square square = new Square();
        int tempNumber;
        String tempText, tempDescription;
        Action tempAction;
        boolean loop;
        
        System.out.print("Number of players: ");
        int playersNumber = scanner.nextInt();
        scanner.nextLine();
        while ( (playersNumber<1) || (playersNumber>4) ) {
            System.out.println("Error! players must be between 1 and 4!");
            System.out.print("Number of players: ");
            playersNumber = scanner.nextInt();
            scanner.nextLine();
        }
        
        refugee = new Refugee[playersNumber];
        
        for (int k=0 ; k<playersNumber ; k++)
        {
            System.out.print("Name of player " + (k+1) + ":");
            playerName = scanner.nextLine();
            refugee[k] = new Refugee();
            refugee[k].setName(playerName);
        }
        
        System.out.println("Let the Game Begin!!!\n\n");
        do 
        {
            playersAlive = 0;
            for (int k=0 ; k<playersNumber ; k++)
            {
                if (refugee[k].alive == true) {
                    playersAlive++;
                }
            }
            if (playersAlive == 0) {
                 System.out.println("Game Over!\nAll players have been eliminated!\n gg wp");
                 return;
            }
            if (i>=playersNumber) {
                i=0;
            }
            
            for (i=i ; i<playersNumber ; i++)
            {
                if ((refugee[i].alive == true) && (turn >= refugee[i].getStay())) {
                    System.out.println("It's " + refugee[i].getName() + "'s turn");
                    System.out.println("You are currently at " + refugee[i].getSquare());
                    System.out.println("You currently have " + refugee[i].getMoney());
                    System.out.println("You have already spent " + refugee[i].getExpenses() + "€");
                    System.out.println("To roll the dice press 1\nTo save the game press 2\nTo load a previous game press 3\nTo exit the current game press 4");
                    menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            tempAction = new RollDiceAction();
                            tempAction.act(refugee[i]);
                            break;
                        case 2:
                            try {
                                outputStream = new PrintWriter( new FileOutputStream ("RefugeolySave.txt"));
                            } catch (FileNotFoundException e) {
                                System.err.println("An error has occured while saving the game");
                                outputStream.close();
                            }
                            outputStream.println(playersNumber);
                            outputStream.println(turn);
                            for (int k=0 ; k<playersNumber ; k++)
                            {
                                outputStream.println(refugee[k].getMoney());
                                outputStream.println(refugee[k].getName());
                                outputStream.println(refugee[k].getSquare());
                                outputStream.println(refugee[k].getExpenses());
                                outputStream.println(refugee[k].isAlive());
                                outputStream.println(refugee[k].hasProtection());
                                outputStream.println(refugee[k].getStay());
                            }
                            outputStream.println(MafiaBank.getMoney());
                            outputStream.println(NGOBank.getMoney());
                            i--;
                            outputStream.println(i);
                            outputStream.close();
                            System.out.println("Game was saved succesfuly!");
                            break;
                        case 3:
                            try {
                                inputStream = new Scanner(new File("RefugeolySave.txt"));
                            }
                            catch (FileNotFoundException e) {
                                System.err.println("An error has occured while loading the game");
                                inputStream.close();
                            }
                            
                            playersNumber = inputStream.nextInt();
                            inputStream.nextLine();
                            refugee = new Refugee[playersNumber];
                            for (int k=0 ; k<playersNumber ; k++)
                            {
                                refugee[k] = new Refugee();
                            }
                            turn=inputStream.nextInt(); 
                            inputStream.nextLine();
                            for (int k=0 ; k<playersNumber ; k++) {
                                refugee[k].money = inputStream.nextInt();
                                inputStream.nextLine();
                                refugee[k].setName(inputStream.nextLine());
                                refugee[k].moveTo(inputStream.nextInt());
                                inputStream.nextLine();
                                refugee[k].expenses = inputStream.nextInt();
                                inputStream.nextLine();
                                refugee[k].alive = inputStream.nextBoolean();
                                inputStream.nextLine();
                                refugee[k].protection = inputStream.nextBoolean();
                                inputStream.nextLine();
                                refugee[k].setStay(inputStream.nextInt());
                                inputStream.nextLine();
                            }
                            
                            MafiaBank.setMoney(inputStream.nextInt());
                            inputStream.nextLine();
                            NGOBank.money = inputStream.nextInt();
                            inputStream.nextLine();
                            i = inputStream.nextInt();
                            inputStream.nextLine();
                            
                            inputStream.close();
                            
                            System.out.println("GAME LOADED SUCCESFULLY!!!");
                            break;
                        case 4:
                            System.out.println("Thank you for playing our game!\nHave a nice day!");
                            break;
                        default:
                            break;
                            
                            
                    }
                    if (menu == 4) {
                        return;
                    }
                    else if (menu == 1) {
                        loop = true;
                        turn++;
                        while (loop) {
                            switch (refugee[i].getSquare()) {
                                case 0:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Country in conflict.";
                                    tempDescription = "In the last few years the main number of refugees come form Iraq, Syria, Pakistan, Libya, Afghanistan and Kurdistan, but also from many countries in Africa.";
                                    board.setSquare(tempNumber, tempText, tempDescription, null);
                                    board.printBoard(j);
                                    loop = false;
                                    break;
                                case 1:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Food for the journey. Pay $100.";
                                    tempDescription = "This is key once leaving a country in conflict. The journey is always difficult to predict. Outsourcing food is fundamental once a refugee starts a journey.";
                                    tempAction = new PayMoneyAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    if (refugee[i].getMoney() - 100 <= 0) {
                                        refugee[i].death();
                                    }
                                    else {
                                        tempAction.act(refugee[i]);
                                    }
                                    loop = false;
                                    break;
                                case 2:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Car. You get a free lift. Roll dice. ";
                                    tempDescription = "NGO volunteers or simply samaritans often help refugees by taken them to countries borders for free in their personal cars.";
                                    tempAction = new RollDiceAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    break;
                                case 3:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Communication gear. Mobile phone and sim card. Pay $300 to the Mafia Bank.";
                                    tempDescription = "hone contact is an essential kit for the journey. Refugees required to be able to contact family or the ma a via phone during the journey to safety. Often the Mafia obligates refugees to buy their phones through them, to controls their numbers and communications";
                                    tempAction = new PayMoneyAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    if (refugee[i].getMoney() - 300 <= 0) {
                                        refugee[i].death();
                                    }
                                    else {
                                        tempAction.act(refugee[i]);
                                    }
                                    loop = false;
                                    break;
                                case 4:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Army Control. You go back to war box (0).";
                                    tempDescription = "Often refugees find sudden Army Controls during their journey. The military stop them and in most of the cases divert them back to their countries of origin.";
                                    tempAction = new GoToAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    loop = false;
                                    break;
                                case 5:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Border 1. Go back to war box (0).";
                                    tempDescription = "There are country borders in every country that the refugees have to go through. Refugees are often stuck in these ones till they have their documents checked or pay money for visas to corrupt army forces in order to carry on the journey.";
                                    tempAction = new GoToAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    loop = false;
                                    break;
                                case 6:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Mafia. Pay 1000$.";
                                    tempDescription = "Specialised in Refugees, Mafias have spread dramatically in the last few years. These ones have an enormous power in countries like Turkey and Libya. It is almost impossible to reach Europe without paying the Mafia.";
                                    tempAction = new PayMoneyAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    if (refugee[i].getMoney() - 1000 <= 0) {
                                        refugee[i].death();
                                    }
                                    else {
                                        tempAction.act(refugee[i]);
                                    }
                                    loop = false;
                                    break;
                                case 7:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Live Vest. You have an extra life if you land in the sea (Box 10). ";
                                    tempDescription = "Live Vest. You have an extra life if you land in the sea (Box 10). ";
                                    tempAction = null;
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    refugee[i].getProtection();
                                    loop = false;
                                    break;
                                case 8:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "GPS Location. Stay for a turn. ";
                                    tempDescription = "Before boarding the boats, the Mafia concentrates the refugees in specific places. The Mafia move them in groups towards the departure points.";
                                    tempAction = null;
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    loop = false;
                                    break;
                                case 9:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Boat. Pay 3000$ to the Mafia Bank. Roll dice.";
                                    tempDescription = "The average price for crossing the Mediterranean is around 3000$. Sometimes refugee families get a discount";
                                    tempAction = new PayMoneyAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction); 
                                    board.printBoard(j);
                                    if (refugee[i].getMoney() - 3000 <= 0) {
                                        refugee[i].death();
                                    }
                                    else {
                                        tempAction.act(refugee[i]);
                                    }
                                    tempAction = new RollDiceAction();
                                    tempAction.act(refugee[i]);
                                    break;
                                case 10:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Dead at Sea. You are dead and out of the game. ";
                                    tempDescription = "Over 3000 people died and around 4000 have disappeared in the Mediterranean only in 2017.";
                                    tempAction = null;
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    if (refugee[i].hasProtection() == true) {
                                        System.out.println("But thank god, you have a live vest!\n That was close... (phew)");
                                        refugee[i].useProtection();
                                    }
                                    else {
                                        refugee[i].death();
                                    }
                                    loop = false;
                                    break;
                                case 11:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "You get sick. Stay one turn.";
                                    tempDescription = "The travelling conditions on the Mafia boats are terrible, lack of drinking water, enough fuel just to reach the coast, the extremely dangerous hygiene conditions and of course the uncertainty, It is very probable to arrived to the coast sick. ";
                                    tempAction = null;
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    loop = false;
                                    break;
                                case 12:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "You reach an EU coast. Roll dice. ";
                                    tempDescription = "The lucky refugees that have made the sea journey and have reached an EU coast, start here another journey. Often some refugees are also tricked by the Mafia and land them in a beach not far from where they left. They are back in Libya or turkey.";
                                    tempAction = new RollDiceAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    break;
                                case 13:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Tent and sleeping bag. Pay 200$ to the Mafia Bank.";
                                    tempDescription = "The Mafia does not allow refugees to carry much personal items with them. Once they arrived to an EU coast they have to buy essential stuff, a tent and a sleeping bag can be sold for 300$";
                                    tempAction = new PayMoneyAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    if (refugee[i].getMoney() - 200 <= 0) {
                                        refugee[i].death();
                                    }
                                    else {
                                        tempAction.act(refugee[i]);
                                    }
                                    loop = false;
                                    break;
                                case 14:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Border Police. Stay one turn. ";
                                    tempDescription = "The border police in many countries in Europe discretionally stop refugees from crossing. Sometimes keeping them for days next to the border controls.";
                                    tempAction = null;
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    loop = false;
                                    break;
                                case 15:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Border Control 2. Back to Border Control 1.";
                                    tempDescription = "In some Border Controls in Europe, refugees are stopped and put in buses and taken to the previous country border.";
                                    tempAction = new GoToAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    loop = false;
                                    break;
                                case 16:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Refugee Camp. Pay 500$ to the Mafia bank to leave and roll dice. ";
                                    tempDescription = "The Mafia controls refugees all way through. Sometimes refugees have to pay the Mafia just to let them leave the camps.";
                                    tempAction = new PayMoneyAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    if (refugee[i].getMoney() - 500 <= 0) {
                                        refugee[i].death();
                                    }
                                    else {
                                        tempAction.act(refugee[i]);
                                    }
                                    tempAction = new RollDiceAction();
                                    tempAction.act(refugee[i]);
                                    break;
                                case 17:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Train. Roll dice. ";
                                    tempDescription = "Short distance trains are used by refugees within countries.";
                                    tempAction = new RollDiceAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    break;
                                case 18:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Red Cross Shelter. Jump to river crossing box (22).";
                                    tempDescription = "The Red Cross have a number of shelters in different parts of the European routes used by refugees. In those ones they provide of toilets, food and sometimes sleeping areas.";
                                    tempAction = new GoToAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    loop = false;
                                    break;
                                case 19:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Guard Dogs. Stay one turn. ";
                                    tempDescription = "It has been reported that in some Eastern European countries police and civilians use guard dogs to track refugees.";
                                    tempAction = null;
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    loop = false;
                                    break;
                                case 20:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "NGO Support. You receive 1000$ from NGO Bank. ";
                                    tempDescription = "Some NGOs help refugees by giving them sums of money to enable them to carry on the journey.";
                                    tempAction = new RecieveMoneyAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    loop = false;
                                    break;
                                case 21:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Theft. You lose 1500$. ";
                                    tempDescription = "Robbery is very common in refugee camps. Some refugees lose all their savings due to thieves.";
                                    tempAction = new PayMoneyAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    if (refugee[i].getMoney() - 1500 <= 0) {
                                        refugee[i].death();
                                    }
                                    else {
                                        tempAction.act(refugee[i]);
                                    }
                                    loop = false;
                                    break;
                                case 22:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "River Crossing. Roll dice and go backwards by the number on the dice. ";
                                    tempDescription = "There are many rivers to cross in order to avoid police control. Specially in winter these rivers are almost impossible to cross. Refugees sometimes spend days and weeks till the river level comes down and then can be crossed.";
                                    tempAction = new RollDiceAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    break;
                                case 23:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "NGO Lift. Jump to family reunion box (29).";
                                    tempDescription = "NGO volunteers often give free lifts to refugees in private cars. Hidden them in the boot or covered with blankets.";
                                    tempAction = new GoToAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    loop = false;
                                    break;
                                case 24:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Border Police. Stay one turn. ";
                                    tempDescription = "The border police in many countries in Europe discretionally stop refugees from crossing. Sometimes keeping then for days next to the border controls.";
                                    tempAction = null;
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    loop = false;
                                    break;
                                case 25:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Border Control 3. Back to border 2 (box 15).";
                                    tempDescription = "In some Border Controls in Europe, refugees are stopped and put in buses and taken to the previous country border.";
                                    tempAction = new  GoToAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    loop = false;
                                    break;
                                case 26:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Asylum Paperwork. Pay 1000$ to Mafia Bank. Option A: Pay $1500 to Mafia Bank and roll dice. Option B: Don’t pay and stay 2 turns.";
                                    tempDescription = "Some refugees are pressurised by the Mafia to let them deal with the Asylum Seeker Paperwork, obviously under a payment that sometimes can reach big sums of money. In many of the cases these documents never appear or get lost.";
                                    tempAction = null;
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    option = scanner.next();
                                    if (option.compareTo("A")==0 || option.compareTo("a")==0) {
                                        tempAction = new PayMoneyAction();
                                        if (refugee[i].getMoney() - 1500 <= 0) {
                                            refugee[i].death();
                                        }
                                        else {
                                            tempAction.act(refugee[i]);
                                            tempAction = new RollDiceAction();
                                            tempAction.act(refugee[i]);
                                        }
                                    }
                                    else {
                                        refugee[i].setStay(turn+2);
                                        loop = false;
                                    }
                                    break;
                                case 27:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Storm. Stay one turn.";
                                    tempDescription = "Weather conditions in central Europe can be very extreme in winter. Often refugees get stuck for days and weeks in the middle of nowhere.";
                                    tempAction = null;
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    loop = false;
                                    break;
                                case 28:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "UNHCR Aid. Roll dice. ";
                                    tempDescription = "The United Nations High Commissioner for Refugees provides support in different parts of the refugee journey.";
                                    tempAction = new RollDiceAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    break;
                                case 29:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Family Reunion. Jump to bus box (31)";
                                    tempDescription = "Most of refugees families can’t travel together, due to money or due to complexity of moving in groups. Some refugee families reunite in different parts of the journey. Sometimes parents send their children ahead alone as it is more probable for a child to get asylum.";
                                    tempAction = new GoToAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    loop = false;
                                    break;
                                case 30:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Right Wing Militia. Back to Border police box (24).";
                                    tempDescription = "Fascist Militias have spread dramatically along many country borders in Europe. These people act in Paramilitary way, uniformed and sometimes with real arms, beating, raping and torturing refugees.";
                                    tempAction = new GoToAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    loop = false;
                                    break;
                                case 31:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Mafia Bus. Pay 800$ to Mafia Bank. Roll dice";
                                    tempDescription = "Mafia use buses to move long number of refugees from border to border. Due to stronger control of border policy, buses have been replaced by trucks or vans for this purpose. A long number of refugees have been found dead inside of abandoned lorries in car parks, petrol stations or even next to border controls by Mafia drivers.";
                                    tempAction = new PayMoneyAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    if (refugee[i].getMoney() - 800 <= 0) {
                                        refugee[i].death();
                                    }
                                    else {
                                        tempAction.act(refugee[i]);
                                        tempAction = new RollDiceAction();
                                        tempAction.act(refugee[i]);
                                    }
                                    break;
                                case 32:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Government Detention Camp. Stay one turn. ";
                                    tempDescription = "All European countries have Detention Camps for illegal immigrants. Immigration detention refers to the government practice of detaining asylum seekers and other migrants for administrative purposes, typically to establish their identities, or to facilitate their immigration claims resolution and/or their removals. It is an administrative process rather than a criminal procedure. Detention time defers from country to country, also does the conditions inside of those ones in each country.";
                                    tempAction = null;
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    loop = false;
                                    break;
                                case 33:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Asylum Seeker Application rejected. Back to Train box (17) and Roll dice.";
                                    tempDescription = "Decisions often depend on whether the case owner and the person’s account to be believable. There are a number of ways that an applicant’s credibility may be damaged, for example, if they have given inaccurate or inconsistent information. Case owners must also take the applicant’s behaviour into account.";
                                    tempAction = new GoToAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    tempAction = new RollDiceAction();
                                    tempAction.act(refugee[i]);
                                    break;
                                case 34:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Border Police. Stay one turn.";
                                    tempDescription = "The border police in many countries in Europe discretionally stop refugees from crossing. Sometimes keeping then for days next to the border controls.";
                                    tempAction = null;
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    loop = false;
                                    break;
                                case 35:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Border Control 4. Back to Border 3 (box 25).";
                                    tempDescription = "In some Border Controls in Europe, refugees are stopped and put in buses and taken to the previous country border.";
                                    tempAction = new GoToAction(); 
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    loop = false;
                                    break;
                                case 36:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Asylum Seeker Application Approved. You win. ";
                                    tempDescription = "In The United Kingdom if the claim is allowed on the grounds of the 1951 Geneva Convention, either by the UK Border Agency or at appeal, the applicant gets Refugee Status, which lasts for five years. The UK Border Agency can review this grant of status during this time if there is good reason, such as if the circumstances in the country of origin have changed. After five years, if it is still unsafe for the person to return to their own country, they will be able to apply for a legal status known as Indefinite Leave to Remain (ILR) in the UK.";
                                    tempAction = null;
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    System.out.println("Conngratulations " + refugee[i].getName() + "!!!");
                                    System.exit(0);
                                    loop = false;
                                    break;
                                case 37:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Mafia. Pay 1000$ to Mafia Bank.";
                                    tempDescription = "Specialised Refugee Ma as have spread dramatically in the last few years. These ones have an enormous power in countries like Turkey, Afghanistan or Libya. It is almost impossible to reach Europe without paying the Mafia.";
                                    tempAction = new PayMoneyAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    if (refugee[i].getMoney() - 1000 <= 0) {
                                        refugee[i].death();
                                    }
                                    else {
                                        tempAction.act(refugee[i]);
                                    }
                                    loop = false;
                                    break;
                                case 38:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "Deported. You are sent back to war box (0). ";
                                    tempDescription = "Deportation, legally speaking (UK) is the enforced removal of someone “for the public good”, usually after serving a criminal sentence in the UK. Removals and deportations are usually carried out either on a commercial airline (one person being removed/deported, usually escorted by security guards, and the other passengers are the public travelling for holiday or business) or by private charter flight (usually lots of people being removed/deported to the same country at the same time).";
                                    tempAction = new GoToAction();
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    tempAction.act(refugee[i]);
                                    loop = false;
                                    break;
                                case 39:
                                    tempNumber = refugee[i].getSquare();
                                    tempText = "New Home. You are finally accepted. You win. ";
                                    tempDescription = "According to the European Parliament in 2015 and 2016 alone, more than 2.5 million people applied for asylum in the EU. Authorities in the member states issued 593,000 first instance asylum decisions in 2015 - over half of them positive. Most people who applied for protection at the height of the refugee crisis in 2015 had to wait until 2016 to receive their ruling. That year 1.1 million asylum decisions were made. 61% of those were positive with one third of applicants granted refugee status, the highest level of international protection.";
                                    tempAction = null;
                                    board.setSquare(tempNumber, tempText, tempDescription, tempAction);
                                    board.printBoard(j);
                                    System.out.println("Conngratulations " + refugee[i].getName() + "!!!");
                                    System.exit(0);
                                    loop = false;
                                    break;
                                default: 
                                    break;
                            }
                        j++;
                        }
                    }
                }
            }
            
        }while (!false);
    }
}
