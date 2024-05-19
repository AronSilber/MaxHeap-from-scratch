import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Aron Silberwasser
 * @version 1.0
 * @userid asilberwasser3
 * @GTID 903683147
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 (including the empty 0
     * index) where n is the number of data in the passed in ArrayList (not
     * INITIAL_CAPACITY). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     *
     * Consider how to most efficiently determine if the list contains null data.
     * 
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data.contains(null)) {
            throw new IllegalArgumentException("cannot have null elements in arraylist");
        }

        backingArray = (T[]) new Comparable[2 * data.size() + 1];

        int i = 1;
        Iterator<T> it = data.iterator();

        while (it.hasNext()) {
            backingArray[i] = it.next();
            size++;
            i++;
        }

        for (int j = size / 2; j > 0; j--) {
            backingArray = downHeap(backingArray, j);
        }
    }

    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }

        if (backingArray.length == size + 1) {
            T[] tempArr = (T[]) new Comparable[2 * backingArray.length];

            for (int i = 0; i <= size; i++) {
                tempArr[i] = backingArray[i];
            }
            backingArray = tempArr;
        }

        backingArray[size + 1] = data;
        size++;

        backingArray = upHeap(backingArray, size);
    }

    /**
     * performs upheap algorithm recursively
     * @param curr current heap holding data
     * @param index current index of new data
     * @return heap in correct order
     */
    private T[] upHeap(T[] curr, int index) {
        if (index <= 1) {
            return curr;
        }

        if (curr[index / 2].compareTo(curr[index]) < 0) {
            T temp = curr[index / 2];
            curr[index / 2] = curr[index];
            curr[index] = temp;

            upHeap(curr, index / 2);
        }
        return curr;
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("heap cannot be empty");
        }

        T removedData = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;

        size--;

        backingArray = downHeap(backingArray, 1);

        return removedData;
    }

    /**
     * performs downheap algorithm recursively
     * @param curr current state heap
     * @param index index of current data to be ordered
     * @return heap in correct order
     */
    private T[] downHeap(T[] curr, int index) {
        if (curr[2 * index] == null) {
            return curr;
        }
        if (curr[2 * index + 1] == null) {
            if (curr[1].compareTo(curr[size]) < 0) {
                T temp = curr[1];
                curr[1] = curr[size];
                curr[size] = temp;
            }

            return curr;
        } else {
            int olderSibling;
            if (curr[2 * index + 1].compareTo(curr[2 * index]) > 0) {
                olderSibling = 2 * index + 1;
            } else {
                olderSibling = 2 * index;
            }

            if (curr[index].compareTo(curr[olderSibling]) < 0) {
                T temp = curr[olderSibling];
                curr[olderSibling] = curr[index];
                curr[index] = temp;

                backingArray = downHeap(curr, olderSibling);
            }

            return curr;
        }
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return backingArray[1] == null;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
