class GiantBook {

	static QuickFindUF uf;
	private static boolean giantFound;
	private static boolean isolatedFound;

	private static boolean[] isConnected;  // determine if index has been connected
	private static int connectedComponents;
	private static int turns;

	private static double[] giantTurns;
	private static double[] noIsolatedTurns;
	private static double[] connectedTurns;

	public static void main(String[] args){
		checkInput(args);

		int nodes = Integer.parseInt(args[0]);
		int times = Integer.parseInt(args[1]);

		giantTurns = new double[times];
		noIsolatedTurns = new double[times];
		connectedTurns = new double[times];
		long start = System.currentTimeMillis();

		for (int i = 0; i < times; i++){

			uf = new QuickFindUF(nodes);
			turns = 0;
			giantFound = false;
			isolatedFound = false;
			connectedComponents = 0;
			isConnected = new boolean[nodes];

			while(!isConnectedNetwork()){
				turns++;
				int p = StdRandom.uniform(nodes);
				int q = StdRandom.uniform(nodes);
				uf.union(p,q);
				checkNonIsolated(p, q);

				//Check for giants
				// if (!giantFound && hasGiantComponent(nodes)){ 
				// 	giantTurns[i] = turns; 
				// 	giantFound = true; 
				// }
				// // Check for isolated nodes
				// if (!isolatedFound){	
				// 	if(hasNonIsolated(nodes)) {	
				// 		noIsolatedTurns[i] = turns; 
				// 		isolatedFound = true; 
				// 	}
				// }
			}
			connectedTurns[i] = turns; // when we exit loop, connected network has been reached
			turns = 0; // reset to zero for each turn
		} // end loop

		long end = System.currentTimeMillis(); // get end time
		getResults(nodes, times, start, end); // print results
		StdOut.printf("%nExiting...%n");
	}

	private static void checkInput(String[] args){
		if (args.length != 2){
			StdOut.println("Usage: java GiantBook <Nodes> <Times>"); 
			System.exit(1);
		}
	}

	private static boolean hasGiantComponent(int nodes){
		return uf.getMaxComponentSize() > nodes/2;
	}

	private static boolean hasNonIsolated(int nodes){
		return nodes == connectedComponents;
	}

    private static void checkNonIsolated(int p, int q){
    	if (!isConnected[p]){ isConnected[p] = true; connectedComponents++; }
    	if (!isConnected[q]){ isConnected[q] = true; connectedComponents++; }
    }

    public static boolean isConnectedNetwork(){
        return uf.count() == 1;
    }

    private static void getResults(int nodes, int times, long start, long end){

    	StdOut.printf("MEAN & STANDARD DEVIATION RESULTS%n");

    	StdOut.printf("Nodes:\t\t %d %n", nodes);
    	StdOut.printf("Times:\t\t %d %n", times);
    	StdOut.printf("Milliseconds:\t %d %n%n", (end-start));

    	StdOut.printf("Giant:\t\t %.2e (stddev: %.2e) %n", StdStats.mean(giantTurns), StdStats.stddev(giantTurns));
    	StdOut.printf("No Isolated:\t %.2e (stddev: %.2e) %n", StdStats.mean(noIsolatedTurns), StdStats.stddev(noIsolatedTurns));
    	StdOut.printf("Connected:\t %.2e (stddev: %.2e) %n", StdStats.mean(connectedTurns), StdStats.stddev(connectedTurns));
    }
}