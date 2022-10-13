import java.util.*;

public class ThetaStar {

    public Node[][] graph;
    private int startX; // x coordinate of start vertex
    private int startY; // y coordinate of start vertex
    private int goalX; // x coordinate of goal vertex
    private int goalY; // y coordinate of goal vertex
    private int cols;
    private int rows;
    private int[][] gridCells; // grid cells
    public ArrayList<Node> path; // shortest path found from start to goal
    public minHeap fringe;
    private Map<Integer,Set<Integer>> blocked; // includes all blocked cells

    /**
     * Implements the theta star algorithm to find the shortest any angle path from the start to goal vertex.
     * @param startX x coordinate of start vertex
     * @param startY y coordinate of start vertex
     * @param goalX x coordinate of goal vertex
     * @param goalY y coordinate of goal vertex
     * @param cols number of columns in grid
     * @param rows number of rows in grid
     * @param gridCells matrix where 0 represents free cells and 1 represents blocked cells of the grid
     * @return the shortest path from start vertex to goal vertex
     */
    public ArrayList<Node> ThetaStarAlgorithm(int startX, int startY, int goalX, int goalY, int cols, int rows, int[][] gridCells, Map<Integer,Set<Integer>> blocked){

        // initializing variables
        this.startX = startX;
        this.startY = startY;
        this.goalX = goalX;
        this.goalY = goalY;
        this.cols = cols;
        this.rows = rows;
        this.gridCells = gridCells;
        this.path = new ArrayList<Node>();
        this.blocked = blocked;

        // initialize graph
        graph = new Node[rows + 1][cols + 1];
        Node goalNode = new Node (goalY - 1, goalX - 1, 0);
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= cols; j++) {
                graph[i][j] = new Node(i, j, cval(new Node(i, j, 0), goalNode));
            }
        }

        System.out.println("Columns: " + cols + "   Rows: " + rows);
        // TODO: Delete below print
        // for(int[] i : blocked){
        //     for(int j : i){
        //         System.out.print(j + " ");
        //     }
        //     System.out.println();
        // }
        for(int[] i : gridCells){
            for(int j : i){
                System.out.print(j + " ");
            }
            System.out.println();
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
            //fringe.print();
            s = fringe.pop();

            if (s.row == goal.row && s.col == goal.col) {
                path.add(s);
                while (!(s.parent.col == s.col && s.parent.row == s.row)) {
                    path.add(s.parent);
                    s = s.parent;
                }

                Collections.reverse(path);

                return path;
            }

            closed.add(s);
            //System.out.println("added to closed: "+(s.col+1)+", "+(s.row+1));
            ArrayList<Node> succs = succ(s);


            for (int i = 0; i < succs.size(); i++) {
                Node si = succs.get(i);
                //System.out.println("testing succ:"+(si.col+1)+", "+(si.row+1));
                if (si == null) {
                    break;
                }
                if (!closed.contains(si)) {
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

        System.out.println("theta* path not found");
        return null;
    }

    private void updateVertex(Node s, Node si) {
        boolean isFringe = false;
        double c = cval(s,si);
        double no1 = Math.round(s.parent.g*1000.0)/1000.0;
        double no2 = Math.round(si.g*1000.0)/1000.0;
        if(UnblockedPath(s.parent,si)){
            if(no1 + cval(s.parent,si) < no2){
                si.g = s.parent.g + cval(s.parent,si);
                si.parent = s.parent;
                for(int j = 0; j < fringe.A.length; j++) {
                    if(fringe.A[j] == si) {
                        isFringe = true;
                        break;
                    }
                }
                if(isFringe) fringe.remove(si);
                si.f = si.g + si.h;
                fringe.insert(si);
            }
        }
        if ((s.g + c) < si.g) {
            si.g = s.g + c;
            si.parent = s;
            for (int j = 0; j < fringe.A.length; j++) {
                if (fringe.A[j] == si) {
                    isFringe = true;
                    break;
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
                if (!(row == r && col == c)){
                    boolean isBlocked = false;
                    int vr = Math.min(row, r) + 1;
                    int vc = Math.min(col, c) + 1;
                    // if horizontal movement
                    if (row == r) {
                        boolean bottom = false;
                        boolean top = false;

                        if(r==rows){
                            bottom = true;
                        }
                        if(r == 0){
                            top = true;

                        }
                        // for (int i = 0; i < blocked.length; i++) {
                        //     if (blocked[i][0] == vc && blocked[i][1] == vr) {
                        //         bottom = true;
                        //     }
                        //     if (blocked[i][0] == vc && blocked[i][1] == (vr-1)) {
                        //         top = true;
                        //     }
                        // }
                        if(blocked.containsKey(vc) && blocked.get(vc).contains(vr)) bottom = true;
                        if(blocked.containsKey(vc) && blocked.get(vc).contains(vr - 1)) top = true;

                        if(top && bottom){
                            isBlocked = true;
                        }

                    } else if (col == c) {
                        boolean right = false;
                        boolean left = false;

                        if(c==cols){
                            right = true;
                        }
                        if(c == 0){
                            left = true;

                        }
                        // for (int i = 0; i < blocked.length; i++) {
                        //     if (blocked[i][0] == vc && blocked[i][1] == vr) {
                        //         right = true;
                        //     }
                        //     if (blocked[i][0] == (vc-1) && blocked[i][1] == vr) {
                        //         left = true;
                        //     }
                        // }
                        if(blocked.containsKey(vc) && blocked.get(vc).contains(vr)) right = true;
                        if(blocked.containsKey(vc - 1) && blocked.get(vc - 1).contains(vr)) left = true;
                       // System.out.println((vc-1)+","+(vr));
                        if(left && right){
                            isBlocked = true;
                        }

                        if(c == 0 || c == cols){
                            isBlocked=true;
                        }
                        int newc = vc-1; //left vs right movement
                        if(r<row){
                            newc = vc+1;
                            //  System.out.println("down movement: from "+c+" to "+col);
                        }
                        // for (int i = 0; i < blocked.length; i++) {
                        //     if (blocked[i][0] == vc && blocked[i][1] == vr) {
                        //         //System.out.println("up is blocked");
                        //         for(int j=0; j<blocked.length; j++){
                        //             if (blocked[j][0] == (newc) && blocked[j][1] == (vr)) {
                        //                 isBlocked = true;
                        //             }
                        //         }

                        //     }
                        // }
                        if(blocked.containsKey(vc) && blocked.get(vc).contains(vr))
                            if(blocked.containsKey(newc) && blocked.get(newc).contains(vr)) isBlocked = true;

                    } 
                    else if(blocked.containsKey(vc) && blocked.get(vc).contains(vr)) isBlocked = true;

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

    // need to edit these
    public double hval(int vertexX, int vertexY){

        double val = 0;
        int x = vertexX-1;
        int y = vertexY-1;

        val = Math.round(graph[y][x].h * 100.0) / 100.0;
        return val;
    }
    public double gval(int vertexX, int vertexY){

        double val = 0;
        int x = vertexX-1;
        int y = vertexY-1;

        val = Math.round(graph[y][x].g * 100.0) / 100.0;
        return val;
    }
    public double fval(int vertexX, int vertexY){

        double val = 0;
        int x = vertexX-1;
        int y = vertexY-1;

        val = Math.round(graph[y][x].f * 100.0) / 100.0;
        return val;
    }
    public double cval(Node s, Node si){
        return Math.sqrt(Math.pow(Math.abs(s.row - si.row),2) + Math.pow(Math.abs(s.col - si.col),2));
    }

    /**
     * Uses Bresenham's line drawing algorithm to perform line of sight checks for obstacle nodes
     * @param s starting node
     * @param si successor node
     * @return true if no obstacle in path, false if there is an obstacle in path
     */
    private boolean UnblockedPath (Node s, Node si){

        //System.out.println("startX: " + startX + " startY: " + startY + " goalX: " + goalX + " goalY: " + goalY + " cols: " + cols + " rows: " + rows + " ");
        // initialize variables
        int x0 = s.row + 1;
        int y0 = s.col + 1;
        int x1 = si.row + 1;
        int y1 = si.col + 1;
        int f = 0; // f = g [distance from start vertex] + h [heuristic value]
        int dy = y1 - y0;
        int dx = x1 - x0;
        int sx, sy;
        System.out.println("x0: " + x0 + " y0: " + y0 + " x1: " + x1 + " y1: " + y1);
        // initialize variables
        if(dy < 0){
            dy = -dy;
            sy = -1;
//            System.out.println("going1 \t dy: "+dy+"\tsy: "+sy);
        }
        else{
            sy = 1;
//            System.out.println("going2 \t dy: "+dy+"\tsy: "+sy);
        }

        if(dx < 0){
            dx = -dx;
            sx = -1;
//            System.out.println("going3 \t dx: "+dx+"\tsx: "+sx);
        }
        else{
            sx = 1;
//            System.out.println("going4 \t dx: "+dx+"\tsx: "+sx);
        }

        if(dx >= dy){
//            System.out.println("going5");
            while(x0 != x1){
                f = f + dy;
//                System.out.println("going6 \tf: "+f);
                if(f >= dx){
                    int x = x0 + ((sx - 1)/2);
                    int y = y0 + ((sy - 1)/2);
                   System.out.println("going7 grid(" + x + "," + y + ") = " + gridCells[x][y]);
                    if(isBlocked(x, y)) return false;
                    y0 += sy;
                    f -= dx;
//                    System.out.println("going8 \tf: "+f+"\ty0: " + y0);
                }
                int x = x0 + ((sx - 1)/2);
                int y = y0 + ((sy - 1)/2);
                System.out.println("going9 grid(" + x + "," + y + ") = " + gridCells[x][y]);
                if(f != 0 && isBlocked(x, y)){
                    return false;
                }
                if(dy == 0 && isBlocked(x, y0) && isBlocked(x, y0 - 1)){
                   System.out.println("going10 grid(" + x + "," + y0 + ") = " + gridCells[x][y0]);
                   System.out.println("grid(" + x + "," + (y0-1) + ") = " + gridCells[x][y0-1]);
                    return false;
                }
                x0 += sx;
//                System.out.println("going11 x0: " + x0);
            }
        }
        else {
//            System.out.println("going12");
            while (y0 != y1) {
                f = f + dx;
                int x = x0 + ((sx - 1) / 2);
                int y = y0 + ((sy - 1) / 2);
                System.out.println("going14 grid(" + x + "," + y + ") = " + gridCells[x][y]);
//                System.out.println("going13 \tf: " + f);
                if (f >= dy) {
                    if (isBlocked(x, y)) return false;
                    x0 += sx;
                    f -= dy;
//                    System.out.println("going15 \tf: " + f + "\tx0: " + x0);
                }
                x = x0 + ((sx - 1) / 2);
                y = y0 + ((sy - 1) / 2);
                System.out.println("going16 grid(" + x + "," + y + ") = " + gridCells[x][y]);
                if (f != 0 && isBlocked(x, y)) {
                    return false;
                }
                if (dx == 0 && isBlocked(x0, y) && isBlocked(x0 - 1, y)) {
                   System.out.println("going17 grid(" + x0 + "," + y + ") = " + gridCells[x0][y]);
                   System.out.println("grid(" + (x0 - 1) + "," + y + ") = " + gridCells[x0 - 1][y]);
                    return false;
                }
                y0 += sy;
//                System.out.println("going18 y0: " + y0);
            }
        }
//        System.out.println("going19TRUE");
        return true;
    }

    private boolean isBlocked(int x, int y){
        if(x < 1 || y < 1 || x > rows || y > cols){
            System.out.println("isBlocked() (" + x + "," + y + ")  rows: " + rows + "cols: " + cols + " - out of bounds");
            return true;
        } 
        if(blocked.containsKey(y) && blocked.get(y).contains(x)){
            System.out.println("isBlocked() - blocked in map");
            return true;
        } 
        return false;
    }

}
