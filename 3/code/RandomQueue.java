import java.util.Iterator;

/**
 * The RandomQueue class
 * Date: 22/02/2015
 * Course: Algorithms and Data Structures
 * @author Dennis Sadeler Shapira
 * @author Jonas Lomholdt
 */
public class RandomQueue<Item> implements Iterable<Item> {
    
    /*
     * Data Fields
     */
    private Item[] rq;
    private int rq_size = 0;

    /**
     * The RandomQueue constructor creates a new array
     */
    public RandomQueue() {
        rq = (Item[]) new Object[2];
    }

    /**
     * Checks if the random queue is empty
     * @return true if there are no elements in the array
     */
    public boolean isEmpty() {
        return rq_size == 0;
    }

    /**
     * returns the size (amount of elements in the random queue)
     * @return integer size of random queue
     */
    public int size() {
        return rq_size;
    }

    /**
     * Resizes the array to the parameter value and copies 
     * the old array to the new one.
     * @param max the size of the new array
     */
    private void resize(int max) {
        Item[] temp_rq = (Item[]) new Object[max];
        for(int i = 0; i < rq_size; i++){
            temp_rq[i] = rq[i];
        }
        rq = temp_rq; 
    }

    /**
     * Enqueues an item at a random index by moving whatever is already at the
     * index to the back of the queue. 
     * @param item the item to be enqued.
     */
    public void enqueue(Item item) {
        if(rq_size == rq.length) resize(2*rq.length);      
        int randomInsertionIndex = getRandomIndex(rq_size);
        int lastInsertionIndex = rq_size;
        rq[lastInsertionIndex] = rq[randomInsertionIndex];
        rq[randomInsertionIndex] = item;
        rq_size++;
    }

    /**
     * Dequeues a random Item from the queue and deletes it.
     * @return a random Item from the queue.
     */
    public Item dequeue() {
        if(rq_size > 0 && rq_size == rq.length/4) resize(rq.length/2);
        int randomIndex = getRandomIndex(rq_size);
        Item lastItem = rq[rq_size-1];
        Item tempItem = rq[randomIndex];
        rq[randomIndex] = lastItem;
        rq[rq_size-1] = null;
        rq_size--;
        return tempItem;
    }

     /**
     * Returns a random number between 0 (inclusive) and maxRandomNumber (exclusive).
     * @param  maxRandomNumber highest number (exclusive)
     * @return a random integer in the specific range.
     */
    private int getRandomIndex(int maxRandomNumber){
        if(maxRandomNumber == 0){
            return 0;
        }else{
            return(StdRandom.uniform(maxRandomNumber));
        }
    }

    /**
     * Sample method returns a random Item from the queue without deleting it
     * @return a Item from the queue
     */
    public Item sample(){
        int d = getRandomIndex(rq_size);       
        return rq[d];
    }

    /**
     * Overridden toString method enables us to print the RandomQueue
     * @return a string representation of the queue
     */
    public String toString(){
        String s = "";
        for(int i = 0; i < rq_size; i++){
            s += rq[i]+" ";
        }
            return s;
    }

    /**
     * Returns an iterator for RandomQueue
     * @return a RandomQueueIterator object
     */
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    /**
     * The RandomQueueIterator class
     */
    private class RandomQueueIterator implements Iterator<Item> {

        Item[] temp;
        private int currentIndex = 0;

        /**
         * RandomQueueIterator constructor creates a new array of precisely rq_size
         * and copies the values from the original array over in random order.
         */
        public RandomQueueIterator(){
            temp = (Item[]) new Object[rq_size];
            int elm = 0;

            for (int i = 0; i < rq_size; i++){
                Item item = rq[i];
                int randomInsertionIndex = getRandomIndex(elm);
                int lastInsertionIndex = elm;
                temp[lastInsertionIndex] = temp[randomInsertionIndex];
                temp[randomInsertionIndex] = item;
                elm++;
            }
        }

        /**
         * Returns true if there are more elements to iterate over
         * @return boolean true or false
         */
        public boolean hasNext()  { return currentIndex < rq_size; }

        /**
         * Remove method not implemented intentionally
         */
        public void remove() {}

        /**
         * Next method returns the next element in the iterable array
         * @return a Item element 
         */
        public Item next() {
            Item n = temp[currentIndex];
            currentIndex++;
            return n;
        }
    }

    /**
     * The main method had client code that was provided 
     * by the teacher of the course, and remains untouched.
     */
    public static void main(String args[])
    {
		// Build a queue containing the Integers 1,2,...,6:
        RandomQueue<Integer> Q= new RandomQueue<Integer>();
        for (int i = 1; i < 7; ++i) Q.enqueue(i); // autoboxing! cool!

        // Print 30 die rolls to standard output
        StdOut.print("Some die rolls: ");
        for (int i = 1; i < 30; ++i) StdOut.print(Q.sample() +" ");
        StdOut.println();

        // Let’s be more serious: do they really behave like die rolls?
        int[] rolls= new int [10000];
        for (int i = 0; i < 10000; ++i)
        rolls[i] = Q.sample(); // autounboxing! Also cool!
        StdOut.printf("Mean (should be around 3.5): %5.4f\n", StdStats.mean(rolls));
        StdOut.printf("Standard deviation (should be around 1.7): %5.4f\n",
        StdStats.stddev(rolls));

        // Now remove 3 random values
        StdOut.printf("Removing %d %d %d\n", Q.dequeue(), Q.dequeue(), Q.dequeue());

        // Add 7,8,9
        for (int i = 7; i < 10; ++i) Q.enqueue(i);

        // Empty the queue in random order
        while (!Q.isEmpty()) StdOut.print(Q.dequeue() +" ");
        StdOut.println();

        // Let’s look at the iterator. First, we make a queue of colours:
        RandomQueue<String> C= new RandomQueue<String>();
        C.enqueue("red"); C.enqueue("blue"); C.enqueue("green"); C.enqueue("yellow");
        Iterator I= C.iterator();
        Iterator J= C.iterator();
        StdOut.print("Two colours from first shuffle: "+I.next()+" "+I.next()+" ");
        StdOut.print("\nEntire second shuffle: ");
        while (J.hasNext()) StdOut.print(J.next()+" ");
        StdOut.print("\nRemaining two colours from first shuffle: "+I.next()+" "+I.next());
    }
}