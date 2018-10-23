import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class ParallelHuffmanEncoder1 {
	AtomicIntegerArray aFreq;
	IntegerBitArray[] codes;
	
	public ParallelHuffmanEncoder1(int threadCount, String filename) {
		//first, read in the file.
		File f = new File(filename);
		BufferedReader fr = null;
		
		try {
			fr = new BufferedReader(new FileReader(f + ".txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Could not find file with name: " + f);
		}
		long originalSize = f.length();
		
		//create array of atomic ints to be incremented as characters are found
		aFreq = new AtomicIntegerArray(128);
		
		//setting common threadpool to whatever user specified as the threadCount
		if(threadCount != -1) {
			System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(threadCount));
		}
		
		CodeTimer treeTimer = new CodeTimer();
		treeTimer.start("Creation of the Huffman Tree");
		//count frequency of each character and increment correct pos in array
		fr.lines().parallel().forEach((m) -> {
			for(int i = 0; i < m.length(); ++i) {
				char c = m.charAt(i);
				aFreq.incrementAndGet((int)c);
			}
		});
		
		//now that we have frequency, it's time to assemble the nodes into the
		//huffman tree. First, we sort the nodes by frequency, using a PriorityQueue
		PriorityQueue<HuffmanNode> q = new PriorityQueue<>();
		for(int i = 0; i < aFreq.length(); ++i) {
			int charFreq = aFreq.get(i);
			if(charFreq != 0) {
				q.add(new HuffmanNode(charFreq, (char)i));
			}
		}
		
		
		//Next, we start branching children based on frequency until
		//only the root node remains in the priorityQueue.
		HuffmanNode root = null;
		while(q.size() > 1) {
			HuffmanNode x = q.poll();
			HuffmanNode y = q.poll();
			
			HuffmanNode parent = new HuffmanNode(x.freq+y.freq, '@');
			parent.left = x;
			parent.right = y;
			root = parent;
			q.add(parent);
		}
		Driver.specificResults[2][Driver.trialNum] = treeTimer.stop();
		
		//Invoke a function to populate the lookup table for each character code
		codes = new IntegerBitArray[128];
		setCode(root, "");
		
		BinaryOut w = new BinaryOut(filename + "_comp.txt");
		treeTimer.start("Compression and Writing of Data");
		writeTree(root, w);
		
		try {
			fr = new BufferedReader(new FileReader(filename + ".txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		writeData(fr, w, root);
		w.close();
		Driver.specificResults[3][Driver.trialNum] = treeTimer.stop();
		
		/*
		BinaryIn reader = new BinaryIn(filename + "_comp.txt");
		root = readTree(reader);
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(filename + "_un_comp.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		readData(reader, writer, root);
		*/
		
	}
	
	private void writeData(BufferedReader fr, BinaryOut w, HuffmanNode root) {
		fr.lines().forEach(m -> {
			for(char c : m.toCharArray()) {
				boolean[] bits = codes[c].toBooleanArray();
				for(boolean b : bits) {
					w.write(b);
				}
			}
		});
	}
	
	private void readData(BinaryIn in, BufferedWriter writer, HuffmanNode root) {
		HuffmanNode current = root;
		while(!in.isEmpty()) {
			boolean b = in.readBoolean();
			current = (b) ? current.right : current.left;
			if(current.left == null && current.right == null) {
				try {
					writer.write(current.c);
				} catch (IOException e) {
					e.printStackTrace();
				}
				current = root;
			}
		}
	}
	
	private void printCode(HuffmanNode root, String s) 
    { 
        if (root.left == null && root.right == null) {  
            System.out.println(root.c + ":" + root.code);
            return; 
        } 
  
        printCode(root.left, s + "0"); 
        printCode(root.right, s + "1"); 
    } 
	
	private void setCode(HuffmanNode root, String s) {
		if (root.left == null && root.right == null) {  
            codes[root.c] = new IntegerBitArray(true, s);
            return; 
        } 
  
        setCode(root.left, s + "0"); 
        setCode(root.right, s + "1");
	}
	
	private void writeTree(HuffmanNode root, BinaryOut w) {
		if (root.left == null && root.right == null) {  
			w.write(true);
			w.write(root.c);
            return; 
        }
		
		w.write(false);
		writeTree(root.left, w);
		writeTree(root.right, w);
	}
	
	private HuffmanNode readCode(BinaryIn r, String s) {
		if(r.readBoolean()) {
			HuffmanNode res = new HuffmanNode(r.readChar());
			res.code = new IntegerBitArray(true, s);
			return res;
		}
		else {
			HuffmanNode left = readCode(r, s + "0");
			HuffmanNode right = readCode(r, s + "1");
			HuffmanNode parent = new HuffmanNode('@');
			parent.left = left;
			parent.right = right;
			return parent;
		}
	}
	
	private HuffmanNode readTree(BinaryIn r) {
		if(r.readBoolean()) {
			return new HuffmanNode(r.readChar());
		} else {
			HuffmanNode left = readTree(r);
			HuffmanNode right = readTree(r);
			HuffmanNode parent = new HuffmanNode('@');
			parent.left = left;
			parent.right = right;
			return parent;
		}
	}
	
	private class HuffmanNode implements Comparable<HuffmanNode>{
		public int freq;
		public char c;
		public HuffmanNode left;
		public HuffmanNode right;
		public IntegerBitArray code;
		public HuffmanNode(char c) {
			this.c = c;
		}
		
		public HuffmanNode(int freq, char c) {
			this.freq=freq;
			this.c=c;
		}
		
		public int compareTo(HuffmanNode other) {
			return this.freq - other.freq;
		}
	}
}
