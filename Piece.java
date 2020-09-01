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



public class Piece{
    private int x,y;
    private char PName;
    private Button button;
    private char team;
    ButtonHandler btnHandler = new ButtonHandler();
    Piece (int x,int y)
    {
        this.x = x;
        this.y = y;     
        setUpPiece(); //setup button based X Y on coordinates
    }

    public void setName (char name)
    {
        this.Pname = name;
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

    private void setUpPiece() 
    {
        if(this.y == 0)
        {
            switch(x){
                case 0:
                case 6:
                    this.button = new Button(" ", new ImageView("ChessPiece/addB.png"));
                    this.button.setOnAction(btnHandler);
                    this.PName = 'p';
                    this.team = 'b';
                    break;
                case 1:
                case 5:
                    this.button = new Button(" ", new ImageView("ChessPiece/triangleB.png"));
                    this.button.setOnAction(btnHandler);
                    this.PName = 't';
                    this.team = 'b';
                    break;
                case 2:
                case 4:
                    this.button = new Button(" ",  new ImageView("ChessPiece/chevronB.png"));
                    this.button.setOnAction(btnHandler);
                    this.PName = 'c';
                    this.team = 'b';
                    break;
                case 3:
                    this.button = new Button(" ", new ImageView("ChessPiece/sunB.png"));
                    this.button.setOnAction(btnHandler);
                    this.PName = 's';
                    this.team = 'b';
                    break;
                default:
                    this.button = new Button(" ", new ImageView("ChessPiece/empty.png"));
                    this.button.setOnAction(btnHandler);
                    

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
                    this.PName = 'a';
                    this.team = 'r';
                    break;
                case 1:
                case 5:
                    this.button = new Button(" ", new ImageView("ChessPiece/triangleR.png"));
                    this.button.setOnAction(btnHandler);
                    this.PName = 'a';
                    this.team = 'r';
                    break;
                case 2:
                case 4:
                    this.button = new Button(" ", new ImageView("ChessPiece/chevronR.png"));
                    this.button.setOnAction(btnHandler);
                    this.PName = 'a';
                    this.team = 'r';
                    break;
                case 3:
                    this.button = new Button(" ", new ImageView("ChessPiece/sunR.png"));
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

    //@Override
    //public String toString()
    //{
        //return "Chess @ X:" + x + " Y:"+ y;
    //}
}