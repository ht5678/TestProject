package proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 代理练习
 * @author byht
 *
 */
public class ProxyTest {

	
	public static void main(String[] args) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		//这个方法有两个参数，第一个参数是需要代理类的类加载器，另一个是需要代理类接口数组
		Class clazzProxy0 = Proxy.getProxyClass(Collection.class.getClassLoader(), Collection.class);
		System.out.println(clazzProxy0);
		Constructor[] constructors = clazzProxy0.getConstructors();
		for(Constructor constructor : constructors){
			System.out.print(constructor.getName());
			System.out.print("(");
			for(Class clazz : constructor.getParameterTypes()){
				System.out.print(clazz.getName());
			}
			System.out.print(")");
			System.out.println();
		}
		
		
		//方法一：实例化一个代理练习
		System.out.println("=================第一个代理练习=============");
		InvocationHandler handler0 = new MyInvocationHandler();
		Class clazzProxy1 = Proxy.getProxyClass(Foo.class.getClassLoader(), new Class[]{Foo.class});
		Foo f0 = (Foo) clazzProxy1.getConstructor(new Class[]{InvocationHandler.class})
			.newInstance(new Object[]{handler0});
		System.out.println(f0);
		
		//方法二：实例化一个代理练习
		System.out.println("=================第二个代理练习=============");
		Class clazzProxy2 = Proxy.getProxyClass(Foo.class.getClassLoader(), new Class[]{Foo.class});
		Foo f1 = (Foo) clazzProxy1.getConstructor(new Class[]{InvocationHandler.class})
			.newInstance(new Object[]{new InvocationHandler() {
				
				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
						throws Throwable {
					// TODO Auto-generated method stub
					return null;
				}
			}});
		System.out.println(f1);
		
		//方法三：实例化一个代理练习
		System.out.println("=================第三个代理练习=============");
		Class clazzProxy3 = Proxy.getProxyClass(Foo.class.getClassLoader(), new Class[]{Foo.class});
		Foo f2 = (Foo) Proxy.newProxyInstance(Foo.class.getClassLoader(), new Class[]{Foo.class}, new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				// TODO Auto-generated method stub
				Long start = System.currentTimeMillis();
				List<String> strList = new ArrayList<String>();
				System.out.println(System.currentTimeMillis()-start);
				return null;
			}
		});
		System.out.println(f2);
		
		//用代理来实例化一个实现类
		System.out.println("-=========练习查看invocationhandler的内部原理");
		Class clazzProxy4 = Proxy.getProxyClass(Collection.class.getClassLoader(), new Class[]{Collection.class});
		Constructor constructor = clazzProxy4.getConstructor(InvocationHandler.class);
		Collection collection = (Collection) constructor.newInstance(new InvocationHandler() {
			//TODO;代码放到这行，collection.size()才会正常显示
			List<String> target = new ArrayList<String>();
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				//TODO;如果把代码移到这行，就会发现collection.size()为0
//				List<String> target = new ArrayList<String>();
				//执行方法
				Object retVal = method.invoke(target, args);
				return retVal;
			}
		});
		collection.add("zhangsan");
		collection.add("lisi");
		collection.add("wangwu");
		System.out.println(collection);
		System.out.println(collection.size());
	}
	
	
	//自定义代理实现类
	static class MyInvocationHandler implements InvocationHandler{

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
