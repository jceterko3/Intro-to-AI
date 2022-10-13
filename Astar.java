// adding something so i can push DELETE THIS

import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;

public class Astar {

    public Node[][] graph;
    private int startX; // x coord of start vertex
    private int startY; // y coord of start vertex
    private int goalX; // x coord of goal vertex
    private int goalY; // y coord of goal vertex
    private int cols;
    private int rows;
    private int[][] blocked; // includes all blocked cells
    public Node[] path; // shortest path found from start to goal
    public minHeap fringe;

    // A* algorithm
    public Node[] A(int sx, int sy, int gx, int gy, int col, int row, int[][] bkd, int count) {

        // initializing private variables
        startX = sx;
        startY = sy;
        goalX = gx;
        goalY = gy;
        cols = col;
        rows = row;
        blocked = bkd;
        path = new Node[(rows+1)*(cols)+(rows)*(cols+1)];
        int ip = 0;

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

        // setting up A* start, goal, fringe, parents, closed list
        Node s;
        Node start = graph[startY - 1][startX - 1];
        Node goal = graph[goalY - 1][goalX - 1];
        start.parent = start;
        fringe = new minHeap((rows + 1) * (cols + 1));
        start.f = start.h;
        fringe.insert(start);
        //ArrayList<Node> closed = new ArrayList<Node>();
        Node[] closedNew = new Node[(rows+1)*(cols+1)];
        int ic = 0; //closed index

        while (!fringe.isEmpty()) {
            s = fringe.pop();
            if (s.row == goal.row && s.col == goal.col) {
                path[ip]=s;
                ip++;
                while (!(s.parent.col == s.col && s.parent.row == s.row)) {
                    path[ip]=s.parent;
                    ip++;
                    s = s.parent;
                }

                path = reverseArr(path, ip-1);
                return path;
            }

            //closed.add(s);
            closedNew[ic] = s;
            ic++;
            // System.out.println("added to closed: "+(s.col+1)+", "+(s.row+1));
            Node[] succs = succ(s);
           // ArrayList<Node> succs = succ(s);

            for (int i = 0; i < succs.length; i++) {
                Node si = succs[i];
                // System.out.println("testing succ:"+(si.col+1)+", "+(si.row+1));
                if (si == null) {
                    break;
                }
                boolean isClosed = false;
                for(Node n:closedNew){
                    if(n==si){
                        isClosed = true;
                        break;
                    }
                }
                if (isClosed == false) {
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

        System.out.println("A* path not found");
        return null;

    }
    private Node[] reverseArr(Node[] path, int end){
        Node[] pathNew = new Node[end+1];
        int start = 0;
        while(start<end){
            pathNew[start] = path[end];
            pathNew[end] = path[start];
            start++;
            end--;
        }
        return pathNew;
       
    }

    private void updateVertex(Node s, Node si) {

        double c = Math.sqrt(Math.abs(s.row - si.row) + Math.abs(s.col - si.col));
        double no1 = Math.round(s.g*1000.0)/1000.0;
        double no2 = Math.round(si.g*1000.0)/1000.0;

        if ((no1 + c) < no2) {
            si.g = s.g + c;
            si.parent = s;
            boolean isFringe = false;
            for (int j = 0; j < fringe.A.length; j++) {
                if (fringe.A[j] == si) {
                    isFringe = true;
                }
            }
            if (isFringe) {
                fringe.remove(si);
            }
            si.f = si.g + si.h;
            fringe.insert(si);
        }

    }

    private Node[] succ(Node s) {

        int r = s.row;
        int c = s.col;
        Node[] succs = new Node[8];
        int count = 0;

        for (int row = r - 1; row < (r + 2); row++) {
            for (int col = c - 1; col < (c + 2); col++) {
                if (!(row == r && col == c)) {

                    boolean isBlocked = false;
                    int vr = Math.min(row, r) + 1;
                    int vc = Math.min(col, c) + 1;
                   // System.out.println("succ for " + (r + 1) + "," + (c + 1) + ": " + (row + 1) + "," + (col + 1));

                    // if horizontal movement

                    if (row == r) {
                        boolean bottom = false;
                        boolean top = false;

                        if (r == rows) {
                            bottom = true;
                        }
                        if (r == 0) {
                            top = true;

                        }
                        for (int i = 0; i < blocked.length; i++) {
                            if (blocked[i][0] == vc && blocked[i][1] == vr) {
                                bottom = true;
                            }
                            if (blocked[i][0] == vc && blocked[i][1] == (vr - 1)) {
                                top = true;
                            }
                        }
                        
                        if (top && bottom) {
                            isBlocked = true;
                        }

                    } else if (col == c) {
                        boolean right = false;
                        boolean left = false;

                        if (c == cols) {
                            right = true;
                        }
                        if (c == 0) {
                            left = true;

                        }
                        for (int i = 0; i < blocked.length; i++) {
                            if (blocked[i][0] == vc && blocked[i][1] == vr) {
                                right = true;
                            }
                            if (blocked[i][0] == (vc - 1) && blocked[i][1] == vr) {
                                left = true;
                            }
                        }
                        if (left && right) {
                            isBlocked = true;
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
                                succs[count] = graph[row][col];
                               // succs.add(graph[row][col]);
                                //System.out.println("SUCCESS");
                                count++;

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
                // System.out.print(Math.round(graph[i][j].h * 100.0) / 100.0 + " ");
            }
            // System.out.println();
        }
    }

    public double hval(int vertexX, int vertexY) {
        // System.out.println("rows: "+graph.length+", cols: "+graph[0].length);
        double val = 0;
        int x = vertexX - 1;
        int y = vertexY - 1;

        val = Math.round(graph[y][x].h * 1000.0) / 1000.0;
        return val;
    }

    public double gval(int vertexX, int vertexY) {

        double val = 0;
        int x = vertexX - 1;
        int y = vertexY - 1;

        val = Math.round(graph[y][x].g * 1000.0) / 1000.0;
        return val;
    }

    public double fval(int vertexX, int vertexY) {

        double val = 0;
        int x = vertexX - 1;
        int y = vertexY - 1;

        val = Math.round(graph[y][x].f * 1000.0) / 1000.0;
        return val;
    }

}
