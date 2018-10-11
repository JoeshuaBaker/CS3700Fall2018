
public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int size = 1 + (int)(Math.random()*50);
		int generations = 1 + (int)(Math.random()*200);
		double aliveChance = 0.1 + Math.random()/2.0;
		
		
		Conway c = new Conway(size, size, generations, aliveChance);
		
		System.out.println("Results for size: " + size + ", gens: " + generations + " and chance: " + aliveChance + ".");
	}

}
