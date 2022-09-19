// adding something so i can push DELETE THIS

import java.awt.*;
import javax.swing.*;

import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;


public class Test {

    public static void main(String args[]){

        String fileName = args[0];

        // initializing variables used to create grid & start/goal vertex
        int sx = 0; //start x
        int sy = 0; //start y
        int gx = 0; //goal x
        int gy = 0; //goal y
        int col = 0; //#columns
        int row = 0; //#rows
        
        // array that holds all blocked cells 
        int isBlocked; 
        int count = 0;

        ArrayList<ArrayList<Integer>> bkd = new ArrayList<ArrayList<Integer>>();
        
        try{
            Scanner scan = new Scanner(new File(fileName));
		
	    // error message if txt file is empty | might need to consider more cases 
            if(scan.hasNextInt() == false){
                System.out.println("GRID DOES NOT EXIST");
            }

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
                for(int i = 0; i < col*row; i++){
                    //hold all variables
                    int x = scan.nextInt();
                    int y = scan.nextInt();
                    isBlocked = scan.nextInt(); 

                    if(isBlocked == 1){    //if blocked then add to arraylist
                        bkd.add(new ArrayList<Integer>());
                        bkd.get(count).add(x);   
                        bkd.get(count).add(y); 
                        count++;
                    }
                    

                }

            }

            scan.close();

        } catch (FileNotFoundException e) {
	        System.out.println(fileName + " not found");
	    }

        int[][] b = bkd.stream().map(x -> x.stream().mapToInt(Integer::intValue).toArray()).toArray(int[][]::new); 

        //test to print array of blocked cells (delete later)
        for(int i=0; i<b.length; i++){
            //System.out.print(b[i][0]+",");
            //System.out.println(""+b[i][1]);
        }

        // A* Methods
        Astar Apath = new Astar();
        ArrayList<Node> apath = Apath.A(sx,sy,gx,gy,col,row,b,count);
        //Apath.print_hval_A();

        // draws the grid with path 
        Frame grid = new Frame(sx,sy,gx,gy,col,row,b,count,apath);

        // maybe here ask about viewing a vertex and displaying info in terminal?
        /*Scanner question = new Scanner(System.in);
        System.out.println("Enter a vertex (x,y) to view more info");
                
        String answer = question.nextLine();
        System.out.println("VALUES : ");*/
         
    }
    
}
