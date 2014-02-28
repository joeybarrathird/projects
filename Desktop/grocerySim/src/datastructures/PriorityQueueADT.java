package datastructures;

//*******************************************************************
//
//PriorityQueueADT.java		Authors: Lewis/Chase
//
//*******************************************************************

public interface PriorityQueueADT <T> {
	
	public void add(T element); // inserts element in priority queue

	public T removeMin(); // removes and returns the smallest item

	public T findMin(); // returns smallest item

	public void makeEmpty(); // removes all items

	public boolean isEmpty(); // returns true if empty; else false

	public int size(); // returns the size of the queue
	
	 public String toString();   // returns string representation of queue   
}
