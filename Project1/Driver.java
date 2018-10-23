
public class Driver {

	public static long[][] trialResults;
	public static long[][] specificResults;
	public static int trialNum;
	
	public static void main(String[] args) {
		int trials = 200;
		trialResults = new long[4][trials];
		specificResults = new long[8][trials];
		trialNum = 0;
		
		CodeTimer t = new CodeTimer();
		for(int i = 0; i < trials; ++i) {
			System.out.println("Running trial " + i + "/" + trials);
			t.start("Single Threaded Encoding");
			SingleThreadHuffmanEncoder st = new SingleThreadHuffmanEncoder("Constitution");
			trialResults[0][i] = t.stop();
			
			t.start(Runtime.getRuntime().availableProcessors() + " Thread Parallel Encoding Implementation #1");
			ParallelHuffmanEncoder1 h1 = new ParallelHuffmanEncoder1(-1, "Constitution");
			trialResults[1][i] = t.stop();
			
			t.start(Runtime.getRuntime().availableProcessors() + " Thread Parallel Encoding Implementation #2");
			ParallelHuffmanEncoder2 h2 = new ParallelHuffmanEncoder2(-1, "Constitution");
			trialResults[2][i] = t.stop();
			
			t.start(Runtime.getRuntime().availableProcessors() + " Thread Parallel Encoding Implementation #3");
			ParallelHuffmanEncoder3 h3 = new ParallelHuffmanEncoder3(-1, "Constitution");
			trialResults[3][i] = t.stop();
			
			trialNum++;
		}
		
		System.out.println("Average of Single Threaded Trials: " + runningAverage(trialResults[0])/1000000 + " ms.");
		System.out.println("     Avg time to construct tree: " + runningAverage(specificResults[0])/1000000 + " ms.");
		System.out.println("     Avg time to encode file: " + runningAverage(specificResults[1])/1000000 + " ms.");
		System.out.println("Average of Parallel Thread Implementation: " + runningAverage(trialResults[2])/1000000 + " ms.");
		System.out.println("     Avg time to construct tree: " + runningAverage(specificResults[4])/1000000 + " ms.");
		System.out.println("     Avg time to encode file: " + runningAverage(specificResults[5])/1000000 + " ms.");
		System.out.println("Average of Parallel Runnable Implementation: " + runningAverage(trialResults[3])/1000000 + " ms.");
		System.out.println("     Avg time to construct tree: " + runningAverage(specificResults[6])/1000000 + " ms.");
		System.out.println("     Avg time to encode file: " + runningAverage(specificResults[7])/1000000 + " ms.");
		System.out.println("Average of Parallel Stream Implementation: " + runningAverage(trialResults[1])/1000000 + " ms.");
		System.out.println("     Avg time to construct tree: " + runningAverage(specificResults[2])/1000000 + " ms.");
		System.out.println("     Avg time to encode file: " + runningAverage(specificResults[3])/1000000 + " ms.");
		
		System.out.println("Compression % (did not change across implementations): " + (26015.0/45704.0)*100 + "%");

	}
	
	public static double runningAverage(long[] samples) {
		double avg = (double)samples[0];
		for(int i = 1; i < samples.length; ++i) {
			avg = avg * ((double)i-1)/(double)i + (double)samples[i]/(double)i;
		}
		
		return avg;
	}

}
