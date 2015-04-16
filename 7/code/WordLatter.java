import java.util.TreeMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Collection;
import java.io.File;

public class WordLatter{

	private static TreeMap<String, Set<String>> permutationMap = new TreeMap<>();
	private static TreeMap<String, Set<String>> fourLetterMap = new TreeMap<>();
	private static TreeMap<String, Bag<String>> graph = new TreeMap<>();

	private static TreeMap<String, Boolean> marked = new TreeMap<>();
	private static TreeMap<String, Integer> distTo = new TreeMap<>();

	private static void addWordToMap(String word){
		String sortedWord = sortWord(word);
		String fourLetterWord = sortWord(word.substring(1,word.length()));

		addPermutationsToMap(word, sortedWord);
		addFourLetterWordToMap(word, fourLetterWord);
	}

	private static void addPermutationsToMap(String word, String sortedWord){

		StdOut.println("Word: " + word);

		for (int i = 0; i < sortedWord.length(); i++) {
			Set<String> set = new TreeSet<>();
			StringBuilder sb = new StringBuilder(sortedWord);
			sb.deleteCharAt(i);
			String wordPermutation = sb.toString();

			// PRINT TEST
			StdOut.println(wordPermutation);

			if(permutationMap.containsKey(wordPermutation)){
				Set<String> setValues = permutationMap.get(wordPermutation);
				setValues.add(word);
			}
			else{
				set.add(word);
				permutationMap.put(wordPermutation, set);
			}
		}
	}

	private static void addFourLetterWordToMap(String word, String fourLetterWord){
		Set<String> set = new TreeSet<>();

		if(fourLetterMap.containsKey(fourLetterWord)){
			Set<String> setValues = fourLetterMap.get(fourLetterWord);
			setValues.add(word);
		}
		else{
			set.add(word);
			fourLetterMap.put(fourLetterWord, set);
		}
	}

	private static String sortWord(String word){
		char[] arr = word.toCharArray();
		Arrays.sort(arr);
		return new String(arr);
	}

	public static Iterable<String> adj(String v) {
        return graph.get(v);
    }

    private static int bfs(String from, String to) {
    	StdOut.println("From: " + from + " To: " + to);
    	if (!graph.containsKey(from)) return -2;
    	if (from.equals(to)) return 0;
        Queue<String> q = new Queue<String>();
        marked.put(from, true);
        distTo.put(from, 0);
        q.enqueue(from); // s
        while (!q.isEmpty()) {
            String v = q.dequeue();
            StdOut.println(adj(v));
            for (String word : adj(v)) {
            	//if (marked.get(word) == null) return -2;
            	StdOut.println("Checking neighbour " + word);
                if (!marked.get(word)) {
                    distTo.put(word, distTo.get(v) + 1);
                    marked.put(word, true);

                    StdOut.println("Does " + word + " equals " + to);
                    if (word.equals(to)){
                    	StdOut.println(word + " equals " + to);
                    	return distTo.get(word);
                    }
                    // marked[word] = true;
                    q.enqueue(word);
                }
            }
        }
        return -1;
    }

	public static void main(String[] args) {
		long start = System.currentTimeMillis();



		while(StdIn.hasNextLine()){
			String word = StdIn.readLine();
			addWordToMap(word);
			graph.put(word, new Bag<String>());
			marked.put(word, false);
		}

		// Collection permutationMapEntrySet = permutationMap.entrySet();
		Iterator iterator = permutationMap.entrySet().iterator();
		Map.Entry<String, Set<String>> permutationEntry = (Map.Entry<String, Set<String>>) iterator.next();
		boolean isChecked = false;
		for (Map.Entry<String, Set<String>> fourLetterEntry : fourLetterMap.entrySet()) {

			Set<String> wordFromSet = fourLetterEntry.getValue();

			while (iterator.hasNext()) {
				
				if(isChecked){
					permutationEntry = (Map.Entry<String, Set<String>>) iterator.next();
				}
				// if permutaionmapKey > fourLetterMap key .. break;	
				if (permutationEntry.getKey().compareTo(fourLetterEntry.getKey()) > 0){
					isChecked = false; 
					break;
				} 
				// if == (.equals) lav en arc/edge mellem de to values
				if (permutationEntry.getKey().equals(fourLetterEntry.getKey())){
					StdOut.println("Key Match: " + permutationEntry.getKey() + ", " + fourLetterEntry.getKey());
					// add edge to all words in wordFrom set 
					for (String wordFrom : wordFromSet) {
						Set<String> wordToSet = permutationEntry.getValue();
						for (String wordTo : wordToSet) {
							// if not the value add to graph
							if(!wordFrom.equals(wordTo)){
								Bag<String> gFrom = graph.get(wordFrom);
								gFrom.add(wordTo);
							}
						}
					}
				}
				isChecked = true;
			}				
		}


		long end = (System.currentTimeMillis() - start);
		StdOut.printf("Graph Done in %d ms%n", end);

		printGraph();

		In in = new In(new File(args[0]));
		while(in.hasNextLine()){
			try{
				String wordFrom = in.readString();
				StdOut.println("From: " + wordFrom);
				String wordTo = in.readString();
				StdOut.println("To: " + wordTo);
				StdOut.println(bfs(wordFrom, wordTo));
				
			}
			catch(Exception e){
				StdOut.println("End of file...");

			}
		}



	}

	public static void printGraph(){
		StdOut.println("=====================GRAPH=======================");
		for (Map.Entry<String, Bag<String>> entry : graph.entrySet()) {
			StdOut.println("Key: " + entry.getKey());
			StdOut.print("Value: ");
			for (String value : entry.getValue()) {
				StdOut.print(value + ", ");
				
			}
			StdOut.println();
			
		}
		StdOut.println("================================================");
	}

}