package grocery;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import datastructures.LinkedPriorityQueue;

/**
 * In this simulator, the currentTime is incremented with each iteration in
 * a while loop. Each iteration corresponds to a single second. Throughout
 * the simulation we check for conditions to be met (ex. cashier becomes available)
 * with respect to currentTime. The customer class and cashier class all contain
 * methods that use the currentTime in simSetup to set appropriate values
 * within each class. The output of the simulation will be in the output.txt
 * included in the package grocery. It is not set to append and will overwrite
 * each time the simulation is ran.
 * @author Joe Ybarra III
 *
 */

public class simSetup {

	private static final int MAXTPI = 4; //Time per item for cashier
	private static final int LATESTARRIVAL = 64080; //17.8 hours till close (seconds)
	private static final int MAXITEMS = 128; //assuming max items is 128
	private static final int TOTALCUSTOMERS = 2500; //assumed daily number of customers
	private static final int TOTALCASHIERS = 5; //Number of cashiers working
	private Random rand = new Random();
	private ArrayList<Customer> starting;
	private static ArrayList<Customer> finished;
	private ArrayList<Customer> waiting;
	private int currentTime;
	private ArrayList<Cashier> cashiers;
	private ArrayList<Cashier> cashiersAvail;
	private ArrayList<Cashier> cashiersNotAvail;
	private LinkedPriorityQueue<Customer> line;
	private int averageWaitTime;
	private int[] itemsProcessed = new int[TOTALCASHIERS]; //holds the number items scanned by each respective clerk
	private int[] numOfCustomers = new int[TOTALCASHIERS]; //holds the number of completed transaction customers
	private int[] volumePerHour = new int[18]; //Store is open from 6am to 12am.
	private DecimalFormat form = new DecimalFormat("#.##");
	
	public simSetup(){
		starting = new ArrayList<Customer>();
		finished = new ArrayList<Customer>();
		waiting = new ArrayList<Customer>();
		line = new LinkedPriorityQueue<Customer>();
		cashiers = new ArrayList<Cashier>();
		cashiersAvail = new ArrayList<Cashier>();
		cashiersNotAvail = new ArrayList<Cashier>();
		addCustomers();
		addCashiers();
		currentTime = 0;
		setToZero(itemsProcessed);
		setToZero(numOfCustomers);
		setToZero(volumePerHour);
	}
	
	/**
	 * Add Customers to the starting queue. From this queue
	 * Customers will either be set to a Cashier or put in the
	 * waiting queue.
	 */
	private void addCustomers(){
		for(int i = 0; i < TOTALCUSTOMERS; i++)
			starting.add(new Customer(rand.nextInt(LATESTARRIVAL),rand.nextInt(MAXITEMS)+1));
	}
	
	/**
	 * Add cashiers to the cashiers arrayList which will then
	 * be used to add cashiers to the cashiersAvail arrayList.
	 */
	private void addCashiers(){
		for(int i = 0; i < TOTALCASHIERS; i++){
		   cashiers.add(new Cashier(rand.nextInt(MAXTPI)+1));
		   cashiers.get(i).setID(i);
		   }
	}
	
