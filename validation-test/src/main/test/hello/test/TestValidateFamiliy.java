package hello.test;

import hello.Family;
import hello.Person;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestValidateFamiliy {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		System.out.println("test start");
		Person person = new Person();
		person.setId("123456789012345678");
		person.setAge(81);
		person.setCallNo("13888888888");
		person.setName("Chad Chen");
		person.setAddr("somewhere");
		person.setEmail("chad@abc.com");
		person.setSex("male");
		
		System.out.println("new Person success");

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		System.out.println("create validtor success");
		
		Family family = new Family();
		family.setFamilyName("Chad's Family");
		family.getPersons().add(person);
		
		System.out.println("new Family success");

		Set<ConstraintViolation<Family>> constraintViolations = validator
				.validate(family);
		
		System.out.println("validate done, result:");
		System.out.flush();

		for (ConstraintViolation<Family> constraintViolation : constraintViolations) {
			System.err.println("property [" + constraintViolation.getPropertyPath() + "] "
					+ constraintViolation.getMessage()
					+ ", value=" + constraintViolation.getInvalidValue());
			System.err.flush();
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("test end");
		System.out.flush();
		
	}

}
