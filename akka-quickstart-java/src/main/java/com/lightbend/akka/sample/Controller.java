package com.lightbend.akka.sample;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;

public abstract class Controller extends Thread{
	protected ArrayBlockingQueue<Object> buffer;
	protected int valuesExpected;
	protected int valuesRecieved = 0;
	private Consumer[] consumers;
	
	public Controller(int valuesExpected, Consumer[] consumers) {
		buffer = new ArrayBlockingQueue<>(10);
		this.valuesExpected = valuesExpected;
		valuesRecieved = 0;
		this.consumers = consumers;
	}
	
	public void run() {
		long time = 0;
		long lastTime = System.nanoTime();
		int seconds = 0;
		while(valuesRecieved < valuesExpected) {
			time += System.nanoTime() - lastTime;
			lastTime = System.nanoTime();
			if(time > 2000000000l) {
				time -= 20000000000l;
				System.out.println("Still running. Consumed " + valuesRecieved + " items in " + seconds + " seconds.");
				seconds+=20;
			}
			for(int i = 0; i < consumers.length; ++i) {
				if(consumers[i].isReady && !buffer.isEmpty()) {
					consumers[i].submit(buffer.poll());
					++valuesRecieved;
					//if(valuesRecieved < 5) System.out.println("Submitted item " + valuesRecieved);
				}
				else {
					//System.out.println("isReady: " + consumers[i].isReady + ", isEmpty: " + !buffer.isEmpty());
				}
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < consumers.length; ++i) {
			consumers[i].isDone = true;
		}
		try {
			Driver.b.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void submit(Object o) {
		try {
			buffer.put(o);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
