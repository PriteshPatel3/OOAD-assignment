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
import javafx.stage.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/* View part of MVC */
public final class BoardFX //Pritesh Najmi
{   
    static GridPane board = new GridPane(); //singleton dp one instance of gridpane accessable by all classes
    ButtonHandler menuHand = new MenuHandler(); //intialization handler class for menuitem
    
    //annoucement text
    static Label text = new Label("Hi there"); //Text that apears at the bottom of the screen, single instance access by all 

    BoardFX(Stage primaryStage) //Pritesh Najmi Aisyah
    {
        board.setAlignment(Pos.CENTER); 
        board.setHgap(5);
        board.setVgap(5);

        //create a menu
        Menu menu = new Menu("Options"); //intialize a drop down menu button 
		
		//create menu options
        MenuItem opt1 = new MenuItem("Save Game"); //under drop down got these buttons
        opt1.setId("1"); //id purpose for handling the click logic
        opt1.setOnAction(menuHand); //telling the button where to look when onClick event happens

        MenuItem opt2 = new MenuItem("Load Game");
        opt2.setId("2");
        opt2.setOnAction(menuHand);

        MenuItem opt3 = new MenuItem("Restart Game");
        opt3.setId("3");
        opt3.setOnAction(menuHand);
		
		//add menu options to menu
		menu.getItems().add(opt1); //adding buttons to the drop down menu
		menu.getItems().add(opt2);
		menu.getItems().add(opt3);
		
		//create menu bar
		MenuBar bar = new MenuBar(); // a menubar
		
		//add menu to menubar
        bar.getMenus().add(menu); // menu bar has a menu button
        
        
        //Vbox
        VBox layout = new VBox(5);
        layout.getChildren().add(bar); //setting up the layout of the screen
        layout.getChildren().add(board);
        layout.getChildren().add(text);

        //board.setRotate(300);

        //Set up primary Stage
        Scene scene = new Scene(layout,1024,1024);
        scene.getStylesheets().add("style.css"); //styling purposes cause we cool :)

        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static int getX(Object source) //Najmi//used to get x,y on gridpane based on button click // used during early development for bug testing 
    {
        Button b = (Button) source; 
        return board.getRowIndex(b);
    }

    public static int getY(Object source) //Najmi
    {
        Button b = (Button) source; 
        return board.getColumnIndex(b);
    }

    public static GridPane getBoard() //Pritesh
    {
        return board;
    }

    public void setBoard(Node node, int col, int row) //Pritesh// add nodes aka button to the gridpane
    {
        board.add(node,col,row);
    }

    public static void changeText(String annoucement) //Najmi//fucntion used to change text that apears at the bottom of the screen 
    {
        text.setText(annoucement);
    }
}
