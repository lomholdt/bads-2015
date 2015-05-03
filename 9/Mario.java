import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

/**
 * Vector Mario Class
 * 
 */
public class Mario{

	public static int X;
	public static int Y;
	public static char[][] grid;
	public static Queue<Vertex> startVertices = new Queue<>();
	private static Queue<Vertex> nextVertex = new Queue<>();
	public static Set<Vertex> visitedVertices = new HashSet<>();
	public static Map<Vertex, Vertex> graph = new HashMap<>();

	public static void main(String[] args) {
		gridInit(); // Initialize grid
		printGrid(); // print the grid
		//buildGraph(startVertices.dequeue());

		test();
				
	}

	/**
	 * Initialise the grid 
	 * 
	 */
	public static void gridInit(){
		Y = StdIn.readInt(); // rows
		X = StdIn.readInt(); // columns

		grid =  new char[Y][X];

		for (int row = 0; row < Y; row++){
			StdIn.readChar();
			for (int col = 0; col < X; col++){
				char v = StdIn.readChar();
				switch(v){
					case '\n' : continue;
					case 'S' : startVertices.enqueue(new Vertex(row, col, 0,0));
							   grid[row][col] = v;
					case ' ' : case 'O' : case 'F' : grid[row][col] = v;
				}				
			}
		}
		StdOut.print("Grid Initialized");
	}

	/**
	 * Prints the grid
	 * 
	 */
	public static void printGrid(){
		StdOut.println();
		for (int row = 0; row < Y; row++) {
			for (int col = 0; col < X; col++) {
				StdOut.print(grid[row][col]);
			}
			StdOut.println();
		}
	}

	public static void buildGraph(Vertex startVertex){
		nextVertex.enqueue(startVertex); // add the first vertex

		while(!nextVertex.isEmpty()){
			Vertex current = nextVertex.dequeue();
			addAdjacentVertices(current); // add all vertices we can go to


		}

	}

	public static void addAdjacentVertices(Vertex from){
		for (int i = -1; i <= 1; i++){
			int row = from.x + i;
			int newDeltaX = from.deltax + i;
			for(int k = -1; k <= 1; k++){
				int col = from.y + k; 
				int newDeltaY = from.deltay + k;
				if(isSafe(row,col)){
					StdOut.println("(" + row + "," + col + ") DELTA:" + newDeltaX + "," + newDeltaY);
					Vertex v = new Vertex(row, col, newDeltaX, newDeltaY);
					if(!visitedVertices.contains(v)){
						nextVertex.enqueue(v);
						visitedVertices.add(v);
					}

				}	
			}
		}
	}

	/**
	 * Coordinate is in bound of grid
	 * @param  x [description]
	 * @param  y [description]
	 * @return   [description]
	 */
	public static boolean isInBound(int x, int y){
		return x >= 0 && x < X && y >= 0 && y < Y;
	}

	/**
	 * Is the grid coordinate terrain
	 * @param  x [description]
	 * @param  y [description]
	 * @return   [description]
	 */
	public static boolean isTerrain(int x, int y){
		return grid[x][y] == 'O';
	}


	public static boolean isSafe(int x, int y){
		return isInBound(x, y) && !isTerrain(x, y);
	}


	/**
	 * For visually testing correctness of parsed input file 
	 */
	public static void test(){
		for (Vertex v : startVertices) {
			StdOut.println("Start Is: " + "(" + v.x + "," + v.y + ")");	
		}


		Vertex a = new Vertex(1,1,0,0);
		Vertex b = new Vertex(1,1,0,0);

		if(a.compareTo(b) == 0){
			StdOut.println("a og b er ens");
		}
		else{
			StdOut.println("a og b er IKKE ens");	
		}


		if(isSafe(0,3)){
			StdOut.println("Den er sikker do!");
		}
		else{
			StdOut.println("Den er ikke sikker...");	
		}



		// StdOut.println(grid[0][4]);
		// StdOut.println(grid[3][8]);		
	}
}