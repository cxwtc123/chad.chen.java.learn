package hello;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class Family {

	@NotNull
	private List<Person> persons;

	{
		{
			persons = new ArrayList<Person>();
		}
	}

	@NotNull
	private String familyName;

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

}
