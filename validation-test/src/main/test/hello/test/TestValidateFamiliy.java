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
		
		Person person = new Person();
		person.setId("123456789012345678");
		person.setAge(81);
		person.setCallNo("13888888888");
		person.setName("Chad Chen");
		person.setAddr("somewhere");
		person.setEmail("chad@abc.com");
		person.setSex("male");

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Family family = new Family();
		family.setFamilyName("Chad's Family");
		family.getPersons().add(person);

		Set<ConstraintViolation<Family>> constraintViolations = validator
				.validate(family);

		for (ConstraintViolation<Family> constraintViolation : constraintViolations) {
			System.err.println("property [" + constraintViolation.getPropertyPath() + "] "
					+ constraintViolation.getMessage()
					+ ", value=" + constraintViolation.getInvalidValue());

		}
		
	}

}
