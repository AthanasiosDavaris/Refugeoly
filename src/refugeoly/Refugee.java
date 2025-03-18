package refugeoly;

public class Refugee extends GiverEntity {
    
    public String name;
    
    int board=1; 
    
    int square = 0;
    
    int expenses = 0;
    
    boolean alive = true;
    
    boolean protection = false;
    
    int stay = -1;
    
    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void moveTo(int square) {
        this.square=square;
    }
    
    public int getSquare()
    {
        return square;
    }
    
    public void receiveMoney(int money) {
        this.money = this.money + money;
    }
    
    @Override
    public void giveMoney(int cost) throws NoMoneyException {
        try {
            if(cost<=this.money)
            {
                this.money=this.money-cost;
                expenses = expenses + cost;
            }
            else
            {
                throw new NoMoneyException(name+"ran out of money");
            }
        }
        catch (NoMoneyException ex)
        {
            System.out.println(ex);
        }
    }
    
    public int getExpenses() {
        return expenses;
    }
    
    public boolean isAlive() {
        return alive;
    }
    
    public boolean hasProtection() {
        return protection;
    }
    
    public void moveSquare(int square) {
        this.square = this.square +  square;
    }
    
    public void death() {
        System.out.println(getName() + " has been eliminated!!!");
        System.out.println("R.I.P.");
        this.alive = false;
        setMoney(0);
    }
    
    public void useProtection() {
        this.protection = false;
    }
    
    public void goBackwards(int square) {
        this.square =-  square;
    }
    
    public int getStay() {
        return stay;
    }
    public void setStay(int turns) {
        this.stay = turns;
    }
    
    @Override
    public void setMoney (int money) {
        super.setMoney(money);
    }
    
    public void getProtection() {
        this.protection = true;
    }
}
