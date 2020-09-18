import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.*;
import java.io.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import java.time.*;
import java.time.format.*;
import javafx.stage.*;
import javafx.application.Platform;

/* Controller part of MVC */
public class ButtonHandler extends MainGame implements EventHandler<ActionEvent> //Pritesh Najmi Fong Darren Aisyah
{
    @Override
    public void handle(ActionEvent e) //Pritesh Najmi//functions to handle what happen when a certain button is clicked
    {
        StringBuilder sb = new StringBuilder(); //used to concatinate integer x and y to a string so it can used as a key to access the linkedhashmap
        int x = BoardFX.getX(e.getSource());
        int y = BoardFX.getY(e.getSource());
        sb.append(x);
        sb.append(y);
        String currentCord = sb.toString(); //push a string of coordinate of the click into a stack 

        if(cordStack.isEmpty()) //if stack empty it checks if the first click is on an empty piece or not
        {
            //if user clicks on empty space
            if (Character.compare(pMap.get(sb.toString()).getName(), '\0') == 0)
            {
                BoardFX.changeText("No piece selected"); //shows you a message if you select a button with no piece
            }

            else //if the first click is not empty then add the click coordinate to stack 
            {
                cordStack.push(currentCord);
                BoardFX.changeText(pMap.get(currentCord).getFullTeam() + " " + pMap.get(currentCord).getFullName() + " selected");
            }
        }

        else //if stack is not empty it will push the destination click coordinate to the stack
        {
            cordStack.push(currentCord);
        }



        if (cordStack.size() == 2) // stack has 2 element destination coordinate and source coordinate
        {
            
            //get destination and source coordinates
            String destinationCord = cordStack.pop(); //getting destination and source coordinates
            String sourceCord = cordStack.pop();

            //checks to see that the user does not press the same button and the source isnt an empty button
            if( !(destinationCord.equals(sourceCord)) && (Character.compare(pMap.get(sourceCord).getName() ,'\0') != 0) )
            {
                    if(checkPiece(sourceCord, destinationCord) == true) //movement logic checker for the piece selected
                    {
                        if(checkTurn(sourceCord)) // check if the selected piece is correct team on the correct turn or not
                        {
                            //prints out move message, to show user it moved successfully 
                            BoardFX.changeText(pMap.get(sourceCord).getFullTeam() + " " + pMap.get(sourceCord).getFullName() + " Moved Succesfully ");
                            move(destinationCord, sourceCord);
                            switchPieces(sourceCord);
							gameOver();
                        }

                        else
                        {
                            BoardFX.changeText("Wrong team chosen"); //show user an error message at the bottom of the screen if wrong team chosen
                        }

                    }

                    else
                    {
                        BoardFX.changeText("Piece cannot be moved there"); //show user an error message at the bottom of the screen if wrong movement logic
                    }
                
            }
        }

        System.out.println("X: " + x + " Y: "+ y); //print to command line for debugging check
        System.out.println("Name:" + pMap.get(sb.toString()).getName());
        System.out.println(cordStack.size());
    }

    private void move (String destinationCord, String sourceCord) //Najmi//general movement logic 
    {
        //get team name and piece name
        char sourceName = pMap.get(sourceCord).getName(); // gets name and team of the source coordinate/piece
        char sourceTeam = pMap.get(sourceCord).getTeam();

        //sets destination icon to the same icon as the source
        pMap.get(destinationCord).getButton().setGraphic(pMap.get(sourceCord).getButton().getGraphic());
        pMap.get(destinationCord).setName(sourceName); //change destination name to source name
        pMap.get(destinationCord).setTeam(sourceTeam); //change destination team to source team

        //sets source button as empty space
        pMap.get(sourceCord).getButton().setGraphic(new ImageView("ChessPiece/empty.png"));
        pMap.get(sourceCord).setName('\0');
        pMap.get(sourceCord).setTeam('\0');
    }

    private boolean checkTurn(String sourceCord) //Pritesh//logic to check whose turn its suppose to be
    {
        int turn = pMap.get(sourceCord).getTurn(); //gets turn from piece obj
        if (turn % 2 == 0) //if turn is in the multiple of 2 (0,2,4,6,8,....) its red team
        {
            if (Character.compare(pMap.get(sourceCord).getTeam(),'r') == 0)
            {   
                pMap.get(sourceCord).increaseTurn(); //increase turn
                BoardFX.getBoard().setRotate(180); //rotate board after the turn over
                return true;
            }
            else
                return false;
        }
        else //if turn is odd number then blue team turn
        {
            if (Character.compare(pMap.get(sourceCord).getTeam(),'b') == 0)
            {
                pMap.get(sourceCord).increaseTurn();
                BoardFX.getBoard().setRotate(0); //roates board back to 0 after blue move
                return true;
            }
            else
                return false; // return false meaning wrong team chosen cannot move
        }
    }

