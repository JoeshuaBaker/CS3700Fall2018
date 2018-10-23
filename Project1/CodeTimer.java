
public class CodeTimer {
	long start;
	long finish;
	long finishMS;
	String label;
	boolean doNotPrint;
	
	public CodeTimer() {
		doNotPrint = true;
	}
	
	public CodeTimer(boolean doNotPrint) {
		this.doNotPrint = doNotPrint;
	}
	
	public void start(String label) {
		this.label = label;
		if(!doNotPrint)System.out.println("Running " + label);
		start = System.nanoTime();
	}
	
	public long stop() {
		finish = System.nanoTime() - start;
		finishMS = finish/1000000;
		
		if(!doNotPrint)System.out.println("Finished running " + label);
		if(!doNotPrint)System.out.println("Ran in " + finish + "ns (" + finishMS + "ms).");
		return finish;
		
	}
}
