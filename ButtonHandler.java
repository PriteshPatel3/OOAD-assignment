import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.*;
import java.io.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import java.time.*;
import java.time.format.*;
import javafx.stage.*;

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
                //if(checkTurn(sourceCord))
                //{
                    if(checkPiece(sourceCord, destinationCord) == true)
                    {
                        if(checkTurn(sourceCord))
                        {
                            move(destinationCord, sourceCord);
                            switchPieces(sourceCord);
							gameOver();
                        }
                    }
                //}
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
        pMap.get(destinationCord).setTeam(sourceTeam);

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
                    //return movePlus(xDes,yDes,xSour,ySour);
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
                    if(checkObstaclesAr(xDes,yDes,xSour,ySour))
                    {
                        return moveAr(xDes,yDes,xSour,ySour,sourceCord,destinationCord);
                    }
                    else
                    {
                        return false;
                    } 
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
	
	public void gameOver ()
	{
		boolean win = false;
		int sun = 0 ;
		char team = '\0';
		
		for (Map.Entry<String, Piece> gridEntry : pMap.entrySet())
		{
			
			if (Character.compare(gridEntry.getValue().getName(), 's') == 0)
			{
				sun++;
				team = gridEntry.getValue().getTeam();
			}
		}
		if (sun ==1)
		{
			win = true;
			MainGame.popUp(team);
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
        if (turn % 4 == 0 )
        {
            for(Map.Entry<String, Piece> gridEntry : pMap.entrySet())
            {
                Piece tempPiece = gridEntry.getValue();
                //System.out.println("Team Name :" + tempPiece.getTeam());
                switch (tempPiece.getName())
                {
                    //Dis wont work
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
        else if (turn % 4 == 1 && turn != 1)
        {
            for(Map.Entry<String, Piece> gridEntry : pMap.entrySet())
            {
                Piece tempPiece = gridEntry.getValue();
                //System.out.println("Team Name :" + tempPiece.getTeam());
                switch (tempPiece.getName())
                {
                    //Dis wont work
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
        StringBuilder sb = new StringBuilder();
        if (yDes == ySour && (xDes < xSour)) //Moving Vertically at Y Axis in a decreasing movement, i.e (from X7 -> X5)
        {   
            for (int i = xDes + 1; i < xSour; i++)
            {
                //Reset Size of String
                sb.setLength(0);
                sb.trimToSize();

                sb.append(i);
                sb.append(yDes);
                
                String coord = sb.toString(); // i(x coordinate) + yDes in string
                Piece tempPiece = pMap.get(coord);
                System.out.println(i);
                //System.out.println(tempPiece.getName() + " " + coord);
                if (Character.compare(tempPiece.getName(),'\0') != 0) //Checks if piece exist
                {
                    System.out.println("There's an obstacle in the way of X1");
                    return false;
                }
            }
        }
        else if (yDes == ySour && (xDes > xSour)) //Move Vertical atbY Axis in increasing direction
        {
            for (int i = xDes - 1; i > xSour; i--) 
            {
                //Reset Size of String
                sb.setLength(0);
                sb.trimToSize();

                sb.append(i);
                sb.append(yDes);
                
                String coord = sb.toString(); // i(x coordinate) + yDes in string
                Piece tempPiece = pMap.get(coord);
                System.out.println(i);
                //System.out.println(tempPiece.getName() + " " + coord);
                if (Character.compare(tempPiece.getName(),'\0') != 0) 
                {
                    System.out.println("There's an obstacle in the way of X2");
                    return false;
                }
            }
        }
        else if (xDes == xSour && (yDes < ySour)) //Move Vertical at X Axis in increasing direction
        {
            for (int i = yDes + 1; i < ySour; i++) 
            {
                //Reset Size of String
                sb.setLength(0);
                sb.trimToSize();

                sb.append(xDes);
                sb.append(i);
                
                String coord = sb.toString(); // i(x coordinate) + yDes in string
                Piece tempPiece = pMap.get(coord);
                System.out.println(i);
                //System.out.println(tempPiece.getName() + " " + coord);
                if (Character.compare(tempPiece.getName(),'\0') != 0) 
                {
                    System.out.println("There's an obstacle in the way of Y1");
                    return false;
                }
            }
        }
        else if (xDes == xSour && (yDes > ySour)) //Move Vertical at X Axis in increasing direction
        {
            for (int i = yDes - 1; i > ySour; i--) 
            {
                //Reset Size of String
                sb.setLength(0);
                sb.trimToSize();

                sb.append(xDes);
                sb.append(i);
                
                String coord = sb.toString(); // i(x coordinate) + yDes in string
                Piece tempPiece = pMap.get(coord);
                System.out.println(i);
                //System.out.println(tempPiece.getName() + " " + coord);
                if (Character.compare(tempPiece.getName(),'\0') != 0) 
                {
                    System.out.println("There's an obstacle in the way of Y2");
                    return false;
                }
            }
        }
        else
        {
            //Reminder to change this
            System.out.println("Logic Failed, you smol pp");
        }
        return true;
    }


    public boolean checkObstaclesAr(int xDes, int yDes, int xSour, int ySour)
    {
        //int tempCoordX, tempCoordY;
        StringBuilder sb = new StringBuilder();

        if (yDes == ySour && (xDes < xSour)) //Moving Vertically at Y Axis in a decreasing movement, i.e (from X7 -> X5)
        {   
            for (int i = xDes + 1; i < xSour; i++)
            {
                //Reset Size of String
                sb.setLength(0);
                sb.trimToSize();

                sb.append(i);
                sb.append(yDes);
                
                String coord = sb.toString(); // i(x coordinate) + yDes in string
                Piece tempPiece = pMap.get(coord);
                System.out.println(i);
                //System.out.println(tempPiece.getName() + " " + coord);
                if (Character.compare(tempPiece.getName(),'\0') != 0) //Checks if piece exist
                {
                    System.out.println("There's an obstacle in the way of X1");
                    return false;
                }
            }
        }
        else if (yDes == ySour && (xDes > xSour)) //Move Vertical atbY Axis in increasing direction
        {
            for (int i = xDes - 1; i > xSour; i--) 
            {
                //Reset Size of String
                sb.setLength(0);
                sb.trimToSize();

                sb.append(i);
                sb.append(yDes);
                
                String coord = sb.toString(); // i(x coordinate) + yDes in string
                Piece tempPiece = pMap.get(coord);
                System.out.println(i);
                //System.out.println(tempPiece.getName() + " " + coord);
                if (Character.compare(tempPiece.getName(),'\0') != 0) 
                {
                    System.out.println("There's an obstacle in the way of X2");
                    return false;
                }
            }
        }
        else{
            System.out.println("Ummm can you check again what you did?");
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
        int id = Integer.parseInt(menu.getId());
        FileChooser chooser = new FileChooser();
        DirectoryChooser dChooser = new DirectoryChooser();

        switch(id)
        {
            case 1:
                try
                {
                    File saveDirectory = dChooser.showDialog(MainGame.getStage());
                    String directory = saveDirectory.getAbsolutePath();
                    StringBuilder name = new StringBuilder();

                    for (int i = 0; i < directory.length(); i++) 
                    {
                        if (directory.charAt(i) == '\\') 
                            name.append("\\");
                    
                        name.append(directory.charAt(i));
                    }
                    
                    name.append("\\\\Webale_save.txt");
                    
                    saveGame(name.toString());
                }

                catch (IOException f)
                {
                    System.out.println("File not created");
                }
                

                break;

            case 2:
                try
                {
                    File loadFile = chooser.showOpenDialog(MainGame.getStage());
                    loadGame(loadFile);
                }

                catch (IOException g)
                {
                    System.out.println("File not loaded");
                }
                

                break;

            case 3:
                break;

        }


    }

    public void saveGame(String fileName) throws IOException
    {
        
            //create file
            File savedFile = new File(fileName);

            //create file writer class
            FileWriter fw = new FileWriter(savedFile);

            //create print writer class
            PrintWriter pw = new PrintWriter(fw);

            //get date and time
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
            LocalDateTime now = LocalDateTime.now();  
            String dateTime = dtf.format(now);

            //Prints the date and time to the first line of text
            pw.println("Date/Time: " + dateTime);
            pw.println("XY: x = x Coordinate: y = y Coordinate ");
            pw.println("p = PLus: t = Triangle: c = Chevron: s = Sun: a = Arrow: * = Empty");

            for (int i = 0; i<8; i++)
            {
                pw.println(" ");
                pw.println("Row: " + i);
                for (int j = 0; j<7; j++)
                {
                    StringBuilder sb = new StringBuilder();
                    sb.append(i);
                    sb.append(j);
                    
                    //Get name
                    char name = pMap.get(sb.toString()).getName();
                    char player = pMap.get(sb.toString()).getTeam();

                    //if empty declare as *
                    if(Character.compare(name,'\0') == 0)
                    {
                        name = '*';
                        player = '*';
                    }



                    pw.println(sb.toString() + ":" + name + ":" + player);
                }
            }

            pw.close();
    }

    public void loadGame(File file) throws IOException
    {
        ArrayList<String> cordArray = new ArrayList<String>();
        ArrayList<String> nameArray = new ArrayList<String>();
        ArrayList<String> teamArray = new ArrayList<String>();

        
        BufferedReader br = new BufferedReader(new FileReader(file)); 
        
        String st; 
        
        for (int i = 0; i<4; i++)
        {
            st = br.readLine();
        }

        for (int j = 0; j<8; j++)
        {
            //skips row declaration line
            st = br.readLine();

            //gets the info for cords and pieces
            for (int k = 0; k<7; k++)
            {
                st = br.readLine();
                
                String[] arrOfStr = st.split(":", 3);

                cordArray.add(arrOfStr[0]);
                nameArray.add(arrOfStr[1]);
                teamArray.add(arrOfStr[2]);

            }
            
            //skips white space
            st = br.readLine();

        }

        for (int r = 0; r<56; r++)
        {
            String cord = cordArray.get(r);
            
            char piece = nameArray.get(r).charAt(0);
            char team = teamArray.get(r).charAt(0);
            
            if(Character.compare(piece,'*') == 0)
            {
                piece = '\0';
                team = '\0';
            }

            ImageView icon = new ImageView();
            icon = nameToImage(piece,team);


            
            pMap.get(cord).getButton().setGraphic(icon);
            pMap.get(cord).setName(piece);
            pMap.get(cord).setTeam(team);
        }
  
  
    }

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