    private boolean checkPiece(String sourceCord, String destinationCord) //Pritesh//tell pieces how to move based on the name of piece
    {
        //seperate string to int x y
        int xDes = Integer.parseInt(String.valueOf(destinationCord.charAt(0))); //seperate coord to x y 
        int yDes = Integer.parseInt(String.valueOf(destinationCord.charAt(1)));
        int xSour = Integer.parseInt(String.valueOf(sourceCord.charAt(0)));
        int ySour = Integer.parseInt(String.valueOf(sourceCord.charAt(1)));
        char name = pMap.get(sourceCord).getName();
        if(Character.compare(pMap.get(sourceCord).getTeam(),pMap.get(destinationCord).getTeam()) != 0) //if team not same then can eat
        {  
            switch(name)
            {
                case 'p':
                    if(checkObstaclesPlus(xDes,yDes,xSour,ySour)) //Check for any obstacles
                    {
                        return movePlus(xDes,yDes,xSour,ySour); //call movement logic function
                    }
                    else
                    {
                        return false; //return false means logic is wrong cannot move
                    }
                    
                case 't':
                    if (checkObstaclesTri(xDes,yDes,xSour,ySour))
                    {
                        return moveTri(xDes,yDes,xSour,ySour);
                    }
                    else
                    {
                        return false;
                    }
                case 'c':
                    return moveChev(xDes,yDes,xSour,ySour);
                case 's':
                    return moveSun(xDes,yDes,xSour,ySour);
                case 'a':
                    if(checkObstaclesAr(xDes,yDes,xSour,ySour))
                    {
                        return moveAr(xDes,yDes,xSour,ySour,sourceCord,destinationCord);
                    }
                    else
                    {
                        return false;
                    } 
                default:
                    return false;
            }
        }
        else
        {
            return false;
        }
    }
	
	public void gameOver () //Aisyah//checks if game over
	{
		boolean win = false;
		int sun = 0 ;
		String team = "\0";
		
		for (Map.Entry<String, Piece> gridEntry : pMap.entrySet()) //loops the map and count number of sun
		{
			
			if (Character.compare(gridEntry.getValue().getName(), 's') == 0) //if found a sun piece count ++
			{
				sun++;
				team = gridEntry.getValue().getFullTeam();
			}
		}
		if (sun ==1) //after looping only 1 sun found meaning that sun team won
		{
			win = true;
			MainGame.popUp(team); //displays a popup window
		} 
		
	}

