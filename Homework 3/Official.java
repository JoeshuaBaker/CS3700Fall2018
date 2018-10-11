
public class Official extends Thread {
	public int rank;
	public String name;
	public Official Leader;
	
	private static String[] firstNames = {"Abe", "Buck", "Chad", "Darren", "Escabar", 
			"Fannie", "Genny", "Hilda", "Indigo", "Josh", "Klein", "Lizzie", "Manuel",
			"Nick", "Oliver", "Pamela", "Quinton", "Rachel", "Steve", "Tim", "Yannie"
	};
	
	private static String[] lastNames = {"Anthonies", "Budsworth", "Cadwell", "Baker", "Harrington",
			"Miller", "Sanchez", "Keeler", "Klekas", "Williams", "Woody", "Byrd", "Sentry", "Young",
			"Buster", "Willard"
	};
	
	public Official() {
		name = firstNames[(int)(Math.random()*firstNames.length)] + " " + 
				lastNames[(int)(Math.random()*lastNames.length)];
		rank = (Math.random() < 0.5) ? (int)(Math.random()*Integer.MAX_VALUE) : 
										(int)(Math.random()*Integer.MIN_VALUE);
	}
	
	@Override
	public void run() {
		Rank.rank.reportRank(this);
		boolean finished = false;
		while(!finished) {
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				if(Rank.highestRank >= rank) {
					if(Rank.leader == this) {
						System.out.println(name + ":" + " Haha! I am the new leader!");
					}
					else {
						System.out.println(name + ": " + Rank.leader.name + " is my new leader! I bow out of the race.");
						Leader = Rank.leader;
						finished = true;
					}
				}
				else {
					System.out.println(name + ": " + "Hey! I'm better than that leader...");
				}
			}
		}
	}
}
