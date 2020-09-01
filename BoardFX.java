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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public final class BoardFX
{   
    private static GridPane board = new GridPane();

    BoardFX(Stage primaryStage)
    {
        board.setAlignment(Pos.CENTER);
        board.setHgap(5);
        board.setVgap(5);

        Menu menu = new Menu("Options");
        MenuBar bar = new MenuBar();
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
        Scene scene = new Scene(layout);
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

    public GridPane get()
    {
        return board;
    }
}

