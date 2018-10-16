import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int input = -1;
		Scanner in = new Scanner(System.in);
		do {
			System.out.println("Welcome to philosopher sim.");
			System.out.println("0 -- Quit Sim");
			System.out.println("1 -- Simulate with Structured Locks");
			System.out.println("2 -- Simulate with Unstructured Locks");
			try {
				input = in.nextInt();
			} catch(Exception e) {
				System.out.println("Please input a number (0, 1, or 2).");
			}
			switch(input) {
			case 1:
				System.out.println("Starting Structured Sim...");
				StructuredSim sim = new StructuredSim();
				System.out.println("Finished Structured Sim!");
				break;
			case 2:
				System.out.println("Starting Unstructured Sim...");
				UnstructuredSim usim = new UnstructuredSim();
				System.out.println("Finished Unstructured Sim!");
				break;
			}
		} while(input != 0);
	}

}
