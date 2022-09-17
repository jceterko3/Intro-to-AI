import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import java.io.InputStream;
import java.io.FileInputStream;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;

public class Frame extends JFrame { // a class to create the GUI

    public Node[][] graph;
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

    public Frame(int sx, int sy, int gx, int gy, int col, int row, int[][] bkd, int count) { // creates a pop up window

        startX = sx; // initializing private variables, need to offset by *50 to fit scale of grid
        startY = sy;
        goalX = gx;
        goalY = gy;
        cols = col;
        rows = row;
        blocked = bkd;
        bkdCount = count;
        // path = pth;

        setSize(100 * cols, 100 * rows);
        setTitle("GRID PATH");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // calculate heuristic values and construct
        graph = new Node[rows + 1][cols + 1];
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= cols; j++) {
                double xdiff = Math.abs(j + 1 - goalX);
                double ydiff = Math.abs(i + 1 - goalY);
                graph[i][j] = new Node(i, j,
                        Math.sqrt(2) * Math.min(xdiff, ydiff) + Math.max(xdiff, ydiff) - Math.min(xdiff, ydiff));
            }
        }

    }

    public ArrayList<Node> Astar() {
        path = new ArrayList<Node>();

        // System.out.println("TRULY INTIALpath length: " + path.size());

        Node s;
        Node start = graph[startY - 1][startX - 1];
        Node goal = graph[goalY - 1][goalX - 1];
        start.parent = start;
        fringe = new minHeap(start, (rows + 1) * (cols + 1));
        ArrayList<Node> closed = new ArrayList<Node>();
        // System.out.println("FRINGE 1: " + (fringe.A[1].col + 1) + ", " +
        // (fringe.A[1].row + 1));

        while (!fringe.isEmpty()) {
            // System.out.println("reached while loop");
            // fringe.print();
            s = fringe.pop();
            // System.out.println("ROWWW:" + goal.row);
            if (s.row == goal.row && s.col == goal.col) {
                // System.out.println("path found");
                path.add(s);
                // System.out.println("INITIAL path length: " + path.size());

                int help = 0;
                while (!(s.parent.col == s.col && s.parent.row == s.row)) {
                    // System.out.println("addednode" + help + ": " + (s.col + 1) + ", " + (s.row +
                    // 1));
                    path.add(s.parent);
                    // System.out.println(help + "path length: " + path.size());

                    s = s.parent;
                    help++;
                }
                // System.out.println("path length: " + path.size());
                // System.out.println("RETURN PATH");
                Collections.reverse(path);

                return path;

                // return path
            }
            closed.add(s);
            ArrayList<Node> succs = succ(s);
            // System.out.println(succs.toString());
            // System.out.println(succs.get(0).toString());
            for (int i = 0; i < succs.size(); i++) {
                Node si = succs.get(i);
                if (si == null) {
                    break;
                }
                if (closed.indexOf(si) == -1) {
                    boolean isFringe = false;
                    for (int j = 0; j < fringe.A.length; j++) {
                        if (fringe.A[j] == si) {
                            isFringe = true;
                        }
                    }
                    if (!isFringe) {
                        si.g = Double.POSITIVE_INFINITY;
                        si.parent = null;
                    }
                    updateVertex(s, si);
                }
            }
        }

        return null;

    }

    private void updateVertex(Node s, Node si) {
        double c = Math.sqrt(Math.abs(s.row - si.row) + Math.abs(s.col - si.col));
        if ((s.g + c) < si.g) {
            si.g = s.g + c;
            si.parent = s;
            fringe.remove(si);
            si.f = si.g + si.h;
            fringe.insert(si);
        }
    }

    private ArrayList<Node> succ(Node s) {
        int r = s.row;
        int c = s.col;
        ArrayList<Node> succs = new ArrayList<Node>();
        int count = 0;

        for (int row = r - 1; row < (r + 2); row++) {
            for (int col = c - 1; col < (c + 2); col++) {
                if (!(row == r && col == c)) {
                    boolean isBlocked = false;
                    int vr = Math.min(row, r) + 1;
                    int vc = Math.min(col, c) + 1;
                    // if horizontal movement
                    if (row == r) {
                        for (int i = 0; i < blocked.length; i++) {
                            if (blocked[i][0] == vc && blocked[i][1] == vr) {
                                if (blocked[i][0] == (vc) && blocked[i][1] == (vr - 1)) {
                                    isBlocked = true;
                                }

                            }
                        }

                    } else if (col == c) {
                        for (int i = 0; i < blocked.length; i++) {
                            if (blocked[i][0] == vc && blocked[i][1] == vr) {
                                if (blocked[i][0] == (vc - 1) && blocked[i][1] == (vr)) {
                                    isBlocked = true;
                                }

                            }
                        }
                    } else {
                        for (int i = 0; i < blocked.length; i++) {
                            if (blocked[i][0] == vc && blocked[i][1] == vr) {
                                isBlocked = true;
                            }
                        }
                    }

                    if (!isBlocked) {
                        if (row >= 0 && row <= rows) {
                            if (col >= 0 && col <= cols) {

                                succs.add(graph[row][col]);
                                count++;
                                // System.out.println("added successor: " +(col+1)+", "+(row+1));
                                // System.out.println(succs[count].toString());

                            }
                        }
                    }
                }
            }

        }

        return succs;

    }

    public void print_hval_A() {
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= cols; j++) {
                System.out.print(Math.round(graph[i][j].h * 100.0) / 100.0 + "   ");
            }
            System.out.println();
        }
    }

    // creates grid with components
    public void paint(Graphics g) {

        // draws grid rectangles
        for (int x = 50; x <= 50 * cols; x += 50) {
            for (int y = 50; y <= 50 * rows; y += 50) {
                g.drawRect(x, y, 50, 50);
            }
        }
        
                // adding numbers to grid 
        int num1 = 1;
        for(int x = 50; x <= 50*(cols+1); x += 50){
            String number1 = Integer.toString(num1);
            g.drawString(number1, x, 40);
            num1++;
        }
        int num2 = 1;
        for(int y = 50; y <= 50*(rows+1); y += 50){
            String number2 = Integer.toString(num2);
            g.drawString(number2, 40, y);
            num2++;
        }

        // color in blocked cells
        g.setColor(Color.gray);
        int pt1 = 0;
        int pt2 = 0;
        int blockedX = 0;
        int blockedY = 0;
        for (int count = bkdCount; count > 0; count--) {
            blockedX = blocked[pt1][pt2] * 50;
            blockedY = blocked[pt1][pt2 + 1] * 50;
            g.fillRect(blockedX, blockedY, 50, 50);
            pt1++;
        }

        // circles start and goal vertex
        int dia = 25; // diameter of circle
        g.setColor(Color.blue);
        g.drawOval(startX * 50 - (dia / 2), startY * 50 - (dia / 2), dia, dia);
        g.setColor(Color.green);
        g.drawOval(goalX * 50 - (dia / 2), goalY * 50 - (dia / 2), dia, dia);

        // labels start and goal vertex
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        g.drawString("Start Vertex", startX * 50 - (dia / 2), startY * 50 - (dia / 2));
        g.drawString("Goal Vertex", goalX * 50 - (dia / 2), goalY * 50 - (dia / 2));

        // add the final path

    }

}
