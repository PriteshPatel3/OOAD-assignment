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


public class MainGame extends Application
{
    GridPane chessBoard = new GridPane();
    static Stage pStage;
    static LinkedHashMap<String, Piece> pMap = new LinkedHashMap<String, Piece>(57,0.75f,false); //size, load order, how to insert, false = insertion order
    static Stack<String> cordStack = new Stack<String>();

    @Override
    public void start(Stage primaryStage)
    {
        BoardFX chessBoard = new BoardFX(primaryStage);
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
    
    private void setStage(Stage primaryStage)
    {
        this.pStage = primaryStage;
    }
    
    public static Stage getStage()
    {
        return pStage;
    }
}

