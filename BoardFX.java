import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.text.*;

public class BoardFX extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        //Declare gridpane for the chess game
        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setHgap(5);
        board.setVgap(5);

        // Making the board
        for(int i=0;i<7;i++)
        {
            for (int j=0;j<8;j++)
            {
                Image icon = new Image(getClass().getResourceAsStream("clear.png"));
                Button piece = new Button();
                
                piece.setMinSize(100,100);
                piece.setStyle("-fx-background-color: grey; ");

                board.add(piece,i,j);
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

        //Set up primary Stage
        Scene scene = new Scene(layout);
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
}