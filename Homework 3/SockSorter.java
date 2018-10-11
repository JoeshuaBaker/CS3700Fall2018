
public class SockSorter {
	
	public SockSorter() {
		Washer washer = new Washer();
		SockMatcher matcher = new SockMatcher(washer);
		
		SockCreator red = new SockCreator(Sock.Color.Red, matcher);
		SockCreator blue = new SockCreator(Sock.Color.Blue, matcher);
		SockCreator green = new SockCreator(Sock.Color.Green, matcher);
		SockCreator orange = new SockCreator(Sock.Color.Orange, matcher);

		washer.start();
		matcher.start();
		red.start();
		blue.start();
		green.start();
		orange.start();
	}
	
	public static void processFinished() {
		System.out.println("Sorting process complete.");
	}
}
