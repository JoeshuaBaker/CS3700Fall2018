
public class SockCreator extends Thread{
	
	private Sock.Color color;
	private SockMatcher matcher;
	
	public SockCreator(Sock.Color color, SockMatcher matcher) {
		this.color = color;
		this.matcher = matcher;
	}
	
	@Override
	public void run() {
		int rand = (int)(Math.random()*100) + 1;
		int sockCount = 0;
		while(sockCount < rand) {
			sockCount++;
			System.out.println(this.toString() + " thread produced sock " + sockCount + " of " + rand);
			matcher.addSock(new Sock(this.color), (sockCount == rand));
		}
		
		System.out.println(this.toString() + " thread finished producing " + rand + " socks.");
	}
	
	public String toString() {
		String comp = "";
		switch(color) {
		case Red:
			comp += "Red";
			break;
			
		case Blue:
			comp += "Blue";
			break;
			
		case Green:
			comp += "Green";
			break;
			
		case Orange:
			comp += "Orange";
			break;
			
		default:
			comp += "None";
			break;
		}
		
		comp += " SockCreator";
		return comp;
	}
}
