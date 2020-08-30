import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.text.*;

public class Peice()
{
    int x,y;
    String PName;
    Button button;
    Image icon;

    Piece (int x,int y,String name, Button button,Image icon)
    {
        this.x = x;
        this.y = y;
        this.PName = name;
        
        Button temp = new Button ();      
        temp.setMinSize(100,100);
        temp.setStyle("-fx-background-color: grey; ");
        temp.setGraphic(icon);
        this.button = temp;
    }
}