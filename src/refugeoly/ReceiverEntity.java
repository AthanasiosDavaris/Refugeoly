package refugeoly;


public class ReceiverEntity implements MoneyReceiver {
    public String name;
    public int money = 0;
    
    public String getName () {
        return name;
    }
    
    public void setName(String s) {
        name = s;    
    }
    
    public int getMoney() {
        return money;
    }
    
    public void setMoney(int money) {
        this.money = money;
    }
    
    public void receiveMoney(int m) {
        money=money+m;
    }
}
