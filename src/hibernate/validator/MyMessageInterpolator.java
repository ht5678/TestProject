package hibernate.validator;

import java.util.Locale;

import javax.validation.MessageInterpolator;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;

/**
 * 自定义消息拦截者
 * @author byht
 *
 */
public class MyMessageInterpolator implements MessageInterpolator {
	
	private MessageInterpolator delegate;
	
	public MyMessageInterpolator(){
		this.delegate = new ResourceBundleMessageInterpolator();
	}

	@Override
	public String interpolate(String result, Context context) {
		// TODO Auto-generated method stub
		result = delegate.interpolate(result, context);
		System.out.println("result:"+result);
		System.out.println("context:"+context);
		return result;
	}

	@Override
	public String interpolate(String arg0, Context arg1, Locale arg2) {
		// TODO Auto-generated method stub
		System.out.println(arg0);
		System.out.println(arg1);
		System.out.println(arg2);
		return null;
	}

}
