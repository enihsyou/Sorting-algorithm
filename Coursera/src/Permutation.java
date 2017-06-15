import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            final String item = StdIn.readString();
            String s = item;
            if (queue.size() >= size) { s = queue.dequeue(); }
            if (StdRandom.bernoulli()) { queue.enqueue(item); }
            else { queue.enqueue(s); }
        }
        final Iterator<String> iterator = queue.iterator();
        for (int i = 0; i < size; i++) {
            StdOut.println(iterator.next());
        }
    }
}
