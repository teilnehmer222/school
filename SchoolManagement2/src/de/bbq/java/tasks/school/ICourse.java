package de.bbq.java.tasks.school;

import java.util.ArrayList;

/**
 * @author Thorsten2201
 *
 */
public interface ICourse extends IDaoSchool {
	boolean hasStudents();

	String toString();

	ITeacher getTeacher();

	void setTeacher(ITeacher t);

	void removeTeacher();

	boolean hasTeacher();

	ArrayList<IStudent> getStudents();

	void addStudent(IStudent student);

	void removeStudent(IStudent student);
}
