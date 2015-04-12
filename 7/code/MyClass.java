public class MyClass{



	public static void main(String[] args) {

		String a = "abcd";
		String b = "cdb";

		String[] x = b.split("");
		x.sort();

		for (String z : x) {
			StdOut.println(z);
			
		}





	}
}