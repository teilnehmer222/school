package de.bbq.java.tasks.school;

public class SchoolMember extends Person {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1582579411217913540L;
	private long id;

	public SchoolMember() {
		super();
		id = School.highestMemberId++;

	}

	public long getId() {
		return id;
	}

}
