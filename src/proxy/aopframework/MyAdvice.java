package proxy.aopframework;

public class MyAdvice implements Advice{

	private long start;
	
	@Override
	public void beforeMethod() {
		// TODO Auto-generated method stub
		start = System.currentTimeMillis();
		System.out.println("======start");
	}

	@Override
	public void afterMethod() {
		// TODO Auto-generated method stub
		System.out.println("======"+(System.currentTimeMillis()-start));
	}

	
}
