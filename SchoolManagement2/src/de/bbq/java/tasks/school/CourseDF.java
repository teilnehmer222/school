package de.bbq.java.tasks.school;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author teilnehmer
 *
 */
public class CourseDF implements Serializable {
	private static final long serialVersionUID = -4457277057001458163L;
	static long highestCourseId = 1000;
	
	private static List<CourseDF> courses = new ArrayList<>();

	final static String[] names = new String[] { "Schlafuntericht", "Nichtstun 2.0", "Däumchendrehen",
			"Aus dem Fenster schauen", "Stinken für Dummies", "Wie saue ich das Klo voll", "Waschbecken verstopfen II",
			"Feueralarm drücken", "Aufzug blockieren", "Türen verkeilen", "Kernschmelze leichtgemacht", "Deppenradar" };


	// private long myTeacherId = -1;
	private TeacherDF teacher;
	// private List<Long> studentIds = new ArrayList<>();
	private List<StudentDF> students = new ArrayList<>();
	
	//Serializable Properties
	private long id;
	private String courseName;

	private static long createNewID() {
		return CourseDF.highestCourseId++;
	}

	private CourseDF(String courseName) {
		this.id = CourseDF.createNewID();
		this.courseName = courseName;
		courses.add(this);
	}

	public static CourseDF createCourse(String courseName) {
		return new CourseDF(courseName);
	};

	// public List<Long> getStudentIds() {
	// return studentIds;
	// }
	public List<StudentDF> getStudents() {
		return students;
	}

	@Override
	public String toString() {
		return courseName;

	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public long NURZUMSPEICHERN() {
		return id;
	}

	// private long getMyTeacherId() {
	// return myTeacherId;
	// }

	public void setTeacher(TeacherDF t) {
		this.teacher = t;
		// unnecercarry:
		// myTeacherId = t.getId();
	}

	public TeacherDF getTeacher() {
		return this.teacher;
	}

	// private void setMyTeacherId(long myTeacherId) {
	// this.myTeacherId = myTeacherId;
	// }

	// public void addStudent(Long studentId) {
	// studentIds.add(studentId);
	// }

	public void addStudent(StudentDF student) {
		students.add(student);
	}

	public void removeStudent(StudentDF student) {
		if (students.contains(student)) {
			students.remove(student);
		}
	}
	// public void removeStudent(long id) {
	// for (long studentEntry : studentIds) {
	// if (studentEntry == id) {
	// studentIds.remove(id);
	// }
	// }
	// }

	/**
	 * static methods
	 */
	public static CourseDF findCourseById(long courseId) {
		CourseDF foundCourse = null;
		for (CourseDF course : courses) {
			if (course.id == courseId) {
				foundCourse = course;
				break;
			}
		}
		return foundCourse;
	}

	public boolean hasTeacher() {
		// boolean hasTeaacher = false;
		// CourseDF course = findCourseById(this.id);
		// if (course.getMyTeacherId() != -1)
		// hasTeaacher = true;
		return (this.getTeacher() != null); // this.getMyTeacherId() != -1;
	}

	// private static boolean hasTeacher(long courseId) {
	//
	// }

	public static List<CourseDF> getCourses() {
		return courses;
	}

	public void delete() {
		// TODO: delete from data-source
		courses.remove(this);

	}
	// I do not want to HAVE TO CALL YOUR LOGIC in my code!!!!!!->indirect
	// public static void removeCourse(CourseDF course) {
	// courses.remove(course);
	// }

	// I do not want to HAVE TO CALL YOUR LOGIC in my code!!!!!!->indirect
	// construction
	// public static void addCourseToList(CourseDF course) {
	// courses.add(course);
	// }

	public static String generateNewName() {
		int randomNum = 0 + (int) (Math.random() * names.length);
		return names[randomNum];
	}
}
