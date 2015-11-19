package de.bbq.java.tasks.school;

public class SchoolMember extends Person {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1582579411217913540L;
	static long highestMemberId = 1000;
	
	private long id;
	
	public SchoolMember() {
		super();
		id = SchoolMember.highestMemberId++;

	}

	public long getSchoolMemberId() {
		return id;
	}

}
