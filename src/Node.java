class Node<K,V>{
    K key; V value;
    Node<K,V> next;
    Node<K,V> right, left;
    int N;
    int aux;
    Node<K,V> parent;

    public Node(K key, V value, Node<K,V> next){
        this.key = key;
        this.value = value;
        this.next = next;
    }
    public Node(K key, V value){
        this.key = key; this.value = value;
        this.N = 1;
    }
    public int getAux() {return aux;}
    public void setAux(int value){aux = value;}
}