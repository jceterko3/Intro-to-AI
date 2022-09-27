// adding something so i can push DELETE THIS

public class minHeap {
    public Node[] A;
    private int size;
    private int max;

    public minHeap(int max) {
        this.max = max;
        this.size = 0;
        A = new Node[max + 1];
        A[0] = null;
    }

    public boolean isEmpty() {
        if (A[1] == null) {
            return true;
        }
        return false;
    }

    private int parent(int ptr) {
        if(ptr==1){
            return ptr;
        }
        return ptr / 2;
    }

    private int leftChild(int ptr) {
        return (2 * ptr);
    }

    private int rightChild(int ptr) {
        return (2 * ptr) + 1;
    }

    // swap two nodes
    private void swap(int a, int b) {
        Node temp;
        temp = A[a];
        A[a] = A[b];
        A[b] = temp;
    }

    public void insert(Node node) {
      //  System.out.println("INSERTING: "+ (node.col+1)+", "+(node.row+1));
        if (size >= max) {
            return;
        }
        if (size==0){
            A[1]=node;
            size++;
            return;
        }
        size++;
        A[size] = node;
        int curr = size;
        while ((A[curr].f < A[parent(curr)].f)) {

            swap(curr, parent(curr));
            curr = parent(curr);
          //  System.out.println("test: "+parent(curr));
        }

        double epsilon = 0.000001d;


        if(Math.abs(A[curr].f-A[parent(curr)].f) < epsilon){
           // System.out.println("tiebreak: "+(A[curr].col+1)+", "+(A[curr].row+1)+" and "+(A[parent(curr)].col+1)+", "+(A[parent(curr)].row+1));

            if(A[curr].g>A[parent(curr)].g){
                swap(curr, parent(curr));
                curr = parent(curr);
            }

        }
      //  System.out.println("inserted: "+(A[curr].col+1)+", "+(A[curr].row+1)+", index="+curr);
/*
        System.out.print("FRINGE:  ");

        for (int i = 1; i < A.length; i++) {
            if (A[i] != null) {
                System.out.print((A[i].col + 1) + ", " + (A[i].row + 1) + ", f=" + (Math.round(A[i].f*1000.0) / 1000.0)
                        + "    ");

            }

        }
        System.out.println();
        */

    }

    public Node pop() {

        Node popped = A[1];
        A[1] = A[size];
        A[size] = null;
        size = size - 1;
        //System.out.println("size: "+size);
        heapify(1);
        //System.out.println("popped "+(popped.col+1)+", "+(popped.row+1));

        return popped;
    }

    public void remove(Node s) {
        int index = find(s);
        if (index >= 0) {
            A[index] = A[size];
            A[size] = null;
            size = size - 1;
            heapify(index);
        }

    }

    public int find(Node s) {
        for (int i = 0; i < A.length; i++) {
            if (A[i] != null) {
                if (A[i].row == s.row && A[i].col == s.col) {
                    return i;
                }

            }

        }
        return -1;
    }

    private boolean isLeaf(int ptr) {
        if (ptr > (size / 2)) {
            return true;
        }
        return false;
    }

    // heapify node at ptr
    private void heapify(int ptr) {
        if (!isLeaf(ptr)) {
            int swap_ptr = ptr;
            if (rightChild(ptr) <= size) {
                if (A[leftChild(ptr)].f < A[rightChild(ptr)].f) {
                    swap_ptr = leftChild(ptr);
                } else {
                    swap_ptr = rightChild(ptr);
                }
                if (A[ptr].f > A[leftChild(ptr)].f || A[ptr].f > A[rightChild(ptr)].f) {
                    swap(ptr, swap_ptr);
                    heapify(swap_ptr);
                }
            } else {
                swap_ptr = leftChild(ptr);   
                if (A[ptr].f > A[leftChild(ptr)].f) {
                    swap(ptr, swap_ptr);
                    heapify(swap_ptr);
                }             
            }
            
        }
    }

    public void print() {
        System.out.print("FRINGE:  ");

        for (int i = 1; i < A.length; i++) {
            if (A[i] != null) {
                System.out.print("index:"+i+", "+(A[i].col + 1) + ", " + (A[i].row + 1) + ", f=" + (Math.round(A[i].f) * 100.0 / 100.0)
                        + "    ");

            }

        }
        System.out.println();

    }
}