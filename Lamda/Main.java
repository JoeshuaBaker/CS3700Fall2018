
public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//no parameter
		//aBlockOfCode = () -> { System.out.println("Hello world!"); };
		//parameter lambda
		//doubleNumberFunction = (int a)  -> a*2;
		//two arguments
		//addFunction = (int a, int b) -> a + b;
		
		MyLambda myLambdaFunction = () -> System.out.println("Hello World!");
		MyAdd addFunction = (int a, int b) -> a + b;
		myLambdaFunction.foo();
		
		StringLengthLambda myLambda = s -> s.length();
		System.out.println(myLambda.getLength("Hello Lambda"));
		
		Thread myThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Printed inside Runnable");
			}
		});
		
		myThread.run();
		
		Thread myLambdaThread = new Thread(()->System.out.println("Printed inside Runnable"));
		myLambdaThread.run();
	}
	
}


interface MyLambda {
	void foo();
}

interface MyAdd {
	int add(int a, int b);
}

interface StringLengthLambda {
	int getLength(String s);
}
