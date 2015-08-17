package collection;

import java.util.List;


/**
 * 
 * 类MyListTest.java的实现描述：TODO 类实现描述 
 * @author yuezhihua 2015年8月17日 下午3:40:56
 */
public class MyListTest {
    
    
    public static void main(String[] args) {
        //测试添加元素
        MyList<String> list = add();
        
        //根据索引获取数据
        get(list, 2);
        
        //测试删除元素
        testRemoveByIndex(list, 2);
        
        /**
         * 通过value删除
         */
        testRemoveByValue(list, "zhangsan");
     
        //打印信息
        toString(list);
    }
    
    
    /**
     * 根据索引获取
     * @param list
     * @param index
     */
    public static void get(MyList<String> list,int index){
        String str = list.get(index);
        System.out.println(str);
    }
    
    
    /**
     * 删除元素
     * @param index
     */
    public static void testRemoveByValue(MyList<String> list,Object value){
        list.remove(value);
    }
    
    
    /**
     * 删除元素
     * @param index
     */
    public static void testRemoveByIndex(MyList<String> list,int index){
        list.remove(index);
    }
    
    
    /**
     * 打印集合信息
     * @param list
     */
    public static void toString(MyList<String> list){
      //集合
      System.out.println(list);
    }
    
    
    /**
     * 获取集合的size
     * @param list
     */
    public static void getSize(MyList<String> list){
      //集合的size
      System.out.println(list.size());
    }
    
    
    /**
     * 添加元素
     */
    public static MyList<String> add(){
        MyList<String> list = new MyList<String>(2);
        list.add("zhangsan");
        list.add("wangwu");
        list.add("zhaoliu");
        list.add("hahaha");
        list.add("wugui");
        
        //集合
        System.out.println(list);
        return list;
    }
    

}
