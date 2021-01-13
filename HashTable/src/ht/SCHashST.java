/**
 * 
 */
package ht;

import java.util.NoSuchElementException;

/**
 * Hash Table implemented with Separate Chaining collision resolution.
 * Citation: Based on Algorithms course by Robert Sedgewick and Kevin Wayne.
 * @param <Key> Key 
 * @param <Data> Data associated with Key
 * @author Riley Harris
 *
 */
public class SCHashST<Key, Data> {
	/** Number of chains*/
	private static final int M = 5;
	/** Hashed Table */
	private Node[] st;
	/** Number of key/value pairs */
	private int size;
	
	/**
	 *  Construct an empty Hash Table.
	 */
	public SCHashST() {
		st = new Node[M];
		size = 0;
	}
	
	/**
	 * Returns the number of key/value pairs.
	 * @return size as integer
	 */
	public int size() {
		return size;
	}
	/**
	 * Returns hash value as an integer between 0 and M-1.
	 * @param key Key to hash
	 * @return hash value 
	 */
	private int hash(Key key) {
		// 0x7fffffff is used to guarantee a positive integer result
		// mod M will return an int between 0 and M-1
		return (key.hashCode() & 0x7fffffff) % M;
	}
	
	/**
	 * Puts a key and its associated data into the table.
	 * Key will be "mapped" to its data. 
	 * Inserts key + data into front slot of designated chain.
	 * @param key Key to map
	 * @param data Data to map
	 * @throws IllegalArgumentException if data or key is null
	 */
	public void put(Key key, Data data) {
		if (key == null || data == null) {
			throw new IllegalArgumentException("Key and/or Data may not be null.");
		}
		int index = hash(key);
		Node current = st[index];
		while (current != null) {
			if (current.key.equals(key)) {
				return;
			}
			current = current.next;
		}
		Node n = new Node(key, data, st[index]);
		st[index] = n;
		size++;
	}
	
	/**
	 * Returns the data associated with the specified key.
	 * @param key Key to extract data from
	 * @return Data, or null if key does not exist.
	 * @throws IllegalArgumentException if key is null
	 */
	@SuppressWarnings("unchecked")
	public Data get(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Cannot search for null key.");
		}
		int index = hash(key);
		Node current = st[index];
		while (current != null) {
			if (current.key.equals(key)) {
				return (Data) current.dat;
			}
			current = current.next;
		}
		return null;
	}
	
	/**
	 * Removes specified key (and its data) from the table.
	 * @param key Key to remove
	 * @return removed Data 
	 * @throws IllegalArgumentException if key to remove is null
	 * @throws NoSuchElementException if key does not exist
	 */
	@SuppressWarnings("unchecked")
	public Data remove(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("Cannot remove null key.");
		}
		int index = hash(key);
		if (st[index] == null) {
			throw new NoSuchElementException("Key does not exist.");
		}
		Data d = null;
		Node current = st[index];
		if (current.key.equals(key)) { // key to remove is at front
			d = (Data) current.dat;
			st[index] = current.next;
			size--; 
			return d;
		} else { // key to remove not at front
			while (current.next != null) {
				if (current.next.key.equals(key)) {
					d = (Data) current.next.dat;
					current.next = current.next.next;
					size--;
					return d;
				}
			}
			// At this point the key could not be found, so exception is thrown
			throw new NoSuchElementException("Key does not exist.");
		}
	}
	
	/**
	 * Node for linked list. 
	 * @author Riley Harris
	 *
	 */
	private static class Node {
		/** Key of Node */
		private Object key; // Object instead of Key bc you cannot create an array with Generic types.
		/** Data of Node */
		private Object dat; // Same with this field
		/** Reference to next Node */
		private Node next;
		
		/**
		 * Constructor accepting a Key and its associated Data.
		 * @param key Key of Node
		 * @param dat Data of Node
		 */
		private Node(Object key, Object dat) {
			this.key = key;
			this.dat = dat;
			this.next = null;
		}
		
		/**
		 * Constructor allowing for the next reference to be specified.
		 * @param key Key of Node
		 * @param dat Data of Node
		 * @param next Reference to next Node
		 */
		private Node(Object key, Object dat, Node next) {
			this.key = key;
			this.dat = dat;
			this.next = next;
		}
	}
}
