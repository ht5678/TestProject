package proxy.aopframework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理bean工厂
 * @author byht
 *
 */
public class ProxyBeanFactory {

	private static Object target = null;
	
	private static Advice advice = null;
	
	/**
	 * 获取代理
	 * @param target
	 * @param advice
	 * @return
	 */
	public static Object getProxy(){
		Class<?> proxyClazz = Proxy.getProxyClass(target.getClass().getClassLoader(), target.getClass().getInterfaces());
		Object proxyObj = null;
		try{
			proxyObj = proxyClazz.getConstructor(InvocationHandler.class).newInstance(new InvocationHandler() {
				
				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
						throws Throwable {
					advice.beforeMethod();
					Object retVal = method.invoke(target, args);
					advice.afterMethod();
					return retVal;
				}
			});
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return proxyObj;
	}

	public static Object getTarget() {
		return target;
	}

	public static void setTarget(Object target) {
		ProxyBeanFactory.target = target;
	}

	public static Advice getAdvice() {
		return advice;
	}

	public static void setAdvice(Advice advice) {
		ProxyBeanFactory.advice = advice;
	}
	
}