	/**
	 * This is the workhorse of the class. It runs the simulation
	 * and calls the appropriate methods to output to the external file.
	 */
	public void run(){
		Iterator<Customer> iter = starting.iterator();
		Iterator<Cashier> cIter = cashiers.iterator();
		
		/*add all the customers to the line*/
		while(iter.hasNext()){
			line.add(iter.next());
		}
		
		/*add cashiers to the available queue*/
		Cashier tmp;
		while(cIter.hasNext()){
			tmp = cIter.next();
			if(tmp.isAvail())
				cashiersAvail.add(tmp);
		}

		while(!line.isEmpty() || !cashiersNotAvail.isEmpty()){          
			/*
			 * This segment is responsible for either assigning a customer waiting in line to
			 * a cashier or the waiting queue (stuck in line).
			 */
			if(line.isEmpty() == false){//this line ensures that findMin won't be executed on a empty line (throws an error)
			   while(line.findMin().retArrivalTime() == currentTime){
				   if(cashiersAvail.isEmpty() == false){
					   cIter = cashiersAvail.iterator();
					   tmp = cIter.next();
					   tmp.setAvail(false);
					   tmp.setCustomer(line.removeMin());
					   tmp.retCustomer().setClerkID(tmp);
					   tmp.retCustomer().setServiceTime(tmp);
					   tmp.retCustomer().setFinishedTime(currentTime);
					   cIter.remove();
					   cashiersNotAvail.add(tmp);
					   if(line.isEmpty()){
						  break;
					   }
				   }else if(cashiersAvail.isEmpty() == true){
					   waiting.add(line.removeMin());
					   if(line.isEmpty()){
						   break;
					   }
				   }
			   }
			}			
			
			/*
			 * Check and see if the transaction is complete
			 * and reassign the cashier to the available queue
			 * and add the customer to the finished transaction queue.
			 */
			cIter = cashiersNotAvail.iterator();
			while(cIter.hasNext()){
				tmp = cIter.next();
				if(tmp.retCustomer().retFinishedTime() == currentTime){
					finished.add(tmp.retCustomer());
					addToVolumeArray(volumePerHour,tmp.retCustomer());
					itemsProcessed[tmp.retCustomer().getClerkID()] += tmp.retCustomer().retNumItems();
					numOfCustomers[tmp.retCustomer().getClerkID()]++;
					cIter.remove();
					tmp.setAvail(true);
					cashiersAvail.add(tmp);
				}
			}		
			/*
			 * Now we check to see if there is an available cashier
			 * and some customer waiting in line. If there is someone waiting in
			 * line we assign them to the cashier.
			 */
			cIter = cashiersAvail.iterator();
			iter = waiting.iterator();
			while(cIter.hasNext()){
				tmp = cIter.next();
				if(iter.hasNext()){
					 cIter.remove();
					 tmp.setAvail(false);
					 tmp.setCustomer(iter.next());
					 iter.remove();
					 tmp.retCustomer().setClerkID(tmp);
					 tmp.retCustomer().setServiceTime(tmp);
					 tmp.retCustomer().setFinishedTime(currentTime);
					 cashiersNotAvail.add(tmp);
				}
			}
			
			/*
			 * current time is increase by 1 unit per cycle ran.		
			*/
			currentTime++;
			
			/*
			 * increase the waitTime of everyone in the queue by 1
			 */
			iter = waiting.iterator();
			while(iter.hasNext())
				iter.next().waitTimeIncrease();	
			
		}
		iter = finished.iterator();
		while(iter.hasNext()){
			averageWaitTime += iter.next().retWaitTime();
		}
		
		  PrintWriter pOut = null;
		  try {
		     File file = new File("src/grocery/output.txt");
		     FileWriter fOut = new FileWriter(file);
		     pOut = new PrintWriter(fOut);
		     pOut.println("Total simulated time ran: " + form.format(((double)currentTime/(double)3600)) + " hours.");
				if(averageWaitTime != 0)
					pOut.println("Average customer wait time: " + form.format((((double)averageWaitTime/(double)finished.size())/(double)60)) + " minutes.");
				else
					pOut.println("Average wait time: 0 minutes.");
				pOut.println("Total customers served: " + finished.size());
				pOut.println("Customers per hour:");
				for(int i = 0; i < volumePerHour.length; i++){
		        	if(i < 5){
		        		pOut.printf("\t%02dam - %02dam: %04d\n",(i+6),(i+7),volumePerHour[i]);
		        	}else if(i == 5){
		        		pOut.printf("\t%02dam - %02dpm: %04d\n",11,12,volumePerHour[i]);
		        	}else if(i == 6){
		        		pOut.printf("\t%02dpm - %02dpm: %04d\n",12,1,volumePerHour[i]);	
		        	}else if(i > 6 && i < 17){
		        		pOut.printf("\t%02dpm - %02dpm: %04d\n",(i-6),(i-5),volumePerHour[i]);
		        	}else if(i == 17){
		        		pOut.printf("\t%02dpm - %02dam: %04d\n",11,12,volumePerHour[i]);
		        	}
				}
				pOut.println("-------------------------------------");
				pOut.println("<Cashier Information>\n");
		        for(int i = 0; i < TOTALCASHIERS; i++){
		        	pOut.println("Cashier " + i);
		        	pOut.println("\tItems processed: " + itemsProcessed[i]);
		        	pOut.println("\tNumber of customers served: " + numOfCustomers[i]);
		        	pOut.println("\tTime per item: " + cashiersAvail.get(i).getTimePerItem() + " seconds.");
				}
		        pOut.println();
		        pOut.println("-------------------------------------");
		        formattedOutput(pOut);
		  } catch (IOException e) {
		     e.printStackTrace();
		  } finally {
		     if (pOut != null) {
		        pOut.close();
		     }
		  }      
	}//end of run function
	
	
	/*
	 * I call this to print the finished customer information
	 *  instead of calling toString on the 
	 * arrayList (finished) containing Customers.
	 */
	public static void formattedOutput(PrintWriter pOut){
		
		/*
		 * If the number of customers exceeds 9999
		 * increase the format of howTo appropriately.
		 * i.e, 10000 -> howTo = "%05d";
		 */
		  try {
			 String hold;
		     String howTo = "%04d"; 
		     Iterator<Customer> iter = finished.iterator();
			 Customer tmp;
			 int count = 1;
			 while(iter.hasNext()){
				 tmp = iter.next();
				 hold = String.format(howTo,count,"");
				 pOut.print("Customer " + hold + "|");
			     pOut.println(tmp);
				 count++;
			   }
			 pOut.println("<--- END OF SIMULATION RUN --->\n\n\n");
		  } finally {
		     if (pOut != null) {
		        pOut.close();
		     }
		  }
	}
	
