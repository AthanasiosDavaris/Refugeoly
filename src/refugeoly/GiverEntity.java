package refugeoly;


public class GiverEntity implements MoneyGiver {
    
    public String name;
    public int money=10000;
    
    public void setName(String s) {
        name = s;
    }
    
    public int getMoney(){
        return money;
    }
    
    public void setMoney (int money) {
        this.money = money;
    }
    
    public void giveMoney(int money)throws NoMoneyException
    {
        try {
            if(money<=this.money)
            {
                this.money=this.money-money;
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
}

