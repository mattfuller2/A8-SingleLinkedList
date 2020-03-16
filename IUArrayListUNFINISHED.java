import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Array-based implementation of IndexedUnsortedList. An Iterator with working remove() method is
 * implemented, but ListIterator is unsupported.
 * 
 * @author
 *
 * @param <T> type to store
 */
public class IUArrayListUNFINISHED<T> implements IndexedUnsortedList<T> {
	private static final int DEFAULT_CAPACITY = 10;
	private static final int NOT_FOUND = -1;

	private T[] array;
	private int rear;
	private int modCount;

	/** Creates an empty list with default initial capacity */
	public IUArrayListUNFINISHED() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Creates an empty list with the given initial capacity
	 * 
	 * @param initialCapacity
	 */
	@SuppressWarnings("unchecked")
	public IUArrayListUNFINISHED(int initialCapacity) {
		array = (T[]) (new Object[initialCapacity]);
		rear = 0;
		modCount = 0;
	}

	/** Double the capacity of array */
	private void expandCapacity() {
		array = Arrays.copyOf(array, array.length * 2);
	}

	@Override
	public void addToFront(T element) {
		// TODO
		if (isEmpty()) {
			array[0] = element;
			rear++;
		}
		else {
			add(0, element);
		}
	}

	@Override
	public void addToRear(T element) {
		// TODO
		add(element);
	}

	@Override
	public void add(T element) {
		// TODO
		if ((array.length - rear) <= 5) {
			expandCapacity();
		}
		array[rear++] = element;
	}

	@Override
	public void addAfter(T element, T target) {
		// TODO
		int index = indexOf(target);
		if (index == NOT_FOUND) {
			throw new NoSuchElementException();
		}
		// array[index + 1] = element;
		//
		// for (int i = index; i < size(); i++) {
		// array[i + 1] = array[i];
		// }
	}

	@Override
	public void add(int index, T element) {
		// TODO
		if ((array.length - rear) <= 5) {
			expandCapacity();
		}

		if (index < 0 || index > rear) {
			throw new IndexOutOfBoundsException();
		}

		array[index] = element;

		int scan = index;
		while (scan < rear) {
			array[scan] = array[scan + 1];
			array[scan + 1] = null;
			scan++;
		}
		rear++;
	}

	@Override
	public T removeFirst() {
		// TODO
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = remove(0);

		return retVal;

	}

	@Override
	public T removeLast() {
		// TODO
		if (array[0] == null || iterator().hasNext() == false) {
			throw new NoSuchElementException();
		}

		T retVal = array[rear - 1];

		array[rear] = null;
		rear--;
		modCount++;

		return retVal;
	}

	@Override
	public T remove(T element) {
		int index = indexOf(element);
		if (index == NOT_FOUND) {
			throw new NoSuchElementException();
		}

		T retVal = array[index];

		return retVal;
	}

	@Override
	public T remove(int index) {
		// TODO
		if (index < rear) {
			T retVal = array[index];
			array[index] = null;
			int scan = index;
			while (scan < rear) {
				array[scan] = array[scan + 1];
				array[scan + 1] = null;
				scan++;
			}
			rear--;
			return retVal;
		}
		else {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public void set(int index, T element) {
		// TODO
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		else
			array[index] = element;
	}

	@Override
	public T get(int index) {
		// TODO
		if (index < rear) {
			return array[index];
		}
		else {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public int indexOf(T element) {
		int index = NOT_FOUND;

		if (!isEmpty()) {
			int i = 0;
			while (index == NOT_FOUND && i <= rear) {
				if (element.equals(array[i])) {
					index = i;
				}
				else {
					i++;
				}
			}
		}

		return index;
	}

	@Override
	public T first() {
		// TODO
		if (array[0] == null || iterator().hasNext() == false) {
			throw new NoSuchElementException();
		}

		return array[0];
	}

	@Override
	public T last() {
		// TODO
		if (array[0] == null || iterator().hasNext() == false) {
			throw new NoSuchElementException();
		}
		return array[rear - 1];
	}

	@Override
	public boolean contains(T target) {
		return (indexOf(target) != NOT_FOUND);
	}

	@Override
	public boolean isEmpty() {
		// TODO
		if (array[0] == null || iterator().hasNext() == false)
			return true;
		return false;
	}

	@Override
	public int size() {
		return rear;
	}

	@Override
	public Iterator<T> iterator() {
		return new ALIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUArrayList */
	private class ALIterator implements Iterator<T> {
		private int nextIndex;
		private int iterModCount;

		public ALIterator() {
			nextIndex = 0;
			iterModCount = modCount;
		}

		@Override
		public boolean hasNext() {
			// TODO
			boolean status = true;
			if (nextIndex >= rear) {
				status = false;
				return status;
			}

			return status;
		}

		@Override
		public T next() {
			checkForComodification();

			if (array[nextIndex] == null) {
				throw new NoSuchElementException();
			}

			T nextVal = array[nextIndex];
			nextIndex++;

			return nextVal;
		}

		@SuppressWarnings("unused")
		@Override
		public void remove() {
			// TODO
			iterModCount++;

			if (nextIndex < iterModCount) {
				throw new IllegalStateException();
			}

			T retVal = array[nextIndex];
			rear--;

			// Shifts the appropriate elements
			if (rear > -1) {
				for (int scan = 0; scan < rear; scan++) {
					array[scan] = array[scan + 1];
				}

				array[rear] = null;
			}

		}

		final void checkForComodification() {
			if (modCount != iterModCount)
				throw new ConcurrentModificationException();
		}

	}

	@Override
	public String toString() {
		String result = "[";
		// for (int i = 0; i < rear; i++) {
		int i = 0;
		for (T j : array) {
			if (array.length == 0)
				break;
			else if (i == (array.length - 1)) {
				result += j;
			}
			else {
				result += j + ", ";
			}
			i++;
		}
		result += "]";
		return result;
	}
}
