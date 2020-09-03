import java.util.*;
import java.time.*;
import java.time.format.*;
import java.io.*;

public class Test
{

    public static void main(String[] args)
    { 
        String str = "00:*:r"; 
        String[] arrOfStr = str.split(":", 3); 
  
        for (String a : arrOfStr) 
            System.out.println(a); 

    } 
   
    


}