// adding something so i can push DELETE THIS

import java.awt.*;
import javax.swing.*;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;

public class Frame extends JFrame { // a class to create the GUI

    private int startX; // x coord of start vertex
    private int startY; // y coord of start vertex
    private int goalX; // x coord of goal vertex
    private int goalY; // y coord of goal vertex
    private int cols;
    private int rows;
    private int[][] blocked; // includes all blocked cells
    private int bkdCount;
    public ArrayList<Node> path; // shortest path found from start to goal
    public minHeap fringe;

    public Frame(int sx, int sy, int gx, int gy, int col, int row, int[][] bkd, int count, ArrayList<Node> Apth) { // creates a pop up window

        startX = sx; // initializing private variables, need to offset by *50 to fit scale of grid
        startY = sy;
        goalX = gx;
        goalY = gy;
        cols = col;
        rows = row;
        blocked = bkd;
        bkdCount = count;
        path = Apth;

        getContentPane().setBackground(Color.WHITE);
        setSize(1600,850);
        setTitle("GRID PATH");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    // creates grid with components
    public void paint(Graphics g) {

        // determining scale, need to give some space for border 
        int scaleX = 1000/(cols+2);
        int scaleY = 850/(rows+2);

        // draws grid rectangles
        for (int x = scaleX; x <= scaleX * cols; x += scaleX) {
            for (int y = scaleY; y <= scaleY * rows; y += scaleY) {
                g.drawRect(x, y, scaleX, scaleY);
            }
        }
        
        // adding numbers to grid 
        g.setFont(new Font("TimesRoman", Font.PLAIN, 7));
        int num1 = 1;
        for(int x = scaleX; x <= scaleX*(cols+1); x += scaleX){
            String number1 = Integer.toString(num1);
            g.drawString(number1, x, scaleY);
            num1++;
        }
        int num2 = 1;
        for(int y = scaleY; y <= scaleY*(rows+1); y += scaleY){
            String number2 = Integer.toString(num2);
            g.drawString(number2, scaleX-10, y);
            num2++;
        }

        // color in blocked cells
        g.setColor(Color.gray);
        int pt1 = 0;
        int pt2 = 0;
        int blockedX = 0;
        int blockedY = 0;
        for (int count = bkdCount; count > 0; count--) {
            blockedX = blocked[pt1][pt2] * scaleX;
            blockedY = blocked[pt1][pt2 + 1] * scaleY;
            g.fillRect(blockedX, blockedY, scaleX, scaleY);
            pt1++;
        }

        // circles start and goal vertex
        int dia = scaleX/2; // diameter of circle
        g.setColor(Color.blue);
        g.drawOval(startX * scaleX - (dia / 2), startY * scaleY - (dia / 2), dia, dia);
        g.setColor(Color.green);
        g.drawOval(goalX * scaleX - (dia / 2), goalY * scaleY - (dia / 2), dia, dia);

        // labels start and goal vertex
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        g.drawString("Start Vertex", startX * scaleX - (dia / 2), startY * scaleY - (dia / 2));
        g.drawString("Goal Vertex", goalX * scaleX - (dia / 2), goalY * scaleY - (dia / 2));

        // add the final path
        if(path!=null){
            g.setColor(Color.red);
            int[] xcoords = new int[path.size()];
            int[] ycoords = new int[path.size()];
            for(int i = 0; i < path.size(); i++){
                xcoords[i] = (path.get(i).col + 1) * scaleX;
                ycoords[i] = (path.get(i).row + 1) * scaleY;
            }
            g.drawPolyline(xcoords,ycoords,path.size());

        }

       
    }

}
