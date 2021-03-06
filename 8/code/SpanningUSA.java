import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class SpanningUSA{

	private static Map<String, Integer> citiesToInt = new HashMap<>();
	private static Map<Integer, String> intToCities = new HashMap<>();
	private static EdgeWeightedGraph graph;

	public static void main(String[] args) {
        String input = "";

        if(!StdIn.isEmpty()){
        	int count = 0;
        	while(!StdIn.isEmpty()){ // Add vertices to map
        		input = StdIn.readLine().trim();

        		if(!input.contains("[")){
        			citiesToInt.put(input, count);
        			intToCities.put(count, input);
        			count++;
        		}
        		else{ break; }
        	}
        	
            graph = new EdgeWeightedGraph(citiesToInt.size());
        	
        	while(!StdIn.isEmpty()){
        		
        		String weightStr = input.substring(input.indexOf("[")+1 , input.indexOf("]"));
                String cities = input.substring(0 , input.indexOf("[")-1 );
                String  first = cities.substring(0 , input.indexOf("--"));
                String  second = cities.substring(input.indexOf("--")+2 , cities.length());

                Double weight = Double.parseDouble(weightStr);
				Edge e = new Edge(citiesToInt.get(first), citiesToInt.get(second), weight);
				graph.addEdge(e);
                input = StdIn.readLine();
        	}
               

			//StdOut.println(graph);

			for (Edge e : graph.edges()) {
				int val1 = e.either();
				int val2 = e.other(val1);

				String cityA = intToCities.get(val1);
				String cityB = intToCities.get(val2);
				Double weight = e.weight();
			}

			PrimMST mst = new PrimMST(graph);
			double weightSum = 0.0;

			for (Edge e : mst.edges()) {
				int val1 = e.either();
				int val2 = e.other(val1);

				String cityA = intToCities.get(val1);
				String cityB = intToCities.get(val2);
				Double weight = e.weight();
				weightSum += weight;

				StdOut.println(String.format("%s - %s : %1.2f", cityA, cityB, weight));
			}
			StdOut.println(weightSum);
		}
	}
}