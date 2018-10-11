
public class Sock implements Comparable<Sock>{
	
	public enum Color {
		None(-1),
		Red(0),
		Green(1),
		Blue(2),
		Orange(3);
		
		private int value;
		
		public int getValue() {
			return value;
		}
		
		private Color(int _value) {
			value = _value;
		}
	}
	
	public Color color = Color.None;
	public boolean isDestroyed = false;
	
	public Sock(Color color) {
		this.color = color;
		isDestroyed = false;
	}
	
	public void destroy() {
		isDestroyed = true;
	}
	
	public int compareTo(Sock other) {
		int value1 = this.color.getValue();
		int value2 = this.color.getValue();
		if(value1 < value2) {
			return -1;
		}
		else if(value1 > value2) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public String toString() {
		switch(color) {
		case Red:
			return "Red";
			
		case Blue:
			return "Blue";
			
		case Green:
			return "Green";
			
		case Orange:
			return "Orange";
			
		default:
			return "None";
		}
	}
}
