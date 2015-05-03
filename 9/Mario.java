import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

/**
 * Vector Mario Class
 * 
 */
public class Mario{

	public static int X;
	public static int Y;
	public static char[][] grid;
	public static Map<Vertex, Bag<Vertex>> graph = new HashMap<>();
	public static ArrayList<Vertex> path = new ArrayList<>();
	private static ArrayList<Vertex> startVertices = new ArrayList<>();
	private static Bag<Vertex> endVertices = new Bag<>();
	private static Queue<Vertex> nextVertex = new Queue<>();
	private static ArrayList<Vertex> visitedVertices = new ArrayList<>();
	private static final char POINTER = 'X';
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_CLS = "\u001b[2J";
    private static final String ANSI_HOME = "\u001b[H";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    Mario() { } // Please do not instansiate this class

    /**
     * Main method
     * @param args
     */
	public static void main(String[] args) {
		StdOut.println("Initializing grid...");
		gridInit(); // Initialize grid
		StdOut.println("Building graph...");
		buildGraph(startVertices.get(0)); // TODO Husk at lave alle grapher af alle start!
		bfs(startVertices.get(0)); // BFS
		animateGrid();

	}


	/**
	 * Clears the terminal
	 */
	public static void clearScreen(){
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
	}

	public static void animateGrid(){
		int count = 1;
		for (int i = path.size() - 1; i >= 0; i--) {
			grid[path.get(i).x][path.get(i).y] = 'X';
			clearScreen();
			printGrid();

			StdOut.println("Distance: " + count++);
			delay(350);	
		}
	}


	/**
	 * Delays for millis time
	 * @param millis
	 */
	public static void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exp) {
        }
    }

	/**
	 * Initialise the grid
	 */
	public static void gridInit(){
		Y = StdIn.readInt();
		X = StdIn.readInt();

		grid =  new char[X][Y];

		for (int row = 0; row < Y; row++){
			StdIn.readChar();
			for (int col = 0; col < X; col++){
				char v = StdIn.readChar();
				switch(v){
					case '\n' : continue;
					case 'S' : startVertices.add(new Vertex(col, row, 0,0));
							   grid[col][row] = v;
					case ' ' : case 'O' : grid[col][row] = v;
					case 'F' : endVertices.add(new Vertex(col, row, 0,0));
							   grid[col][row] = v;
				}				
			}
		}
	}


	/**
	 * Get adjacent vertices
	 * @param  v
	 * @return  
	 */
	public static Bag<Vertex> adj(Vertex v){
		return graph.get(v);
	}


	/**
	 * Prints the grid
	 * 
	 */
	public static void printGrid(){
		StdOut.println();
		for (int row = 0; row < Y; row++) {
			for (int col = 0; col < X; col++) {
				char current = grid[col][row];
				if(current == POINTER) StdOut.print(ANSI_YELLOW);
				StdOut.print(grid[col][row]);
				StdOut.print("\u001B[0m");
			}
			StdOut.println();
		}
	}


	/**
	 * Builds the graph
	 * @param startVertex
	 */
	public static void buildGraph(Vertex startVertex){
		nextVertex.enqueue(startVertex); // add the first vertex

		while(!nextVertex.isEmpty()){
			Vertex current = nextVertex.dequeue();
			visitedVertices.add(startVertex);
			addAdjacentVertices(current); // add all vertices we can go to
		}
	}


	/**
	 * Adds the adjacent vertices from the grid
	 * @param from
	 */
	public static void addAdjacentVertices(Vertex from){
		Bag<Vertex> bag = new Bag<>();
		graph.put(from, bag);
		for (int i = -1; i <= 1; i++){
			int newDeltaY = from.deltay + i;
			int row = from.y + newDeltaY;
			for(int k = -1; k <= 1; k++){
				int newDeltaX = from.deltax + k;
				int col = from.x + newDeltaX;
				if(isSafe(col,row)){
					Vertex v = new Vertex(col, row, newDeltaX, newDeltaY);
					if(!reallyContains(v)){
						nextVertex.enqueue(v);
						bag.add(v);
						visitedVertices.add(v);
					}
				}	
			}
		}
	}


	/**
	 * BFS Search 
	 * @param start
	 */
	public static void bfs(Vertex start){
	    Queue<Vertex> q = new Queue<Vertex>();
	    q.enqueue(start);
	    start.visited = true;

	    while(!q.isEmpty()){
	    	Vertex v = q.dequeue();
	    	if(isAtGoal(v.x, v.y)){
	    		// return the number og steps
	    		reconstructPath(v, start);
	    		break;
	    	}
	    	for (Vertex ve: adj(v)) {
	    		if(!ve.visited){
	    			ve.visited = true;
	    			ve.previous = v;
	    			q.enqueue(ve);
	    		}	
	    	}
    	}
	}


	/**
	 * Reconstructs the path from the end back to the start
	 * @param  end  
	 * @param  start
	 * @return      
	 */
	public static ArrayList<Vertex> reconstructPath(Vertex end, Vertex start){
		while(end.compareTo(start) != 0){
			path.add(end);
			end = end.previous;
		}
		path.add(start);
		return path;
	}


	/**
	 * Get the distance
	 * @return
	 */
	public static int getDistance(){
		return path.size();
	}


	/**
	 * Have we reached a finishing coordinate
	 * @param  x
	 * @param  y
	 * @return  
	 */
	public static boolean isAtGoal(int x, int y){
		return grid[x][y] == 'F';
	}


	/**
	 * Regular contains method did not work, this one is slow, but does.
	 * @param  either
	 * @return       
	 */
	public static boolean reallyContains(Vertex either){
		for (Vertex other : visitedVertices) {
			if(either.x == other.x && either.y == other.y && either.deltax == other.deltax && either.deltay == other.deltay) return true;
		}
		return false;
	}


	/**
	 * Coordinate is in bound of grid
	 * @param  x
	 * @param  y
	 * @return  
	 */
	public static boolean isInBound(int x, int y){
		return x >= 0 && x < X && y >= 0 && y < Y;
	}


	/**
	 * Is the grid coordinate terrain
	 * @param  x
	 * @param  y
	 * @return  
	 */
	public static boolean isTerrain(int x, int y){
		return grid[x][y] == 'O';
	}


	/**
	 * Is this coordinate safe to move to
	 * @param  x
	 * @param  y
	 * @return  
	 */
	public static boolean isSafe(int x, int y){
		return isInBound(x, y) && !isTerrain(x, y);
	}

}