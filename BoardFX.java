import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.text.*;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class BoardFX extends Application
{

    static GridPane board = new GridPane();
    @Override
    public void start(Stage primaryStage)
    {
        
        ArrayList<Piece> pieces = new ArrayList<>();
        //Declare gridpane for the chess game
        
        board.setAlignment(Pos.CENTER);
        board.setHgap(5);
        board.setVgap(5);
        int count = 0;

        
        // Making the board
        for(int i=0;i<7;i++)
        {
            for (int j=0;j<8;j++)
            {
                //Image icon = new Image(getClass().getResourceAsStream("clear.png"));
                pieces.add(new Piece(i,j));
                //piece.setMinSize(100,100);
                //piece.setStyle("-fx-background-color: grey; ");
                board.add(pieces.get(count).getButton(count),i,j);
                count++;
            }
        }
        

        //Design Menu
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

    public static void barrelRoll()
    {
        board.setRotate(300);
    }
}

