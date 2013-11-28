package reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import org.junit.Test;


/**
 * 强引用和弱引用练习
 * @author yuezhihua
 *
 */
public class ReferenceTest {
	
	public static boolean isRun = true;
	
	/**
	 * 虚引用练习：虚顾名思义就是没有的意思，建立虚引用之后通过get方法返回结果始终为null,
	 * 通过源代码你会发现,虚引用通向会把引用的对象写进referent,只是get方法返回结果为null.先看一下和gc交互的过程在说一下他的作用.
	 *	1 不把referent设置为null, 直接把heap中的new String("abc")对象设置为可结束的(finalizable).
	 *	2 与软引用和弱引用不同, 先把PhantomRefrence对象添加到它的ReferenceQueue中.然后在释放虚可及的对象. 
	 *	你会发现在收集heap中的new String("abc")对象之前,你就可以做一些其他的事情.通过以下代码可以了解他的作用.
	 */
	@Test
	public void testPhantomReference(){
		String abc = new String("abc");
		System.out.println(abc.getClass()+"@"+abc.hashCode());
		final ReferenceQueue<String> referenceQueue = new ReferenceQueue<String>();
		new Thread(){
			public void run(){
				while(isRun){
					Object o = referenceQueue.poll();
					if(o!=null){
						try{
							Field ref = Reference.class.getDeclaredField("referent");
							ref.setAccessible(true);
							Object result = ref.get(o);
							System.out.println("gc will collect : "+result.getClass()+"@"+result.hashCode());
							
						}catch(Exception e){
							System.out.println(e.getMessage());
						}
					}
				}
			}
		}.start();
		
		
		try {
			PhantomReference<String> phantomReference = new PhantomReference<String>(abc, referenceQueue);
			abc = null;
			Thread.currentThread().sleep(3000);
			System.gc();
			Thread.currentThread().sleep(3000);
			isRun = false;
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * 弱引用练习：
	 */
	@Test
	public void testWeakReference(){
		String abc = new String("abc");
		WeakReference<String> weakReference = new WeakReference<String>(abc);
		abc = null;
		System.out.println("before gc :"+weakReference.get());
		System.gc();
		System.out.println("after gc:"+weakReference.get());
	}
	
	
	/**
	 * 软引用练习
	 */
	@Test
	public void testSoftReference(){
		User user = new User(1101, "zhangsan", "zhangsan");
		SoftReference<User> softReference = new SoftReference<User>(user);
		//引用时
		if(softReference!=null){
			user = softReference.get();
		}else{
			user =  new User(1101, "zhangsan", "zhangsan");
			softReference = new SoftReference<User>(user);
		}
	}
	
	
	/**
	 * 第一个练习
	 */
	@Test
	public void test01(){
		//1.强引用
		String abc = new String("abc");
		//2.软引用
		SoftReference<String> softReference = new SoftReference<String>(abc);
		//3.弱引用
		WeakReference<String> weakReference = new WeakReference<String>(abc);
		//4.虚引用
		abc = null;
		//5.
		softReference.clear();
		
		
		
	}
	
}
