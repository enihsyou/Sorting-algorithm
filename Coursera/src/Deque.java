import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> front, tail;
    private int size;

    public Deque() {

    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.removeFirst();
        deque.removeFirst();
        deque.addLast(-2);
        deque.addLast(-3);
        deque.addLast(-4);
        for (Integer integer : deque) {
            System.out.println(integer);
        }
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
    }

    public void addFirst(Item item) {
        if (item == null) { throw new NullPointerException(); }
        final Node<Item> f = this.front;
        this.front = new Node<>(item, this.front, null);
        if (f == null) { tail = front; }
        else { f.prev = front; }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        final Item old = front.item;
        final Node<Item> next = front.next;
        front = next;
        if (next == null) { tail = null; }
        else { next.prev = null; }
        size--;
        return old;
    }

    public void addLast(Item item) {
        if (item == null) { throw new NullPointerException(); }
        final Node<Item> t = this.tail;
        this.tail = new Node<>(item, null, this.tail);
        if (t == null) { front = tail; }
        else { t.next = tail; }
        size++;
    }

    public Item removeLast() {
        if (isEmpty()) { throw new NoSuchElementException(); }
        Item old = tail.item;
        Node<Item> prev = tail.prev;
        tail = prev;
        if (prev == null) { front = null; }
        else { prev.next = null; }
        size--;
        return old;
    }

    public boolean isEmpty() { return size == 0; }

    public int size() { return size; }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    private static class Node<Item> {
        private final Item item;
        private Node<Item> next;
        private Node<Item> prev;

        Node(final Item item, final Node<Item> next, final Node<Item> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        public Node(final Item item) {
            this.item = item;
        }
    }

    private class LinkedListIterator implements Iterator<Item> {
        private Node<Item> currentPointer;

        LinkedListIterator() {
            currentPointer = front;
        }

        @Override
        public boolean hasNext() {
            return currentPointer != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) { throw new NoSuchElementException(); }
            final Item item = currentPointer.item;
            currentPointer = currentPointer.next;
            return item;
        }
    }
}
