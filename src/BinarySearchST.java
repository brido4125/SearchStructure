import java.util.ArrayList;

public class BinarySearchST<K extends Comparable<K>, V> {
    private static final int INIT_CAPACITY = 10;
    private K[] keys;
    private V[] values;
    private int N;

    public BinarySearchST(){
        keys = (K[])new Comparable[INIT_CAPACITY];
        values = (V[])new Object[INIT_CAPACITY];
    }
    public BinarySearchST(int capacity){
        keys = (K[])new Comparable[capacity];
        values = (V[])new Object[capacity];
    }
    public boolean contains(K key){return get(key) != null;}
    public boolean isEmpty(){return N == 0;}
    public int size() {return N;}

    private void resize(int capacity){
        K[] tempk = (K[]) new Comparable[capacity];
        V[] tempv = (V[]) new Object[capacity];
        for(int i = 0; i < N ; i++){
            tempk[i] = keys[i];
            tempv[i] = values[i];
        }
        values = tempv;
        keys = tempk;
    }

    private int search(K key){
        int lo = 0;
        int hi = N -1;
        while (lo <= hi){
            int mid = (hi + lo) / 2;
            int cmp = key.compareTo(keys[mid]);

            if(cmp < 0)         hi = mid - 1;//cmp is smaller than 0 means key is smaller than keys[mid]
            else if(cmp > 0)    lo = mid + 1;
            else                return mid;
        }
        return lo;
    }

    public V get(K key){
        if(isEmpty())   return null;
        int i = search(key);
        //compareTo가 0이면 두 수는 같다라는 뜻이다.
        if(i < N && keys[i].compareTo(key) == 0)    return values[i];
        else    return null;
    }

    public void put(K key, V value){
        int i = search(key);
        if( i < N && keys[i].compareTo(key) == 0){
            values[i] = value;
            return;
        }
        if(N == keys.length)
            resize(2 * keys.length);
        for(int j = N ; j > i ; j--){
            keys[j] = keys[j-1];
            values[j] = values[j-1];
        }
        keys[i] = key; values[i] = value;
        N++;
    }

    public void delete(K key){
        if(isEmpty()) return;
        int i = search(key);
        if(i == N || keys[i].compareTo(key) != 0) return;

        for(int j = i; j < N -1; j++){
            keys[j] = keys[j+1];
            values[j] = values[j+1];
        }
        N--;
        keys[N] = null; values[N] = null;
        if(N > INIT_CAPACITY && N == keys.length/4)
            resize(keys.length/2);
    }

    public Iterable<K> keys(){
        ArrayList<K> keyList = new ArrayList<K>(N);
        for(int i = 0 ; i < N ; i++){
            keyList.add(keys[i]);
        }
        return keyList;
    }
}
