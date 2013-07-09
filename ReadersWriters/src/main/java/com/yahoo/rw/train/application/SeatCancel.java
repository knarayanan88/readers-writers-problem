package com.yahoo.rw.train.application;

class SeatCancel implements Runnable {
	
	private Train tr = new Train();
	private int numOfSeatsToCancel;

	public SeatCancel(Train tr, int numOfSeatsToCancel) {
		this.tr = tr;
		this.numOfSeatsToCancel = numOfSeatsToCancel;
	}

	public void run() {
		System.out.println("Started Writer-Cancel Thread : "
				+ Thread.currentThread().getName());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		tr.cancelTickets(numOfSeatsToCancel);

		System.out.println("Thread :" + Thread.currentThread().getName()
				+ " Cancelled 1 Ticket. Remaining Seats: "
				+ tr.getRemainingSeats());
	}

}
