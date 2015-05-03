import java.util.Set;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.ArrayList;

public class BFS {
    //private boolean[] marked;  // marked[v] = is there an s->v path?
    private static ArrayList<Vertex> marked = new ArrayList<>();
    //private int[] edgeTo;      // edgeTo[v] = last edge on shortest s->v path
    //private int[] distTo;      // distTo[v] = length of shortest s->v path
    private static Map<Vertex, Integer> distTo = new HashMap<>();


    // BFS from single source
    public static void bfs(Map<Vertex, Bag<Vertex>> graph, Vertex start) {
        Queue<Vertex> q = new Queue<Vertex>();
        marked.add(start);
        distTo.put(start, 0);
        q.enqueue(start);
        while (!q.isEmpty()) {
            Vertex v = q.dequeue();
            for (Vertex w : Mario.adj(v)) {
                if (!reallyContains(w)) {
                    //edgeTo[w] = v;
                    distTo.put(w, distTo.get(w)+1);
                    marked.add(w);
                    q.enqueue(w);
                }
            }
        }
    }

    public static boolean reallyContains(Vertex either){
        for (Vertex other : marked) {
            if(either.x == other.x && either.y == other.y) return true;
        }
        return false;
    }

    /**
     * Is there a directed path from the source <tt>s</tt> (or sources) to vertex <tt>v</tt>?
     * @param v the vertex
     * @return <tt>true</tt> if there is a directed path, <tt>false</tt> otherwise
     */
    public static boolean hasPathTo(Vertex v) {
        if(reallyContains(v)) return true;
        return false;
    }

    /**
     * Returns the number of edges in a shortest path from the source <tt>s</tt>
     * (or sources) to vertex <tt>v</tt>?
     * @param v the vertex
     * @return the number of edges in a shortest path
     */
    public static  int distTo(Vertex v) {
        return distTo.get(v);
    }

    // /**
    //  * Returns a shortest path from <tt>s</tt> (or sources) to <tt>v</tt>, or
    //  * <tt>null</tt> if no such path.
    //  * @param v the vertex
    //  * @return the sequence of vertices on a shortest path, as an Iterable
    //  */
    // public static Iterable<Integer> pathTo(int v) {
    //     if (!hasPathTo(v)) return null;
    //     Stack<Integer> path = new Stack<Integer>();
    //     int x;
    //     for (x = v; distTo[x] != 0; x = edgeTo[x])
    //         path.push(x);
    //     path.push(x);
    //     return path;
    // }



}