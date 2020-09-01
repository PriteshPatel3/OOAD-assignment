import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.*;
import javafx.scene.image.*;
import javafx.scene.control.*;

public class ButtonHandler extends MainGame implements EventHandler<ActionEvent> 
{
    @Override
    public void handle(ActionEvent e)
    {
        StringBuilder sb = new StringBuilder();
        int x = BoardFX.getX(e.getSource());
        int y = BoardFX.getY(e.getSource());
        sb.append(x);
        sb.append(y);
        
        cordStack.push(sb.toString());


        if (cordStack.size() == 2)
        {
            //Move Function
            move(cordStack);
            cordStack.clear();
        }

        System.out.println("X: " + x + " Y: "+ y);
        System.out.println("Name:" + pMap.get(sb.toString()).getName());
        System.out.println(cordStack.size());
    }

    public void move (Stack<String> SStack)
    {
        String destinationCord = SStack.pop();
        String sourceCord = SStack.pop();

        char sourceName = pMap.get(sourceCord).getName();
        char sourceTeam = pMap.get(sourceCord).getTeam();

        ImageView icon = nameToImage(sourceName,sourceTeam);

        //sets source button as clear
        pMap.get(sourceCord).getButton().setGraphic(new ImageView("ChessPiece/empty.png"));
        pMap.get(sourceCord).setName('\0');
        pMap.get(sourceCord).setTeam('\0');

        //sets destination icon
        pMap.get(destinationCord).getButton().setGraphic(icon);
        pMap.get(destinationCord).setName(sourceName);
        pMap.get(destinationCord).setTeam(sourceTeam);;
    }

    // gets the piece image based on the piece name and team
    public ImageView nameToImage (char name, char team)
    {
        ImageView icon;
        switch(name)
        {
            case 'p':
                if (Character.compare(team,'b') == 0)
                {
                    icon = new ImageView("ChessPiece/addB.png");
                }

                else 
                {
                    icon = new ImageView("ChessPiece/addR.png");
                }

                break;

            case 't':
                if (Character.compare(team,'b') == 0)
                {
                    icon = new ImageView("ChessPiece/triangleB.png");
                }

                else
                {
                    icon = new ImageView("ChessPiece/triangleR.png");
                }
                break;

            case 'c':
                if (Character.compare(team,'b') == 0)
                {
                    icon = new ImageView("ChessPiece/chevronB.png");
                }

                else
                {
                    icon = new ImageView("ChessPiece/chevronR.png");
                }
                break;

            case 's':
                if (Character.compare(team,'b') == 0)
                {
                    icon = new ImageView("ChessPiece/sunB.png");
                }

                else
                {
                    icon = new ImageView("ChessPiece/sunR.png");
                }
                break;

            case 'a':
                if (Character.compare(team,'b') == 0)
                {
                    icon = new ImageView("ChessPiece/arrowB.png");
                }

                else
                {
                    icon = new ImageView("ChessPiece/arrowR.png");
                }
                break;

            default:
                icon = new ImageView("ChessPiece/empty.png");
        }

        return icon;
    }
}