import java.util.Stack;

public class Washer extends Thread {
	public Stack<Sock> destroy;
	private boolean finished = false;
	
	public Washer() {
		destroy = new Stack<Sock>();
		finished = false;
	}
	
	@Override
	public void run() {
		while(!finished || !destroy.isEmpty()) {
			if(!destroy.isEmpty()) {
				Sock s = destroy.pop();
			}
		}
		SockSorter.processFinished();
	}
	
	public void setFinished() {
		finished = true;
	}
	
	public synchronized void submitSocks(Sock one, Sock two) {
		if(one.color == two.color) {
			System.out.println("Destroying " + one.toString() + " sock and " + two.toString() + " sock.");
			destroy.push(one);
			destroy.push(two);
		}
		else {
			System.out.println("Error: two unmatched socks sent to washer.");
		}
	}
}
