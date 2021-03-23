import java.util.ArrayList;

public class BST <K extends Comparable<K>, V>{
    protected Node<K,V> root;
    //Same package only can access to protected things
    protected Node<K,V> treeSearch(K key){
        Node<K,V> x = root;
        while (true) {
            int cmp = key.compareTo(x.key);
            if( cmp == 0)           return x;
            else if(cmp <= 0){
                if(x.left == null)  return x;
                else                x = x.left;
            }
            else{
                if(x.right == null) return x;
                else                x = x.right;
            }
        }
    }
    protected void rebalancedInsert(Node<K,V> x){
        resetSize(x.parent,1);
    }
    protected void rebalancedDelete(Node<K,V> p, Node<K,V> deleted){
        resetSize(p,-1);
    }
    protected void resetSize(Node<K,V> x,int value){
        for( ; x!=null; x = x.parent){
            x.N += value;
        }
    }

    protected boolean isLeaf(Node<K,V> x){
        return x.left == null && x.right == null;
    }

    //오른쪽, 왼쪽 둘다 자식을 가지고 있는 노드이다.
    protected boolean isTwoNode(Node<K,V> x){
        return x.left != null && x.right != null;
    }
    //makeLeft 가 true 면 왼쪽으로 연결, false 면 오른쪽으로 연결
    protected void relink(Node<K,V> parent, Node<K,V> child, boolean makeLeft){
        if(child != null)   child.parent = parent;
        if(makeLeft)        parent.left = child;
        else                parent.right = child;
    }

    protected Node<K,V> min(Node<K,V> x){
        while (x.left != null){
            x = x.left;
        }
        return x;
    }

    public int size() {return (root != null) ? root.N : 0;}
    public boolean contains(K key) {return get(key) != null;}
    public boolean inEmpty() {return root == null;}

    public V get(K key){
        if(root == null) return null;
        Node<K,V> x = treeSearch(key);
        if(key.equals(x.key))
            return x.value;
        else{
            return null;
        }
    }

    public void put(K key,V val){
        if(root == null){ root = new Node<K,V>(key,val); return;}
        Node<K,V> x = treeSearch(key);
        int cmp = key.compareTo(x.key);
        if(cmp == 0)    x.value = val; // Already has key ,so just reset a value
        else{
            Node<K,V> newNode = new Node<K,V>(key,val);
            if(cmp < 0) x.left = newNode;
            else        x.right = newNode;
            newNode.parent = x;
            rebalancedInsert(newNode);
        }
    }
    //Return the ordered keys in List
    public Iterable<K> keys() {
        if(root == null)    return null;
        ArrayList<K> keyList = new ArrayList<K>(size());
        inorder(root,keyList);
        return keyList;
    }

    private void inorder(Node<K,V> x, ArrayList<K> keyList){
        if( x != null){
            inorder(x.left,keyList);
            keyList.add(x.key);
            inorder(x.right,keyList);
        }
    }

    public void delete(K key){
        if(root == null) return;
        Node<K,V> x,y,p;
        x = treeSearch(key); //x는 delete 할 node

        //if there is key in tree
        if(!key.equals(x.key))
            return;

        if(x == root || isTwoNode(x)){
            //root 이면서 Leaf 인 경우 => 노드가 루트 하나밖에 없는 tree.
            if(isLeaf(x)) {
                root = null;
            }
            //Root 의 자식이 한쪽에만 있는 경우.
            else if(!isTwoNode(x)){
                root = (x.right == null) ? x.left : x.right;
                root.parent = null;
            }
            else{ // 자식이 둘인 노드
                y = min(x.right); // inorder successor => 삭제할 노드보다 큰 노드들 중에 가장 작은 노드
                x.key = y.key;
                x.value = y.value;
                p = y.parent;
                relink(p,y.right,y == p.left);
                rebalancedDelete(p,y);
            }
        }
        else{
            p = x.parent;
            if(x.right == null)
                relink(p,x.left,x == p.left);
            else if(x.left == null)
                relink(p,x.right, x == p.left);
            rebalancedDelete(p,x);
        }
    }

    public K min(){
        if(root == null) return null;
        Node<K,V> x = root;
        while (x.left != null){
            x = x.left;
        }
        return x.key;
    }
    public K max(){
        if(root == null) return null;
        Node<K,V> x = root;
        while (x.right != null){
            x = x.right;
        }
        return x.key;
    }

    //key보다 작거나 같은 key들 중에서 가장 큰 key return
    public K floor(K key){
        if(root == null || key == null) return null;
        Node<K,V> x = floor(root,key);
        if( x == null) return null;
        else            return x.key;
    }
    private Node<K,V> floor(Node<K,V> x, K key){
        if( x == null)  return null;
        int cmp = key.compareTo(x.key);
        if( cmp == 0) return x;
        if(cmp < 0) return floor(x.left,key);
        Node<K,V> t = floor(x.right,key);
        if( t != null)  return t;
        else            return x;

    }

    public int rank(K key) {
        if (root == null || key == null) return 0;
        Node<K, V> x = root;
        int num = 0;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) { // key보다 작은 노드 발견!
                num += 1 + size(x.left);
                x = x.right;
            }
            else {
                num += size(x.left);
                break;
            }
        }
        return num;
    }
    private int size(Node<K,V> x) {return (x!=null) ? x.N : 0;}

    public K select(int rank){
        if(root == null || rank < 0 || rank >= size())
            return null;
        Node<K,V> x = root;
        while(true){
            int t = size(x.left);
            if( rank < t )
                x = x.left;
            else if(rank > t){
                rank = rank - t - 1; // 찾으려는 노드가 노드 x(처음에는 root)보다 오른쪽이면  x의 왼쪽 노드들 및 x는 rank에서 제외
               x = x.right;
            }
            else{
                return x.key;
            }
        }
    }
}
