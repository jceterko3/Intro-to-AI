// adding something so i can push DELETE THIS

public class Node{
    public double h;
    public double g;
    public double f;
    public Node parent;
    public int row;
    public int col;


    public Node(int row, int col, double h){ //3 param constructor
        this.row = row;
        this.col = col;
        this.h=h;
    }


    public String toString(Node n){
        return (n.col+1)+","+(n.row+1)+"      ";
    }
    
}
