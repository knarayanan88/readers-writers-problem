package com.yahoo.rw.solutions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * 
 * Name : ReentrantRWLock.java
 * Author : knar@
 * Description : Overrides the reader and writer abstract methods of ReadersWriterInterface
 * Refer to src/main/resources for more information on the input and output
 * 
 */

public class ReentrantRWLock implements ReadersWriterInterface {

	private Map<String, Integer> schedules;
	private final ReadWriteLock rwLock;
	private final Lock readLock;
	private final Lock writeLock;

	public ReentrantRWLock() {
		schedules = new HashMap<String, Integer>();
		rwLock = new ReentrantReadWriteLock(true);
		readLock = rwLock.readLock();
		writeLock = rwLock.writeLock();

		fillMapWithInitialSchedules();
	}

	public void reader(String flight) throws InterruptedException {

		readLock.lock();

		// Read the schedules...
		try {
			Thread.sleep(1000);

			System.out.println("Schedule for " + flight + " is :"
					+ schedules.get(flight) + " read by "
					+ Thread.currentThread().getName());

			// Reading done
		} finally {
			readLock.unlock();
		}
	}

	public void writer(String flight, Integer time) throws InterruptedException {

		writeLock.lock();

		// Writing/Updating new/existing schedules

		try {
			Thread.sleep(1000);

			setMapValue(flight, time);

			System.out.println("Set for " + flight + " time: " + time
					+ " by Writer Thread: " + Thread.currentThread().getName());

			// Writing done
		} finally {
			writeLock.unlock();
		}
	}

	public static void main(String[] args) {

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
