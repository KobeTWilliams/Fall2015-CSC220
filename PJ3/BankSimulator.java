package PJ3;

import java.util.*;
import java.io.*;

// You may add new functions or data in this class 
// You may modify any functions or data members here
// You must use Customer, Teller and ServiceArea
// to implement Bank simulator

class BankSimulator {

  // input parameters
  private int numTellers, customerQLimit;
  private int simulationTime, dataSource;
  private int chancesOfArrival, maxTransactionTime;

  // statistical data
  private int numGoaway, numServed, totalWaitingTime;
  private float avgWatingTime;

  // internal data
  private int customerIDCounter;   // customer ID counter
  private ServiceArea servicearea; // service area object
  private Scanner dataFile;	   // get customer data from file
  private Scanner input;
  private Random dataRandom;	   // get customer data using random function
  private String fileName;

  // most recent customer arrival info, see getCustomerData()
  private boolean anyNewArrival;
  private int transactionTime;

  // initialize data fields
  private BankSimulator()
  {
      numTellers = 0;
      customerQLimit = 0;
      simulationTime = 0;
      dataSource = 0;
      chancesOfArrival = 0;
      maxTransactionTime = 0;
      numGoaway = 0;
      numServed = 0;
      totalWaitingTime = 0;
      avgWatingTime = 0;
	// add statements
  }

  private void setupParameters()
  {
	// read input parameters
	// setup dataFile or dataRandom
	// add statements
      input = new Scanner(System.in);
      System.out.print("Enter simulation time (positive integer) : ");
      simulationTime = input.nextInt();
      System.out.print("Enter maximum transaction time of customers : ");
      maxTransactionTime = input.nextInt();
      System.out.print("Enter chances (0% < & <= 100%) of new customer : ");
      chancesOfArrival = input.nextInt();
      System.out.print("Enter the number of tellers : ");
      numTellers = input.nextInt();
      System.out.print("Enter customer queue limit : ");
      customerQLimit = input.nextInt();
      System.out.print("Enter 1/0 to get data from file/Random : ");
      dataSource = input.nextInt();
      if(dataSource == 1) {
          input.nextLine();
          System.out.print("Enter filename : ");
          fileName = input.nextLine();
          try {
              File file = new File(fileName);
              dataFile = new Scanner(file);
          } catch (FileNotFoundException e) {
              e.printStackTrace();
          }
      }


  }


  // Refer to step 1 in doSimulation()
  private void getCustomerData()
  {
	// get next customer data : from file or random number generator
	// set anyNewArrival and transactionTime
	// add statements
      customerIDCounter++;
      if(dataSource == 0) {
          dataRandom = new Random();
          anyNewArrival = ((dataRandom.nextInt(100)+1) <= chancesOfArrival);
          transactionTime = dataRandom.nextInt(maxTransactionTime) + 1;

     }else if(dataSource == 1) {

          int data1 = dataFile.nextInt();
          int data2 = dataFile.nextInt();
          //System.out.println(data1 + " " + data2);
          anyNewArrival = (((data1 % 100) + 1) <= chancesOfArrival);
          transactionTime = (data2 % maxTransactionTime) + 1;
      }
  }



