package grocery;

public class Customer implements Comparable<Object>{

	private int numItems;
	private int serviceTime; //This is determined by the Cashiers timePerItem
	private int departTime;
	private int arrivalTime;
	private int waitTime;
	private int finishedTime; //Determined in the simSetup run method currentTime + serviceTime
	private int clerkID; //ID of the Cashier that serves the customer
	
	public Customer(int arrive,int numI){
		numItems = numI;
		arrivalTime = arrive;
		serviceTime = 0;
		finishedTime = 0;
		clerkID = 0;
		waitTime = 0;
	}
	
	public void setServiceTime(Cashier cash){
		serviceTime = numItems * cash.getTimePerItem();
	}
	
	public int retNumItems(){
		return numItems;
	}
	
	public void setDepartTime(int dTime){
		departTime = dTime;
	}
	public int retArrivalTime(){
		return arrivalTime;
	}
	
	public int retDepartTime(){
		return departTime;
	}
	
	public int retServiceTime(){
		return serviceTime;
	}
	
	public int retFinishedTime(){
		return finishedTime;
	}

	public void setClerkID(Cashier cash){
		clerkID = cash.getID();
	}
	
	public int getClerkID(){
		return clerkID;
	}
	
	@Override
	public int compareTo(Object arg0) {
		Customer tmp = (Customer) arg0;
		if(this.retArrivalTime() == tmp.retArrivalTime())
		    return 0;
		else if(this.retArrivalTime() > tmp.retArrivalTime())
			return 1;
		else
			return -1;
	}
	
	public String toString(){
		String[] hold = new String[3];
		String howTo = "%6d";
	
		hold[0] = String.format(howTo,arrivalTime,"");
		hold[1] = String.format(howTo,waitTime,"");
		hold[2] = String.format(howTo,finishedTime,"");
		
		return "Arrival time:" + 
        hold[0] + 
        "|Wait time: " +
        hold[1] + 
        "|Finished time: " +
        hold[2] +
        "|Cashier ID: " + 
        clerkID;
		
	}
	
	public void waitTimeIncrease(){
		waitTime++;
	}
	
	public int retWaitTime(){
		return waitTime;
	}
	
	public void setFinishedTime(int currentTime){
		finishedTime = currentTime + serviceTime;
	}
	
	public int getFinishedTime(){
		return finishedTime;
	}
	
}
