package de.bbq.java.tasks.school;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * @author teilnehmer222
 *
 */
public class TeacherDF extends SchoolPersonAbstract implements ITeacher {
	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	private transient ArrayList<ICourse> courses = new ArrayList<>();
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	private TeacherDF(String firstName, DaoSchoolAbstract dataAccessObject) {
		super(dataAccessObject);
		super.setFirstName(firstName);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Static
	private static final long serialVersionUID = -3548796163205043453L;
	private static ArrayList<ITeacher> allTeachers = new ArrayList<>();

	private static String generateNewName() {
		String[] array = new String[] { "Leerer Abwesend", "Musikleerer", "Deuschleerer", "Verleerer", "Entlährer",
				"Laubbläser", "Labersack", "Zutexter", "Volllaberer", "Berieseler", "Hintergrundrauschen",
				"Disturbing Noise" };
		int randomNum = 0 + (int) (Math.random() * array.length);
		return array[randomNum];
	}

	private static TeacherDF createTeacher(String firstName, DaoSchoolAbstract dataAccessObject) {
		TeacherDF teacher = new TeacherDF(firstName, dataAccessObject);
		allTeachers.add(teacher);
		return teacher;
	}

	public static ITeacher createTeacher(boolean random, DaoSchoolAbstract dataAccessObject) {
		String newName = TeacherDF.generateNewName();
		TeacherDF newTeacher = null;
		if (!random) {
			newName = JOptionPane.showInputDialog("Bitte einen Namen eingeben:");
		}
		TeacherDF.createTeacher(newName, dataAccessObject);
		return newTeacher;
	}

	public static ArrayList<ITeacher> getTeachers() {
		return allTeachers;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Getter / Setter ITeacher
	@Override
	public void addCourse(ICourse course) throws Exception {
		try {
			courses.add(course);
			course.setTeacher(this);
			// course.setMyTeacherId(myTeacherId);

		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public void removeCourse(ICourse course) {
		course.removeTeacher();
		for (ICourse courses : this.courses) {
			if (courses.equals(course)) {
				this.courses.remove(course);
				break;
			}

		}
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
			TeacherDF.allTeachers.remove(this);
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
