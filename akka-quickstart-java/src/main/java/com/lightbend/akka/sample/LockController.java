package com.lightbend.akka.sample;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.ReentrantLock;

public class LockController extends Controller {
	ReentrantLock l;
	
	public LockController(int valuesExpected, Consumer[] consumers) {
		super(valuesExpected, consumers);
		l = new ReentrantLock();
	}
	
	@Override
	public void submit(Object o) {
		l.lock();
		try {
			buffer.put(o);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		l.unlock();
	}
}
