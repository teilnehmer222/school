package de.bbq.java.tasks.school;

import java.io.Serializable;

/**
 * @author Thorsten2201
 *
 */
public class Address extends SchoolItemAbstract implements Serializable {

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	protected Address(DaoSchoolAbstract dataAccessObject) {
		super(dataAccessObject);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Static
	private static final long serialVersionUID = -2223301940441781708L;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Properties to serialize
	private String streetName;
	private String houseNumber;
	private int zipCode;
	private String City;
	private String Country;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Getter / Setter
	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// SchoolItemAbstract
	@Override
	public boolean saveElement() {
		return super.saveElement();
	}

	@Override
	public boolean loadElement() {
		return super.loadElement();
	}

	@Override
	public boolean deleteElement() {
		return super.deleteElement();
	}

	@Override
	public boolean saveAll() {
		return false;
	}

	@Override
	public boolean loadAll() {
		return false;
	}
	/////////////////////////////////////////////////////////////////////////////////////

}
