package de.bbq.java.tasks.school;

public interface IStudent {
	String toString();
	
	CourseDF getCourse();
	void setCourse(CourseDF course);
	
	boolean hasCourse();
	boolean hasCourse(CourseDF course);
}
