import java.util.concurrent.locks.ReentrantLock;

public class UnstructuredSim {
	public UnstructuredSim() {
		UnstructuredPhilosopher[] phils = new UnstructuredPhilosopher[5];
		for(int i = 0; i < phils.length; ++i) {
			phils[i] = new UnstructuredPhilosopher(""+i);
		}
		
		ReentrantLock[] forks = new ReentrantLock[5];
		for(int i = 0; i < forks.length; ++i) {
			forks[i] = new ReentrantLock();
		}
		
		phils[0].left = forks[4];
		phils[0].right = forks[0];
		for(int i = 1; i < phils.length; ++i) {
			phils[i].left = forks[i-1];
			phils[i].right = forks[i];
		}
		
		for(int i = 0; i < phils.length; ++i) {
			phils[i].start();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < phils.length; ++i) {
			try {
				phils[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
