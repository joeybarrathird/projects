package datastructures;

//*******************************************************************
//
//LinearNode.java		Authors: Lewis/Chase
//
//Represents a nod in a linked list.
//*******************************************************************

public class LinearNode<T> {
	private LinearNode<T> next;
	private T element;

	public LinearNode() {
		next = null;
		element = null;
	}

	public LinearNode(T elem) {
		next = null;
		element = elem;
	}

	public LinearNode<T> getNext() {
		return next;
	}

	public void setNext(LinearNode<T> node) {
		next = node;
	}

	public T getElement() {
		return element;
	}

	public void setElement(T elem) {
		element = elem;
	}
}