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
            //get destination and source coordinates
            String destinationCord = cordStack.pop();
            String sourceCord = cordStack.pop();
            //checks to see that the user does not press the same button and the source isnt an empty button
            if( !(destinationCord.equals(sourceCord)) && (Character.compare(pMap.get(sourceCord).getName() ,'\0') != 0) )
            {
                //clear stack
                //cordStack.clear();
                if(checkPiece(sourceCord, destinationCord) == !false)
                    move(destinationCord, sourceCord);
            }
        }

        System.out.println("X: " + x + " Y: "+ y);
        System.out.println("Name:" + pMap.get(sb.toString()).getName());
        System.out.println(cordStack.size());
    }

    private void move (String destinationCord, String sourceCord)
    {
        //get team name and piece name
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

    private boolean checkPiece(String sourceCord, String destinationCord)
    {
        //seperate string to int x y
        int xDes = Integer.parseInt(String.valueOf(destinationCord.charAt(0)));
        int yDes = Integer.parseInt(String.valueOf(destinationCord.charAt(1)));
        int xSour = Integer.parseInt(String.valueOf(sourceCord.charAt(0)));
        int ySour = Integer.parseInt(String.valueOf(sourceCord.charAt(1)));
        char name = pMap.get(sourceCord).getName();
        if(Character.compare(pMap.get(sourceCord).getTeam(),pMap.get(destinationCord).getTeam()) != 0)
        {  
            switch(name)
            {
                case 'p':
                    movePlus(xDes,yDes,xSour,ySour);
                    return true;
                    //break;
                case 't':
                    moveTri(xDes,yDes,xSour,ySour);
                    return true;
                    //break;
                case 'c':
                    moveChev(xDes,yDes,xSour,ySour);
                    return true;
                    //break;
                case 's':
                    moveSun(xDes,yDes,xSour,ySour);
                    return true;
                    //break;
                case 'a':
                    moveAr(xDes,yDes,xSour,ySour);
                    return true;
                    //break;
                default:
                    return false;
                    //break;

            }
        }
    }

    private void moveAr (int xDes, int yDes, int xSour, int ySour)
    {
        if ((xSour - xDes == 0) && ((ySour - yDes == 1) || (ySour - yDes == 2))){}
            //legal
        else{}
            //illegal
    }

    private void moveSun (int xDes, int yDes, int xSour, int ySour)
    {
        if ((yDes == ySour) && (xDes == xSour))
        {
            //System.out.println("No!");
        }
        else if ((Math.abs(yDes-ySour) <= 1) || (Math.abs(xDes-xSour) <= 1))
        {
            //System.out.println("Sure!");
        }
    }

    private void movePlus (int xDes, int yDes, int xSour, int ySour)
    {
        if ((((Math.abs(yDes-ySour) != 0) && (Math.abs(xDes-xSour) == 0)) || ((Math.abs(yDes-ySour) == 0)) && (Math.abs(xDes-xSour) != 0)))
        {
            //System.out.println("This move is allowed");
        }
        else
        {
            //System.out.println("This move is not allowed");
        }
    }

    private boolean moveChev(int x1, int y1, int x2, int y2)
    {
        int diffX = Math.abs(x2-x1);
        int diffY = Math.abs(y2-y1);

        
        if (((diffX == 1) && (diffY == 2)) || ((diffX == 2)&&(diffY == 1)))
        {
            return true;
        }

        else 
        {
            return false;
        }
    }
    
    public void moveTri (int xDes, int yDes, int xSour, int ySour)
    {
        if ((Math.abs(yDes-ySour) == Math.abs(xDes-xSour)) && (Math.abs(yDes-ySour) != 0) && (Math.abs(xDes-xSour) != 0))
        {
            //System.out.println("This move is allowed");
        }
        else
        {
            //System.out.println("This move is not allowed");
        }
    }
        

        // gets the piece image based on the piece name and team
    private ImageView nameToImage (char name, char team)
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