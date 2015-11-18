package de.bbq.java.tasks.school;

import java.io.Serializable;

public class Adress implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2223301940441781708L;
	private String streetName;
	private int houseNumber;
	private int zipCode;
	private String City;
	private String Country;
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public int getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(int houseNumber) {
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
	
}
