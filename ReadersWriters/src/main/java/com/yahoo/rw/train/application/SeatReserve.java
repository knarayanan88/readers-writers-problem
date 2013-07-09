package com.yahoo.rw.train.application;

/*
 * Name : SeatReserve.java
 * Author : knar@
 * Description : Writer Thread that takes num of seats to Reserve and reduces the available/remaining seat shared variable.
 * 
 */

class SeatReserve implements Runnable {
	private Train t = new Train();
	private int numOfSeatsToBook;

	public SeatReserve(Train tr, int numOfSeatsToBook) {
		this.t = tr;
		this.numOfSeatsToBook = numOfSeatsToBook;
	}

	public void run() {

		System.out.println("Started Writer-Reserve Thread : "
				+ Thread.currentThread().getName());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		if (t.bookTickets(numOfSeatsToBook)) {
			System.out.println("Thread " + Thread.currentThread().getName()
					+ ": Booked " + numOfSeatsToBook
					+ " seats..Remaining Seats: " + t.getRemainingSeats());
		} else {
			System.out.println("Thread " + Thread.currentThread().getName()
					+ " No Seats Available to Book..");
		}
	}
}
