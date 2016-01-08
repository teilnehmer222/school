package de.bbq.java.tasks.school;

import java.util.ArrayList;

/**
 * @author teilnehmer222
 *
 */
public class Student extends SchoolPersonAbstract implements IStudent {
	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	// private transient ICourse course;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	private Student(String firstName, EDaoSchool eDataAccess) throws Exception {
		super(eDataAccess);
		super.setFirstName(firstName);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Static
	private static final long serialVersionUID = -8838146635169751075L;
	private static ArrayList<IStudent> allStudents = new ArrayList<>();

	public static boolean load(Student student) {
		student.id = SchoolItemAbstract.getNewId();
		allStudents.add(student);
		return true;
	}

	private static String generateNewName() {
		String[] array = new String[] { "Depp", "Trottel", "Idiot", "Armleuchter", "Hirni", "Totalversager",
				"Baumschulabbrecher", "Volldepp", "Volltrottel", "Extremdepp", "Superidiot", "Dummbeutel",
				"Trottelkopf", "Hirngesicht", "Bauer", "Viehbauer", "Behindikind" };
		int randomNum = 0 + (int) (Math.random() * array.length);
		return array[randomNum];
	}

	public static Student createStudent(String firstName, EDaoSchool eDataAccess) {
		Student student = null;
		try {
			student = new Student(firstName, eDataAccess);
			allStudents.add(student);
		} catch (Exception e) {
			Kursverwaltung.showException(e);
		}
		return student;
	}

	public static Student createStudent(boolean random, EDaoSchool eDataAccess) {
		String newName = Student.generateNewName();
		Student newStudent = null;
		if (!random) {
			newName = Kursverwaltung.showInput("Bitte einen Namen eingeben:");
		}
		newStudent = Student.createStudent(newName, eDataAccess);
		return newStudent;
	}

	public static ArrayList<IStudent> getStudents() {
		return allStudents;
	}

	public static void studentDeleted(IStudent student) {
		if (allStudents.contains(student)) {
			allStudents.remove(student);
			Kursverwaltung.deleteElement((SchoolItemAbstract) student);
		}
	}

	public static void reset() {
		allStudents = new ArrayList<>();
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Getter / Setter IStudent
	@Override
	public ICourse getCourse() {
		for (ICourse c : Course.getCourses()) {
			if (c.hasStudent(this)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public String getDescription() {
		StringBuffer bu = new StringBuffer();
		bu.append(this.getFirstName() + " " + getLastName() + "\n");
		if (this.getBirthDate() != null) {
			bu.append(Kursverwaltung.getGermanDate().format(this.getBirthDate()) + "\n");
		}
		bu.append(this.getAdress().getDescription());
		return bu.toString();
	}
	// @Override
	// public void setCourse(ICourse course) {
	// this.course = course;
	// }
	//
	// @Override
	// public void removeCourse() {
	// this.course = null;
	// }

	@Override
	public boolean hasCourse(ICourse course) {
		if (course != null) {
			return course.equals(this.getCourse());
		} else {
			return false;
		}
	}

	@Override
	public boolean hasCourse() {
		return this.getCourse() != null;
	}
	/////////////////////////////////////////////////////////////////////////////////////
}
