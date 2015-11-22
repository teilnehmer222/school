package de.bbq.java.tasks.school;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * @author teilnehmer222
 *
 */
public class Teacher extends SchoolPersonAbstract implements ITeacher {
	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	private transient ArrayList<ICourse> courses = new ArrayList<>();
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	private Teacher(String firstName, EDaoSchool eDataAccess) throws Exception {
		super(eDataAccess);
		super.setFirstName(firstName);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Static
	private static final long serialVersionUID = -3548796163205043453L;
	private static ArrayList<ITeacher> allTeachers = new ArrayList<>();

	private static String generateNewName() {
		String[] array = new String[] { "Geistig Abwesender", "Musikleerer", "Deuschleerer", "Verleerer", "Entlährer",
				"Laubbläser", "Labersack", "Zutexter", "Volllaberer", "Berieseler", "Hintergrundrauschen",
				"Verstörendes Geräusch", "Arschkopf", "Dildogesicht", "Zwerg Nase", "Halodri", "Birkenstockdepp",
				"Fotzenkopf", "Hirschgesicht", "Althippy" };
		int randomNum = 0 + (int) (Math.random() * array.length);
		return array[randomNum];
	}

	private static Teacher createTeacher(String firstName, EDaoSchool eDataAccess) {
		Teacher teacher = null;
		try {
			teacher = new Teacher(firstName, eDataAccess);
			allTeachers.add(teacher);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return teacher;
	}

	public static ITeacher createTeacher(boolean random, EDaoSchool eDataAccess) {
		String newName = Teacher.generateNewName();
		Teacher newTeacher = null;
		if (!random) {
			newName = JOptionPane.showInputDialog("Bitte einen Namen eingeben:");
		}
		Teacher.createTeacher(newName, eDataAccess);
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
			Teacher.allTeachers.remove(this);
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
