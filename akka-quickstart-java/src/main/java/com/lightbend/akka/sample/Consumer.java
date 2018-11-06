package com.lightbend.akka.sample;

public class Consumer extends Thread{
	public static boolean isDebug = false;
	public boolean isReady;
	public boolean isDone;
	public boolean isStopped;
	public Object item;
	
	public Consumer() {
		reset();
		isStopped = false;
	}
	
	public void reset() {
		isReady = true;
		isDone = false;
	}
	
	public void run() {
		while(!isStopped) {
			while(!isDone) {
				if(!isReady) {
					consume();
				}
			}
			//System.out.println("Resetting!");
			reset();
		}
	}
	
	public void submit(Object o) {
		isReady = false;
		item = o;
	}
	
	public void consume() {
		//System.out.println("Consuming an item");
		try {
			if(isDebug)
				Thread.sleep(10);
			else
				Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		item = null;
		isReady = true;
	}
}
