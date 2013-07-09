package com.yahoo.rw.solutions;

import junit.framework.TestCase;

import org.junit.Test;

/*
 * 
 * Name : FirstReadersWriterTest.java
 * Author : knar@
 * Description : Driver class to test the execution of FirstReadersWriter.java 
 * Refer to src/main/resources/README.txt for more information on the input and output
 * 
 */

public class FirstReadersWriterTest extends TestCase {

	FirstReadersWriterSolution f1 = new FirstReadersWriterSolution();
	
	@Test
   public void testExecution() 
   {
	
		Thread[] users = new Thread[15];
		String flight = "Flight-1"; // Can also take the flight as command line argument..

		users[0] = new Thread(new Writers(f1, flight));
		users[1] = new Thread(new Readers(f1, flight));
		users[2] = new Thread(new Writers(f1, flight));
		users[3] = new Thread(new Readers(f1, flight));
		users[4] = new Thread(new Readers(f1, flight));
		users[5] = new Thread(new Readers(f1, flight));
		users[6] = new Thread(new Readers(f1, flight));
		users[7] = new Thread(new Readers(f1, flight));
		users[8] = new Thread(new Writers(f1, flight));
		users[9] = new Thread(new Readers(f1, flight));
		users[10] = new Thread(new Writers(f1, flight));
		users[11] = new Thread(new Writers(f1, flight));
		users[12] = new Thread(new Readers(f1, flight));
		users[13] = new Thread(new Readers(f1, flight));
		users[14] = new Thread(new Readers(f1, flight));

		for (int i = 0; i < 15; i++) {
			users[i].start();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < 15; i++) {
			try {
				users[i].join();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
		System.out.println("Completed...");
		assertEquals(new Integer(104), f1.getMapValue(f1, flight)); 
	}

	
	
}


