package webservice.server;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * 
 * @author byht
 *
 */
@WebService
public interface MyService {

	@WebResult(name="addResult")
	public int add(@WebParam(name="a")int a , @WebParam(name="b")int b);
	
}
