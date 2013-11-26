package proxy.aopframework;

public class AopFrameworkTest {

	public static void main(String[] args) {
		BeanFactory factory = new BeanFactory();
		System.out.println(factory.getBean("xxx"));
	}
}
