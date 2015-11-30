package de.bbq.java.tasks.school;

/**
 * @author Thorsten2201
 *
 */
public interface ITeacher { // extends IDaoSchool {
	String toString();

	void addCourse(ICourse course) throws Exception;

	void removeCourse(ICourse course);
	
	int getCoursesCount();
	
	String getDescription();
}
