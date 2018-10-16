
public class UnstructuredPhilosopher extends Philosopher {
	public UnstructuredPhilosopher(String name) {
		super(name);
	}
	
	@Override
	public void run() {
		while(eaten < MAXTRIALS) {
			boolean success = true;
			System.out.println("Philosopher " + name + " tries to acquire left fork.");
			if(left.tryLock()) {
				pickupLeft();
				System.out.println("Philosopher " + name + " tries to acquire right fork.");
				if(right.tryLock()) {
					pickupRight();
					eat(1000 + (int)(Math.random()*9000));
					dropLeft();
					left.unlock();
					dropRight();
					right.unlock();
				}
				else {
					System.out.println("Philosopher " + name + " could not acquire right fork. Thinking instead!");
					success = false;
				}
			}
			else {
				System.out.println("Philosopher " + name + " could not acquire left fork. Thinking instead!");
				success = false;
			}
			
			if(!success) {
				if(left.isHeldByCurrentThread()) {
					left.unlock();
					dropLeft();
				}
				if(right.isHeldByCurrentThread()) {
					right.unlock();
					dropRight();
				}
				think(1000 + (int)(Math.random()*4000));
			}
		}
	}
}
