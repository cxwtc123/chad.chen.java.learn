package hello.test;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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

		Person person = new Person();
		person.setId("1239012345678");
		person.setAge(201);
		person.setCallNo("13888888888");
		person.setName("Chad Chen");
		person.setAddr("somewhere");
		person.setEmail("chadabc.com");
		person.setSex("madle");

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<Person>> constraintViolations = validator
				.validate(person);

		for (ConstraintViolation<Person> constraintViolation : constraintViolations) {
			System.err.println("property [" + constraintViolation.getPropertyPath() + "] "
					+ constraintViolation.getMessage()
					+ ", value=" + constraintViolation.getInvalidValue());

		}

	}

}
