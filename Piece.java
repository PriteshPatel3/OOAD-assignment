import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.image.ImageView;
import java.util.ArrayList;


/* Model part of MVC */
/* Observer Pattern 1 Piece object is mapped to 1 button, Any changes on Piece class in observed on the button*/ 
/*State pattern, Piece object state determines how to move the pieces
Example if state of Piece change from plus piece to triangle piece, the piece will have behavior of a triangle */
public class Piece //Pritesh Najmi
{
    private int x,y;
    private char PName;
    private Button button;
    private char team;
    private static int turn; //turn to know its blue or red team turn 
    ButtonHandler btnHandler = new ButtonHandler(); //initialization handler for buttons
    Piece (int x,int y) // constructor for Piece class
    {
        this.x = x;
        this.y = y;     
        setUpPiece(); //setup button based X Y on coordinates
    }

    public void setName (char name)
    {
        this.PName = name;
    }
    
    public Button getButton()
    {
        return button;
    }

    public char getName()
    {
        return PName;
    }

    public char getTeam()
    {
        return this.team;
    }

    public void setTeam(char team)
    {
        this.team = team;
    }

    public int getTurn()
    {
        
        return turn;
    }

    public void increaseTurn()
    {
        turn++;
    }

    private void setUpPiece() //Pritesh//functions to setup pieces on the board based on the x and y coordinates
    {
        if(this.y == 0)
        {
            switch(x){
                case 0:
                case 6:
                    this.button = new Button(" ", new ImageView("ChessPiece/addB.png")); //if row 0 on col 6 and 0 piece will be blue plus
                    this.button.setOnAction(btnHandler);
                    this.PName = 'p';
                    this.team = 'b';
                    break;
                case 1:
                case 5:
                    this.button = new Button(" ", new ImageView("ChessPiece/triangleB.png")); //if row 0 on col 5 and 0 piece will be blue triangle
                    this.button.setOnAction(btnHandler);
                    this.PName = 't';
                    this.team = 'b';
                    break;
                case 2:
                case 4:
                    this.button = new Button(" ",  new ImageView("ChessPiece/chevronB.png"));//if row 0 on col 4 and 0 piece will be blue Chevron
                    this.button.setOnAction(btnHandler);
                    this.PName = 'c';
                    this.team = 'b';
                    break;
                case 3:
                    this.button = new Button(" ", new ImageView("ChessPiece/sunB.png")); //if row 0 on col 3 piece will be blue sun
                    this.button.setOnAction(btnHandler);
                    this.PName = 's';
                    this.team = 'b';
                    break;
                default:
                    this.button = new Button(" ", new ImageView("ChessPiece/empty.png")); //if coordinate were not mentioned in the switches above 
                    this.button.setOnAction(btnHandler);                                   //the button will not have a Piece object and will be considered blank
                    

            } 
        }
        else if(this.y == 1)
        {
            ImageView tempRotate = new ImageView("ChessPiece/arrowB.png");
            //If all arrows rotated if reached end, this could be the cause
            switch(x){
                case 0:
                case 2:
                case 4:
                case 6:
                    this.button = new Button(" ", tempRotate);
                    this.button.setOnAction(btnHandler);
                    this.PName = 'a';
                    this.team = 'b';
                    break;
                default:
                    this.button = new Button(" ", new ImageView("ChessPiece/empty.png"));
                    this.button.setOnAction(btnHandler);
                    
            }
        }

        else if(this.y == 7 )
        {
            switch(x){
                case 0:
                case 6:
                    this.button = new Button(" ", new ImageView("ChessPiece/addR.png"));
                    this.button.setOnAction(btnHandler);
                    this.PName = 'p';
                    this.team = 'r';
                    break;
                case 1:
                case 5:
                    this.button = new Button(" ", new ImageView("ChessPiece/triangleR.png"));
                    this.button.setOnAction(btnHandler);
                    this.PName = 't';
                    this.team = 'r';
                    break;
                case 2:
                case 4:
                    this.button = new Button(" ", new ImageView("ChessPiece/chevronR.png"));
                    this.button.setOnAction(btnHandler);
                    this.PName = 'c';
                    this.team = 'r';
                    break;
                case 3:
                    this.button = new Button(" ", new ImageView("ChessPiece/sunR.png"));
                    this.button.setOnAction(btnHandler);
                    this.PName = 's';
                    this.team = 'r';
                    break;
                default:
                    this.button = new Button(" ", new ImageView("ChessPiece/empty.png"));
                    this.button.setOnAction(btnHandler);
                    this.PName = '\0';
            }
        }
        else if(this.y == 6)
        {
            switch(x){
                case 0:
                case 2:
                case 4:
                case 6:
                    this.button = new Button(" ", new ImageView("ChessPiece/arrowR.png"));
                    this.button.setOnAction(btnHandler);
                    this.PName = 'a';
                    this.team = 'r';
                    break;
                default:
                    this.button = new Button(" ", new ImageView("ChessPiece/empty.png"));
                    this.button.setOnAction(btnHandler);
                    this.PName = '\0';
            }
        }
        else
        {
            this.button = new Button(" ", new ImageView("ChessPiece/empty.png"));
            this.button.setOnAction(btnHandler);
            
            this.PName = '\0';
        }
    }

    public String getFullName() //Najmi//get full name for printing purposes // To show user error more clearly
    {
        char name = this.getName();

        String fullName = "default";
        switch (name)
        {
            case 'c':
                fullName = "Chevron";
                break;

            case 's':
                fullName = "Sun";
                break;

            case 't':
                fullName = "Triangle";
                break;

            case 'a':
                fullName = "Arrow";
                break;

            case 'p':
                fullName = "Plus";
                break;

        }

        return fullName;

    }

    public String getFullTeam() //Najmi//get full team for printing purposes // To show user error more clearly
    {
        char team = this.getTeam();
        String fullTeam = "default";
        switch (team)
        {
            case 'b':
                fullTeam = "Blue";
                break;

            case 'r':
                fullTeam = "Red";
                break;
        }

        return fullTeam;

    }

}