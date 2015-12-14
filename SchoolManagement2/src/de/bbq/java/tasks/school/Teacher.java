package de.bbq.java.tasks.school;

import java.util.ArrayList;

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

	public static boolean load(Teacher teacher) {
		allTeachers.add(teacher);
		teacher.id = SchoolItemAbstract.getNewId();
		return true;
	}

	private static String generateNewName() {
		String[] array = new String[] { "Geistig Abwesender", "Laubbläsleer", "Labersack", "Zutexter", "Volllaberer",
				"Berieseler", "Hintergrundrauschen", "Verstörendes Geräusch", "Dildogesicht", "Halodri",
				"Birkenstockdepp", "Fotzenkopf", "Hirschfresse", "Althippy", "Schnarchnase" };
		int randomNum = 0 + (int) (Math.random() * array.length);
		return array[randomNum];
	}

	public static Teacher createTeacher(String firstName, EDaoSchool eDataAccess) {
		Teacher teacher = null;
		try {
			teacher = new Teacher(firstName, eDataAccess);
			allTeachers.add(teacher);
		} catch (Exception e) {
			Kursverwaltung.showException(e);
		}
		return teacher;
	}

	public static ITeacher createTeacher(boolean random, EDaoSchool eDataAccess) {
		String newName = Teacher.generateNewName();
		Teacher newTeacher = null;
		if (!random) {
			newName = Kursverwaltung.showInput("Bitte einen Namen eingeben:");
		}
		newTeacher = Teacher.createTeacher(newName, eDataAccess);
		return newTeacher;
	}

	public static ArrayList<ITeacher> getTeachers() {
		return allTeachers;
	}

	public static void teacherDeleted(SchoolItemAbstract editItem) {
		if (allTeachers.contains(editItem)) {
			allTeachers.remove(editItem);
		}
	}

	private static int getCourseCount(Teacher teacher) {
		int cnt = 0;
		for (ICourse c : Course.getCourses()) {
			if (c.hasTeacher()) {
				if (c.getTeacher().equals(teacher)) {
					cnt++;
				}
			}
		}
		return cnt;
	}

	public static void reset() {
		allTeachers = new ArrayList<>();
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Getter / Setter ITeacher
	@Override
	public void addCourse(ICourse course) {
		this.getCourses().add(course);
		course.setTeacher(this);
		this.afterChange();	
	}

	private ArrayList<ICourse> getCourses() {
		if (this.courses == null) {
			this.courses = new ArrayList<>();
		}
		return courses;
	}

	@Override
	public void removeCourse(ICourse course) {
		course.removeTeacher();
		for (ICourse courses : this.getCourses()) {
			if (courses.equals(course)) {
				this.getCourses().remove(course);
				break;
			}
		}
		this.afterChange();	
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

	@Override
	public int getCoursesCount() {
		return Teacher.getCourseCount(this);
	}
	/////////////////////////////////////////////////////////////////////////////////////

}
