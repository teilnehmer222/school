package de.bbq.java.tasks.school;

/**
 * @author Thorsten2201
 *
 */
public interface IStudent extends IDaoSchool {
	String toString();

	ICourse getCourse();

	void setCourse(ICourse course);

	boolean hasCourse();

	boolean hasCourse(ICourse course);
}
