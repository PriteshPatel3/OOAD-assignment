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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class MainGame extends Application
{
    StringBuilder sb = new StringBuilder();
    GridPane chessBoard = new GridPane();

    @Override
    public void start(Stage primaryStage)
    {
        BoardFX chessBoard = new BoardFX(primaryStage);
        int count = 0;
        // Making the board
        for(int i=0;i<7;i++)
        {
            for (int j=0;j<8;j++)
            {                                                                       //size, load order, how to insert
                                                                                    //false = insertion order
                LinkedHashMap<String, Piece> pMap = new LinkedHashMap<String, Piece>(57,0.75f,false);
                sb.append(i);
                sb.append(j);
                String coord = sb.toString();
                pMap.put(coord,new Piece(i,j));
                chessBoard.get().add(pMap.get(coord).getButton(),i,j);
            }
        }
    } 
}

