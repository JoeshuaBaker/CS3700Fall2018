import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MatrixMult{
	int[][] mat1;
	int[][] mat2;
	int[][] res;
	int m;
	int n;
	int p;
	public static int numOfThreads = 1;
	
	public MatrixMult(int m, int n, int p, int numOfThreads) {
		mat1 = new int[m][n];
		mat2 = new int[n][p];
		MatrixMult.numOfThreads = numOfThreads;
		
		this.m = m;
		this.n = n;
		this.p = p;
		
		for(int i = 0; i < m; ++i) {
			for(int j = 0; j < n; ++j) {
				mat1[i][j] = (int)(Math.random()*100);
			}
		}
		
		for(int i = 0; i < n; ++i) {
			for(int j = 0; j < p; ++j) {
				mat2[i][j] = (int)(Math.random()*100);
			}
		}
		
		ExecutorService s = Executors.newSingleThreadExecutor();
		Future<int[][]> task = s.submit(new SubMatrix(mat1, mat2, n));
		
		try {
			res = task.get();
		} catch (Exception e) {
			System.out.println("Exception in main thread");
		}
		
		
		for(int i = 0; i < res.length; ++i) {
			for(int j = 0; j < res[0].length; ++j) {
				System.out.print(res[i][j] + ",");
			}
			System.out.println();
		}
		
		System.out.println();
        s.shutdown();
	}
}
