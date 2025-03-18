
package refugeoly;


public class GoToAction extends Action
{
    
    @Override
     public void act(Refugee refugee)
    {
         switch (refugee.getSquare()) {
             case 4 -> refugee.moveTo(0);
             case 5 -> refugee.moveTo(0);
             case 15 -> refugee.moveTo(5);
             case 18 -> refugee.moveTo(22);
             case 23 -> refugee.moveTo(29);
             case 25 -> refugee.moveTo(15);
             case 29 -> refugee.moveTo(31);
             case 30 -> refugee.moveTo(24);
             case 33 -> refugee.moveTo(17);
             case 35 -> refugee.moveTo(25);
             case 38 -> refugee.moveTo(0);
             default -> {
             }
         }
        
    }
    
    
    
    
    
    
    
    
    
    
}
