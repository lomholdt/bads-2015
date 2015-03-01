class State implements Comparable<State>{

	private String name;
	private int seats;
	private int population;
	private double rank;



	public State(String name, int population){
		this.name = name;
		this.population = population;
		this.seats = 1;
	}

	public String getName(){
		return name;
	}

	public int getSeats(){
		return seats;
	}

	public void incSeats(){
		this.seats++;
		StdOut.println(getName()+ " seats are now: " + seats);
	}

	public int getPopulation(){
		return population;
	}

	@Override
	public int compareTo(State other){
		rank = population / Math.sqrt(seats*(seats+1));

		StdOut.println(getName() + " : " + rank + " Seats: " + seats);
		
		if (rank > other.rank){
			return 1;
		}
		else if (rank == other.rank){
			return 0;
		}
		else{
			return -1;
		}
	}
}