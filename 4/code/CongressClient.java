/**
 * Class: CongressClient
 * This is the client for our Congress program. 
 * It utilises the max priority queue (MaxPQ) from 
 * the course book.
 * @author Dennis Sadeler Shapira
 * @author Jonas Lomholdt
 */
class CongressClient{
	public static void main(String[] args){
		MaxPQ pq = new MaxPQ();

		int numberOfStates = Integer.parseInt(StdIn.readLine());
		int numberOfSeats = Integer.parseInt(StdIn.readLine()) - numberOfStates;

		while(!StdIn.isEmpty()) { // Fill initial queueu
			String name = StdIn.readLine(); //name 
			int population = Integer.parseInt(StdIn.readLine());

			State state = new State(name, population);
			pq.insert(state);
		}

		while (numberOfSeats != 0){ // reorder the queue (give seats)
			State s = (State)pq.delMax();
			s.incSeats();
			numberOfSeats--;
			pq.insert(s);
		}

		while(!pq.isEmpty()){ // print the queue
			State s = (State)pq.delMax();
			StdOut.println(s.getName() + " " + s.getSeats());
		}
	}
}