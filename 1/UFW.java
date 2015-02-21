/**
* File: UFW.java
* Course: Algorithms and Data Structures (Spring 2015)
* Assignment: Connected Components Warmup
* 
* The following is mostly just code from the 
* WeightedQuickUnionUF.java main file. I have added
* one line of code, to achieve the correct output as shown
* on line 24.
*
* @author Jonas Lomholdt
* @date 31/01-2015
*/
public class UFW {
	public static void main(String[] args){
		int N = StdIn.readInt();
		WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N);
		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (uf.connected(p, q)) continue;
			uf.union(p, q);
		}
		if (uf.connected(0,1)) StdOut.println("true"); // This line is made by me
	}
}