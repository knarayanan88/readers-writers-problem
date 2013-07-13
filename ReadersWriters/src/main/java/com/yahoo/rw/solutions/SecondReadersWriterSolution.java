package com.yahoo.rw.solutions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

/*
 * 
 * Name : SecondReadersWriterSolution.java
 * Author : knar@
 * Description : Overrides the reader and writer abstract methods of ReadersWriterInterface
 * Refer to src/main/resources for more information on the input and output
 * 
 */

public class SecondReadersWriterSolution implements ReadersWriterInterface {

	private Semaphore write;
	private Semaphore read;
	private Semaphore mutex1, mutex2, mutex3;
	private int readCount = 0;
	private int writeCount = 0;

	private Map<String, Integer> schedules;

	public SecondReadersWriterSolution() {
		readCount = 0;
		writeCount = 0;
		write = new Semaphore(1, true);
		read = new Semaphore(1, true);
		mutex1 = new Semaphore(1, true);
		mutex2 = new Semaphore(1, true);
		mutex3 = new Semaphore(1, true);
		schedules = new HashMap<String, Integer>();

		fillMapWithInitialSchedules();

	}

	public void writer(String flight, Integer time) throws InterruptedException {
		mutex1.acquire();

		try {
			writeCount++;
			if (writeCount == 1) {
				read.acquire();
			}
		} finally {
			mutex1.release();
		}

		write.acquire(); // Only 1 Writer can enter the critical section at a
							// time...

		// Writing/Updating new/existing schedules
		try {
			Thread.sleep(1000);

			setMapValue(flight, time);

			System.out.println("Set for " + flight + " time: " + time
					+ " by Writer Thread: " + Thread.currentThread().getName());

			// Writing done

			if (write.hasQueuedThreads()) {
				System.out
						.println("There are "
								+ write.getQueueLength()
								+ " waiting Threads on the write semaphore during writing");
			}
		} finally {
			write.release();
		}

		mutex1.acquire();
		try {
			writeCount--;
			if (writeCount == 0) {
				read.release();
			}
		} finally {
			mutex1.release();
		}
	}

	public void reader(String flight) throws InterruptedException {
		
		mutex3.acquire();
		read.acquire();
		mutex2.acquire();

		readCount++;
		if (readCount == 1) {
			write.acquire();
		}
		mutex2.release();
		read.release();
		mutex3.release();

		// Read the schedules...

		Thread.sleep(1000);

		System.out.println("Schedule for " + flight + " is :"
				+ schedules.get(flight) + " read by "
				+ Thread.currentThread().getName());

		// Reading done

		mutex2.acquire();
		try {

			readCount--;
			if (readCount == 0) {
				if (write.hasQueuedThreads()) {
					System.out
							.println("There are "
									+ write.getQueueLength()
									+ " waiting Threads on the write semaphore during reading ");
				}

				write.release();
			}
		} finally {
			mutex2.release();
		}
	}

	public Integer getMapValue(ReadersWriterInterface r, String s) {
		return schedules.get(s);
	}

	public void setMapValue(String flght, Integer time) {
		this.schedules.put(flght, time);
	}

	public void fillMapWithInitialSchedules() {
		this.schedules.put("Flight-1", 1);
		this.schedules.put("Flight-2", 4);
		this.schedules.put("Flight-3", 6);
		this.schedules.put("Flight-4", 9);
		this.schedules.put("Flight-5", 10);
		this.schedules.put("Flight-6", 13);
		this.schedules.put("Flight-7", 15);
		this.schedules.put("Flight-8", 17);
		this.schedules.put("Flight-9", 20);
		this.schedules.put("Flight-10", 23);

	}

}
