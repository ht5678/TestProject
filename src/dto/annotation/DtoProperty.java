package dto.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD})
public @interface DtoProperty {

	public abstract Class<?> entityClass();
	
	public abstract String entityProperty();
	
	public abstract boolean nullable();
	
	public abstract boolean readOnly();
	
	public abstract boolean trim();
	
}
