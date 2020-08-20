/**
 * Created by JianjunChen on 08/18/2020
 * This is a Array based Doubly Ended Queue Data Structure!!
 * @Rule The starting size of your array should be 8.
 * @Rule Implement all the methods listed above in “The Deque API” section.
 * @Rule The amount of memory that at any given time must be proportional to the number
 * of items. For arrays of length 16 or more, your usage factor should always be at least 25%.
 *
 */


// Circular ArrayDeque
public class ArrayDeque<T> {
    private int initialCapacity = 8; //initial array capacity
    private int capacity;  //current array capacity
    private int eFactor = 2; //expanding factor
    private double usageFactor;
    private int mCapacity = 16; // The minimum capacity for contraction
    private double mRatio = 0.25; //The minimum usage ratio before contraction
    private int cFactor = 2; //contraction factor
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        capacity = initialCapacity;
        items = (T[]) new Object[initialCapacity];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    /** Finding the next nextFirst and nextLast index in circle ArrayDeque. */
    private int oneMinus(int index) {
        if (index == 0) { // whether the index is out of bounds!
            index = capacity - 1;
        } else {
            index -= 1;
        }
        return index;
    }

    private int onePlus(int index) {
        if (index == capacity - 1) { // whether the index is out of bounds!
            index = 0;
        } else {
            index += 1;
        }
        return index;
    }

    /** Resize the original array to a new array with given capacity. */
    private void resize(int newCapacity) {
        T[] a = (T[]) new Object[newCapacity];

        int currentFirst = onePlus(nextFirst);
        int currentLast = oneMinus(nextLast);

        if (currentFirst < currentLast) {
            int length = currentLast - currentFirst + 1;
            System.arraycopy(items, currentFirst, a, 0, length);
            nextFirst = newCapacity - 1;
            nextLast = length;
        } else {
            int firstRightCount = capacity - currentFirst;
            int firstLeftCount = capacity - firstRightCount;
            System.arraycopy(items, currentFirst, a, 0, firstRightCount);
            System.arraycopy(items, 0, a, firstRightCount, firstLeftCount);

            nextFirst = newCapacity - 1;
            nextLast = capacity;
        }

        capacity = newCapacity;
        items = a;

    }

    /** Adds an item of type T to the front of the deque.
     * @Rule Must take constant time, except during resizing operations.
     */
    public void addFirst(T item) {
        items[nextFirst] = item;
        size += 1;
        nextFirst = oneMinus(nextFirst);

        //The array is full, needed resize operation!
        if (size == capacity) {
            resize(size * eFactor);
        }
    }

    /** Adds an item of type T to the back of the deque.
     * @Rule Must take constant time, except during resizing operations.
     */
    public void addLast(T item) {
        items[nextLast] = item;
        size += 1;
        nextLast = onePlus(nextLast);

        //The array is full, needed resize operation!
        if (size == capacity) {
            resize(size * eFactor);
        }
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque() {
        if (isEmpty()) {
            return;
        }
        int index = onePlus(nextFirst);
        while (index != nextLast) {
            System.out.print(items[index] + " ");
            index = onePlus(index);
        }
    }

    private void contract() {
        usageFactor = (double) size / capacity;
        if (usageFactor <= mRatio && capacity >= mCapacity) {
            int newCapacity = capacity / cFactor;
            resize(newCapacity);
        }
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null.
     * @Rule must take constant time, except during resizing operations.
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        int currentFirst = onePlus(nextFirst);
        T currentFirstItem = items[currentFirst];
        nextFirst = currentFirst;
        items[nextFirst] = null;
        size -= 1;

        contract();

        return currentFirstItem;
    }

    /** Removes and returns the item at the back of the deque. If no such item
     * exists, returns null..
     * @Rule must take constant time, except during resizing operations.
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        int currentLast = oneMinus(nextLast);
        T currentLastItem = items[currentLast];
        nextLast = currentLast;
        items[nextLast] = null;
        size -= 1;

        return currentLastItem;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     * @Rule must take constant time.
     */
    public T get(int index) {
        if (index >= size) {
            return null;
        }

        int indexFromFirst = nextFirst + 1 + index;
        if (indexFromFirst >= capacity) {
            indexFromFirst -= capacity;
        }

        return items[indexFromFirst];
    }
}


