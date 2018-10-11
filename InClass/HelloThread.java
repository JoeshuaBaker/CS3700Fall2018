public class HelloThread extends Thread {
	public int threadID = 0;
	private static Thread[] threads;
	
	public HelloThread(int id)
	{
		super();
		threadID = id;
	}
	
	//synchronous run that waits for prev thread to be done
	public void run() {
		if(threadID != 0)
		{
			try {
				threads[threadID - 1].join();
			} catch (InterruptedException e) {}
		}
		System.out.println("Hello from thread: " + threadID);
	}
	/*
	public void run() {
		System.out.println("Hello from thread: " + threadID);
	}
	*/
	public static void main(String[] args) {
		threads = new Thread[100];
		for(int i = 0; i < 100; i++) {
			threads[i] = new HelloThread(i);
			threads[i].start();
		}
	}
	
}