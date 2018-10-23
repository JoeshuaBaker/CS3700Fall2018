
public class IntegerBitArray {
	private int a;
	private byte highestBit;
	private boolean trackHighestBit;
	
	public IntegerBitArray(boolean trackHighestBit) {
		a=0;
		this.trackHighestBit = trackHighestBit;
		highestBit = -1;
	}
	
	public IntegerBitArray(boolean trackHighestBit, int array) {
		a = array;
		this.trackHighestBit = trackHighestBit;
		highestBit = -1;
	}
	
	public IntegerBitArray(boolean trackHighestBit, boolean[] bitArray) {
		a=0;
		this.trackHighestBit = trackHighestBit;
		highestBit = -1;
		for(int i = 0; i < bitArray.length; ++i) {
			pushAndSet(bitArray[i]);
		}
	}
	
	public IntegerBitArray(boolean trackHighestBit, String bitArray) {
		a=0;
		this.trackHighestBit = trackHighestBit;
		highestBit = -1;
		for(int i = 0; i < bitArray.length(); ++i) {
			char c = bitArray.charAt(i);
			if(c == '0') {
				pushAndSet(false);
			}
			else if(c == '1') {
				pushAndSet(true);
			}
		}
	}
	
	public void set(boolean bit, byte index) {
		if(index >= 32 || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		if(trackHighestBit && index > highestBit)
			highestBit = index;
		
		a = (bit)? a | (1 << index) : a & ~(1 << index);
	}
	
	public void set(boolean bit, int index) {
		set(bit, (byte)index);
	}
	
	public void pushAndSet(boolean bit) {
		if(trackHighestBit && highestBit == 32) {
			throw new IndexOutOfBoundsException();
		}
		a = a << 1;
		if(trackHighestBit) highestBit++;
		set(bit, (byte)0);
	}
	
	public int get() {
		return a;
	}
	
	public boolean getBit(byte index) {
		int n = a;
		if(index >= 32 || index < 0) {
			throw new IndexOutOfBoundsException();
		}
	    return ((n >> index) & 1) == 1;
	}
	
	public boolean getBit(int index) {
		return getBit((byte)index);
	}
	
	public int getBitAsInt(byte index) {
		int n = a;
		if(index >= 32 || index < 0) {
			throw new IndexOutOfBoundsException();
		}
	    return ((n >> index) & 1);
	}
	
	public int getBitAsInt(int index) {
		return getBitAsInt((byte) index);
	}
	
	public boolean[] toBooleanArray() {
		boolean[] ret = (trackHighestBit)? 
				new boolean[highestBit + 1] : 
				new boolean[32];
		
		for(int i = ret.length - 1; i >= 0; --i) {
			ret[ret.length - i - 1] = getBit((byte)i);
		}
		
		return ret;
	}
	
	public byte highestBitUsed() {
		return highestBit;
	}
	
	public String toString() {
		String comp = "";
		int limit = (trackHighestBit)? highestBit : 32;
		for(int i = limit; i >= 0; --i) {
			comp += getBitAsInt((byte)i);
		}
		return comp;
	}
}
