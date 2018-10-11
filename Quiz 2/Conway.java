import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Conway {
	
	private Cell[][] board;
	private CyclicBarrier bar;
	
	public Conway(int width, int height, int generations, double chance) {
		board = new Cell[width][height];
		bar = new CyclicBarrier(width*height + 1);
		
		//generate widthxheight cells. Each cell extends Thread.
		for(int i = 0; i < width; ++i) {
			for(int j = 0; j < height; ++j) {
				board[i][j] = new Cell(bar, (Math.random() < chance), generations);
			}
		}
		
		//After all cells have been generated, we give each cell an array of its valid neighbors
		//Then, we start them.
		for(int i = 0; i < width; ++i) {
			for(int j = 0; j < height; ++j) {
				board[i][j].setNeighbors(getSlice(i, j, width, height));
				board[i][j].start();
			}
		}
		
		//Method for printing out each generation
		for(int n = 0; n < generations; ++n) {
			System.out.println("==========Generation " + n + "==========");
			for(int i = 0; i < board.length; ++i) {
				for(int j = 0; j < board[i].length; ++j) {
					//O is used for alive cells
					if(board[i][j].alive) {
						System.out.print("O");
					}
					//X is used for dead cells
					else {
						System.out.print("X");
					}
					System.out.print(" ");
				}
				System.out.println();
			}
			
			try {
				bar.await();
				bar.await();
			} catch (BrokenBarrierException | InterruptedException e) {
				System.out.println("Barrier broken or thread interrupted.");
			}
		}
		
		
	}
	
	private Cell[][] getSlice(int x, int y, int boundW, int boundH) {
		int leftEdge = (x == 0) ? 0 : -1;
		int rightEdge = (x == boundW - 1) ? 0 : 1;
		int topEdge = (y == 0) ? 0 : -1;
		int botEdge = (y == boundH - 1) ? 0 : 1;
		int width = 1 + (int)(Math.abs(leftEdge) + Math.abs(rightEdge));
		int height = 1 + (int)(Math.abs(topEdge) + Math.abs(botEdge));
		
		Cell[][] slice = new Cell[width][height];
		
		
		int wIndex = 0;
		for(int i = leftEdge; i <= rightEdge; ++i) {
			int hIndex = 0;
			for(int j = topEdge; j <= botEdge; ++j) {
				slice[wIndex][hIndex] = board[x + i][y + j];
				hIndex++;
			}
			wIndex++;
		}
		
		return slice;

	}
	
}
