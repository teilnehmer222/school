package de.bbq.java.tasks.school;

import java.util.ArrayList;

public interface ICourse {
	String toString();
	boolean saved = false;
	
	void addStudent(StudentDF student);

	void removeStudent(StudentDF student);

	void setTeacher(TeacherDF t);

	TeacherDF getTeacher();

	ArrayList<StudentDF> getStudents();

	boolean hasTeacher();

	boolean deleteElement();
}
