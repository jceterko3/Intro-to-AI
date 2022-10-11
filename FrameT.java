import java.awt.*;
import javax.swing.*;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;

public class FrameT extends JFrame { // a class to create the GUI

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

    public FrameT(int sx, int sy, int gx, int gy, int col, int row, int[][] bkd, int count, ArrayList<Node> Tpth) { // creates a pop up window

        startX = sx; // initializing private variables, need to offset by *50 to fit scale of grid
        startY = sy;
        goalX = gx;
        goalY = gy;
        cols = col;
        rows = row;
        blocked = bkd;
        bkdCount = count;
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
            scale = 60; 
        }
        else if(rows <= 15 && cols <= 25){
            scale = 40; 
        }
        else if(rows <= 25 && cols <= 45){
            scale = 25; 
        }
        else if(rows <= 30 && cols <= 50){
            scale = 20; 
        }
        else if(rows <= 40 && cols <= 70){
            scale = 16; 
        }
        else if(rows <= 50 && cols <= 100){
            scale = 12; 
        }
        else{
            scale = 10;
        }

        // draws grid rectangles
        for (int x = 50; x < (scale * cols)+50; x += scale) {
            for (int y = 50; y < (scale * rows)+50; y += scale) {
                g.drawRect(x, y, scale, scale);
            }
        }

        // adding numbers to grid 
        g.setFont(new Font("TimesRoman", Font.PLAIN, 7));
        int num1 = 1;
        for(int x = 50; x <= (scale*cols)+50; x += scale){
            String number1 = Integer.toString(num1);
            g.drawString(number1, x, 45);
            num1++;
        }
        int num2 = 1;
        for(int y = 50; y <= (scale*rows)+50; y += scale){
            String number2 = Integer.toString(num2);
            g.drawString(number2, 40, y);
            num2++;
        }

        // color in blocked cells
        g.setColor(Color.lightGray);
        int pt1 = 0;
        int pt2 = 0;
        int blockedX = 0;
        int blockedY = 0;
        for (int count = bkdCount; count > 0; count--) {
            blockedX = ((blocked[pt1][pt2]-1) * scale)+50;
            blockedY = ((blocked[pt1][pt2 + 1]-1) * scale)+50;
            g.fillRect(blockedX, blockedY, scale, scale);
            pt1++;
        }

        // circles start and goal vertex
        int dia = 20; // diameter of circle
        g.setColor(Color.cyan);
        g.drawOval((startX-1) * scale - (dia / 2)+50, (startY-1) * scale - (dia / 2)+50, dia, dia);
        g.setColor(Color.green);
        g.drawOval((goalX-1) * scale - (dia / 2)+50, (goalY-1) * scale - (dia / 2)+50, dia, dia);

        // labels start and goal vertex
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        g.drawString("Start Vertex", (startX-1) * scale - (dia / 2)+50, (startY-1) * scale - (dia / 2)+50);
        g.drawString("Goal Vertex", (goalX-1) * scale - (dia / 2)+50, (goalY-1) * scale - (dia / 2)+50);

        // add the final path theta*
        if(Tpath!=null){
            g.setColor(Color.blue);
            int[] xcoords = new int[Tpath.size()];
            int[] ycoords = new int[Tpath.size()];
            for(int i = 0; i < Tpath.size(); i++){
                xcoords[i] = (Tpath.get(i).col) * scale + 50;
                ycoords[i] = (Tpath.get(i).row) * scale + 50;
            }
            g.drawPolyline(xcoords,ycoords,Tpath.size());

        }

        // key
        g.drawLine(((cols+1)*scale+75), 75, ((cols+1)*scale+100), 75);
        g.drawString("Theta* Path", ((cols+1)*scale+100), 90);

       
    }

}