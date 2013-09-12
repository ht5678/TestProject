package dto.annotation;

@MyAnnotion1(value="my annotation 1")
public class AnnotationDemo {

	@MyAnnotion2(description="my annotation 2",isAnnotation=true)
	public void sayHello(){
		System.out.println("hello");
		
	}
}
