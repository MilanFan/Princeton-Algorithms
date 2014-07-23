import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Princeton Algorithms Assignment 2: Randomized Queues and Deques.
 *
 * @author Milan Fan
 *
 */
public class Deque<Item> implements Iterable<Item> {

    private int N;				// number of elements on deque
    private Node<Item> first;	// beginning of deque
    private Node<Item> last;	// end of deque

    /**
     * helper linked list class
     *
     * @param <Item>
     */
    private static class Node<Item> {
        private Item item;
        private Node<Item> previous;
        private Node<Item> next;
    }

	/**
	 * Construct an empty deque.
	 */
	public Deque() {
        first = null;
        last  = null;
        N = 0;
	}

	/**
	 * Is the deque empty?
	 * @return true if the deque empty; false otherwise.
	 */
	public boolean isEmpty() {
        return first == null;
	}

	/**
	 * Return the number of items on the deque.
	 * @return the number of items on the deque.
	 */
	public int size() {
		return N;
	}

	/**
	 * Insert the item at the front.
	 * @param item the item to be inserted at the front.
	 */
	public void addFirst(Item item) {
		if (item == null) throw new NullPointerException();
		Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.previous = null;
        first.next = oldfirst;
        if (oldfirst == null) { last = first;              }
        else                  { oldfirst.previous = first; }
        N++;
	}

	/**
	 * Insert the item at the end.
	 * @param item the item to be inserted at the end.
	 */
	public void addLast(Item item) {
		if (item == null) throw new NullPointerException();
		Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.previous = oldlast;
        last.next = null;
        if (oldlast == null) { first = last;        }
        else                 { oldlast.next = last; }
        N++;
	}

	/**
	 * Delete and return the item at the front.
	 * @return the item at the front deleted.
	 */
	public Item removeFirst() {
		if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = first.item;
        first = first.next;
        N--;
        if (isEmpty()) { last = null;           }  // to avoid loitering
        else           { first.previous = null; }
        return item;
	}

	/**
	 * Delete and return the item at the end.
	 * @return the item at the end deleted.
	 */
	public Item removeLast() {
		if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = last.item;
        last = last.previous;
        N--;
        if (last == null) { first = null;     }   // to avoid loitering
        else              { last.next = null; }
        return item;
	}

	/**
	 * Return an iterator over items in order from front to end.
	 * @return an iterator over items in order from front to end.
	 */
	public Iterator<Item> iterator() {
        return new DequeIterator<Item>(first);  
	}

    /**
     * Deque iterator
     *
     * @param <Item>
     */
    @SuppressWarnings("hiding")
	private class DequeIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator(Node<Item> first) {
        	current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
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
