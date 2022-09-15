import java.awt.*;
import javax.swing.*;

import java.util.Scanner;
import java.io.*;

public class Test {

    public static void main(String args[]){

        String fileName = args[0];

        // initializing variables used to create grid & start/goal vertex
        int sx = 0; int sy = 0; int gx = 0; int gy = 0; int col = 0; int row = 0; 
        
        // array that holds all blocked cells 
        int bkd[][] = new int[100][50];                                     // this is hard coded might need to change 
        int isBlocked = 3; int count = 0;

        try{
            Scanner scan = new Scanner(new File(fileName));

            // ASSUMING TXT FILES ARE CORRECT 
            // might need to change while loop and obtaining cells section in case 
            while(scan.hasNextLine() && scan.hasNextInt()){
                
                // obtaining start, goal, and size 
                sx = scan.nextInt();
                sy = scan.nextInt();
                gx = scan.nextInt();
                gy = scan.nextInt();
                col = scan.nextInt();
                row = scan.nextInt();

                // obtaining the cells, not sure if this has to be optimized in some way 
                // he mentioned in the assignment we can only do one initialization for vertices?       
                int pointer1 = 0; int pointer2 = 0;     
                for(int tracker1 = 0; tracker1 < col*row; tracker1++){

                    bkd[pointer1][pointer2] = scan.nextInt();                     
                    bkd[pointer1][pointer2+1] = scan.nextInt();  
                    isBlocked = scan.nextInt();

                    if(isBlocked == 0){              
                        bkd[pointer1][pointer2] = 0;                     
                        bkd[pointer1][pointer2+1] = 0;  
                    }
                    else{           
                        pointer1++;
                        count++;
                    }
                }

            }

            scan.close();

        } catch (FileNotFoundException e) {
	        System.out.println(fileName + " not found");
	    }


        Frame grid = new Frame(sx,sy,gx,gy,col,row,bkd,count);

        // maybe here ask about viewing a vertex and displaying info in terminal?
        /*Scanner question = new Scanner(System.in);
        System.out.println("Enter a vertex (x,y) to view more info");
                
        String answer = question.nextLine();
        System.out.println("VALUES : ");*/

    }
    
}
