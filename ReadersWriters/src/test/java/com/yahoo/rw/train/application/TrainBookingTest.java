package com.yahoo.rw.train.application;

import org.junit.Test;

import com.yahoo.rw.train.application.SeatCancel;
import com.yahoo.rw.train.application.SeatReserve;
import com.yahoo.rw.train.application.SeatStatus;
import com.yahoo.rw.train.application.Train;

public class TrainBookingTest {

	Train t = new Train();

	@Test
	public void testExecution() {

		Thread[] users = new Thread[17];

		users[0] = new Thread(new SeatStatus(t));
		users[1] = new Thread(new SeatReserve(t, 2));
		users[2] = new Thread(new SeatReserve(t, 3));
		users[3] = new Thread(new SeatStatus(t));
		users[4] = new Thread(new SeatStatus(t));
		users[5] = new Thread(new SeatCancel(t, 1));
		users[6] = new Thread(new SeatStatus(t));
		users[7] = new Thread(new SeatStatus(t));
		users[8] = new Thread(new SeatReserve(t, 1));
		users[9] = new Thread(new SeatReserve(t, 2));
		users[10] = new Thread(new SeatStatus(t));
		users[11] = new Thread(new SeatStatus(t));
		users[12] = new Thread(new SeatCancel(t, 1));
		users[13] = new Thread(new SeatReserve(t, 4));
		users[14] = new Thread(new SeatStatus(t));
		users[15] = new Thread(new SeatReserve(t, 1));
		users[16] = new Thread(new SeatStatus(t));

		for (int i = 0; i < 17; i++) {
			users[i].start();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
				
		}
		for (int i = 0; i < 17; i++) {
			try {
				users[i].join();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
    	}
	}
}
