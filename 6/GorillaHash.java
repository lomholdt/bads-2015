import java.util.ArrayList;

public class GorillaHash{

	private static int k = 20;
	private static int d = 10000;

	public static void main(String[] args) {
		ArrayList<Species> species = new ArrayList<>();
		Species s = new Species(k);
		while(StdIn.hasNextLine()){ // read the whole file
			String currentLine = StdIn.readLine();
			if (currentLine.startsWith(">")){
				s = new Species(d);
				String[] name = currentLine.split(" ");
				 s.name = name[0].substring(1, name[0].length());
				species.add(s);
			}
			else{
				s.sq += currentLine;
			}
		}

		StdOut.printf("%14s", "");
		for (Species a : species){ // Header
			System.out.printf("%14s", a.name);
		}
		StdOut.println();

		for (Species a : species) { // rows 
			setKgrams(a, d);
			StdOut.printf("%14s", a.name);
			for (Species b : species){ // columns
				setKgrams(b, d);
				StdOut.print(String.format("%14.3f" , getSimilarity(a, b))); 
			}
			StdOut.println();
		}
	}

	public static void setKgrams(Species species, int d){
		String tempGram = "";
		int nextIndex = 0;
		for (int i = 0; i < species.sq.length(); i++){
			if ((i % k) == 0 && !tempGram.isEmpty()) {
				species.kGrams[nextIndex++] = hashString(tempGram, d);
				tempGram = "";
			}
			tempGram += species.sq.charAt(i);
		}
	}

	private static int hashString(String s, int d){
		return (s.hashCode() % d);
	}

	private static double getScalarValue(double[] a, double[] b){
		// calculate dot product to get scalar value
		double scalarValue = 0;
		int size = (a.length > b.length) ? b.length : a.length;

		for (int i = 0; i < size; i++){
			scalarValue += a[i] * b[i];
		}
		return scalarValue;
	}

	private static double getVectorLength(double[] a){
		double sum = 0;
		for (int i = 0; i < a.length; i++){
			sum += Math.pow(a[i], 2);
		}
		// StdOut.println("Length: " + Math.sqrt(sum));
		return Math.sqrt(sum);
	}

	public static double getSimilarity(Species a, Species b){
		double numerator = getScalarValue(a.kGrams, b.kGrams);
		double denominator = getVectorLength(a.kGrams) * getVectorLength(b.kGrams);
		return numerator / denominator;
	}

	private static void printArray(double[] a){
		for (int i = 0; i < a.length; i++){
			StdOut.print(a[i] + ", ");
		}
	}
}