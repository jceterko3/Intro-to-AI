import java.io.*;

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

        int num = 1;
        int test = 2;
        String newLine = System.getProperty("line.separator");

        content.write(num + " " + test + newLine);            // figure out how to write the proper stuff
        content.write("hello");
        content.close(); 

    } catch (IOException e) {
        System.out.println("ERROR");
    }

  }

}