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
                if(checkTurn(sourceCord))
                {
                    if(checkPiece(sourceCord, destinationCord) == true)
                    {
                        move(destinationCord, sourceCord);
                        switchPieces(sourceCord);
                    }
                }
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

        //ImageView icon = nameToImage(sourceName,sourceTeam);

        //sets destination icon
        pMap.get(destinationCord).getButton().setGraphic(pMap.get(sourceCord).getButton().getGraphic());
        pMap.get(destinationCord).setName(sourceName);
        pMap.get(destinationCord).setTeam(sourceTeam);;

        //sets source button as clear
        pMap.get(sourceCord).getButton().setGraphic(new ImageView("ChessPiece/empty.png"));
        pMap.get(sourceCord).setName('\0');
        pMap.get(sourceCord).setTeam('\0');
    }

    private boolean checkTurn(String sourceCord)
    {
        int turn = pMap.get(sourceCord).getTurn();
        if (turn % 2 == 0)
        {
            if (Character.compare(pMap.get(sourceCord).getTeam(),'r') == 0)
            {   
                pMap.get(sourceCord).increaseTurn();
                BoardFX.getBoard().setRotate(180);
                return true;
            }
            else
                return false;
        }
        else
        {
            if (Character.compare(pMap.get(sourceCord).getTeam(),'b') == 0)
            {
                pMap.get(sourceCord).increaseTurn();
                BoardFX.getBoard().setRotate(0);
                return true;
            }
            else
                return false;
        }
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
                    if(checkObstaclesPlus(xDes,yDes,xSour,ySour)) //Check for any obstacles
                    {
                        return movePlus(xDes,yDes,xSour,ySour);
                    }
                    else
                    {
                        return false;
                    }
                    
                    //break;
                case 't':
                    return moveTri(xDes,yDes,xSour,ySour);
                    //break;
                case 'c':
                    return moveChev(xDes,yDes,xSour,ySour);
                    //return true;
                    //break;
                case 's':
                    return moveSun(xDes,yDes,xSour,ySour);
                    //return true;
                    //break;
                case 'a':
                    return moveAr(xDes,yDes,xSour,ySour,sourceCord,destinationCord);
                    //return true;
                    //break;
                default:
                    return false;
                    //break;
            }
        }
        else
        {
            return false;
        }
    }

    private boolean moveAr (int xDes, int yDes, int xSour, int ySour, String sourceCord, String destinationCord)
    {

        if (Character.compare(pMap.get(sourceCord).getTeam(),'b') == 0)
        {
            if ((Math.abs(ySour - yDes) == 0))
            {
                if (((xSour - xDes) == -1 || (xSour - xDes) == -2) && pMap.get(sourceCord).getButton().getGraphic().getRotate() == 0)
                {
                    if(xDes == 7 || xDes == 0)
                        {
                            if(pMap.get(sourceCord).getButton().getGraphic().getRotate() == 0)
                                pMap.get(sourceCord).getButton().getGraphic().setRotate(180); 
                        }
                    return true;
                }
                else if (((xSour - xDes) == 1 || (xSour - xDes) == 2) && pMap.get(sourceCord).getButton().getGraphic().getRotate() == 180)
                {
                    if(xDes == 7 || xDes == 0)
                        {
                            if(pMap.get(sourceCord).getButton().getGraphic().getRotate() == 180)
                                pMap.get(sourceCord).getButton().getGraphic().setRotate(0); 
                        }
                    return true;
                }
                else
                    return false;
            }
            else
            {
                return false;
            }
        }

        else if (Character.compare(pMap.get(sourceCord).getTeam(),'r') == 0)
        {
            if ((Math.abs(ySour - yDes) == 0))
            {
                if (((xSour - xDes) == 1 || (xSour - xDes) == 2) && pMap.get(sourceCord).getButton().getGraphic().getRotate() == 0)
                {
                    if(xDes == 7 || xDes == 0)
                    {
                        if(pMap.get(sourceCord).getButton().getGraphic().getRotate() == 0)
                            pMap.get(sourceCord).getButton().getGraphic().setRotate(180); 
                    }
                    return true;
                }
                else if (((xSour - xDes) == -1 || (xSour - xDes) == -2) && pMap.get(sourceCord).getButton().getGraphic().getRotate() == 180)
                {
                    if(xDes == 7 || xDes == 0)
                    {
                        if(pMap.get(sourceCord).getButton().getGraphic().getRotate() == 180)
                            pMap.get(sourceCord).getButton().getGraphic().setRotate(0); 
                    }
                    return true;
                }
                    
                else
                    return false;
            }
            else
            {
                return false;
            }
        }

        else
            return false;
    }

    private boolean moveSun (int xDes, int yDes, int xSour, int ySour)
    {
        if ((Math.abs(yDes-ySour) <= 1) && (Math.abs(xDes-xSour) <= 1))
        {
            return true;
        }
        else 
        {
            return false;
        }
    }

    private boolean movePlus (int xDes, int yDes, int xSour, int ySour)
    {
        if ((((Math.abs(yDes-ySour) != 0) && (Math.abs(xDes-xSour) == 0)) || ((Math.abs(yDes-ySour) == 0)) && (Math.abs(xDes-xSour) != 0)))
        {
            return true;
        }
        else
        {
            return false;
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
    
    public boolean moveTri (int xDes, int yDes, int xSour, int ySour)
    {
        if ((Math.abs(yDes-ySour) == Math.abs(xDes-xSour)) && (Math.abs(yDes-ySour) != 0) && (Math.abs(xDes-xSour) != 0))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void switchPieces(String sourceCord)
    {
        int turn = pMap.get(sourceCord).getTurn();
        //int turn = pMap.get(sourceCord).getTurn();
        //System.out.println("Current Team :" + team);
        if (turn % 4 == 0 ) //For Red Teams
        {
            for(Map.Entry<String, Piece> gridEntry : pMap.entrySet())
            {
                Piece tempPiece = gridEntry.getValue();
                //System.out.println("Team Name :" + tempPiece.getTeam());
                switch (tempPiece.getName())
                {
                    case 't':
                        if(Character.compare(tempPiece.getTeam(),'r') == 0)
                        {
                            tempPiece.getButton().setGraphic(new ImageView("ChessPiece/addR.png"));
                            tempPiece.setName('p');   
                        }         
                        break;
                    case 'p':
                        if(Character.compare(tempPiece.getTeam(),'r') == 0)
                        {
                            tempPiece.getButton().setGraphic(new ImageView("ChessPiece/triangleR.png"));
                            tempPiece.setName('t');
                        }
                        break;
                    default:
                        break;
                        //return false;
                }
            }
        }
        else if (turn % 4 == 1 && turn != 1) //For Blue Teams
        {
            for(Map.Entry<String, Piece> gridEntry : pMap.entrySet())
            {
                Piece tempPiece = gridEntry.getValue();
                //System.out.println("Team Name :" + tempPiece.getTeam());
                switch (tempPiece.getName())
                {
                    case 't':
                        if(Character.compare(tempPiece.getTeam(),'b') == 0)
                        {
                            tempPiece.getButton().setGraphic(new ImageView("ChessPiece/addB.png"));
                            tempPiece.setName('p');   
                        } 
                            //return true;            
                        break;
                    case 'p':
                        if(Character.compare(tempPiece.getTeam(),'b') == 0)
                        {
                            tempPiece.getButton().setGraphic(new ImageView("ChessPiece/triangleB.png"));
                            tempPiece.setName('t');
                        }
                        //return true;
                        //System.out.println("Red Plus Changed");
                        break;
                    default:
                        break;
                        //return false;
                }
            }
        }
            //return false;
    }

    public boolean checkObstaclesPlus(int xDes, int yDes, int xSour, int ySour)
    {
        int tempCoordX, tempCoordY;
        for(Map.Entry<String, Piece> gridEntry : pMap.entrySet())
        {
            String tempCoord = gridEntry.getKey();
            tempCoordX = Integer.parseInt(String.valueOf(tempCoord.charAt(0)));
            tempCoordY = Integer.parseInt(String.valueOf(tempCoord.charAt(1)));

            if (yDes == ySour && yDes == tempCoordY && (xDes < xSour)) //Moving Vertically at X Axis in a decreasing movement, i.e (from X7 -> X5)
            {
                
                for (int i = (xDes + 1); i < xSour; i++)
                {
                    System.out.println("It manages to go in");
                    StringBuilder sb = new StringBuilder(); // i use to append i j so i can make it as key
                    sb.append(yDes);
                    sb.append(i);
                    String coord = sb.toString(); // yDes + j in string
                    Piece tempPiece = pMap.get(coord);
                    System.out.println(tempPiece.getName() + " " + coord);

                    if (Character.compare(tempPiece.getName(),'\0') != 0) 
                    {
                        System.out.println("Theres an obstacle in the way");
                        return false;
                    }
                }
            }
            else if (yDes == ySour && yDes == tempCoordY && (xDes > xSour)) //Moving Vertically at X Axis in a increasing movement, i.e (from X5 -> X7)
            {
                for (int i = (xDes - 1); i >= xSour; i--)
                {
                    System.out.println("It manages to go in");
                    StringBuilder sb = new StringBuilder(); // to combine two ints as a key
                    sb.append(yDes);
                    sb.append(i);
                    String coord = sb.toString(); // yDes + j in string
                    Piece tempPiece = pMap.get(coord);
                    System.out.println(tempPiece.getName() + " " + coord);

                    if (Character.compare(tempPiece.getName(),'\0') != 0) 
                    {
                        System.out.println("Theres an obstacle in the way");
                        return false;
                    }
                }
            }
            else
            {
                System.out.println("This logic is not implemented yet");
            }
        }
        return true;
    }
}

class MenuHandler extends ButtonHandler
{
    @Override
    public void handle(ActionEvent e)
    {
        MenuItem menu = (MenuItem)e.getSource();
        System.out.println(menu.getId());
    }
}