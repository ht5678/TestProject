package hibernate.validator;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 测试hibernate_validator
 * @author byht
 *
 */
public class TestHibernateValidator {

	public static void main(String[] args) {
		
		Car car = new Car( null, "A", 1, 400.123456, BigDecimal.valueOf( 200000 ) );
		
		ValidatorFactory validatorFactory = Validation.byDefaultProvider()
        .configure()
        .messageInterpolator( new MyMessageInterpolator() )
        .buildValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		
//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//		Validator validator = factory.getValidator();

		Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car);
		System.out.println(constraintViolations.size());
		
		System.out.println("==================hll的分割线=================");
		
		String message = "";

		message = validator.validateProperty( car, "licensePlate" )
		        .iterator()
		        .next()
		        .getMessage();
		assertEquals(
		        "The license plate must be between 2 and 14 characters long",
		        message
		);

		message = validator.validateProperty( car, "seatCount" ).iterator().next().getMessage();
		assertEquals( "There must be at least 2 seats", message );

		message = validator.validateProperty( car, "topSpeed" ).iterator().next().getMessage();
		assertEquals( "The top speed 400.12 is higher than 350", message );

		message = validator.validateProperty( car, "price" ).iterator().next().getMessage();
		assertEquals( "Price must not be higher than $100000", message );
		
		//这个有错误是因为null
		message = validator.validateProperty( car, "manufacturer" )
        .iterator()
        .next()
        .getMessage();
		assertEquals( "may not be null", message );
	}

	/**
	 * 打印信息
	 * @param string
	 * @param message
	 */
	private static void assertEquals(String string, String message) {
		// TODO Auto-generated method stub
		System.out.println(string.equals(message));
	}
}
