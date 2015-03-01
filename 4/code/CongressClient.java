class CongressClient{

	


	public static void main(String[] args){

		// if (args.length != 1){
		// 	StdOut.println("Usage: java CongressClient <datafile>");
		// 	return;
		// }

		MaxPQ pq = new MaxPQ();

		int numberOfStates = Integer.parseInt(StdIn.readLine());
		int numberOfSeats = Integer.parseInt(StdIn.readLine());
		numberOfSeats = numberOfSeats - numberOfStates;

		while(!StdIn.isEmpty()) {
			String name = StdIn.readLine(); //name 
			int population = Integer.parseInt(StdIn.readLine()); //population

			// StdOut.println(name);
			// StdOut.println(population);

			State state = new State(name, population);
			pq.insert(state);
		}


		StdOut.println("########## HERFRA!!! ##########");

		while (numberOfSeats != 0){
			StdOut.println("nseats triggered");
			State s = (State)pq.delMax();
			s.incSeats();
			numberOfSeats--;
			pq.insert(s);
		}

		// System.out.println(pq.toString());







		while(!pq.isEmpty()){
			State s = (State)pq.delMax();
			StdOut.println(s.getName() + " " + s.getSeats());
		}
	}
}