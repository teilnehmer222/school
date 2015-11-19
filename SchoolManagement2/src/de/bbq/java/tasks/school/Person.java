package de.bbq.java.tasks.school;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6025725912701734860L;

	private String firstName;
	private String lastName;
	private Adress adress;
	private Date birthDate;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

}
