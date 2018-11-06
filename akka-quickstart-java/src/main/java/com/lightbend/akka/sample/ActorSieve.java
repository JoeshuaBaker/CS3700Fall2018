package com.lightbend.akka.sample;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.ActorSystem;

import java.util.concurrent.atomic.AtomicIntegerArray;

import com.lightbend.akka.sample.Printer.Greeting;


//#greeter-messages
public class ActorSieve extends AbstractActor {
//#greeter-messages

	public int reached;
	private ActorSystem next;
	
  static public Props props(ActorSystem next) {
    return Props.create(ActorSieve.class, () -> new ActorSieve(next));
  }
  
  static public class checkN {
	  public int n;
	  public AtomicIntegerArray a;
	  public ActorSieve parent;
	  
	  public checkN(int n, AtomicIntegerArray a, ActorSieve parent) {
		    this.n = n;
		    this.a = a;
		    this.parent = parent;
	  }
  }
  
  public ActorSieve(ActorSystem next) {
	  reached = 0;
	  this.next = next;
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(checkN.class, x -> {
        	reached = x.n;
        	boolean sentNext = false;
        	
        	int nextN = reached + 1;
        	while(!sentNext) {
        		if(nextN >= 1000000) {
        			break;
        		}
        		//System.out.println("creating next child");
        		if(x.parent != null) {
        			if(nextN < x.parent.reached) {
        				if(x.a.get(nextN) == 0) {
        					sentNext = true;
        					System.out.println(nextN);
        					ActorRef nextActor = next.actorOf(ActorSieve.props(next));
            				nextActor.tell(new checkN(nextN, x.a, this), getSelf());
        				}
        			}
        			else {
        				Thread.sleep(0);
        				nextN--;
        			}
        			
        		}
        		else {
        			if(x.a.get(nextN) == 0) {
        				sentNext = true;
        				System.out.println(nextN);
        				ActorRef nextActor = next.actorOf(ActorSieve.props(next));
        				nextActor.tell(new checkN(nextN, x.a, this), getSelf());
        			}
        		}
        		nextN++;
        	}
        	
        	for(int i = reached; i < 1000000; i += x.n) {
        		//System.out.println("clearing nums" + i);
        		if(x.parent != null) {
        			if(i < x.parent.reached) {
        				x.a.set(i, -1);
        			}
        			else {
        				if(i >= 1000000) {
        					break;
        				}
        				Thread.sleep(0);
        				i -= x.n;
        			}
        		}
        		else {
        			x.a.set(i, -1);
        		}
        		reached = i;
        	}
        	
        	if(x.n == 999983) {
        		Driver.timerStop();
        	}
        	reached = 1000000;
        })
        .build();
  }
//#greeter-messages
}
//#greeter-messages
