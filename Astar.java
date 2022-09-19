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
    public ArrayList<Node> path; // shortest path found from start to goal
    public minHeap fringe;

    // A* algorithm
    public ArrayList<Node> A(int sx, int sy, int gx, int gy, int col, int row, int[][] bkd, int count) {

        // initializing private variables
        startX = sx; 
        startY = sy;
        goalX = gx;
        goalY = gy;
        cols = col;
        rows = row;
        blocked = bkd;
        path = new ArrayList<Node>();
        
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
        ArrayList<Node> closed = new ArrayList<Node>();


        while (!fringe.isEmpty()) {
            fringe.print();
            s = fringe.pop();

            if (s.row == goal.row && s.col == goal.col) {
                path.add(s);

                int help = 0;
                while (!(s.parent.col == s.col && s.parent.row == s.row)) {
                    path.add(s.parent);
                    s = s.parent;
                    System.out.println("ADDED NODE:"+(s.col+1)+", "+(s.row+1));
                    help++;
                }

                Collections.reverse(path);

                return path;

            }

            closed.add(s);
            System.out.println("closed: "+(s.col+1)+", "+(s.row+1));
            ArrayList<Node> succs = succ(s);


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

        System.out.println("path not found");
        return null;

    }

    private void updateVertex(Node s, Node si) {

        double c = Math.sqrt(Math.abs(s.row - si.row) + Math.abs(s.col - si.col));
        if ((s.g + c) < si.g) {
            si.g = s.g + c;
            si.parent = s;
            boolean isFringe = false;
            for (int j = 0; j < fringe.A.length; j++) {
                if (fringe.A[j] == si) {
                    isFringe = true;
                }
            }
            if(isFringe){
                fringe.remove(si);
            }
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

}
