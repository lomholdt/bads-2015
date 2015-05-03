public class Vertex implements Comparable<Vertex>{
	public int x;
	public int y;
	public int deltax;
	public int deltay;

	public boolean visited;
	public Vertex previous;

	Vertex(int x, int y, int deltax, int deltay){
		this.x = x;
		this.y = y;
		this.deltax = deltax;
		this.deltay = deltay;
	}

	@Override
	public int compareTo(Vertex other){
		if (this.x == other.x && 
			this.y == other.y && 
			this.deltax == other.deltax &&
			this.deltay == other.deltay) return 0;
		return -1;
	}

	@Override
	public String toString(){
		return String.format("V:(%d, %d, %d, %d)", x, y, deltax, deltay);

	}
}