  private void doSimulation()
  {

	// add statements
    System.out.println("\n\n        ***  Start Simulation  ***");
	// Initialize ServiceArea
    servicearea = new ServiceArea(numTellers, customerQLimit);
      System.out.println("\n\nTeller #1 to #" + numTellers + " are ready....\n");
	// Time driver simulation loop
  	for (int currentTime = 0; currentTime < simulationTime; currentTime++) {

    		// Step 1: any new customer enters the bank?
    		getCustomerData();
            Customer nextCustomer;
        System.out.println("---------------------------------------------");
        System.out.println("Time : " + currentTime);
    		if (anyNewArrival) {
      		    // Step 1.1: setup customer data
      		    // Step 1.2: check customer waiting queue too long?
                    //           customer goes away or enters queue
                nextCustomer = new Customer(customerIDCounter, transactionTime, currentTime);
                if(servicearea.isCustomerQTooLong()) {
                    System.out.println("\tCustomer #" + nextCustomer.getCustomerID() +
                            " leaves because of the long line.");
                    numGoaway++;
                }else {
                    System.out.println("\tCustomer #" + nextCustomer.getCustomerID() +
                            " arrives with transaction time " + nextCustomer.getTransactionTime() + " units.");
                    servicearea.insertCustomerQ(nextCustomer);
                    System.out.println("\tCustomer #" + nextCustomer.getCustomerID() +
                            " waits in the customer queue.");

                }

            } else {
      		    System.out.println("\tNo new customer!");
                customerIDCounter--;
    		}

    		// Step 2: free busy tellers, add to free tellerQ
            Teller soonFreeTeller = servicearea.getFrontBusyTellerQ();
            while(soonFreeTeller != null && soonFreeTeller.getEndBusyIntervalTime() == currentTime) {

                    System.out.println("\tCustomer #" + (soonFreeTeller.getCustomer()).getCustomerID() + " is done.");
                    soonFreeTeller.busyToFree();
                    servicearea.removeBusyTellerQ();
                    servicearea.insertFreeTellerQ(soonFreeTeller);
                    System.out.println("\tTeller  #" + soonFreeTeller.getTellerID() + " is free.");
                    soonFreeTeller = servicearea.getFrontBusyTellerQ();
            }
    		// Step 3: get free tellers to serve waiting customers
            while(!servicearea.emptyCustomerQ() && !servicearea.emptyFreeTellerQ()) {
                Customer servingCustomer = servicearea.removeCustomerQ();
                Teller servingTeller = servicearea.removeFreeTellerQ();

                servingTeller.freeToBusy(servingCustomer, currentTime);
                servicearea.insertBusyTellerQ(servingTeller);

                totalWaitingTime = totalWaitingTime + (currentTime - servingCustomer.getArrivalTime());

                System.out.println("\tCustomer #" + servingCustomer.getCustomerID() + " gets a teller.");
                System.out.println("\tTeller #" + servingTeller.getTellerID() +
                        " starts serving customer #" + servingCustomer.getCustomerID() + " for " +
                        servingCustomer.getTransactionTime() + " units.");
                numServed++;
            }

    } // end simulation loop
    if(dataSource == 1) {
        dataFile.close();
    }else {
        dataRandom = null;
    }
    // clean-up
  }

  private void cleanup () {
      customerIDCounter = 0;
      numGoaway = 0;
      numServed = 0;
      totalWaitingTime = 0;
      avgWatingTime = 0;
        if(!servicearea.emptyBusyTellerQ()) {
            servicearea.removeBusyTellerQ();
        }
        if(!servicearea.emptyFreeTellerQ()) {
            servicearea.removeFreeTellerQ();
        }
        if(!servicearea.emptyCustomerQ()) {
            servicearea.removeCustomerQ();
        }
  }

  private void printStatistics()
  {
	// add statements into this method!
	// print out simulation results
	// see the given example in README file 
        // you need to display all free and busy gas pumps

      System.out.println("\n\n============================================\n\n");

      System.out.println("End of simulation report \n\n" +
                 "\t total arrival customer\t:" + customerIDCounter +
                "\n\t customers gone-away\t: " + numGoaway +
                 "\n\t customers served\t: " + numServed);

      System.out.println("\n\n\t *** Current Tellers Info. ***\n\n");
      servicearea.printStatistics();

      avgWatingTime = (numServed == 0) ? 0 : ((float)totalWaitingTime/numServed);
      System.out.println("\n\tTotal waiting time\t: " + totalWaitingTime +
                            "\n\tAverage waiting time\t: " + avgWatingTime);

      System.out.println("\n\n\tBusy Tellers info. : \n\n");
      while(!servicearea.emptyBusyTellerQ()) {
          Teller busyTeller = servicearea.removeBusyTellerQ();
          busyTeller.setEndIntervalTime(simulationTime, 1);
          busyTeller.printStatistics();
      }
      System.out.println("\n\n\tFree Tellers info. : \n\n");
      while(!servicearea.emptyFreeTellerQ()) {
          Teller freeTeller = servicearea.removeFreeTellerQ();
          freeTeller.setEndIntervalTime(simulationTime, 0);
          freeTeller.printStatistics();
          System.out.println("\n");
      }
      cleanup();
//      System.out.println("Busy Teller Q : " + servicearea.emptyBusyTellerQ());
//      System.out.println("Busy Free Q : " + servicearea.emptyFreeTellerQ());
//      System.out.println("Customer Q : " + servicearea.emptyCustomerQ());

  }

  // *** main method to run simulation ****
  public static void main(String[] args) {
   	BankSimulator runBankSimulator = new BankSimulator();
   	runBankSimulator.setupParameters();
   	runBankSimulator.doSimulation();
   	runBankSimulator.printStatistics();


  }

}
