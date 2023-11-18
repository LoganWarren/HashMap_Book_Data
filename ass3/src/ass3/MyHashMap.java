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

    //Construnctors

    public MyHashMap(int capacity, float maxLoadFactor) {
        this.maxLoadFactor = maxLoadFactor;
        this.table = createTable(capacity);
    }

    public MyHashMap() {
        this.maxLoadFactor = DEFAULT_LOAD_FACTOR;
        this.table = createTable(DEFAULT_CAPACITY);
    }

    
    public V get(K key) {
        int index = (key.hashCode() & 0x7fffffff) % table.length;
        LinkedList<SimpleEntry<K, V>> bucket = table[index];
        if (bucket != null) {
            for (SimpleEntry<K, V> entry : bucket) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        int index = (key.hashCode() & 0x7fffffff) % table.length;
        LinkedList<SimpleEntry<K, V>> bucket = table[index];
        if (bucket == null) {
            bucket = table[index] = new LinkedList<>();
        }
        for (SimpleEntry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                V oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
        }
        bucket.add(new SimpleEntry<>(key, value));
        size++;
        if (size > maxLoadFactor * table.length) {
            resizeRehash();
        }
        return null;
    }

    public V remove(K key) {
        int index = (key.hashCode() & 0x7fffffff) % table.length;
        LinkedList<SimpleEntry<K, V>> bucket = table[index];
        if (bucket != null) {
            Iterator<SimpleEntry<K, V>> iterator = bucket.iterator();
            while (iterator.hasNext()) {
                SimpleEntry<K, V> entry = iterator.next();
                if (entry.getKey().equals(key)) {
                    V value = entry.getValue();
                    iterator.remove();
                    size--;
                    return value;
                }
            }
        }
        return null;
    }
    
    public boolean containsKey(K key) {
        if (key == null) throw new NullPointerException();
        
        int index = Math.abs(key.hashCode()) % table.length;
        LinkedList<SimpleEntry<K, V>> bucket = table[index];
        
        if (bucket == null) return false;
        
        for (SimpleEntry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean containsValue(V value) {
        if (value == null) throw new NullPointerException();
        
        for (LinkedList<SimpleEntry<K, V>> bucket : table) {
            if (bucket != null) {
                for (SimpleEntry<K, V> entry : bucket) {
                    if (entry.getValue().equals(value)) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    // Gets all entries in the map

    public List<SimpleEntry<K, V>> getAllEntries(LinkedList<SimpleEntry<K, V>>[] hashTable) {
        List<SimpleEntry<K, V>> entries = new ArrayList<>();
        for (LinkedList<SimpleEntry<K, V>> bucket : hashTable) {
            if (bucket != null) {
                entries.addAll(bucket);
            }
        }
        return entries;
    }

// resizeRehash will resize and rehash the table when load factor is exceded

    private void resizeRehash() { 
        LinkedList<SimpleEntry<K, V>>[] oldTable = this.table;
        this.table = createTable(this.table.length * 2); 
        this.size = 0; 
    
        for (LinkedList<SimpleEntry<K, V>> bucket : oldTable) {
            if (bucket != null) {
                for (SimpleEntry<K, V> entry : bucket) {
                    put(entry.getKey(), entry.getValue()); 
                }
            }
        }
    }
    public boolean isEmpty() {
        return size == 0;
    }
    // checking if the map is empty
    public int size() {
        return size;
    }
    // geting the number of key pairs in the map 
    public void clear() {
        table = createTable(DEFAULT_CAPACITY);
        size = 0;
    }
    // clears the map
    

}
