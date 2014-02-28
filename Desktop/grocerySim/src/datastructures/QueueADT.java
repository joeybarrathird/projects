package datastructures;

//*******************************************************************
//
//QueueADT.java		Authors: Lewis/Chase
//
//*******************************************************************

public interface QueueADT<T> {
		
	public void enqueue(T element); // adds element to rear of queue

	public T dequeue(); // removes and returns element at front of queue        

	public T first(); // returns without removing element at front of queue

	public boolean isEmpty(); // returns true if queue contains no elements 

	public int size(); // returns number of elements in queue

	public String toString(); // returns string representation of queue
}

