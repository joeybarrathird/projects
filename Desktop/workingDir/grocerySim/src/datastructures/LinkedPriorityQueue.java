package datastructures;

import java.util.NoSuchElementException;

//*******************************************************************
//
//LinkedPriorityQueue.java		Authors: Lewis/Chase
//
//*******************************************************************

public class LinkedPriorityQueue <T> implements 
      PriorityQueueADT <T> {
	private LinearNode <T> front;
	private int count;
	
	public LinkedPriorityQueue () {
		front = null;
		count = 0;
	}
	
	public void add(T element){
		LinearNode <T> node = new LinearNode<T> (element);
		Comparable <T> compValue = (Comparable <T>) element;
		LinearNode <T> current = front;
		LinearNode<T> previous = null;
	   
		while (current != null &&  // position current and previous
		    compValue.compareTo(current.getElement()) > 0) {
			previous = current;
			current = current.getNext();
		}
		
		node.setNext(current);
		if (previous == null)  // element belonged at the front
			front = node;
		else 
		    previous.setNext(node);
		count++;	
	}

	public T removeMin() {
		if (front == null)
			throw new NoSuchElementException("removeMin failed");
		T result = front.getElement();
		front = front.getNext();
		count--;
		return result;
	}

	public T findMin() {
		if (front == null)
			throw new NoSuchElementException("removeMin failed");
		return front.getElement();
	}
	
	public void makeEmpty() {
		front = null;
		count = 0;
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
			result = result + next.getElement() + "\n ";
		    next = next.getNext();
		}
	    return result + "]";
	}
}
