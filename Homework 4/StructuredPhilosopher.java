
public class StructuredPhilosopher extends Philosopher {
	public StructuredPhilosopher(String name) {
		super(name);
	}
	
	@Override
	public void run() {
		while(eaten < MAXTRIALS) {
			if(!strange) {
				synchronized(left) {
					pickupLeft();
					synchronized(right) {
						pickupRight();
						eat(1000 + (int)(Math.random()*9000));
					}
					dropRight();
				}
				dropLeft();
			} else {
				synchronized(right) {
					pickupRight();
					synchronized(left) {
						pickupLeft();
						eat(1000 + (int)(Math.random()*9000));
					}
					dropLeft();
				}
				dropRight();
			}
			think(1000 + (int)(Math.random()*4000));
		}
		System.out.println("Philosopher " + name + " is done eating!");
	}
	
}
