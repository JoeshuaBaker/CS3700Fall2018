import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;

public class SubMatrix implements Callable<int[][]>{
	
	int[][] a;
	int[][] b;
	int n;
	
	public SubMatrix(int[][] a, int[][] b, int n) {
		this.a = a;
		this.b = b;
		this.n= n;
	}
	
	@Override
	public int[][] call() {
		if( n == 1)
        {
            int[][] base = new int[1][1];
            base[0][0] = a[0][0]*b[0][0];
            return base;
        }
        else
        {
            int[][] res = new int[n][n];
            
            int[][] upleft = new int[n/2][n/2];
            int[][] upright = new int[n/2][n/2];
            int[][] botleft = new int[n/2][n/2];
            int[][] botright = new int[n/2][n/2];
            
            int[][] upleftb = new int[n/2][n/2];
            int[][] uprightb = new int[n/2][n/2];
            int[][] botleftb = new int[n/2][n/2];
            int[][] botrightb = new int[n/2][n/2];
            
            
            //calculate submatrices
            for(int i=0; i<n; ++i)
            {
                for(int j=0; j<n; ++j)
                {
                    //upper left case
                    if(i<n/2 && j<n/2)
                    {
                        upleft[i][j] = a[i][j];
                        upleftb[i][j] = b[i][j];
                    }
                    //lower left case
                    else if(i>=n/2 && j<n/2)
                    {
                        botleft[i-n/2][j] = a[i][j];
                        botleftb[i-n/2][j] = b[i][j];
                    }
                    //upper right case
                    else if(i < n/2 && j>=n/2)
                    {
                        upright[i][j-n/2] = a[i][j];
                        uprightb[i][j-n/2] = b[i][j];
                    }
                    //lower right case
                    else
                    {
                        botright[i-n/2][j-n/2] = a[i][j];
                        botrightb[i-n/2][j-n/2] = b[i][j];
                    }
                }
            }
            
            //11 = upleft, 12 = upright, 21 = botleft, 22 = botright
            ExecutorService execService = Executors.newFixedThreadPool(MatrixMult.numOfThreads);
            
            Future<int[][]>[] tasks = new Future[8];
            
            tasks[0] = execService.submit(new SubMatrix(upleft, upleftb, n/2));
            tasks[1] = execService.submit(new SubMatrix(upright, botleftb, n/2));
            tasks[2] = execService.submit(new SubMatrix(upleft, uprightb, n/2));
            tasks[3] = execService.submit(new SubMatrix(upright, botrightb, n/2));
            tasks[4] = execService.submit(new SubMatrix(botleft, upleftb, n/2));
            tasks[5] = execService.submit(new SubMatrix(botright, botleftb, n/2));
            tasks[6] = execService.submit(new SubMatrix(botleft, uprightb, n/2));
            tasks[7] = execService.submit(new SubMatrix(botright, botrightb, n/2));
            
            ArrayList<int[][]> subMatrices = new ArrayList<>();
            
            for(int i = 0; i < 8; ++i) {
            	try {
            		subMatrices.add(i, tasks[i].get());
            	} catch(Exception e) {
        			System.out.println("Execution error happened when making submatrices");
        		}
            }
            
            Future<int[][]>[] adders = new Future[4];
            
            adders[0] = execService.submit(new Adder(subMatrices.get(0), subMatrices.get(1)));
            adders[1] = execService.submit(new Adder(subMatrices.get(2), subMatrices.get(3)));
            adders[2] = execService.submit(new Adder(subMatrices.get(4), subMatrices.get(5)));
            adders[3] = execService.submit(new Adder(subMatrices.get(6), subMatrices.get(7)));
            
            ArrayList<int[][]> addMatrices = new ArrayList<>();
            
            for(int fin = 0; fin < 4; ++fin) {
        		try {
        			addMatrices.add(fin, adders[fin].get());
        		} catch (Exception e) {
        			System.out.println("Exception occurred when adding submatrices");
        		}
        	}
            
            upleft = addMatrices.get(0);
            upright = addMatrices.get(1);
            botleft = addMatrices.get(2);
            botright = addMatrices.get(3);
            
            for(int i = 0; i < n; ++i)
            {
                for(int j = 0; j < n; j++)
                {
                    if(i<n/2 && j<n/2)
                    {
                        res[i][j] = upleft[i][j];
                    }
                    //lower left case
                    else if(i>=n/2 && j<n/2)
                    {
                        res[i][j] = botleft[i-n/2][j];
                    }
                    //upper right case
                    else if(i < n/2 && j>=n/2)
                    {
                        res[i][j] = upright[i][j-n/2];
                    }
                    //lower right case
                    else
                    {
                        res[i][j] = botright[i-n/2][j-n/2];
                    }
                }
            }
            
            execService.shutdown();
            return res;
        }
	}
}
