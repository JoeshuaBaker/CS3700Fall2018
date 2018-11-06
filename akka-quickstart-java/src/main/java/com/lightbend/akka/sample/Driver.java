package com.lightbend.akka.sample;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicIntegerArray;

import com.lightbend.akka.sample.ActorConsumer.Greet;
import com.lightbend.akka.sample.ActorSieve.checkN;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Driver {
	private static long time;
	private static String l;
	public static CyclicBarrier b;
	private static ActorSystem system;
	
	public static void main(String[] args) {
		Sieve s = new Sieve();
		timerStart("Single threaded sieve");
		s.sieve(1000000);
		timerStop();
		
		/*
		system = ActorSystem.create("Controller");
		final ActorRef sieve = system.actorOf(ActorSieve.props(system));
		AtomicIntegerArray a = new AtomicIntegerArray(1000000);
		timerStart("ActorSieve Algorithm");
		System.out.println(2);
		sieve.tell(new checkN(2, a, null), ActorRef.noSender());
		
		/*
		Consumer.isDebug = true;
		Consumer[] two = new Consumer[2];
		Consumer[] five = new Consumer[5];
		
		b = new CyclicBarrier(2);
		
		for(int i = 0; i < two.length; ++i) {
			two[i] = new Consumer();
			two[i].start();
		}
		
		runTest(new LockController(500, two), 5, "Locks, 5 Prod, 2 Con.");
		
		for(int i = 0; i < five.length; ++i) {
			five[i] = new Consumer();
			five[i].start();
		}
		
		runTest(new LockController(200, five), 2, "Locks, 2 Prod, 5 Con.");
		runTest(new IsolatedController(500, two), 5, "Isolated Section, 5 Prod, 2 Cons");
		runTest(new IsolatedController(200, five), 2, "Isolated Section, 2 Prod, 5 Cons");
		runTest(new AtomicController(500, two), 5, "Atomic, 5 Prod, 2 Cons");
		runTest(new AtomicController(200, five), 2, "Atomic, 2 Prod, 5 Cons");
		ActorConsumer.Greet.expected = 500;
		runTest(new ActorController(500, two), 5, "Actor, 5 Prod, 2 Con");
		ActorConsumer.Greet.expected = 200;
		runTest(new ActorController(200, five), 2, "Actor, 2 Prod, 5 con");
		
		for(int i = 0; i < two.length; ++i) {
			two[i].isDone = true;
			two[i].isStopped = true;
		}
		for(int i = 0; i < five.length; ++i) {
			five[i].isDone = true;
			five[i].isStopped = true;
		}
		*/
	}
	
	private static void runTest(Controller c, int numProd, String label) {
		timerStart(label);
		c.start();
		createProd(numProd, c);
		try {
			b.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timerStop();
	}
	
	private static Producer[] createProd(int n, Controller c) {
		Producer[] p = new Producer[n];
		for(int i = 0; i < p.length; ++i) {
			p[i] = new Producer(c);
			p[i].start();
		}
		return p;
	}
	
	private static void timerStart(String label) {
		System.out.println("Now timing: " + label);
		l = label;
		time = System.currentTimeMillis();
	}
	
	public static void timerStop() {
		long runTime = System.currentTimeMillis() - time;
		if(system != null) {
			system.terminate();
		}
		System.out.println("Finished running: " + l + " in " + runTime + "ms.");
	}
}
