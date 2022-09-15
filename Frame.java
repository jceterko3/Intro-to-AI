import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import java.io.InputStream;
import java.io.FileInputStream;


public class Frame extends JFrame{                          // a class to create the GUI

    private int startX;                                     // x coord of start vertex
    private int startY;                                     // y coord of start vertex
    private int goalX;                                      // x coord of goal vertex
    private int goalY;                                      // y coord of goal vertex
    private int cols;                                       
    private int rows;
    private int[][] blocked;                            // includes all blocked cells 
    private int bkdCount;
    private int[][] path;                                   // shortest path found from start to goal

    public Frame(int sx, int sy, int gx, int gy, int col, int row, int[][] bkd, int count){      // creates a pop up window

        startX = sx*50;                                        // initializing private variables, need to offset by *50 to fit scale of grid
        startY = sy*50; 
        goalX = gx*50; 
        goalY = gy*50;
        cols = col;
        rows = row;
        blocked = bkd;
        bkdCount = count;
        //path = pth;

        setSize(100*cols, 100*rows); 
        setTitle("GRID PATH");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setVisible(true); 
    } 

    // creates grid with components
    public void paint(Graphics g){                          
        
        // draws grid rectangles 
        for (int x = 50; x <= 50*cols; x += 50){                 
            for (int y = 50; y <= 50*rows; y += 50){                 
                g.drawRect(x, y, 50, 50);
            }
        }

        // color in blocked cells
        g.setColor(Color.gray);
        int pt1 = 0; int pt2 = 0; int blockedX = 0; int blockedY = 0; 
        for(int count = bkdCount; count > 0; count--){
            blockedX = blocked[pt1][pt2]*50;
            blockedY = blocked[pt1][pt2+1]*50;
            g.fillRect(blockedX, blockedY, 50, 50);
            pt1++;
        }
        
        // circles start and goal vertex 
        int dia = 25;                                       // diameter of circle
        g.setColor(Color.blue);
        g.drawOval(startX - (dia/2), startY - (dia/2), dia, dia);
        g.setColor(Color.green);
        g.drawOval(goalX - (dia/2), goalY - (dia/2), dia, dia);

        // labels start and goal vertex 
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman",Font.PLAIN,10));
        g.drawString("Start Vertex", startX - (dia/2), startY - (dia/2));
        g.drawString("Goal Vertex", goalX - (dia/2), goalY - (dia/2));


        // add the final path 

    } 

} 

