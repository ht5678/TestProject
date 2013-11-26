package webservice.server;

import javax.jws.WebService;

@WebService(endpointInterface="webservice.server.MyService")
public class MyServiceImpl implements MyService {

	@Override
	public int add(int a, int b) {
		System.out.println(a+"+"+b+"="+(a+b));
		return a+b;
	}

}
