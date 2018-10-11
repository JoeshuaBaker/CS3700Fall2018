
public class Rank extends Thread{
	
	public static Rank rank;
	
	public static int highestRank = Integer.MIN_VALUE;
	public static Official leader = null;
	Official[] officials;
	
	public Rank(Official[] officials) {
		rank = this;
		this.officials = officials;
		leader = officials[0];
		highestRank = officials[0].rank;
	}
	
	@Override
	public void run() {
		boolean done = false;
		while(!done) {
			try {
				sleep(5000);
				done = true;
			} catch (InterruptedException e) {
				System.out.println("I'm sleeping...!");
			}	
		}
		
		System.out.println("Final leader is: " + leader.name + " with a rank of: " + leader.rank);
	}
	
	public synchronized void reportRank(Official o) {
		if(o.rank > highestRank) {
			System.out.println("Make way! " + o.name + " is the new leader with a rank of " + o.rank);
			leader = o;
			highestRank = o.rank;
			this.interrupt();
			leader.interrupt();
			notifyOfficials();
		}
	}
	
	public void notifyOfficials() {
		for(int i = 0; i < officials.length; ++i) {
			if(officials[i] != leader) officials[i].interrupt();;
		}
	}
}
