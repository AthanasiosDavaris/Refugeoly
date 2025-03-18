package refugeoly;

import java.util.*;

public class RollDiceAction extends Action {
    Scanner input = new Scanner(System.in);
    static int result;
    public int dice;
    private int end;
    private int move;
        
    public int roll() {
        result = (int) (Math.random() * 6) + 1;
        
        System.out.println("It's a " + result + "!!!");
        
        return result;
    }
    
    @Override
    public void act(Refugee refugee) {
        System.out.println("Rolling Dice...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        dice = roll();
        
        System.out.println("moving " + result + " Squares..." );
        try {
            Thread.sleep(1000*result);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        if (refugee.getSquare() + result > 39) {
            end = 2*39 -result -refugee.getSquare();
            move = refugee.getSquare() - end;
            if (move >=0) {
                refugee.moveSquare(move);
            }
            else {
                refugee.goBackwards(move);
            }
        }
        else {
            if (refugee.getSquare() == 22) {
                refugee.goBackwards(result);
            }
            else {
                refugee.moveSquare(result);
            }
        }
    }
    
    public int getResult() {
        return result;
    }
    
    
}
