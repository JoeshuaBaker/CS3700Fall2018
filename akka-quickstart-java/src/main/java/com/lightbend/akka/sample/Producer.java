package com.lightbend.akka.sample;

public class Producer extends Thread {
	private Controller c;
	private int itemsCreated;
	
	public Producer(Controller c) {
		this.c = c;
		itemsCreated = 0;
	}
	
	public void run() {
		itemsCreated = 0;
		while(itemsCreated++ <= 100) {
			c.submit(new Object());
		}
	}
}
