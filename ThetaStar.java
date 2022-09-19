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
     * @return false if no obstacle in path, true if there is an obstacle in path
     */
    public boolean ObstacleInPath(Node s, Node si){

        System.out.println("startX: " + startX + " startY: " + startY + " goalX: " + goalX + " goalY: " + goalY + " cols: " + cols + " rows: " + rows + " ");
        // initialize variables
        int x0 = s.col;
        int y0 = s.row;
        int x1 = si.col;
        int y1 = si.row;
        int f = 0; // f = g [distance from start vertex] + h [heuristic value]
        int dy = y1 - y0;
        int dx = x1 - x0;
        int sx, sy;

        if(dy < 0){
            dy = -dy;
            sy = -1;
        }
        else sy = 1;

        if(dx < 0){
            dx = -dx;
            sx = -1;
        }
        else sx = 1;

        if(dx >= dy){
            while(x0 != x1){
                f = f + dy;
                if(f >= dx){
                    if(grid[x0 + ((sx - 1)/2)][y0 + ((sy - 1)/2)] > 0) return false;
                    y0 += sy;
                    f -= dx;
                }
                if(f != 0 && grid[x0 + ((sx - 1)/2)][y0 + ((sy - 1)/2)] > 0) return false;
                if(dy == 0 && grid[x0 + ((sx - 1)/2)][y0] > 0 && grid[x0 + ((sx - 1)/2)][y0 - 1] > 0) return false;
                x0 += sx;
            }
        }
        else{
            while(y0 != y1){
                f = f + dx;
                if(f >= dy){
                    if(grid[x0 + ((sx - 1)/2)][y0 + ((sy - 1)/2)] > 0) return false;
                    x0 += sx;
                    f -= dy;
                }
                if(f != 0 && grid[x0 + ((sx - 1)/2)][y0 + ((sy - 1)/2)] > 0) return false;
                if(dx == 0 && grid[x0][y0 + ((sy - 1)/2)] > 0 && grid[x0 - 1][y0 + ((sy - 1)/2)] > 0) return false;
                y0 += sy;
            }
        }
        return true;
    }

}
