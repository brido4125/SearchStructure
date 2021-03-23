import java.util.ArrayList;

public class SequentialSearchST<K,V>{
    private Node<K,V> first;
    int N;

    public V get(K key){
        for(Node<K,V> x = first;x != null; x = x.next){
            if(key.equals(x.key))
                return x.value;
        }
        return null;
    }
    public void put(K key, V value){
        for(Node<K,V> x = first; x != null ; x = x.next) {
            if (key.equals(x.key)) {
                x.value = value;
                return;
            }
        }
        first = new Node<K,V>(key,value,first);
        N++;
    }
    public void delete(K key){
        if(key.equals(first.key)){
            first = first.next;
            N--;
            return;
        }
        for(Node<K,V> x = first; x.next != null; x = x.next){
            if(key.equals(x.next.key)){
                x.next = x.next.next;
                N--;
                return;
            }
        }
    }
    public Iterable<K> keys(){
        ArrayList<K> keyList = new ArrayList<K>(N);
        for(Node<K,V> x = first ; x != null ; x = x.next){
            keyList.add(x.key);
        }
        return keyList;
    }
    public boolean contains(K key) {return get(key) != null;}
    public boolean isEmpty() {return N == 0;}
    public int size() {return N;}
}