	/*
	 * This function increases the appropriate customer count per hour based on the 
	 * arrival time of the customer passed. 
	 */
	public static void addToVolumeArray(int[] x, Customer cust){
		if(cust.retArrivalTime() >= 0 && cust.retArrivalTime() <= 3599){
			x[0]++;
		}else if(cust.retArrivalTime() >= 3600 && cust.retArrivalTime() <= 7199){
			x[1]++;
		}else if(cust.retArrivalTime() >= 7200 && cust.retArrivalTime() <= 10799){
			x[2]++;
		}else if(cust.retArrivalTime() >= 10800 && cust.retArrivalTime() <= 14399){
			x[3]++;
		}else if(cust.retArrivalTime() >= 14400 && cust.retArrivalTime() <= 17999){
			x[4]++;
		}else if(cust.retArrivalTime() >= 18000 && cust.retArrivalTime() <= 21599){
			x[5]++;
		}else if(cust.retArrivalTime() >= 21600 && cust.retArrivalTime() <= 25199){
			x[6]++;
		}else if(cust.retArrivalTime() >= 25200 && cust.retArrivalTime() <= 28799){
			x[7]++;
		}else if(cust.retArrivalTime() >= 28000 && cust.retArrivalTime() <= 32399){
			x[8]++;
		}else if(cust.retArrivalTime() >= 32400 && cust.retArrivalTime() <= 35999){
			x[9]++;
		}else if(cust.retArrivalTime() >= 36000 && cust.retArrivalTime() <= 39599){
			x[10]++;
		}else if(cust.retArrivalTime() >= 39600 && cust.retArrivalTime() <= 43199){
			x[11]++;
		}else if(cust.retArrivalTime() >= 43200 && cust.retArrivalTime() <= 46799){
			x[12]++;
		}else if(cust.retArrivalTime() >= 46800 && cust.retArrivalTime() <= 50399){
			x[13]++;
		}else if(cust.retArrivalTime() >= 50400 && cust.retArrivalTime() <= 53999){
			x[14]++;
		}else if(cust.retArrivalTime() >= 54000 && cust.retArrivalTime() <= 57599){
			x[15]++;
		}else if(cust.retArrivalTime() >= 57600 && cust.retArrivalTime() <= 61199){
			x[16]++;
		}else if(cust.retArrivalTime() >= 61200 && cust.retArrivalTime() <= 64800){
			x[17]++;
		}
	}
	
	/*
	 * Sets every index in the int array passed to 0
	 */
	public static void setToZero(int[] x){
		for(int i = 0; i < x.length; i++){
			x[i] = 0;
		}
	}
	
	
}
