package grocery;

public class Cashier {

	private int timePerItem; //
	private Boolean avail;
	private Customer cust;
	private int ID;
	
	public Cashier(int tpi){
		avail = true;
		cust = null;
		timePerItem = tpi;
		ID = 0;
	}
	
	public int getTimePerItem(){
		return timePerItem;
	}
	
	public void setCustomer(Customer cur){
		cust = cur;
		cust.setServiceTime(this);
		avail = false;
	}
	
	public void setAvail(boolean x){
		avail = x;
	}
	
	public boolean isAvail(){
		return avail == true;
	}
	
	public void setID(int x){
		ID = x;
	}
	
	public int getID(){
		return ID;
	}
	
	public Customer retCustomer(){
		return cust;
	}
	
	public String toString(){
		return "Cashier timePerItem: " + timePerItem;
	}

	
}
