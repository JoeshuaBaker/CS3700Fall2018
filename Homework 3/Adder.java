import java.util.concurrent.Callable;

public class Adder implements Callable<int[][]>{
	int[][] a;
	int[][] b;
	public Adder(int[][] a, int[][] b) {
		this.a = a;
		this.b = b;
	}
	
	public int[][] call() throws Exception {
		int[][] res = new int[a.length][a.length];
        for(int i = 0; i < a.length; ++i)
        {
            for(int j = 0; j < a.length; ++j)
            {
                res[i][j] = a[i][j] + b[i][j];
            }
        }
        
        return res;
	}
}
