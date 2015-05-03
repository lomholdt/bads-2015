/**
 * Vector Mario Class
 * 
 */
public class Mario{

	public static int X;
	public static int Y;
	public static char[][] grid;
	public static Queue<Vertex> startVertices = new Queue<>();

	public static void main(String[] args) {
		gridInit(); // Initialize grid
		printGrid(); // print the grid

		test();
				
	}

	/**
	 * Initialise the grid 
	 * 
	 */
	public static void gridInit(){
		X = StdIn.readInt();
		Y = StdIn.readInt();

		grid =  new char[Y][X];

		for (int row = 0; row < X; row++){
			StdIn.readChar();
			for (int col = 0; col < Y; col++){
				char v = StdIn.readChar();
				switch(v){
					case '\n' : continue;
					case 'S' : startVertices.enqueue(new Vertex(col, row, 0,0));
							   grid[col][row] = v;
					case ' ' : case 'O' : case 'F' : grid[col][row] = v;
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
		for (int row = 0; row < X; row++) {
			StdOut.println();
			for (int col = 0; col < Y; col++) {
				StdOut.print(grid[col][row]);
			}
		}
		StdOut.println();
	}

	/**
	 * For visually testing correctness of parsed input file 
	 */
	public static void test(){
		for (Vertex v : startVertices) {
			StdOut.println("Start Is: " + "(" + v.x + "," + v.y + ")");	
		}
		// StdOut.println(grid[0][4]);
		// StdOut.println(grid[3][8]);		
	}
}