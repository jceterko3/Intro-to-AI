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
      int maxX = 4;
      int maxY = 2;
      int minX = 1;
      int minY = 1;
      Random rand = new Random();

      // creating start & goal index 
      int randNumXS = rand.nextInt((maxX - minX) + 1) + 1;
      int randNumYS = rand.nextInt((maxY - minY) + 1) + 1;
      int randNumXG = rand.nextInt((maxX - minX) + 1) + 1;
      int randNumYG = rand.nextInt((maxY - minY) + 1) + 1;

      String newLine = System.getProperty("line.separator");

      // writing start, goal, 100x50 to file 
      content.write(randNumXS + " " + randNumYS + newLine);            
      content.write(randNumXG + " " + randNumYG + newLine);
      content.write(maxX + " " + maxY + newLine);

      // writing cells 
      for(int x = 1; x <= maxX; x++){
        for(int y = 1; y <= maxY; y++){
          content.write(x + " " + y + " ");

          boolean blocked = rand.nextInt(10)==0;
          if(blocked == true){
            content.write(1 + newLine);
          }
          else{
            content.write(0 + newLine);
          }

        }
      }
      content.close(); 
    } 
    catch (IOException e) {
        System.out.println("ERROR");
    }

  }

}