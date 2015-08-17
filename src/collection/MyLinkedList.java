package collection;



/**
 * 
 * 类MyLinkedList.java的实现描述：linkedlist原理实现
 * @author yuezhihua 2015年8月17日 下午4:31:31
 */
public class MyLinkedList<E> {

    
    Node<E> first;
    
    Node<E> last;
    
    int size;
    
    
    public MyLinkedList(){
        
    }
    
    
    
    /**
     * 添加元素
     * @param e
     * @return
     */
    public boolean add(E e){
        final Node<E> l = last;
        final Node<E> node = new Node<>(l, null, e);
        
        return true;
    }
    
    
    
    class Node<E>{
        Node<E> prev;
        Node<E> next;
        Node<E> item;
        
        
        public Node(Node<E> prev, Node<E> next, Node<E> item) {
            super();
            this.prev = prev;
            this.next = next;
            this.item = item;
        }
        
    }
    
    
}
