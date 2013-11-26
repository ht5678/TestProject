package webservice.server;

import javax.xml.ws.Endpoint;

/**
 * webservice服务器端
 * @author byht
 *
 */
public class Server {

	public static void main(String[] args) {
		String url = "http://localhost:8989/ws";
		Endpoint.publish(url, new MyServiceImpl());
	}
	
}
