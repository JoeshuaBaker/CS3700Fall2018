public class HelloRunnable implements Runnable {
	private static int threadID = 0;
	
	
	public void run(){
		int id = threadID++;
		while(true) {
			Thread.sleep(1000);
			System.out.println("Hello from thread: " + threadID);
		}
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < 100; i++)
			(new Thread(new HelloRunnable())).start();
	}
}

/*
//Multiple processes use IPC (interprocess communication)
//Thread --> A lightweight process that allows concurrent software
//Thread Object; Each thread is associated with a specific instance of the class.
//two ways to create an instance:
//1. have direct control over creation and management
//	instantiate a new thread each time we need to do an asynchronous task
2 ways to create thread:
	1A: Provide runnable object


	Atomic Actions in Java
		Read/Write are atomic for reference vars and most primitive (except long&double)
		All varaibles declared volatile are atomic
		Good for thread interference as they cannot be interleaved
		
	Liveness of the thread (ability of thread to progress through instructions)
	3 problems:
		Deadlock (two+ threads race for resources, each grabs one of the necessary
			resources, then sleep forever waiting for the other thread to free it)
		Starvation (One thread hogs all the synchronized resources, never allowing
			the other threads to call those functions)
		Livelock (instead of sleeping, a thread continuously checks for a resource
			which blocks any other thread from running and actually progressing)
			
	High Level Concurrency Objects
	Lock Object -- Supports locking properties that simplify many concurrent applications
		Only 1 thread can own an instance of a Lock object at a given time
		Supports wait and notify through the associated Condition class
		Ability to back out of an attempt to acquire a lock (tryLock method)
			Backs out if lock is not available immediately or after an amount of time.
			
	Executors -- control thread pools. 3 implementations.
		1. Executor Interface -- Simple interface for launching new tasks
		2. ExecutorService -- Sub-Interface of Executor. Adds additional features to help manage lifecycle
		3. ScheduledExecutorService -- Sub-Interface of ExecutorService. Support futures and periodic execution
		
		Basically, create threads ahead of time and tell them to execute something in the future.
		
	Thread Pools: Consist of Worker Threads designed to execute multiple varying tasks,
		not just runnable or callable.
		Goal: Minimize thread creation/declaration by having a fixed pool that pulls tasks
		from a queue
		3 types of thread pools:
		1. newCachedThreadPool
			create executor with expandable thread pool. Good for short lived tasks.
		2. newSingleThreadExecutor
			create executor that executes a single thread at a time.
		3. ScheduledExecutorService
			exactly like #2, but you can schedule when it's supposed to run.
			
	Fork/Join -- Framework implementation of ExecutorService interface.
		Breaks up tasks recurisvely. Goal is to use all processors for max performance.
		Distributes tasks to worker threads from threadpool using work-stealing algorithm
		Free worker threads steal work from busy worker threads
		compute is the starting method
		
		class ASum extends RecursiveTask
			Array
			low
			high
			
			compute() {
				if(low > high) return 0;
				else if(low == high) return array[low]
				else {
					midpoint = (low + high)/2;
					left = new ASum(low, midpoint, array);
					right = new ASum(midpoint+1, high, array);
					right.fork(); //launch future, will not block (starts calculating)
					return left.compute() (starts calculating left) + right.join() (blocks until right resolves);
				}
			}
		
	ConcurrentCollections -- Make it easier to manage large data, reduce need for synchronization
		BlockingQueue--FIFO data structure that blocks or times out if...
			1. user attempts to add to full queue
			2. if user retrieves from an empty queue
		ConcurrentMap--Defines key/value pair as an atomic operation
			1. Allows remove/replace only if key value pair is present
			2. Adds key/value pair only if key is absent
			
	Atomic Variables--found in java.util.concurrent.atomic package
		Has classes that support atomic operations on single variables
		
	Programming Asynchronously using Future objects:
		FA = Future {B} returns future object.
			Future object wraps around variable A and lies to compiler that the value
			exists. The value isn't actually generated until a getter is called.
			
			Future G{FA.Get()}
			
			When you actually need the data (data dependency) it will block on the method
			Get() until it's available.
		
		Model Futures using a computation graph with edges as join conditions.
		Futures avoid race conditions by doing this.
		
	Memoization:
	ex: y1 = g(x1), y2 = g(x2), y3 = [g(x1)] -- g(x1) has already been calculated
	calculating again is a waste of compute time
	replace that second call with a lookup table to the result of the first call.
	use a future to store the first result, and then a future.get() for the second
	
	Streams:
	
		Student.Stream().(S->Print(S))
		
	Barriers: Arrive and Await
		How to stop a thread from executing until a certain condition becomes true.
		next
	Phasers: Splits Barrier into Arrive & awaitAdvance
	
	Data Flow:
			A		B
		C		D		E
		
*/