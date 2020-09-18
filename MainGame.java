import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.text.*;
import java.util.ArrayList;
import javafx.scene.Node;
import java.util.*;
import java.io.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType; 
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.application.Platform;

public class MainGame extends Application //najmi and pritesh
{
    static Stage pStage; // variable to hold primary stage (singleton DP)
    /*Proxy pattern, this LinkedHashMap provide the mean to access the Piece on the board */
    static LinkedHashMap<String, Piece> pMap = new LinkedHashMap<String, Piece>(57,0.75f,false);  //Map to track what Piece is on what coordinate
    static Stack<String> cordStack = new Stack<String>(); // A stack of string used to handle movement logic

    @Override
    public void start(Stage primaryStage)
    {
        BoardFX chessBoard = new BoardFX(primaryStage); //initialize BoardFX object 
        int count = 0;

        setStage(primaryStage);

        // Making the board
        for(int i=0;i<7;i++)
        {
            for (int j=0;j<8;j++)
            {
                StringBuilder sb = new StringBuilder(); // i use to append i j so i can make it as key
                sb.append(j);
                sb.append(i);
                String coord = sb.toString(); // i + j in string
                pMap.put(coord,new Piece(i,j)); // put a new Piece obj at coordinates
                chessBoard.setBoard(pMap.get(coord).getButton(),i,j); // set the piece in the chessboard
            }
        }

        
    }
    private void setStage(Stage primaryStage) //najmi
    {
        this.pStage = primaryStage;
    }
    
    public static Stage getStage() //najmi//assigning primary stage to a static variable so all class can access the same instance of primary stage
    {
        return pStage;
    }
	public static void popUp(String team) //aisyah and fong //Functions to show a popup window when you win the game
	{
		
        Alert alert = new Alert(AlertType.CONFIRMATION); //type of popup is alert
        alert.setTitle("Congratulations");  // title
        alert.setHeaderText("Congratulations Team " + team + " won!"); //content of popup
        alert.setContentText("Play again?");

        ButtonType buttonTypeOne = new ButtonType("Restart"); //button initialization
        ButtonType buttonTypeTwo = new ButtonType("Exit");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel); //setting the button on to the popup window

        Optional<ButtonType> result = alert.showAndWait(); //receive which options user choose
        if (result.get() == buttonTypeOne){// logic to handle options choices
            System.out.println( "Restarting app!" );
            getStage().close();
            Platform.runLater( () -> new MainGame().start( new Stage() ) );
        } else if (result.get() == buttonTypeTwo) {
            Platform.exit();
        } else {
        }
	}
}

