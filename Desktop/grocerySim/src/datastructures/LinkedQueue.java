package datastructures;

import java.util.NoSuchElementException;

//*******************************************************************
//
//LinkedQueue.java		Authors: Lewis/Chase
//
//*******************************************************************

public class LinkedQueue <T> implements QueueADT <T>{
	private LinearNode <T> front;
	private LinearNode <T> back;
	private int count;
	
	public LinkedQueue() {
		front = null;
		back = null;
		count = 0;
	}
	
	public void enqueue(T element) {
		LinearNode <T> node = new LinearNode<T>(element);
        if (front == null) { //Empty list
        	front = node;
        	back = node;
        } else {
            back.setNext(node);
            back = node;
        }
        count++;
	}

	public T dequeue() {
		if (isEmpty())
           throw new NoSuchElementException("Failed to dequeue");
		T result = front.getElement();
		front = front.getNext();
		if (front == null)
			back = null;
		count--;
		return result;
	}

	public T first() {
		if (isEmpty())
			throw new NoSuchElementException("Failed first");
		return front.getElement();
	}

	public boolean isEmpty() {
		return count == 0;
	}

	public int size() {
		return count;
	}

	public String toString() {
		String result = getClass().getName() + ":[";
		LinearNode <T> next = front;
		while (next != null) {
			result = result + next.getElement() + " ";
		    next = next.getNext();
		}
	    return result + "]";
	}
}
