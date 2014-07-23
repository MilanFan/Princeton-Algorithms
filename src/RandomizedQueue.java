import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Princeton Algorithms Assignment 2: Randomized Queues and Deques.
 *
 * @author Milan Fan
 *
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] rq;	// randomized queue
    private int N = 0;	// number of elements on randomized queue

	/**
	 * Construct an empty randomized queue.
	 */
	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		rq = (Item[]) new Object[2];
	}

	/**
	 * Is the randomized queue empty?
	 * @return true if the randomized queue empty; false otherwise.
	 */
	public boolean isEmpty() {
        return N == 0;
	}

	/**
	 * Return the number of items on the randomized queue.
	 * @return the number of items on the randomized queue.
	 */
	public int size() {
		return N;
	}

	/**
	 * resize the array
	 * @param capacity
	 */
	private void resize() {
		if (N == 0) return;
		int capacity = N * 2;
		@SuppressWarnings("unchecked")
		Item[] copy = (Item[]) new Object[capacity];
		for (int i = 0; i < N; i++) { copy[i] = rq[i]; }
		rq = copy;
	}

	/**
	 * Add the item.
	 * @param item the item to be added at the end.
	 */
	public void enqueue(Item item) {
		if (item == null) throw new NullPointerException();
		if (N == rq.length) resize();
		rq[N] = item;
		N++;
	}

	/**
	 * Delete and return a random item.
	 * @return the item at the front deleted.
	 */
	public Item dequeue() {
		if (isEmpty()) throw new NoSuchElementException("Randomized queue underflow");
		int idx = StdRandom.uniform(N);
		Item item = rq[idx];
		if (idx != N-1) { rq[idx] = rq[N-1]; }
		rq[N-1] = null;
		N--;
		if (N == rq.length/4) resize();
        return item;
	}

	/**
	 * Return (but do not delete) a random item.
	 * @return an random item.
	 */
	public Item sample() {
		if (isEmpty()) throw new NoSuchElementException("Randomized queue underflow");
        return rq[StdRandom.uniform(N)];
	}

	/**
	 * Return an independent iterator over items in random order.
	 * @return an independent iterator over items in random order.
	 */
	public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>();  
	}

    /**
     * Randomized queue iterator
     *
     * @param <Item>
     */
    @SuppressWarnings("hiding")
	private class RandomizedQueueIterator<Item> implements Iterator<Item> {

    	private int num = N;
    	private Item[] iq;

        @SuppressWarnings("unchecked")
		public RandomizedQueueIterator() {
        	iq = (Item[]) new Object[N];
        	for (int i = 0; i < N; i++) {
        		iq[i] = (Item) rq[i];
        	}
        }

        public boolean hasNext() { return num > 0; }

        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
    		int idx = StdRandom.uniform(num);
    		Item item = iq[idx];
    		if (idx != num-1) { iq[idx] = iq[num-1]; }
    		iq[num-1] = null;
    		num--;
    		return item;
        }
    }

    /**
     * Unit testing
     * @param args
     */
    public static void main(String[] args) {

	}
}
