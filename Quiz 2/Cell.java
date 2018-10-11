import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Cell extends Thread {
	
	public boolean alive = false;
	CyclicBarrier c;
	Cell[][] neighbors;
	int generations;
	
	public Cell(CyclicBarrier c, boolean state, int generations) {
		this.alive = state;
		this.c = c;
		this.generations = generations;
	}
	
	public void setNeighbors(Cell[][] neighbors) {
		this.neighbors = neighbors;
	}
	
	//game runs in discrete steps. Game uses barrier to ensure synchronicity between these discrete steps.
	public void run() {
		int cycles = 0;
		while(cycles++ < generations) {
			//First half of one cycle: Check the state of each of our neighbors.
			//Do this before we actually change state.
			int neighbors = checkNeighbors();
			try {
				c.await();
			} catch (BrokenBarrierException | InterruptedException e) {
				System.out.println("Barrier broken or thread interrupted.");
			}
			//After all threads have checked their neighbors, all threads change their state simultaneously
			//Then, all threads wait at the barrier again, cycling back to neighbor checking after each thread
			//has changed its state appropriately.
			changeState(neighbors);
			try {
				c.await();
			} catch (BrokenBarrierException | InterruptedException e) {
				System.out.println("Barrier broken or thread interrupted.");
			}
		}
	}
	
	private int checkNeighbors() {
		int count = 0;
		for(int i = 0; i < neighbors.length; ++i) {
			for(int j = 0; j < neighbors[i].length; ++j) {
				if(neighbors[i][j] != this && neighbors[i][j].alive) ++count;
			}
		}
		return count;
	}
	
	private void changeState(int n) {
		if(alive && n < 2) {
			alive = false;
		} else if (alive && n > 3) {
			alive = false;
		} else if (!alive && n == 3) {
			alive = true;
		}
	}
	
}
