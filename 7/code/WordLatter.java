import java.util.TreeMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Collection;

public class WordLatter{

	private static TreeMap<String, Set<String>> permutationMap = new TreeMap<>();
	private static TreeMap<String, Set<String>> fourLetterMap = new TreeMap<>();
	private static TreeMap<String, Bag<String>> graph = new TreeMap<>();

	private static void addWordToMap(String word){
		String sortedWord = sortWord(word);
		String fourLetterWord = sortWord(word.substring(1,word.length()));

		addPermutationsToMap(word, sortedWord);
		addFourLetterWordToMap(word, fourLetterWord);
	}

	private static void addPermutationsToMap(String word, String sortedWord){

		for (int i = 0; i < sortedWord.length(); i++) {
			Set<String> set = new TreeSet<>();
			StringBuilder sb = new StringBuilder(sortedWord);
			sb.deleteCharAt(i);
			String wordPermutation = sb.toString();

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

	public Iterable<String> adj(String v) {
        return graph.get(v);
    }


	public static void main(String[] args) {
		long start = System.currentTimeMillis();



		while(StdIn.hasNextLine()){
			String word = StdIn.readLine();
			addWordToMap(word);
			graph.put(word, new Bag<String>());

		}

		// Collection permutationMapEntrySet = permutationMap.entrySet();
		Iterator iterator = permutationMap.entrySet().iterator();

		for (Map.Entry<String, Set<String>> fourLetterEntry : fourLetterMap.entrySet()) {

			Set<String> wordFromSet = fourLetterEntry.getValue();

			while (iterator.hasNext()) {
				Map.Entry<String, Set<String>> permutationEntry = (Map.Entry<String, Set<String>>) iterator.next();
				// if permutaionmapKey > fourLetterMap key .. break;
				if (permutationEntry.getKey().compareTo(fourLetterEntry.getKey()) > 0) break;
				// if == (.equals) lav en arc/edge mellem de to values
				if (permutationEntry.getKey().equals(fourLetterEntry.getKey())){
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
			}				
		}

		long end = (System.currentTimeMillis() - start) / 1000;
		StdOut.printf("Graph Done in %d%n", end);


	}

}