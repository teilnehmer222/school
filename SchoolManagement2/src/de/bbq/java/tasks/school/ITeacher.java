package de.bbq.java.tasks.school;

public interface ITeacher {
	String toString();

	void addCourse(CourseDF course) throws Exception;

	void removeCourse(CourseDF course);
}
