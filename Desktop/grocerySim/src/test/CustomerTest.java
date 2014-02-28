package test;

import static org.junit.Assert.*;
import grocery.Cashier;
import grocery.Customer;

import org.junit.Test;

public class CustomerTest {

	final int DEFAULTTIMEPER = 3;
	final int DEFAULTARRIVE = 0;
	final int DEFAULTNUMITEMS = 20;
	Cashier cash;
	Customer cust;
	
	//create a Cashier object and Customer object for testing
	public void buildUp(){
		cash = new Cashier(DEFAULTTIMEPER);
		cust = new Customer(DEFAULTARRIVE,DEFAULTNUMITEMS);
	}
	
	/**
	 * This test checks the retServiceTime for correctness with
	 * the default values set when the constructor is called.
	 */
	@Test
	public void retServiceTimeDefaultTest() {
		buildUp();
		assertTrue(cust.retServiceTime() == 0);
	}
	
	/**
	 * This test checks the retServiceTime method for correctness with
	 * the serviceTime set using the values set by using cust.setServiceTime(cash).
	 */
	@Test
	public void retServiceTimeTest() {
		buildUp();
		cust.setServiceTime(cash);
		assertTrue(cust.retServiceTime() == (20*3));
	}
	
	/**
	 * Test for retNumItems correctness. The value of numItems is set
	 * when the constructor is called. The expected value is DEFAULTNUMITEMS.
	 */
	@Test
	public void retNumItemsTest(){
		buildUp();
		assertTrue(20 == cust.retNumItems());
	}
	
	
	/**
	 * Test the retDepartTime method after having called the constructor. Expected value
	 * is 0; Also test retDepartTime method.
	 */
	@Test
	public void retDepartTimeDefaultTest(){
		buildUp();
		assertTrue(0 == cust.retDepartTime());
	}
	
	/**
	 * Test the retDepartTime method after having called the setDepartTime method. Expected value
	 * is 50; Also test retDepartTime method.
	 */
	@Test
	public void setDepartRetDepartTest(){
		buildUp();
		cust.setDepartTime(50);
		assertTrue(50 == cust.retDepartTime());
	}
	
	/**
	 * Test the retArrivalTime method after having called the constructor. Expected value
	 * is 0;
	 */
	@Test
	public void getArrivalTimeTest(){
		buildUp();
		assertTrue(0 == cust.retArrivalTime());
	}
	
	/**
	 * Test the waitTimeIncrease method. Expected value is 1. Also test
	 * the retWaitTime method. 
	 */
	@Test
	public void waitTimeIncreaseTest(){
		buildUp();
		cust.waitTimeIncrease();
		assertTrue(1 == cust.retWaitTime());
	}
	
	/**
	 * Test the setFinishedTime method and getFinishedTime method. We will use
	 * 50 as the currentTime. First we will call the setServiceTime in order to get
	 * setFinishedTime to work correctly. The expected value is 110 (using default values
	 * with the constructor).
	 */
	@Test
	public void setFinishedGetTest(){
		buildUp();
		cust.setServiceTime(cash); //3*20 -> service time = 60
		cust.setFinishedTime(50);
		assertTrue(110 == cust.retFinishedTime());
	}

}
