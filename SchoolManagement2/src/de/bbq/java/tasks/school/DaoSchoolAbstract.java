package de.bbq.java.tasks.school;

import java.util.ArrayList;

public abstract class DaoSchoolAbstract {
	public abstract boolean saveElement(CourseDF course);

	public abstract boolean saveElement(TeacherDF teacher);

	public abstract boolean saveElement(StudentDF student);

	public abstract boolean loadElement(CourseDF course);

	public abstract boolean loadElement(TeacherDF teacher);

	public abstract boolean loadElement(StudentDF student);

	public abstract boolean deleteElement(CourseDF course);

	public abstract boolean deleteElement(TeacherDF teacher);

	public abstract boolean deleteElement(StudentDF student);

	public static boolean saveAll() {
		for (CourseDF c : CourseDF.getCourses()) {
			c.saveElement();
			c.getTeacher().saveElement();
			for (StudentDF s : c.getStudents()) {
				s.saveElement();
			}

		}
		for (TeacherDF t : TeacherDF.getTeachers()) {
			if (!t.isSaved()) {
				t.saveElement();
			}
		}
		for (StudentDF s : StudentDF.getStudents()) {
			if (!s.isSaved()) {
				s.saveElement();
			}
		}
		return true;
	}

	public static boolean loadAll() {
		ArrayList<CourseDF> MOCK = new ArrayList<>();
		for (Object element  : MOCK) {
			((CourseDF) element).loadElement();
		}
		return false;
	}
}