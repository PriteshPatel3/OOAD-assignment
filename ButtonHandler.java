import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ButtonHandler extends MainGame implements EventHandler<ActionEvent> 
{
    @Override
    public void handle(ActionEvent e)
    {
        StringBuilder sb = new StringBuilder();
        int x = board.getColumnIndex((Button)e.getSource());
        int y = board.getRowIndex((Button)e.getSource());
        sb.append(x);
        sb.append(y);
        
        cordStack.push(sb.toString());


        if (butCord.size() == 2)
        {
            //Move Function

        }

        System.out.println("X: " + x + " Y: "+ y);
        System.out.println("Name:" + pMap.get(sb.toString()).getName());
    }

    public void move (Stack<String> SStack)
    {
        String destinationCord = pMap.get(SStack.pop());
        String sourceCord = pMap.get(SStack.pop());

        //char sourcePiece = pMap.get(sourceCord).get
    }
}