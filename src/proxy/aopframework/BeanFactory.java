package proxy.aopframework;

import java.util.Properties;

/**
 * 防spring的aop模块
 * @author byht
 *
 */
public class BeanFactory {

	private static Properties props;
	
	static{
		try{
			props = new Properties();
			props.load(BeanFactory.class.getResourceAsStream("beans.properties"));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public Object getBean(String name){
		String className = props.getProperty(name);
		Object proxy = null;
		try {
			Object bean = Class.forName(className).newInstance();
			if(bean instanceof ProxyBeanFactory){
				ProxyBeanFactory proxyBeanFactory = (ProxyBeanFactory)bean;
				Object target = Class.forName(props.getProperty(name+".target")).newInstance();
				Advice advice = (Advice)Class.forName(props.getProperty(name+".advice")).newInstance();
				proxyBeanFactory.setAdvice(advice);
				proxyBeanFactory.setTarget(target);
				proxy = proxyBeanFactory.getProxy();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return proxy;
	}
	
	
}
