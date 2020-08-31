import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ButtonHandler implements EventHandler<ActionEvent> 
{
    @Override
    public void handle(ActionEvent e)
    {
        System.out.println("Button Dipresskan");
        BoardFX.barrelRoll();
    }
}