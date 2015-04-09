public class FancyRunsort{

    // This class should not be instantiated.
    private FancyRunsort() { }

    // stably merge a[lo..mid] with a[mid+1..hi] using aux[lo..hi]
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

    	double[] d = new double[14];

        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k]; 
        }

        // merge back to a[]
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)              a[k] = aux[j++];  // this copying is unneccessary
            else if (j > hi)               a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else                           a[k] = aux[i++];
        }

    }

    /**
     * [sort description]
     * @param a [description]
     */
    public static void sort(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];

        int lo = 0;
        int mi = 0;
        int runs = 0;
        int runSize = 0;

        while(!isSorted(a)) {
            for (int i = 1; i < N; i++){
                runSize++;
                if (less(a[i], a[i-1])){
                    runs++;
                    
                    lo = i-runSize;
                    mi = i-1;
                    for(int k = i+1; k < N; k++){
                        runSize++;
                        
                        if (less(a[k], a[k-1])){
                            runs++;
                            merge(a, aux, lo, i-1, k-1);
                            
                            i = k+1;
                            runSize = 0;
                            break;                             
                        }
                    }
                }
            }
            runSize = 0;
            if(runs%2 == 0) merge(a, aux, lo, mi, N-1);
       }
    }


  /***********************************************************************
    *  Helper sorting functions
    ***********************************************************************/
    
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

   // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


   /***********************************************************************
    *  Check if array is sorted - useful for debugging
    ***********************************************************************/
    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    /**
     * Reads in a sequence of strings from standard input; bottom-up
     * mergesorts them; and prints them to standard output in ascending order. 
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        FancyRunsort.sort(a);
        assert isSorted(a);
        show(a);
    }
}