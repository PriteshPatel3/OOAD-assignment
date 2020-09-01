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
    //Image icon;
    char[] r1 = {'c','t','v','s','v','t','c'};
    //char[] r2 = {'a',' ','a',' ','a',' ','a'};
    //static ArrayList<Button> btns = new ArrayList<>();
    ButtonHandler btnHandler = new ButtonHandler();
    Piece (int x,int y)
    {
        this.x = x;
        this.y = y;     
        //button.setMinSize(100,100);
        //button.setStyle("-fx-background-color: grey; ");
        //button.setGraphic(icon);
        setUpPiece();
        //this.button.setMinSize(100,100);
    }

    public Button getButton()
    {
        return button;
    }

    private void setUpPiece() 
    {
        if(this.y == 0)
        {
            switch(x){
                case 0:
                    this.button = new Button(" ", new ImageView("ChessPiece/addB.png"));
                    this.button.setOnAction(btnHandler);
                    ////btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 1:
                    this.button = new Button(" ", new ImageView("ChessPiece/triangleB.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 2:
                    this.button = new Button(" ",  new ImageView("ChessPiece/chevronB.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 3:
                    this.button = new Button(" ", new ImageView("ChessPiece/sunB.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 4:
                    this.button = new Button(" ", new ImageView("ChessPiece/chevronB.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 5:
                    this.button = new Button(" ", new ImageView("ChessPiece/triangleB.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 6:
                    this.button = new Button(" ", new ImageView("ChessPiece/addB.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                default:
                    this.button = new Button(" ", new ImageView("ChessPiece/empty.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];

            } 
        }
        else if(this.y == 1)
        {
            ImageView tempRotate = new ImageView("ChessPiece/arrowB.png");
            //If all arrows rotated if reached end, this could be the cause
            switch(x){
                case 0:
                    this.button = new Button(" ", tempRotate);
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 2:
                    this.button = new Button(" ", tempRotate);
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 4:
                    this.button = new Button(" ", tempRotate);
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 6:
                    this.button = new Button(" ", tempRotate);
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                default:
                    this.button = new Button(" ", new ImageView("ChessPiece/empty.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
            }
        }

        else if(this.y == 7 )
        {
            switch(x){
                case 0:
                    this.button = new Button(" ", new ImageView("ChessPiece/addR.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 1:
                    this.button = new Button(" ", new ImageView("ChessPiece/triangleR.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 2:
                    this.button = new Button(" ", new ImageView("ChessPiece/chevronR.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 3:
                    this.button = new Button(" ", new ImageView("ChessPiece/sunR.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 4:
                    this.button = new Button(" ", new ImageView("ChessPiece/chevronR.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 5:
                    this.button = new Button(" ", new ImageView("ChessPiece/triangleR.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 6:
                    this.button = new Button(" ", new ImageView("ChessPiece/addR.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                default:
                    this.button = new Button(" ", new ImageView("ChessPiece/empty.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
            }
        }
        else if(this.y == 6)
        {
            switch(x){
                case 0:
                    this.button = new Button(" ", new ImageView("ChessPiece/arrowR.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 2:
                    this.button = new Button(" ", new ImageView("ChessPiece/arrowR.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 4:
                    this.button = new Button(" ", new ImageView("ChessPiece/arrowR.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                case 6:
                    this.button = new Button(" ", new ImageView("ChessPiece/arrowR.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
                    break;
                default:
                    this.button = new Button(" ", new ImageView("ChessPiece/empty.png"));
                    this.button.setOnAction(btnHandler);
                    //btns.add(this.button);
                    this.PName = r1[1];
            }
        }
        else
        {
            this.button = new Button(" ", new ImageView("ChessPiece/empty.png"));
            this.button.setOnAction(btnHandler);
            //btns.add(this.button);
            this.PName = r1[1];
        }
    }
}