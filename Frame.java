import java.awt.Graphics;
import javax.swing.JFrame;
import java.util.Scanner;


 public class Frame extends JFrame {                        // a class to create the GUI

    public Frame(){                                         // creates a pop up window
        setSize(500, 500); 
        setVisible(true); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    } 

    public void paint(Graphics g){                          // creates the grid (need to take in arguments for start, goal, blocked/unblocked, path)
        for (int x = 50; x <= 300; x += 50)                 // this is a 6x6 grid for now
        for (int y = 50; y <= 300; y += 50)                 
        g.drawRect(x, y, 50, 50);
        // after the program has ran draw everything with the path instead of just a plain grid and adding 
    } 

    public static void main(String args[]){
        Frame grid = new Frame();

        // maybe here ask about viewing a vertex and displaying info in terminal?
        /*Scanner question = new Scanner(System.in);
        System.out.println("Enter a vertex (x,y) to view more info");

        String answer = question.nextLine();
        System.out.println("VALUES : ");*/
    }   

} 

