import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Object[] items;
    private int size;

    public RandomizedQueue() {
        items = new Object[1];
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        // queue.sample();
        // queue.sample();
        // queue.sample();
        queue.dequeue();
        // queue.dequeue();
        queue.enqueue(7);
        for (Integer integer : queue) {
            System.out.println(integer);
        }
    }

    public void enqueue(Item item) {
        if (item == null) { throw new NullPointerException(); }
        ensureSize();
        items[size++] = item;
    }

    public Item sample() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        int index = StdRandom.uniform(size);
        // noinspection unchecked
        return (Item) items[index];
    }

    public boolean isEmpty() { return size == 0; }

    private void ensureSize() {
        if (size >= items.length) { items = copyArray(items.length << 1); }
        else if (size < items.length / 3) { items = copyArray(items.length >> 1); }
    }

    private Object[] copyArray(int newSize) {
        Object[] newItems = new Object[newSize];
        System.arraycopy(items, 0, newItems, 0, Math.min(newSize, items.length));
        return newItems;
    }

    public Item dequeue() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        ensureSize();
        final int pos = StdRandom.uniform(size--);
        final Object item = items[pos];
        items[pos] = null;
        // noinspection unchecked
        return (Item) item;
    }

    public int size() { return size; }

    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private final int[] order;
        private int position;

        QueueIterator() {
            // this.order = IntStream.range(0, size).toArray();
            this.order = StdRandom.permutation(size);
        }

        @Override
        public boolean hasNext() {
            return position != order.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }
            // noinspection unchecked
            return (Item) items[order[position++]];
        }
    }
}
