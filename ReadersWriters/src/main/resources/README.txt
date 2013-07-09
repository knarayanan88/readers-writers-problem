Problem  :
-----------

Readers Writers Problem : http://en.wikipedia.org/wiki/Readers-writers_problem

Scenario : 
---------- 

Readers try to read the schedule of a flight named "Flight-1" while Writers try to modify this flight's schedule. 
A Hashmap with flight names as keys and schedules as values are initialized. 
For simplicity purpose, the Reader and Writer Threads in the code are made to read and update the schedule of 
only 1 flight which is Flight-1. Semaphore & ReentrantReadWriteLock class of Java is used to implement the solutions. 

In reality, multiple readers and writers try to read and write schedules of all the flights and low level synchronization primitives 
like Semaphores etc.. are not used. Instead the Application itself stores data in an RDBMS like Oracle 
which helps in concurrent transactions automatically. The application needn't bother to write the locking mechanism. 
Oracle never blocks reads. By a method of SCN and rollback/undo, it provides consistent Reads at any point in time. 
Multiple Writes are prevented using Row-Level Locks.

Packages :
-----------

ReadersWriters Project contains 2 packages : 

src/main/java : 

com.yahoo.rw.solutions - Solutions to all 3 readers writers problem.
com.yahoo.rw.train.application - A small train reservation program. 

src/test/java :

com.yahoo.rw.solutions - Driver to run all 3 readers writers solutions..
com.yahoo.rw.train.application - Driver to run the Train application.


First Readers Writers Solution
-------------------------------

Running the Code :
------------------

ReadersWriters Project 
	-> stc/test/java 
		-> com.yahoo.rw.solutions
			->FirstReadersWritersTest.java


Input :
--------

No specific command line inputs.

15 Threads (Reader/Writer) are triggered one after the other with an interval of 
100ms between each thread. 

Order of Reader/Writer Threads arrival : 

W R W R R R R R W R W W R R R


Output :
--------

Print Statements have been put at various stages to follow the path of execution.

Threads get executed in the following order : 

W R R R R R R R R R R W W W W 


Drawback : 
-----------

1. Reader Threads are given preference over Writer Threads. Hence, if lot of 
Readers are coming in compared to few Writers, Writer Threads will get starved.
2. Starvation of Writer Threads can result in Reader Threads reading 
old(stale) data.

****************************************************************************

Second Readers Writers Solution
---------------------------------

Running the Code :
------------------

ReadersWriters Project 
	-> stc/test/java 
		-> com.yahoo.rw.solutions
			->SecondReadersWritersTest.java

Input :
--------

No specific command line inputs.

15 Threads (Reader/Writer) are triggered one after the other with an interval of 
100ms between each thread..

Order of Reader/Writer Threads arrival : 

W R W R R R R R W R W W R R R


Output :
--------

W W W W W R R R R R R R R R R


Drawback : 
-----------

1. Writer Threads are given preference over Reader Threads. Hence, lot of 
Readers get starved when there are lot of writes..
2. Starvation of Reader Threads can result in inconsistent reads.

****************************************************************************

Third Readers Writers Solution
-------------------------------

Running the Code :
------------------

ReadersWriters Project 
	-> stc/test/java 
		-> com.yahoo.rw.solutions
			->ThirdReadersWritersTest.java

Input :
--------

No specific command line inputs.

15 Threads (Reader/Writer) are triggered one after the other with an interval of 
100ms between each thread..

Order of Reader/Writer Threads arrival : 

W R W R R R R R W R W W R R R


Output :
--------

W R W R R R R R W R W W R R R  (In the same order as they arrived)


Note: 
------

No threads are allowed to starve. This is done using a common semaphore -
orderMutex for both Readers and Writers to preserve the order in which they are
arriving to access the critical section.

****************************************************************************

ReentrantReadWriteLock
-----------------------

The java.util.concurrent.ReentrantReadWriteLock in Fair mode has an implementation
of the Third solution. 

Running the Code :
------------------

ReadersWriters Project 
	-> stc/test/java 
		-> com.yahoo.rw.solutions
			->ReentrantRWLock.java

Output :
--------

W R W R R R R R W R W W R R R  (In the same order as they arrived)

****************************************************************************

Example Application
--------------------

Train Reservation : 

Running the Code :
------------------

ReadersWriters Project 
	-> stc/test/java 
		-> com.yahoo.rw.train.application
			->TrainBookingTest.java
			
Read and Write Locks of ReentrantReadWriteLock class of java is used to control multiple readers (read 
available seats) and writers (reserve/cancel) access to the shared variable remainingSeats.
