public class TestCosine{
	public static void main(String[] args) {
		Species sa = new Species(2);
		double[] a = {0.0,1.0};
		sa.kGrams = a;
		Species sb = new Species(2);
		double[] b = {0.0,2.0};
		sb.kGrams = b;
		Species sc = new Species(2);
		double[] c = {1.0,0.0};
		sc.kGrams = c;
		Species sd = new Species(2);
		double[] d = {0.0,-1.0};
		sd.kGrams = d;

		Species sx = new Species(1000);
		double[] x = new double[1000];
		sx.kGrams = x;
		for (int i = 0; i < 1000; i++) x[i] = StdRandom.random();

		Species sy = new Species(1000);
		double[] y = new double[1000];
		sy.kGrams = y;
		for (int i = 0; i < 1000; i++) y[i] = StdRandom.random();

		StdOut.println("a " + GorillaHash.getSimilarity(sa, sa));
		StdOut.println("b " + GorillaHash.getSimilarity(sa, sb));
		StdOut.println("c " + GorillaHash.getSimilarity(sb, sc));
		StdOut.println("d " + GorillaHash.getSimilarity(sa, sd));
		StdOut.println("x " + GorillaHash.getSimilarity(sx, sy));
	}
}