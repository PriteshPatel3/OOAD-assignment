import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ButtonHandler implements EventHandler<ActionEvent> 
{
    @Override
    public void handle(ActionEvent e)
    {
        //System.out.println(e.getSource());
        int x = BoardFX.getX(e.getSource());
        int y = BoardFX.getY(e.getSource());
        System.out.println("X: " + x + " Y: "+ y);
        
    }
}