package com.lightbend.akka.sample;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.lightbend.akka.sample.Printer.Greeting;


//#greeter-messages
public class ActorConsumer extends AbstractActor {
//#greeter-messages
  static public Props props(ActorRef printerActor) {
    return Props.create(ActorConsumer.class, () -> new ActorConsumer(printerActor));
  }

  //#item to eat
  static public class ItemToEat {
    public final Object item;

    public ItemToEat(Object item) {
        this.item = item;
    }
  }

  static public class Greet {
	  public static int expected = 500;
	  public final int num;
    public Greet(int num) {
    	this.num = num;
    }
  }

  private final ActorRef printerActor;
  private Object item;

  public ActorConsumer(ActorRef printerActor) {
    this.printerActor = printerActor;
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(ItemToEat.class, ite -> {
          this.item = ite.item;
          if(Consumer.isDebug) {
        	  Thread.sleep(10);
          } else {
        	  Thread.sleep(1000);
          }
        })
        .match(Greet.class, x -> {
          //#greeter-send-message
          printerActor.tell(new Greeting("ate item#: " + x.num), getSelf());
          if(x.num == Greet.expected) {
        	  Driver.b.await();
          }
          //#greeter-send-message
        })
        .build();
  }
//#greeter-messages
}
//#greeter-messages
