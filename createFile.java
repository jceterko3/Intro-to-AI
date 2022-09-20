// adding something so i can push DELETE THIS

import java.io.*;
import java.util.Random;

// creates the test case files with random cells 
public class createFile {
  public static void main(String[] args) {

    // create file
    try {
        
      File fileName = new File(args[0]);            // takes user input for file name 
      fileName.createNewFile();

    } catch (IOException e) {
      System.out.println("ERROR");
    }

    // write to file 
    try{

      FileWriter content = new FileWriter(args[0]);

      // initializing min and max values
      int maxX = 100;
      int maxY = 50;
      int minX = 1;
      int minY = 1;
      Random rand = new Random();

      // creating start & goal index 
      int randNumXS = rand.nextInt((maxX+1 - minX) + 1) + 1;
      int randNumYS = rand.nextInt((maxY+1 - minY) + 1) + 1;
      int randNumXG = rand.nextInt((maxX+1 - minX) + 1) + 1;
      int randNumYG = rand.nextInt((maxY+1 - minY) + 1) + 1;

      String newLine = System.getProperty("line.separator");

      // writing start, goal, grid dimensions
      content.write(randNumXS + " " + randNumYS + newLine);            
      content.write(randNumXG + " " + randNumYG + newLine);
      content.write(maxX + " " + maxY + newLine);

      // determing amount of blocked cells 
      int numBlocked = Math.round(maxX*maxY/10); 
      int count = 0; 

      // writing cells 
      for(int x = 1; x <= maxX; x++){
        for(int y = 1; y <= maxY; y++){
          content.write(x + " " + y + " ");

          boolean blocked = rand.nextInt(10) == 1;
          if(blocked == true && count < numBlocked){
            content.write(1 + newLine);
            count++;
          }
          else{
            content.write(0 + newLine);
          }

        }
      }
      content.close(); 

      System.out.println("blocked: " + numBlocked + " " + "actually: " + count);

    } 
    catch (IOException e) {
        System.out.println("ERROR");
    }

  }

}