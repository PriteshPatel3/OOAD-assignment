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


public final class BoardFX
{   
    static GridPane board = new GridPane();
    ButtonHandler menuHand = new MenuHandler();
    

    BoardFX(Stage primaryStage)


    {
        board.setAlignment(Pos.CENTER);
        board.setHgap(5);
        board.setVgap(5);

        //create a menu
        Menu menu = new Menu("Options");
		
		//create menu options
        MenuItem opt1 = new MenuItem("Save Game");
        opt1.setId("1");
        opt1.setOnAction(menuHand);

        MenuItem opt2 = new MenuItem("Load Game");
        opt2.setId("2");
        opt2.setOnAction(menuHand);

        MenuItem opt3 = new MenuItem("Restart Game");
        opt3.setId("3");
        opt3.setOnAction(menuHand);
		
		//add menu options to menu
		menu.getItems().add(opt1);
		menu.getItems().add(opt2);
		menu.getItems().add(opt3);
		
		//create menu bar
		MenuBar bar = new MenuBar();
		
		//add menu to menubar
        bar.getMenus().add(menu);
        
        //annoucement text
        Label text = new Label("Hi there");

        //Vbox
        VBox layout = new VBox(5);
        layout.getChildren().add(bar);
        layout.getChildren().add(board);
        layout.getChildren().add(text);

        //board.setRotate(300);

        //Set up primary Stage
        Scene scene = new Scene(layout,1024,950);
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static int getX(Object source)
    {
        Button b = (Button) source; 
        return board.getRowIndex(b);
    }

    public static int getY(Object source)
    {
        Button b = (Button) source; 
        return board.getColumnIndex(b);
    }

    public static GridPane getBoard()
    {
        return board;
    }

    public void setBoard(Node node, int col, int row)
    {
        board.add(node,col,row);
    }
}
