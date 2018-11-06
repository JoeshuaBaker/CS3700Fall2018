package com.lightbend.akka.sample;
public class IsolatedController extends Controller {
	
	public IsolatedController(int valuesExpected, Consumer[] consumers) {
		super(valuesExpected, consumers);
	}
	
	@Override
	public synchronized void submit(Object o) {
		try {
			buffer.put(o);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
