import java.util.Scanner;

public class Driver {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean quit = false;
		while(!quit) {
			System.out.println("Enter 0 to Quit.");
			System.out.println("Enter 1 for SockSorter.");
			System.out.println("Enter 2 for MatrixMult.");
			System.out.println("Enter 3 for Election.");
			
			int input = sc.nextInt();
			if(input == 0) {
				quit = true;
			}
			if(input == 1) {
				SockSorter s = new SockSorter();		
			}
			else if(input == 2) {
				for(int n = 2; n <= 32; n*=2) {
					for(int threads = 1; threads <= 8; threads *= 2) {
						if(n == 64 && threads == 8) continue;
						else {
							MatrixMult m = new MatrixMult(n, n, n, threads);	
						}
					}
				}		
			}
			else if(input == 3) {
				Election e = new Election();
			}
		}
	}
}
