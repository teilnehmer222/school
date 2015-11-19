package de.bbq.java.tasks.school;

import java.util.ArrayList;
import java.util.List;

public class StudentDF extends SchoolMember implements IStudent, DaoSchoolInterface {
	private static final long serialVersionUID = -8838146635169751075L;

	private static ArrayList<StudentDF> students = new ArrayList<>();
	private CourseDF course;
	private static DaoSchoolAbstract dataAccessObject;

	private StudentDF(String firstName) {
		super();

		super.setFirstName(firstName);
	}

	public static StudentDF createStudent(String firstName, DaoSchoolAbstract store) {
		StudentDF student = new StudentDF(firstName);
		if (dataAccessObject == null) {
			dataAccessObject = store;
		} else if (!dataAccessObject.getClass().equals(store.getClass())) {
			dataAccessObject = store;
		}
		students.add(student);
		return student;
	}

	// public static boolean hasCourse(long studentId){
	// boolean hasCourse = false;
	// StudentDF student = findStudentById(studentId);
	// if(student.getMyCourseId()!=-1)
	// hasCourse = true;
	// return hasCourse;
	// }

	public boolean hasCourse() {
		return this.course != null;
	}

	public boolean hasCourse(CourseDF course) {
		if (course != null) {
			return course.equals(this.course);
		} else {
			return false;
		}
	}

	public static ArrayList<StudentDF> getStudents() {
		return students;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getFirstName() + " " + this.getLastName();
	}

	// public long getMyCourseId() {
	// return myCourseId;
	// }
	//
	// public void setMyCourseId(long myCourseId) {
	// this.myCourseId = myCourseId;
	// }

	public CourseDF getCourse() {
		return course;
	}

	public void setCourse(CourseDF course) {
		this.course = course;
	}

	// public static void addStudentToList(StudentDF student) {
	// students.add(student);
	// }

	// public static StudentDF findStudentById(long studentId) {
	// StudentDF foundStudent = null;
	// for (StudentDF student : students) {
	// if (student.getSchoolMemberId() == studentId) {
	// foundStudent = student;
	// break;
	// }
	// }
	// return foundStudent;
	// }

	public static String generateNewName() {
		String[] array = new String[] { "Depp", "Trottel", "Idiot", "Armleuchter", "Hirni", "Totalversager",
				"Baumschulabbrecher", "Volltrottel", "Extremdepp", "Superidiot" };
		int randomNum = 0 + (int) (Math.random() * array.length);
		return array[randomNum];
	}

	@Override
	public boolean saveElement() {
		this.setSaved(true);
		return this.dataAccessObject.saveElement(this);
	}

	@Override
	public boolean loadElement() {
		this.setSaved(false);
		return this.dataAccessObject.loadElement(this);
	}

	@Override
	public boolean saveAll() {
		return this.dataAccessObject.saveAll();
	}

	@Override
	public boolean loadAll() {

		return this.dataAccessObject.loadAll();
	}

	@Override
	public boolean deleteElement() {
		students.remove(this);
		return this.dataAccessObject.deleteElement(this);
	}

	private transient boolean saved = false;

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}
}
