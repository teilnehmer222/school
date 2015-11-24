package de.bbq.java.tasks.school;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * @author teilnehmer222
 *
 */
public class Student extends SchoolPersonAbstract implements IStudent {
	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
//	private transient ICourse course;
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

	private static Student createStudent(String firstName, EDaoSchool eDataAccess) {
		Student student = null;
		try {
			student = new Student(firstName, eDataAccess);
			allStudents.add(student);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		return student;
	}

	public static Student createStudent(boolean random, EDaoSchool eDataAccess) {
		String newName = Student.generateNewName();
		Student newStudent = null;
		if (!random) {
			newName = JOptionPane.showInputDialog("Bitte einen Namen eingeben:");
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
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Getter / Setter IStudent
	@Override
	public ICourse getCourse() {
		for (ICourse c : Course.getCourses()) {
			if (c.equals(this)) {
				return c;
			}
		}
		return null;
	}

//	@Override
//	public void setCourse(ICourse course) {
//		this.course = course;
//	}
//
//	@Override
//	public void removeCourse() {
//		this.course = null;
//	}
	
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