    private boolean moveAr (int xDes, int yDes, int xSour, int ySour, String sourceCord, String destinationCord) //Pritesh//arrow movement logic calculation
    {

        if (Character.compare(pMap.get(sourceCord).getTeam(),'b') == 0) //if chosen piece is blue
        {
            if ((Math.abs(ySour - yDes) == 0)) //checks if its moving vertically
            {
                if (((xSour - xDes) == -1 || (xSour - xDes) == -2) && pMap.get(sourceCord).getButton().getGraphic().getRotate() == 0) //makes sures that can only move down
                {
                    if(xDes == 7 || xDes == 0) //if reach the edge then rotate the piece
                        {
                            if(pMap.get(sourceCord).getButton().getGraphic().getRotate() == 0)
                                pMap.get(sourceCord).getButton().getGraphic().setRotate(180); 
                        }
                    return true; //true meaning movement logic correct can move
                }
                else if (((xSour - xDes) == 1 || (xSour - xDes) == 2) && pMap.get(sourceCord).getButton().getGraphic().getRotate() == 180) //if the piece already rotate then it can only move up
                {
                    if(xDes == 7 || xDes == 0) //if it reach the edge rotate
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

        else if (Character.compare(pMap.get(sourceCord).getTeam(),'r') == 0) //same logic as blue but this is for red
        {
            if ((Math.abs(ySour - yDes) == 0))
            {
                if (((xSour - xDes) == 1 || (xSour - xDes) == 2) && pMap.get(sourceCord).getButton().getGraphic().getRotate() == 0) //makes sure at first can only move up
                {
                    if(xDes == 7 || xDes == 0)
                    {
                        if(pMap.get(sourceCord).getButton().getGraphic().getRotate() == 0)
                            pMap.get(sourceCord).getButton().getGraphic().setRotate(180); 
                    }
                    return true;
                }
                else if (((xSour - xDes) == -1 || (xSour - xDes) == -2) && pMap.get(sourceCord).getButton().getGraphic().getRotate() == 180) //after rotate makesure can only move down
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

    private boolean moveSun (int xDes, int yDes, int xSour, int ySour) //Fong//sun movement logic
    {
        if ((Math.abs(yDes-ySour) <= 1) && (Math.abs(xDes-xSour) <= 1)) //ensure sun can only move 1 unit in all directions
        {
            return true;
        }
        else 
        {
            return false;
        }
    }

    private boolean movePlus (int xDes, int yDes, int xSour, int ySour) //Darren//move logic for plus
    {   //move logic ensure plus can only move any number step either vertically or horizontally 
        if ((((Math.abs(yDes-ySour) != 0) && (Math.abs(xDes-xSour) == 0)) || ((Math.abs(yDes-ySour) == 0)) && (Math.abs(xDes-xSour) != 0)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean moveChev(int x1, int y1, int x2, int y2) //Najmi//chevron movement logic
    {
        int diffX = Math.abs(x2-x1);
        int diffY = Math.abs(y2-y1);

        
        if (((diffX == 1) && (diffY == 2)) || ((diffX == 2)&&(diffY == 1))) //logic tto move in a L shape in any direction 
        {
            return true;
        }

        else 
        {
            return false;
        }
    }
    
    public boolean moveTri (int xDes, int yDes, int xSour, int ySour) //Darren//triangle movement logic
    {   //ensure triangle can move diagonally 
        if ((Math.abs(yDes-ySour) == Math.abs(xDes-xSour)) && (Math.abs(yDes-ySour) != 0) && (Math.abs(xDes-xSour) != 0))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void switchPieces(String sourceCord) //Pritesh Darren//logic to switch plusses and traingles
    {
        int turn = pMap.get(sourceCord).getTurn();
        if (turn % 4 == 0 ) //if turn is divisible by 4 (4,8,12,16,20,...) then switch occurs, this logic is for red
        {
            for(Map.Entry<String, Piece> gridEntry : pMap.entrySet()) //loops the map find the triangle and plus
            {
                Piece tempPiece = gridEntry.getValue(); //get the Piece obj
                switch (tempPiece.getName()) //switches based on name
                {
                    case 't': //if triangle then switch to plus and vise versa
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
                }
            }
        }
        else if (turn % 4 == 1 && turn != 1) // turn is at (5,9,13,17,21,....) number dividied by 4 which has remainder of 1 
        {                                    // then switch occurs for blue team
            for(Map.Entry<String, Piece> gridEntry : pMap.entrySet())
            {
                Piece tempPiece = gridEntry.getValue();
                switch (tempPiece.getName())
                {
                    case 't':
                        if(Character.compare(tempPiece.getTeam(),'b') == 0)
                        {
                            tempPiece.getButton().setGraphic(new ImageView("ChessPiece/addB.png"));
                            tempPiece.setName('p');   
                        }          
                        break;
                    case 'p':
                        if(Character.compare(tempPiece.getTeam(),'b') == 0)
                        {
                            tempPiece.getButton().setGraphic(new ImageView("ChessPiece/triangleB.png"));
                            tempPiece.setName('t');
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public boolean checkObstaclesPlus(int xDes, int yDes, int xSour, int ySour) //Darren
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
                if (Character.compare(tempPiece.getName(),'\0') != 0) 
                {
                    System.out.println("There's an obstacle in the way of X2");
                    return false;
                }
            }
        }
        else if (xDes == xSour && (yDes < ySour)) //Move Vertical at X Axis in decreasing direction
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
                if (Character.compare(tempPiece.getName(),'\0') != 0) 
                {
                    System.out.println("There's an obstacle in the way of Y2");
                    return false;
                }
            }
        }
        else
        {
            System.out.println("Illegal movement. Please move according to the Plus piece's logic");
        }
        return true;
    }


    public boolean checkObstaclesAr(int xDes, int yDes, int xSour, int ySour) //Fong
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
            System.out.println("Illegal movement. Please move according to the Triangle piece's logic");
        }

        return true;
    }

    public boolean checkObstaclesTri(int xDes, int yDes, int xSour, int ySour) //Fong
    {
        StringBuilder sb = new StringBuilder();
        if((yDes > ySour) && (xDes > xSour)){ //135 degree for blue, 315 for red (but logically is 45 for both)
            for (int i = yDes - 1 , j = xDes - 1 ;(i > ySour) && (j > xSour); i--, j--)
            {
                //Reset Size of String
                sb.setLength(0);
                sb.trimToSize();

                sb.append(j);
                sb.append(i);
                
                String coord = sb.toString(); // i(x coordinate) + yDes in string
                Piece tempPiece = pMap.get(coord);
                System.out.println(i);
                if (Character.compare(tempPiece.getName(),'\0') != 0) //Checks if piece exist
                {
                    System.out.println("There's an obstacle in the way(1)");
                    return false;
                }
            }
        }
        else if((yDes > ySour) && (xDes < xSour)){ //225 degree for blue, 45 for red (but logically is 135 for both) 
            for (int i = yDes - 1 , j = xDes + 1 ;(i > ySour) && (j < xSour); i--, j++)
            {
                //Reset Size of String
                sb.setLength(0);
                sb.trimToSize();

                sb.append(j);
                sb.append(i);
                
                String coord = sb.toString(); // i(x coordinate) + yDes in string
                Piece tempPiece = pMap.get(coord);
                System.out.println(i);
                if (Character.compare(tempPiece.getName(),'\0') != 0) //Checks if piece exist
                {
                    System.out.println("There's an obstacle in the way(2)");
                    return false;
                }
            }
        }
        else if((yDes < ySour) && (xDes < xSour)){//315 for blue, 135 for red(but logically is 225 for both) 
            for (int i = yDes + 1 , j = xDes + 1 ;(i < ySour) && (j < xSour); i++, j++)
            {
                //Reset Size of String
                sb.setLength(0);
                sb.trimToSize();

                sb.append(j);
                sb.append(i);
                
                String coord = sb.toString(); // i(x coordinate) + yDes in string
                Piece tempPiece = pMap.get(coord);
                System.out.println(i);
                if (Character.compare(tempPiece.getName(),'\0') != 0) //Checks if piece exist
                {
                    System.out.println("There's an obstacle in the way(3)");
                    return false;
                }
            }
        
        }
        else if((yDes < ySour) && (xDes > xSour)){//45 for blue, 225 for red(but logically is 315 for both)
            for (int i = yDes + 1 , j = xDes - 1 ;(i < ySour) && (j > xSour); i++, j--)
            {
                //Reset Size of String
                sb.setLength(0);
                sb.trimToSize();

                sb.append(j);
                sb.append(i);
                
                String coord = sb.toString(); // i(x coordinate) + yDes in string
                Piece tempPiece = pMap.get(coord);
                System.out.println(i);
                if (Character.compare(tempPiece.getName(),'\0') != 0) //Checks if piece exist
                {
                    System.out.println("There's an obstacle in the way(4)");
                    return false;
                }
            }
        
        }
        else
        {
            System.out.println("Illegal movement. Please move according to the Triangle piece's logic");
        }
        return true;
    }

}

class MenuHandler extends ButtonHandler //Najmi//button handler but for menu items
{
    @Override
    public void handle(ActionEvent e)
    {
        MenuItem menu = (MenuItem)e.getSource();
        int id = Integer.parseInt(menu.getId()); //gets id of menu clicked
        FileChooser chooser = new FileChooser();
        DirectoryChooser dChooser = new DirectoryChooser();

        switch(id)
        {
            case 1://Najmi//save button
                try 
                {
                    File saveDirectory = dChooser.showDialog(MainGame.getStage()); //opens file saving dialogue
                    String directory = saveDirectory.getAbsolutePath();
                    StringBuilder name = new StringBuilder();

                    for (int i = 0; i < directory.length(); i++) 
                    {
                        if (directory.charAt(i) == '\\') 
                            name.append("\\");
                    
                        name.append(directory.charAt(i));
                    }
                    
                    name.append("\\\\Webale_save.txt");
                    
                    saveGame(name.toString()); //saves location and pieces on the board
                }

                catch (IOException f)
                {
                    System.out.println("File not created");
                }
                

                break;

            case 2://Najmi//load game
                try
                {
                    File loadFile = chooser.showOpenDialog(MainGame.getStage()); //opens saved file
                    loadGame(loadFile); //runs a function to display the board based on saved file
                }

                catch (IOException g)
                {
                    System.out.println("File not loaded");
                }
                

                break;

            case 3://Fong//restarting button
                
                System.out.println( "Restarting app!" ); //shows in command line for development purposes
                MainGame.getStage().close(); // closes the game 
                Platform.runLater( () -> new MainGame().start( new Stage()) ); //after closing the game tune a new instance of the game
                break;

        }


    }

    public void saveGame(String fileName) throws IOException
    {//Najmi
        
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
    {//Najmi
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
            icon = nameToImage(piece,team); //function to determine what image should be on the piece


            
            pMap.get(cord).getButton().setGraphic(icon);
            pMap.get(cord).setName(piece);
            pMap.get(cord).setTeam(team);
        }
  
  
    }

    private ImageView nameToImage (char name, char team) //function to return the correct image based on the team and name
    {//Najmi                                             // used during loading save file
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