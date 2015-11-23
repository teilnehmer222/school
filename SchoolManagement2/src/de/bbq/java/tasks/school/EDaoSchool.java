package de.bbq.java.tasks.school;

/**
 * @author Thorsten2201
 *
 */
public enum EDaoSchool {
	ABSTACT(0), FILE(1), JDBC(2), JDBC_MYSQL(3);

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	int enumId = 0;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	EDaoSchool(int enumId) {
		this.enumId = enumId;
	};
	/////////////////////////////////////////////////////////////////////////////////////

}
