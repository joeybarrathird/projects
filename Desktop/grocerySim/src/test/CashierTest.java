package test;

import static org.junit.Assert.*;
import grocery.Cashier;
import grocery.Customer;

import org.junit.Test;


public class CashierTest {

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
	 * Testing for correctness from buildUp defaults
	 * to ensure the constructor 
	 */
	@Test
	public void getDefaultTimePerItemtest() {
		buildUp();
		assertTrue(DEFAULTTIMEPER == cash.getTimePerItem());
	}
	
	/**
	 * Test for ID correctness when calling the constructor for
	 * the Cashier class. The Default value should be 0. 
	 */
	@Test
	public void defaultGetIDTest(){
		buildUp();
		assertTrue(0 == cash.getID());
	}
	
	/**
	 * Test for ID correctness when calling the setID method for
	 * the Cashier class. The Default value should be 0, so using the
	 * setID method we should expect the value we set it to (842). 
	 */
	@Test
	public void setGetIDTest(){
		buildUp();
		cash.setID(842);
		assertTrue(842 == cash.getID());
	}
	
	
	/**
	 * Test for Customer correctness when calling the constructor for
	 * the Cashier class. The Default value should be null, so using the
	 * getDefaultCustomer method we should expect the value to be null. 
	 */
	@Test
	public void getDefaultCustomer(){
		buildUp();
		assertTrue(null == cash.retCustomer());
	}
	
	/**
	 * Test for Customer correctness when calling the setCustomer method for
	 * the Cashier class. The Default value should be null, so using the
	 * setCustomer method we should expect the value to be that of cust (In buildUp). 
	 */
	@Test
	public void setCustomerGetCustomerTest(){
		buildUp();
		cash.setCustomer(cust);
		assertTrue(cust.equals(cash.retCustomer()));
	}
	
	/**
	 * Test the isAvail method for correctness. Avail is set to true
	 * by default when the Cashier constructor is called. Expected value is
	 * true.
	 */
	@Test
	public void isAvailDefaultTest(){
		buildUp();
		assertTrue(true == cash.isAvail());
	}
	
	/**
	 * test the setAvail method for correctness. When called by the constructor it is
	 * set to true by default. The expected result is false, after setAvail has been called.
	 */
	@Test
	public void setAvailGetTest(){
		buildUp();
		cash.setAvail(false);
		assertTrue(false == cash.isAvail());
	}
	
	/**
	 * Test the toString method. When called by the constructor we can expect the string
	 * returned to be "Cashier timePerItem: DEFAULTTIMEPER"
	 */
	@Test
	public void toStringDefaultTest(){
		buildUp();
		assertEquals("Cashier timePerItem: " + DEFAULTTIMEPER,cash.toString());
	}
	
	
}
