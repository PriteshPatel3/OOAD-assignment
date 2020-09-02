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

        pMap.get(sourceCord).chgTeamMoves(sourceTeam); //Add Team Moves
        switchPieces(sourceTeam, pMap.get(sourceCord).getTeamMoves(sourceTeam)); //Check if Enough Moves to switch Pieces for a team

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
        

    //gets 
    public void switchPieces(char team, int moves)
    {
        System.out.println("Current Team :" + team);
        if (moves % 2 == 0)
        {
            for(Map.Entry<String, Piece> gridEntry : pMap.entrySet())
            {
                Piece tempPiece = gridEntry.getValue();
                System.out.println("Team Name :" + tempPiece.getTeam());
                switch (tempPiece.getName())
                {
                    //Dis wont work
                    case 't':
                        if (tempPiece.getTeam() == team && tempPiece.getTeam() == 'r')
                        {
                            tempPiece.getButton().setGraphic(new ImageView("ChessPiece/addR.png"));
                            tempPiece.setName('p');
                            System.out.println("Red Triangle Changed");
                        }
                            
                        else if (tempPiece.getTeam() == team && tempPiece.getTeam() == 'b')
                        {
                            tempPiece.getButton().setGraphic(new ImageView("ChessPiece/addB.png"));
                            tempPiece.setName('p');
                            System.out.println("Blue Triangle Changed");
                        }                
                        break;
                    case 'p':
                        if (tempPiece.getTeam() == team && tempPiece.getTeam() == 'r')
                        {
                            tempPiece.getButton().setGraphic(new ImageView("ChessPiece/triangleR.png"));
                            tempPiece.setName('t');
                            System.out.println("Red Plus Changed");
                        }
                        else if (tempPiece.getTeam() == team && tempPiece.getTeam() == 'b')
                        {
                            tempPiece.getButton().setGraphic(new ImageView("ChessPiece/triangleB.png")); 
                            tempPiece.setName('t');
                            System.out.println("Blue Triangle Changed");
                        } 
                        break;
                }
            }
        }
        else
        {
            System.out.println("Now is not the time to switch Pieces boi!");
        }
    }
}