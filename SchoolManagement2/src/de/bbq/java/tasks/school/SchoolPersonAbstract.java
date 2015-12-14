package de.bbq.java.tasks.school;

import java.util.Date;

/**
 * @author Thorsten2201
 *
 */
public abstract class SchoolPersonAbstract extends SchoolItemAbstract {

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	private String firstName;
	private String lastName;
	private Address address = null;
	private Date birthDate;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	public SchoolPersonAbstract(EDaoSchool EDataAccess) {
		super(EDataAccess);
		this.address = new Address(EDataAccess);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Static
	private static final long serialVersionUID = 6025725912701734860L;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Getter / Setter SchoolItem, Overrides IStudent, ITeacher
	@Override
	public final String toString() {
		StringBuffer first = new StringBuffer(), last = new StringBuffer();
		if (this.getFirstName() != null) {
			first.append(this.getFirstName());
		}
		if (this.getLastName() != null) {
			if (first.length() > 0) {
				first.append(" ");
			}
			last.append(this.getLastName());
		}
		return first.append(last.toString()).toString();
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Getter / Setter
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Address getAdress() {
		return this.address;
	}

	public void setAdress(Address address) {
		this.address = address;
	}
	/////////////////////////////////////////////////////////////////////////////////////

}
