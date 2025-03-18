package refugeoly;

import java.util.logging.Level;
import java.util.logging.Logger;


public class PayMoneyAction extends Action {
    
    @Override
    public void act(Refugee refugee) {
        switch (refugee.getSquare()) {
            case 1:
                try {
                    refugee.giveMoney(100);
                } catch (NoMoneyException ex) {
                    Logger.getLogger(PayMoneyAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 3:
                try {
                    refugee.giveMoney(300);
                } catch (NoMoneyException ex) {
                    Logger.getLogger(PayMoneyAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 6:
                try {
                    refugee.giveMoney(1000);
                } catch (NoMoneyException ex) {
                    Logger.getLogger(PayMoneyAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 9:
                try {
                    refugee.giveMoney(3000);
                } catch (NoMoneyException ex) {
                    Logger.getLogger(PayMoneyAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 13:
                try {
                    refugee.giveMoney(200);
                } catch (NoMoneyException ex) {
                    Logger.getLogger(PayMoneyAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 16:
                try {
                    refugee.giveMoney(500);
                } catch (NoMoneyException ex) {
                    Logger.getLogger(PayMoneyAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            case 21:
                try {
                    refugee.giveMoney(1500);
                } catch (NoMoneyException ex) {
                    Logger.getLogger(PayMoneyAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 26:
                try {
                    refugee.giveMoney(1500);
                } catch (NoMoneyException ex) {
                    Logger.getLogger(PayMoneyAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 31:
                try {
                    refugee.giveMoney(800);
                } catch (NoMoneyException ex) {
                    Logger.getLogger(PayMoneyAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 37:
                try {
                    refugee.giveMoney(1000);
                } catch (NoMoneyException ex) {
                    Logger.getLogger(PayMoneyAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                break;
        }
    }
}
   

