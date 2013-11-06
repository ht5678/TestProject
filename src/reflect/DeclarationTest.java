package reflect;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * 泛型练习---取得一个泛型类的具体类型
 * 例：Vector<Date>中取得Date
 * @author byht
 *
 */
public class DeclarationTest {

	
	public static void main(String[] args) throws SecurityException, NoSuchMethodException {
		Method applyMethod = DeclarationTest.class.getMethod("applyList", List.class);
		Type[] types = applyMethod.getGenericParameterTypes();
		ParameterizedType pType = (ParameterizedType) types[0];
		System.out.println(pType.getRawType());
		System.out.println(pType.getActualTypeArguments()[0]);
	}
	
	public static void applyList(List<Date> dates){
		
	}
	
}
