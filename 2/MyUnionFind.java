public class MyUnionFind {
    private int[] parent;           // parent[i] = parent of i
    private int[] size;             // size[i] = number of objects in subtree rooted at i
    
    private int count;              // number of components
    private int maxComponentSize = 0; // max component tree

    public MyUnionFind(int N) {
        count = N;
        parent = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int count() {
        return count;
    }

    public int find(int p) { // root
        validate(p);
        while (p != parent[p]){
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    private void validate(int p) {
        int N = parent.length;
        if (p < 0 || p >= N) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + N);
        }
    }

    public boolean connected(int p, int q) { // find
        return find(p) == find(q);
    }

    public int getMaxComponentSize(){
        return maxComponentSize;
    }

    private void setMaxComponentSize(int size){
        if (size > maxComponentSize){ maxComponentSize = size; }
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
            setMaxComponentSize(size[rootQ]);
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
            setMaxComponentSize(size[rootP]);
        }
        count--;
    }
}