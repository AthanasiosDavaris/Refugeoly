package refugeoly;


public class RecieveMoneyAction extends Action {
    
    @Override
    public void act(Refugee refugee){
        if (refugee.getSquare()==20){
            refugee.receiveMoney(1000);
        }
    }
}
