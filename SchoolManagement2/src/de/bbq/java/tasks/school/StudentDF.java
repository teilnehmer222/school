package de.bbq.java.tasks.school;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * @author teilnehmer222
 *
 */
public class StudentDF extends SchoolPersonAbstract implements IStudent {
	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	private transient ICourse course;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	private StudentDF(String firstName, DaoSchoolAbstract dataAccessObject) {
		super(dataAccessObject);
		super.setFirstName(firstName);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Static
	private static final long serialVersionUID = -8838146635169751075L;
	private static ArrayList<IStudent> allStudents = new ArrayList<>();

	private static String generateNewName() {
		String[] array = new String[] { "Depp", "Trottel", "Idiot", "Armleuchter", "Hirni", "Totalversager",
				"Baumschulabbrecher", "Volltrottel", "Extremdepp", "Superidiot", "Dummbeutel", "Trottelkopf",
				"Hirngesicht" };
		int randomNum = 0 + (int) (Math.random() * array.length);
		return array[randomNum];
	}

	private static StudentDF createStudent(String firstName, DaoSchoolAbstract dataAccessObject) {
		StudentDF student = new StudentDF(firstName, dataAccessObject);
		allStudents.add(student);
		return student;
	}

	public static StudentDF createStudent(boolean random, DaoSchoolAbstract dataAccessObject) {
		String newName = StudentDF.generateNewName();
		StudentDF newStudent = null;
		if (!random) {
			newName = JOptionPane.showInputDialog("Bitte einen Namen eingeben:");
		}
		newStudent = StudentDF.createStudent(newName, dataAccessObject);
		return newStudent;
	}

	public static ArrayList<IStudent> getStudents() {
		return allStudents;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Getter / Setter IStudent
	@Override
	public ICourse getCourse() {
		return course;
	}

	@Override
	public void setCourse(ICourse course) {
		this.course = course;
	}

	@Override
	public boolean hasCourse(ICourse course) {
		if (course != null) {
			return course.equals(this.course);
		} else {
			return false;
		}
	}

	@Override
	public boolean hasCourse() {
		return this.course != null;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// IDaoSchoolAbstract
	@Override
	public boolean saveElement() {
		return super.saveElement();
	}

	@Override
	public boolean loadElement() {
		return super.loadElement();
	}

	@Override
	public boolean deleteElement() {
		boolean ret = super.deleteElement();
		if (ret) {
			StudentDF.allStudents.remove(this);
		}
		return ret;
	}

	@Override
	public boolean saveAll() {
		return super.saveAll();
	}

	@Override
	public boolean loadAll() {
		return super.loadAll();
	}
	/////////////////////////////////////////////////////////////////////////////////////

}
