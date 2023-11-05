package ass3;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;

public class MyHashMap<K, V> {
    private static final int TABLE_SIZE[] = { 5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853, 25717, 51437,
            102877, 205759, 411527, 823117, 1646237, 3292489, 6584983, 13169977, 26339969, 52679969, 105359939,
            210719881, 421439783, 842879579, 1685759167 };
    private static final int DEFAULT_CAPACITY = 11;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private LinkedList<SimpleEntry<K, V>>[] table;
    private int size;  //total number of elements stored in hash table
   
    private final float maxLoadFactor;  // max allowable size/capacity
 
    /**
     * Create a new array of <code>LinkedList<SimpleEntry<K, V>></code> prime size
     * at least <code>capacity</code>.
     * 
     * @param capacity The minimum number of slots in the array.
     * @return The new array with all elements set to <code>null</code>.
     */
    @SuppressWarnings("unchecked")
    private LinkedList<SimpleEntry<K, V>>[] createTable(int capacity) {
        for (int primeCapacity : TABLE_SIZE) {
            if (primeCapacity >= capacity) {
            	capacity = primeCapacity;
                break;
            }
        }
        LinkedList<SimpleEntry<K, V>> storage [] = new LinkedList [capacity];
        
        this.size = 0; 
        return storage;     
    }

    public MyHashMap (int capacity, float maxLoadFactor) {
        // To do: Implement this
    	this.maxLoadFactor = maxLoadFactor;
    }

    public MyHashMap() {
    	// To do: Implement this
    	this.maxLoadFactor = DEFAULT_LOAD_FACTOR;
    }    
    
    public V get(K key) {
    	//TODO implement this
    	
        return null;  
    }

    public V put(K key, V value) {
        // To do: Implement this
    	
        return null;
    }
    
    public V remove (K key) {
    	 // sample implementation
    	int hash = key.hashCode() & Integer.MAX_VALUE;  //converting to a positive int
        int index = hash % this.table.length;
    	
        if (this.table[index] == null)  // not in the table       		
            return null;
    	
        Iterator<SimpleEntry<K, V>> iter = this.table[index].iterator();
        while (iter.hasNext()) {
            SimpleEntry<K, V> entry = iter.next();
            if (!entry.getKey().equals(key))
                continue;                
            iter.remove();  
            this.size --;
            return entry.getValue();
        }
        return null;
    }
    
    public boolean containsKey(K key) {
    	 // To do: Implement this
    	
        return false;        
    }

    public boolean containsValue(V value) {
    	 // To do: Implement this
    	
        return false;
    }

    public List<SimpleEntry<K, V>> getAllEntries(LinkedList<SimpleEntry<K, V>>[] hashTable) {
    	// To do: Implement this.
    	
        return null;
    }

    private void resizeRehash() {
    	// To do: Implement this.
    }
    
   
   
}