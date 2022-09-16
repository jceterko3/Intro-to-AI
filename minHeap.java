public class minHeap {
    public Node[] A;
    private int size;
    private int max;

    public minHeap(Node n, int max) {
        this.max = max;
        this.size = 0;
        A = new Node[max + 1];
        A[1] = n; // first slot is empty
    }

    public boolean isEmpty() {
        if (A[1] == null) {
            return true;
        }
        return false;
    }

    private int parent(int ptr) {
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
        if (size >= max) {
            System.out.println("exceeds max size");// delete later
            return;
        }

        size++;
        A[size] = node;
        int curr = size;

        while (A[curr].f < A[parent(curr)].f) {
            swap(curr, parent(curr));
            curr = parent(curr);
        }
    }

    public Node pop() {

        Node popped = A[1];
        A[1] = A[size];
        size = size - 1;
        heapify(1);

        return popped;
    }

    public void remove(Node s) {
        int index = find(s);
        if (index >= 0) {
            A[index] = A[size];
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
            if (rightChild(ptr) <= size)
                if (A[leftChild(ptr)].f < A[rightChild(ptr)].f) {
                    swap_ptr = leftChild(ptr);
                } else {
                    swap_ptr = rightChild(ptr);
                }
            else
                swap_ptr = leftChild(ptr);

            if (A[ptr].f > A[leftChild(ptr)].f || A[ptr].f > A[rightChild(ptr)].f) {
                swap(ptr, swap_ptr);
                heapify(swap_ptr);
            }
        }
    }

    public void print() {
        for (int i = 1; i < A.length; i++) {
            if(A[i]!=null){
                System.out.println((A[i].col+1)+", "+(A[i].row+1));

            }
        
        }
    }
}