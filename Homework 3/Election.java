
public class Election {
	
	public Election() {
		int n = (int)(Math.random()*500) + 1;
		Official[] officials = new Official[n];
		for(int i = 0; i < n; ++i) {
			officials[i] = new Official();
		}
		
		Rank ranker = new Rank(officials);
		
		for(int i = 0; i < n; ++i) {
			officials[i].start();
		}
	}
}
