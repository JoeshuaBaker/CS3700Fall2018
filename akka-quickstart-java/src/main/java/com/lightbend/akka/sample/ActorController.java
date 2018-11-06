package com.lightbend.akka.sample;
import java.util.concurrent.BrokenBarrierException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.lightbend.akka.sample.ActorConsumer.Greet;
import com.lightbend.akka.sample.ActorConsumer.ItemToEat;

public class ActorController extends Controller {
	private ActorRef[] actors;
	private final int numActors;
	
	public ActorController(int valuesExpected, Consumer[] consumers) {
		super(valuesExpected, consumers);
		final ActorSystem system = ActorSystem.create("Controller");
	    this.numActors = consumers.length;
		
		final ActorRef printerActor = 
		        system.actorOf(Printer.props(), "printerActor");
		
		
		final ActorRef consumer1 = system.actorOf(ActorConsumer.props(printerActor));
		final ActorRef consumer2 = system.actorOf(ActorConsumer.props(printerActor));
		final ActorRef consumer3 = system.actorOf(ActorConsumer.props(printerActor));
		final ActorRef consumer4 = system.actorOf(ActorConsumer.props(printerActor));
		final ActorRef consumer5 = system.actorOf(ActorConsumer.props(printerActor));
		
		if(numActors == 2) {
			actors = new ActorRef[5];
			actors[0] = consumer1;
			actors[1] = consumer2;
		}
		else {
			actors = new ActorRef[5];
			actors[0] = consumer1;
			actors[1] = consumer2;
			actors[2] = consumer3;
			actors[3] = consumer4;
			actors[4] = consumer5;
		}
		
	}
	
	@Override
	public void run() {
		while(valuesRecieved <= valuesExpected) {
			if(!buffer.isEmpty()) {
				actors[valuesRecieved%numActors].tell(new ItemToEat(buffer.poll()), ActorRef.noSender());
				if(valuesRecieved%100 == 0) actors[valuesRecieved%5].tell(new Greet(valuesRecieved), ActorRef.noSender());
				valuesRecieved++;
			}
		}
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
