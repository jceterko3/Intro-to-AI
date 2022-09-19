import java.util.ArrayList;

public class ThetaStar {

    public Node[][] graph;
    private int startX; // x coordinate of start vertex
    private int startY; // y coordinate of start vertex
    private int goalX; // x coordinate of goal vertex
    private int goalY; // y coordinate of goal vertex
    private int cols;
    private int rows;
    private int[][] grid; // blocked cells
    public ArrayList<Node> path; // shortest path found from start to goal
    public minHeap fringe;

    /**
     * Implements the theta star algorithm to find the shortest any angle path from the start to goal vertex.
     * @param sx x coordinate of start vertex
     * @param sy y coordinate of start vertex
     * @param gx x coordinate of goal vertex
     * @param gy y coordinate of goal vertex
     * @param col number of columns in grid
     * @param row number of rows in grid
     * @return the shortest path from start vertex to goal vertex
     */
    public ArrayList<Node> ThetaStarAlgorithm(int sx, int sy, int gx, int gy, int col, int row, int[][] gridCells){

        // initializing variables
        startX = sx;
        startY = sy;
        goalX = gx;
        goalY = gy;
        cols = col;
        rows = row;
        grid = gridCells;
        path = new ArrayList<Node>();

        return path;
    }

    /**
     * Uses Bresenham's line drawing algorithm to perform line of sight checks for obstacle nodes
     * @param s starting node
     * @param si successor node
     * @return true if no obstacle in path, false if there is an obstacle in path
     */
    public boolean UnblockedPath (Node s, Node si){

        System.out.println("startX: " + startX + " startY: " + startY + " goalX: " + goalX + " goalY: " + goalY + " cols: " + cols + " rows: " + rows + " ");
        // initialize variables
        int x0 = s.row;
        int y0 = s.col;
        int x1 = si.row;
        int y1 = si.col;
        int f = 0; // f = g [distance from start vertex] + h [heuristic value]
        int dy = y1 - y0;
        int dx = x1 - x0;
        int sx, sy;
        System.out.println("x0: " + x0 + " y0: " + y0 + " x1: " + x1 + " y1: " + y1 + " dx: " + dx + " dy: " + dy + " ");
        // initialize variables
        if(dy < 0){
            dy = -dy;
            sy = -1;
            System.out.println("going1 \t dy: "+dy+"\tsy: "+sy);
        }
        else{
            sy = 1;
            System.out.println("going2 \t dy: "+dy+"\tsy: "+sy);
        }

        if(dx < 0){
            dx = -dx;
            sx = -1;
            System.out.println("going3 \t dx: "+dx+"\tsx: "+sx);
        }
        else{
            sx = 1;
            System.out.println("going4 \t dx: "+dx+"\tsx: "+sx);
        }

        if(dx >= dy){
            System.out.println("going5");
            while(x0 != x1){
                f = f + dy;
                System.out.println("going6 \tf: "+f);
                if(f >= dx){
                    int x = x0 + ((sx - 1)/2);
                    int y = y0 + ((sy - 1)/2);
                    System.out.println("going7 grid(" + x + "," + y + ") = " + grid[x][y]);
                    if(grid[x][y] > 0) return false;
                    y0 += sy;
                    f -= dx;
                    System.out.println("going8 \tf: "+f+"\ty0: " + y0);
                }
                int x = x0 + ((sx - 1)/2);
                int y = y0 + ((sy - 1)/2);
                if(f != 0 && grid[x][y] > 0){
                    System.out.println("going9 grid(" + x + "," + y + ") = " + grid[x][y]);
                    return false;
                }
                if(dy == 0 && grid[x0 + ((sx - 1)/2)][y0] > 0 && grid[x0 + ((sx - 1)/2)][y0 - 1] > 0){
                    System.out.println("going10 grid(" + x + "," + y0 + ") = " + grid[x][y0]);
                    System.out.println("grid(" + x + "," + (y0-1) + ") = " + grid[x][y0-1]);
                    return false;
                }
                x0 += sx;
                System.out.println("going11 x0: " + x0);
            }
        }
        else {
            System.out.println("going12");
            while (y0 != y1) {
                f = f + dx;
                System.out.println("going13 \tf: " + f);
                if (f >= dy) {
                    int x = x0 + ((sx - 1) / 2);
                    int y = y0 + ((sy - 1) / 2);
                    System.out.println("going14 grid(" + x + "," + y + ") = " + grid[x][y]);
                    if (grid[x0 + ((sx - 1) / 2)][y0 + ((sy - 1) / 2)] > 0) return false;
                    x0 += sx;
                    f -= dy;
                    System.out.println("going15 \tf: " + f + "\tx0: " + x0);
                }
                int x = x0 + ((sx - 1) / 2);
                int y = y0 + ((sy - 1) / 2);
                if (f != 0 && grid[x0 + ((sx - 1) / 2)][y0 + ((sy - 1) / 2)] > 0) {
                    System.out.println("going16 grid(" + x + "," + y + ") = " + grid[x][y]);
                    return false;
                }
                if (dx == 0 && grid[x0][y0 + ((sy - 1) / 2)] > 0 && grid[x0 - 1][y0 + ((sy - 1) / 2)] > 0) {
                    System.out.println("going17 grid(" + x0 + "," + y + ") = " + grid[x0][y]);
                    System.out.println("grid(" + (x0 - 1) + "," + y + ") = " + grid[x0 - 1][y]);
                    return false;
                }
                y0 += sy;
                System.out.println("going18 y0: " + y0);
            }
        }
        System.out.println("going19TRUE");
        return true;
    }

}
