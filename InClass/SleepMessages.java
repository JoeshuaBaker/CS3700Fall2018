public class SleepMessages {
	public static void main(String[] args)
	throws InterruptedException {
		String importantInfo[] = {
			"Mares eat oats", "Does eat oats", "Little Lambs eat Ivy", "A kid will eat ivy too"
		};
		
		for(int i = 0; i < importantInfo.length; i++)
		{
			Thread.sleep(4000);
			System.out.println(importantInfo[i]);
		}
		
	}
}