package com.yahoo.rw.solutions;

/*
 * Name : ReaderWriterInterface.java
 * Author : knar@
 * Methods : reader and writer
 * 
 */

public interface ReadersWriterInterface {

	public void reader(String s) throws InterruptedException;

	public void writer(String s, Integer val) throws InterruptedException;

}
