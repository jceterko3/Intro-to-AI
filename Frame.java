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
    public ArrayList<Node> Apath; // shortest path found from start to goal A*
    public ArrayList<Node> Tpath; // shortest path found from start to goal theta*
    public minHeap fringe;

    public Frame(int sx, int sy, int gx, int gy, int col, int row, int[][] bkd, int count, ArrayList<Node> Apth, ArrayList<Node> Tpth) { // creates a pop up window

        startX = sx; // initializing private variables, need to offset by *50 to fit scale of grid
        startY = sy;
        goalX = gx;
        goalY = gy;
        cols = col;
        rows = row;
        blocked = bkd;
        bkdCount = count;
        Apath = Apth;
        Tpath = Tpth;

        getContentPane().setBackground(Color.WHITE);
        setSize(1600,850);
        setTitle("GRID PATH");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // creates grid with components
    public void paint(Graphics g) {

        // determining scale
        int scale = 1;
        if(rows <= 5 && cols <= 10){
            scale = 100;
        }
        else if(rows <= 10 && cols <= 15){
            scale = 70;
        }
        else if(rows <= 15 && cols <= 25){
            scale = 50;
        }
        else if(rows <= 30 && cols <= 45){
            scale = 30;
        }
        else if(rows <= 45 && cols <= 70){
            scale = 20;
        }
        else if(rows <= 50 && cols <= 100){
            scale = 14; 
        }
        else{
            scale = 10;
        }

        // draws grid rectangles
        for (int x = scale; x <= scale * cols; x += scale) {
            for (int y = scale; y <= scale * rows; y += scale) {
                g.drawRect(x, y, scale, scale);
            }
        }

        // adding numbers to grid 
        g.setFont(new Font("TimesRoman", Font.PLAIN, 7));
        int num1 = 1;
        for(int x = scale; x <= scale*(cols+1); x += scale){
            String number1 = Integer.toString(num1);
            g.drawString(number1, x, scale-3);
            num1++;
        }
        int num2 = 1;
        for(int y = scale; y <= scale*(rows+1); y += scale){
            String number2 = Integer.toString(num2);
            g.drawString(number2, scale-10, y);
            num2++;
        }

        // color in blocked cells
        g.setColor(Color.lightGray);
        int pt1 = 0;
        int pt2 = 0;
        int blockedX = 0;
        int blockedY = 0;
        for (int count = bkdCount; count > 0; count--) {
            blockedX = blocked[pt1][pt2] * scale;
            blockedY = blocked[pt1][pt2 + 1] * scale;
            g.fillRect(blockedX, blockedY, scale, scale);
            pt1++;
        }

        // circles start and goal vertex
        int dia = scale/(3/2); // diameter of circle
        g.setColor(Color.cyan);
        g.drawOval(startX * scale - (dia / 2), startY * scale - (dia / 2), dia, dia);
        g.setColor(Color.green);
        g.drawOval(goalX * scale - (dia / 2), goalY * scale - (dia / 2), dia, dia);

        // labels start and goal vertex
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        g.drawString("Start Vertex", startX * scale - (dia / 2), startY * scale - (dia / 2));
        g.drawString("Goal Vertex", goalX * scale - (dia / 2), goalY * scale - (dia / 2));

        // add the final path A*
        if(Apath!=null){
            g.setColor(Color.red);
            int[] xcoords = new int[Apath.size()];
            int[] ycoords = new int[Apath.size()];
            for(int i = 0; i < Apath.size(); i++){
                xcoords[i] = (Apath.get(i).col + 1) * scale;
                ycoords[i] = (Apath.get(i).row + 1) * scale;
            }
            g.drawPolyline(xcoords,ycoords,Apath.size());

        }

        // add the final path theta*
        if(Tpath!=null){
            g.setColor(Color.blue);
            int[] xcoords = new int[Tpath.size()];
            int[] ycoords = new int[Tpath.size()];
            for(int i = 0; i < Tpath.size(); i++){
                xcoords[i] = (Tpath.get(i).col + 1) * scale;
                ycoords[i] = (Tpath.get(i).row + 1) * scale;
            }
            g.drawPolyline(xcoords,ycoords,Tpath.size());

        }

       
    }

}
