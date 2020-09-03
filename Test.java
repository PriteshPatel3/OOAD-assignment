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
import javafx.stage.DirectoryChooser;
import java.io.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
public class Test extends Application
{
    @Override
    public void start(Stage primaryStage)
    { 
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        String name = selectedDirectory.getAbsolutePath();
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < name.length(); i++) 
        {
            if (name.charAt(i) == '\\') 
                sb.append("\\");
        
            sb.append(name.charAt(i));
        }
        sb.append("\\\\mySave.txt");
        
        
        if(selectedDirectory == null){
            System.out.println("No file selected");
        }
        else
        {
            System.out.println(sb.toString());
        }

    } 
   
    


}