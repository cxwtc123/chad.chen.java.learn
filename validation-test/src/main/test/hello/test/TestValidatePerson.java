package hello.test;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;

import hello.Person;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestValidatePerson {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		System.out.println("TestValidatePerson.test()");

		Person person = new Person();
		person.setId("1239012345678");
		person.setAge(201);
		person.setCallNo("13888888888");
		person.setName("Chad Chen");
		person.setAddr("somewhere");
		person.setEmail("chadabc.com");
		person.setSex("madle");
		
		System.out.println("new Person");

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		System.out.println("create Validator");

		Set<ConstraintViolation<Person>> constraintViolations = validator
				.validate(person);
		
		System.out.println("validate done, results:");

		for (ConstraintViolation<Person> constraintViolation : constraintViolations) {
			System.err.println("property [" + constraintViolation.getPropertyPath() + "] "
					+ constraintViolation.getMessage()
					+ ", value=" + constraintViolation.getInvalidValue());

		}
		
		System.out.println("TestValidatePerson.test() end");
	}
	
	@Test
	public void testMethod() throws NoSuchMethodException, SecurityException {
		
		System.out.println("TestValidatePerson.testMethod()");
		
		Person person = new Person();
		System.out.println("new Person");

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		ExecutableValidator executableValidator = factory.getValidator().forExecutables();
		
		System.out.println("create Validator");
		
		Set<ConstraintViolation<Person>> validateParameters = executableValidator.validateParameters(person, 
				person.getClass().getMethod("setAge", Integer.class),
				new Object[] { null });
		
		System.out.println("validate done, results:");
		
		for (ConstraintViolation<Person> constraintViolation : validateParameters) {
			System.out.println(constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage());
		}
		 
	}

}

