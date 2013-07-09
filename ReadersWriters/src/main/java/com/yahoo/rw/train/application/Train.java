package com.yahoo.rw.train.application;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * Name : Train.java.
 * Author : knar@
 * Description : Has 3 methods : getRemainingSeats, bookTickets and cancelTickets.
 * 
 */

public class Train {

	private static final int TOTALSEATS = 10;
	private int remainingSeats;
	private final ReadWriteLock rwLock;
	private final Lock readLock;
	private final Lock writeLock;

	public Train() {
		this.remainingSeats = TOTALSEATS;
		rwLock = new ReentrantReadWriteLock(true);
		readLock = rwLock.readLock();
		writeLock = rwLock.writeLock();
	}

	public int getRemainingSeats() {
		readLock.lock();
		try {
			return remainingSeats;
		} finally {
			readLock.unlock();
		}
	}

	public boolean bookTickets(int numOfSeatsToBook) {
		writeLock.lock();
		try {
			if (remainingSeats >= numOfSeatsToBook) {
				this.remainingSeats = remainingSeats - numOfSeatsToBook;
				return true;
			} else
				return false;
		} finally {
			writeLock.unlock();
		}
	}

	public void cancelTickets(int numSeatsToCancel) {
		writeLock.lock();
		this.remainingSeats = remainingSeats + numSeatsToCancel;
		writeLock.unlock();

	}

}
