import java.util.concurrent.locks.ReentrantLock;

public class Philosopher extends Thread{
	String name;
	public ReentrantLock left;
	public ReentrantLock right;
	public int eaten;
	public final int MAXTRIALS = 20;
	public boolean strange = false;
	
	public Philosopher(String name) {
		this.name = name;
		eaten = 0;
	}
	
	@Override
	public void run() {
		System.out.println("You shouldn't see this...!");
	}
	
	public void pickupLeft() {
		System.out.println("Philosopher " + name + " picked up the left fork.");
	}
	
	public void dropLeft() {
		System.out.println("Philosopher " + name + " drops the left fork.");
	}
	
	public void pickupRight() {
		System.out.println("Philosopher " + name + " picked up the right fork.");
	}
	
	public void dropRight() {
		System.out.println("Philosopher " + name + " drops the right fork.");
	}
	
	public void think(int ms) {
		System.out.println("Philosopher " + name + " thinks for " + ms + "ms.");
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void eat(int ms) {
		System.out.println("Philosopher " + name + " eats for " + ms + "ms.");
		eaten++;
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}