/**
 * Class: State
 * This class represents each state. 
 * @author Dennis Sadeler Shapira
 * @author Jonas Lomholdt
 */
class State implements Comparable<State>{

	private String name;
	private int seats;
	private int population;
	private double rank;

	public State(String name, int population){
		this.name = name;
		this.population = population;
		seats = 1;
	}

	public String getName(){
		return name;
	}

	public int getSeats(){
		return seats;
	}

	public void incSeats(){
		seats++;
	}

	public int getPopulation(){
		return population;
	}

	public double getRank(){
		rank = population / Math.sqrt((seats*(seats+1)));
		return rank;
	}

	@Override
	public int compareTo(State other){
		rank = getRank();
		return Double.compare(rank, other.getRank());
	}
}