package com.yahoo.rw.solutions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

/*
 * Name : FirstReadersWriterSolution.java
 * Author : knar@
 * Description : Overrides the reader and writer abstract methods of ReadersWriterInterface
 * Refer to src/main/resources for more information on the input and output
 * 
 */

public class FirstReadersWriterSolution implements ReadersWriterInterface {

	private Semaphore write;
	private Semaphore mutex;
	private int readCount;

	private Map<String, Integer> schedules;

	public FirstReadersWriterSolution() {
		readCount = 0;
		write = new Semaphore(1, true);
		mutex = new Semaphore(1, true);
		schedules = new HashMap<String, Integer>();

		fillMapWithInitialSchedules();

	}

	public void writer(String flight, Integer time) throws InterruptedException {
		write.acquire();

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
								+ " waiting Threads on the write semaphore during writing ");
			}
		} finally {
			write.release();
		}

	}

	public void reader(String flight) throws InterruptedException {
		mutex.acquire();

		try {
			readCount++;
			if (readCount == 1) {
				write.acquire();
			}
		} finally {
			mutex.release();
		}
		// Read the schedules...

		Thread.sleep(1000);

		System.out.println("Schedule for " + flight + " is :"
				+ schedules.get(flight) + " read by "
				+ Thread.currentThread().getName());

		// Reading done

		mutex.acquire();
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
			mutex.release();
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
