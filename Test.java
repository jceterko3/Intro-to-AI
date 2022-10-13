import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.lang.Runtime;


public class Test {

    public static void main(String args[]){

        final long startTime = System.nanoTime();

        String fileName = args[0];

        // initializing variables used to create grid & start/goal vertex
        int sx = 0; //start x
        int sy = 0; //start y
        int gx = 0; //goal x
        int gy = 0; //goal y
        int col = 0; //#columns
        int row = 0; //#rows
        int[][] gridCells = new int[0][]; //representing obstacles as 1 in grid
        
        // array that holds all blocked cells 
        int isBlocked; 
        int count = 0;

        ArrayList<ArrayList<Integer>> bkd = new ArrayList<ArrayList<Integer>>();
        Map<Integer,Set<Integer>> blocked = new HashMap<>();
        
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

                gridCells = new int[row + 2][col + 2]; // adding 2 to create a frame of blocked cells around the grid
                for(int i = 0; i < row + 2; i++){
                    gridCells[i][0] = 1;
                    gridCells[i][col + 1] = 1;
                }
                for(int i = 0; i < col + 2; i++){
                    gridCells[0][i] = 1;
                    gridCells[row + 1][i] = 1;
                }

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
                        if(blocked.containsKey(x)) blocked.get(x).add(y);
                        else{
                            Set<Integer> set = new HashSet<>();
                            set.add(y);
                            blocked.put(x,set);
                        } 
                        gridCells[y][x] = 1;
                    }
                }
            }
            //TODO: Delete
            System.out.println("BLOCKED MAP: \n");
            blocked.entrySet().forEach(entry -> {
                System.out.println(entry.getKey() + " " + entry.getValue());
            });

            scan.close();

        } catch (FileNotFoundException e) {
	        System.out.println(fileName + " not found");
	    }

        int[][] b = bkd.stream().map(x -> x.stream().mapToInt(Integer::intValue).toArray()).toArray(int[][]::new); 

        // draws the grid with path
        Scanner pathQues = new Scanner(System.in);
        System.out.println("\nEnter in A star or Theta star in their respective format to view path."); 
        while(pathQues.hasNextLine()){
            String pathAns = pathQues.nextLine();
            if(pathAns.equals("A star")){

                // A* Methods
                Astar Apath = new Astar();
                Node[] apath = Apath.A(sx,sy,gx,gy,col,row,b,count);

                FrameA gridA = new FrameA(sx,sy,gx,gy,col,row,b,count,apath);
    
                // ask about viewing a vertex and displaying info in terminal
                Scanner question = new Scanner(System.in);
                System.out.println("\nEnter a vertex in the form x y to view more info or close GUI to exit");
                // while user is giving input
                while(question.hasNext()){
                    // saving entered vertex
                    int vertexX = question.nextInt();
                    int vertexY = question.nextInt();
    
                    // search for values associated to vertex 
                    double hA = Apath.hval(vertexX, vertexY);
                    double gA = Apath.gval(vertexX, vertexY);
                    double fA = Apath.fval(vertexX, vertexY);
                    System.out.println("A* H VALUE = " + hA);
                    System.out.println("A* G VALUE = " + gA);
                    System.out.println("A* F VALUE = " + fA);
                    System.out.println();
    
                    System.out.println("Enter a vertex in the form x y to view more info or close GUI to exit");
                }
            }
            else if(pathAns.equals("Theta star")){

                // Theta* Method
                ThetaStar thetaPath = new ThetaStar();
                ArrayList<Node> tpath = thetaPath.ThetaStarAlgorithm(sx,sy,gx,gy,col,row,gridCells,blocked);

                FrameT gridT = new FrameT(sx,sy,gx,gy,col,row,b,count,tpath);
    
                // ask about viewing a vertex and displaying info in terminal
                Scanner question = new Scanner(System.in);
                System.out.println("\nEnter a vertex in the form x y to view more info or close GUI to exit");
                // while user is giving input
                while(question.hasNext()){
                    // saving entered vertex
                    int vertexX = question.nextInt();
                    int vertexY = question.nextInt();
    
                    // search for values associated to vertex 
                    double hT = thetaPath.hval(vertexX, vertexY);
                    double gT = thetaPath.gval(vertexX, vertexY);
                    double fT = thetaPath.fval(vertexX, vertexY);
                    System.out.println("theta* H VALUE = " + hT);
                    System.out.println("theta* G VALUE = " + gT);
                    System.out.println("theta* F VALUE = " + fT);
                    System.out.println();
    
                    System.out.println("Enter a vertex in the form x y to view more info or close GUI to exit");
                }
                
            }
            else{
                System.out.println("Please enter A star or Theta star in the correct format.");
            }
    
        }
  
        // time & space 
        final long duration = System.nanoTime() - startTime;
        System.out.println(duration + " nanoseconds");
        Runtime run = Runtime.getRuntime();
        //System.out.println(run.maxMemory() + " bytes");

        System.out.println("start: " + sx + " " + sy);
        System.out.println("goal: " + gx + " " + gy);

    }
    
}