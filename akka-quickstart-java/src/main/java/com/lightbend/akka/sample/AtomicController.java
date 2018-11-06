package com.lightbend.akka.sample;
public class AtomicController extends Controller {
	
	public AtomicController(int valuesExpected, Consumer[] consumers) {
		super(valuesExpected, consumers);
	}
	
	@Override
	public void submit(Object o) {
		try {
			buffer.put(o);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
