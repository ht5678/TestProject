package collection;

import java.util.Arrays;


/**
 * 
 * 类MyList.java的实现描述：arraylist原理
 * @author yuezhihua 2015年8月17日 下午3:24:55
 */
public class MyList<E> {
    
    Object[] elementData ;
    
    int size;
    
    
    /**
     * 手动设置集合size
     * @param size
     */
    public MyList(int capacity){
        elementData = new Object[capacity];
    }
    
    
    /**
     * 默认为10
     */
    public MyList(){
        elementData = new Object[10];
    }
    
    
    
    /**
     * 添加元素
     * @param value
     */
    public boolean add(E value){
        //判断是否扩容
        if((size+1)>elementData.length){
            int newCapacity = elementData.length+(elementData.length>>1); // size + size/2
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
        //添加元素
        elementData[size++] = value;
        return true;
    }
    
    
    /**
     * 根据索引获取元素
     * @param index
     * @return
     */
    public E get(int index){
        return elementData(index);
    }
    
    E elementData(int index) {
        return (E) elementData[index];
    }
    
    
    /**
     * 移除
     * @param index
     * @return
     */
    public E remove(int index) {
        E oldValue = elementData(index);

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // clear to let GC do its work

        return oldValue;
    }
    
    
    
    /**
     * 移除元素
     * @param value
     */
    public boolean remove(Object o){
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }
    
    
    /*
     * Private remove method that skips bounds checking and does not
     * return the value removed.
     */
    private void fastRemove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // clear to let GC do its work
    }

    
    /**
     * list大小
     * @return
     */
    public int size(){
        return size;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(elementData.length);
        sb.append("\n");
        for(int i = 0 ; i < size ;i++){
            sb.append(i).append(":").append(elementData[i]).append("\n");
        }
        return sb.toString();
    }

    
    
}
