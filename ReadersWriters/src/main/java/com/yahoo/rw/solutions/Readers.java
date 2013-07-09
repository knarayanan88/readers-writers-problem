package com.yahoo.rw.solutions;

import java.util.Date;

/*
 * Name : Readers.java
 * Author : knar@
 * Description : Readers - Runnable that reads the schedule of a flight which is passed as parameter.
 * 
 */


class Readers implements Runnable {
	private ReadersWriterInterface obj;
	private String flight;

	public Readers(ReadersWriterInterface obj, String flight) {
		this.obj = obj;
		this.flight = flight;
	}

	public void run() {
		Date date = new Date();
		System.out.println("Started Reader Thread : "
				+ Thread.currentThread().getName() + " at " + date.toString());
		try {
			obj.reader(flight);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

}
