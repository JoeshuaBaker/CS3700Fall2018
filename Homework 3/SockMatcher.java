import java.util.Stack;

public class SockMatcher extends Thread{
	
	private Stack<Sock> redSocks;
	private Stack<Sock> blueSocks;
	private Stack<Sock> greenSocks;
	private Stack<Sock> orangeSocks;
	private boolean[] socksDone;
	private Washer washer;
	
	public SockMatcher(Washer washer) {
		redSocks = new Stack<>();
		blueSocks = new Stack<>();
		greenSocks = new Stack<>();
		orangeSocks = new Stack<>();
		socksDone = new boolean[4];
		socksDone[0] = false;
		socksDone[1] = false;
		socksDone[2] = false;
		socksDone[3] = false;
		this.washer = washer;
	}
	
	@Override
	public void run() {
		boolean stillRunning = true;
		while(stillRunning) {
			if(allDone()) {
				stillRunning = false;
			}
			
			while(redSocks.size() >= 2) {
				System.out.println("SockMatcher submitted two red socks to the washer.");
				washer.submitSocks(redSocks.pop(), redSocks.pop());
			}
			while(blueSocks.size() >= 2) {
				System.out.println("SockMatcher submitted two blue socks to the washer.");
				washer.submitSocks(blueSocks.pop(), blueSocks.pop());
			}
			while(greenSocks.size() >= 2) {
				System.out.println("SockMatcher submitted two green socks to the washer.");
				washer.submitSocks(greenSocks.pop(), greenSocks.pop());
			}
			while(orangeSocks.size() >= 2) {
				System.out.println("SockMatcher submitted two orange socks to the washer.");
				washer.submitSocks(orangeSocks.pop(), orangeSocks.pop());
			}
		}
		
		System.out.println("SockMatcher has finished sorting.");
		System.out.println("Remaining Socks: ");
		System.out.println("Red: " + redSocks.size());
		System.out.println("Blue: " + blueSocks.size());
		System.out.println("Green: " + blueSocks.size());
		System.out.println("Orange: " + blueSocks.size());
		washer.setFinished();
		
	}
	
	private boolean socksRemaining() {
		return redSocks.size() == 0 && blueSocks.size() == 0 
				&& greenSocks.size() == 0 && orangeSocks.size() == 0;
	}
	
	private boolean allDone() {
		return (socksDone[0] && socksDone[1] && socksDone[2] && socksDone[3]);
	}
	
	public synchronized void reportDone(Sock.Color c) {
		socksDone[c.getValue()] = true;
	}
	
	public synchronized void addSock(Sock s, boolean last) {
		switch(s.color) {
		case Red:
			redSocks.add(s);
			break;
		case Blue:
			blueSocks.add(s);
			break;
		case Green:
			greenSocks.add(s);
			break;
		case Orange:
			orangeSocks.add(s);
			break;
		default:
			System.out.println("Could not sort sock " + s);
			break;
				
		}
		
		if(last) {
			reportDone(s.color);
		}
	}
}
