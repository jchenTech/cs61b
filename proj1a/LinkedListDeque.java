/**
 * Created by JianjunChen on 08/16/2020
 * This is a Linked List Doubly ended queue Data Structure using Lists!
 * @Rule The amount of memory that your program uses at any given time must be
 * proportional to the number of items.
 * @Rule Do not maintain references to items that are no longer in the deque.
 * @Rule Implement all the methods listed above in “The Deque API” section.
 */


public class LinkedListDeque<T> {
    /**LinkedNode Nested Class*/
    private class LinkedNode {
        private LinkedNode prev; /* Doubly Linked List*/
        private T item;
        private LinkedNode next;

        public LinkedNode(LinkedNode p, T i, LinkedNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    private LinkedNode sentinel;
    //private LinkedNode last;
    private int size;

    /** Constructor of LinkedListDeque */
    public LinkedListDeque() {
        sentinel = new LinkedNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        //last = sentinel;
        size = 0;
    }

    /** Adds an item of type T to the front of the deque.*/
    public void addFirst(T item) {
        LinkedNode first = new LinkedNode(sentinel, item, sentinel.next);
        /* Note that the order cannot be reversed since if we firstly write
         * sentinel.next = first; the sentinel.next.prev will equal to first.prev!!!!*/
        sentinel.next.prev = first;
        sentinel.next = first;
        size += 1;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        LinkedNode last = new LinkedNode(sentinel.prev, item, sentinel);
        /* Note that the order cannot be reversed!!! */
        sentinel.prev.next = last;
        sentinel.prev = last;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        if (sentinel.next == sentinel) {
            return true;
        }
        return false;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /* Prints the items in the deque from first to last, separated by a space. */
    public void printDeque() {
        LinkedNode p = sentinel;
        while (p.next != sentinel) {
            System.out.print(p.next.item + " ");
            p = p.next;
        }
    }

    /** Removes and returns the item at the front of the deque.
    If no such item exists, returns null.*/
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T firstItem = sentinel.next.item;
        /* Note that the order cannot be reversed!!!*/
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;

        size -= 1;
        return firstItem;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        T lastItem = sentinel.prev.item;
        /* Note that the order cannot be reversed!!! */
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;

        size -= 1;
        return lastItem;

    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item,
    * and so forth. If no such item exists, returns null. Must not alter the deque! */
    public T get(int index) {
        if (index > size - 1) {
            return null;
        }

        LinkedNode p = sentinel.next;
        int i = 0;
        while (i != index) {
            p = p.next;
            i += 1;
        }
        return p.item;
    }

    /** Helper method for getRecursive */
    private T getRecursiveHelper(LinkedNode currentNode, int index) {
        if (index == 0) {
            return currentNode.item;
        }
        return getRecursiveHelper(currentNode.next, index - 1);
    }

    /** Same as get but using recursion!! */
    public T getRecursive(int index) {
        if (index > size - 1) {
            return null;
        }

        return getRecursiveHelper(sentinel.next, index);
    }
}

