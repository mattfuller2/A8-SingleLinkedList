import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Single-linked node implementation of IndexedUnsortedList. An Iterator with
 * working remove() method is implemented, but ListIterator is unsupported.
 * 
 * @author
 * 
 * @param <T>
 *            type to store
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
	private LinearNode<T> head, tail;
	private int size;
	private int modCount;

	/** Creates an empty list */
	public IUSingleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		// TODO
		LinearNode<T> current = new LinearNode<T>(element);
		size++;

		if (head == null) {
			head = current;
			tail = head;
		} else {
			current.setNext(head);
			head = current;
		}
	}

	@Override
	public void addToRear(T element) {
		// TODO
		LinearNode<T> current = new LinearNode<T>(element);
		size++;

		if (head == null) {
			head = current;
			tail = head;
		} else {
			tail.setNext(current);
			tail = current;
		}
	}

	@Override
	public void add(T element) {
		// TODO
		add(size, element);
	}

	@Override
	public void addAfter(T element, T target) {
		// TODO
		int index = indexOf(target);
		if (index == -1) {
			throw new NoSuchElementException();
		}
		size++;
		modCount++;
	}

	@Override
	public void add(int index, T element) {
		// TODO
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		size++;
		modCount++;
	}

	@Override
	public T removeFirst() throws NoSuchElementException {
		// TODO
		if (size == 0)
			throw new NoSuchElementException();

		LinearNode<T> previous = null;
		LinearNode<T> current = head;
		T retVal = head.getElement();

		if (size == 1) {
			head = tail = null;
			return retVal;
		} else if (size > 1) {
			int COUNT = 0;
			while (current != null) {
				previous = current;
				current = previous.getNext();
				current = current.getNext();
				COUNT++;
				tail = current;
			}

		}

		size--;
		modCount++;

		return retVal;
	}

	@Override
	public T removeLast() throws NoSuchElementException {
		// TODO
		if (tail == null) {
			throw new NoSuchElementException();
		}

		LinearNode<T> previous = null;
		LinearNode<T> current = head;

		while (current.getNext() != null) {
			previous = current;
			current = current.getNext();
		}

		LinearNode<T> result = tail;
		tail = previous;
		if (tail == null) // only one element in list
			head = null;
		else
			tail.setNext(null);
		size--;
		modCount++;

		return result.getElement();
	}

	@Override
	public T remove(T element) {
		if (!contains(element)) {
			throw new NoSuchElementException();
		}

		boolean found = true;
		LinearNode<T> previous = null;
		LinearNode<T> current = head;

		while (current != null && !found) {
			if (element.equals(current.getElement())) {
				found = true;
			} else {
				previous = current;
				current = current.getNext();
			}
		}

		if (!found) {
			throw new NoSuchElementException();
		}

		if (size() == 1) { // only node
			head = tail = null;
		} else if (current == head) { // first node
			head = current.getNext();
		} else if (current == tail) { // last node
			tail = previous;
			tail.setNext(null);
		} else { // somewhere in the middle
			previous.setNext(current.getNext());
		}

		size--;
		modCount++;

		return current.getElement();
	}

	@Override
	public T remove(int index) throws IndexOutOfBoundsException {
		// TODO
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		LinearNode<T> current = head;
		LinearNode<T> previous = current.getNext();
		T retVal = current.getElement();

		int COUNT = 0;
		// if (index < size) {
		// current = current.getNext();
		// previous = current.getNext();
		// head = current;
		// COUNT++;
		// }

		while (current != null) {
			if (COUNT == index) {
				retVal = current.getElement();
				modCount++;
			}
			current = current.getNext();
			COUNT++;
			tail = current;
		}

		return retVal;
	}

	@Override
	public void set(int index, T element) throws IndexOutOfBoundsException {
		// TODO
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		// set(index, element);
		size++;
	}

	@Override
	public T get(int index) throws IndexOutOfBoundsException {
		// TODO
		if (index < 0 || index >= size || isEmpty()) {
			throw new IndexOutOfBoundsException();
		}

		T retVal = head.getElement();
		LinearNode<T> current = head;

		if (index == 0) {
			retVal = current.getElement();
		}

		// while (previous != null) {
		// if (count == index)
		// retVal = current.getElement();
		// previous = current.getNext();
		// count++;
		// tail = previous;
		// }

		modCount++;
		return retVal;
	}

	@Override
	public int indexOf(T element) {
		// TODO
		int index = 0;
		LinearNode<T> elem = head;
		for (index = 0; index < size; index++) {
			if (elem == element)
				return index;
			else
				elem = elem.getNext();
		}
		return -1;
	}

	@Override
	public T first() throws NoSuchElementException {
		// TODO
		if (isEmpty())
			throw new NoSuchElementException();

		return head.getElement();
	}

	@Override
	public T last() throws NoSuchElementException {
		// TODO
		if (isEmpty())
			throw new NoSuchElementException();

		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		// TODO
		if (isEmpty())
			return false;

		boolean found = false;
		LinearNode<T> current = head;

		while (current != null && !found)
			if (target.equals(current.getElement()))
				found = true;
			else
				current = current.getNext();

		return found;
	}

	@Override
	public boolean isEmpty() {
		// TODO
		return (head == null);
	}

	@Override
	public int size() {
		// TODO
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUSingleLinkedList */
	private class SLLIterator implements Iterator<T> {
		private LinearNode<T> nextNode;
		private int iterModCount;

		/** Creates a new iterator for the list */
		public SLLIterator() {
			nextNode = head;
			iterModCount = modCount;
		}

		@Override
		public boolean hasNext() throws ConcurrentModificationException {
			// TODO
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();

			return (nextNode != null);
		}

		@Override
		public T next() throws ConcurrentModificationException {
			// TODO
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();

			if (!hasNext())
				throw new NoSuchElementException();

			T result = nextNode.getElement();
			nextNode = nextNode.getNext();
			return result;
		}

		// if the next method has not yet been called, or the remove method has already
		// been called after the last call to the next method
		@Override
		public void remove() throws IllegalStateException {
			// TODO
			if (!hasNext())
				throw new IllegalStateException();

			T result = nextNode.getElement();
			size--;
		}
	}

	@Override
	public String toString() {
		LinearNode<T> current = head;
		String result = null;

		while (current != null) {
			result = result + current.getElement() + "\n";
			current = current.getNext();
		}

		return "[" + result + "]";
	}
}
