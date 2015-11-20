package de.bbq.java.tasks.school;

import java.util.ArrayList;
import java.util.List;

public class TeacherDF extends SchoolMember implements ITeacher, DaoSchoolInterface {
	private static final long serialVersionUID = -3548796163205043453L;

	private static ArrayList<TeacherDF> teachers = new ArrayList<>();
	// private List<Long> courses = new ArrayList<>();
	private ArrayList<CourseDF> courses = new ArrayList<>();
	private static DaoSchoolAbstract dataAccessObject;

	// private boolean addCourseId(Long courseId) {
	// return courses.add(courseId);
	// }

	private TeacherDF(String firstName) {
		super();
		super.setFirstName(firstName);
	}

	public static TeacherDF createTeacher(String firstName, DaoSchoolAbstract store) {
		TeacherDF teacher = new TeacherDF(firstName);
		if (dataAccessObject == null) {
			dataAccessObject = store;
		} else if (!dataAccessObject.getClass().equals(store.getClass())) {
			dataAccessObject = store;
		}
		teachers.add(teacher);
		return teacher;
	}

	public static ArrayList<TeacherDF> getTeachers() {
		return teachers;
	}

	public void addCourse(CourseDF course) throws Exception {
		try {
			courses.add(course);
			course.setTeacher(this);
			// course.setMyTeacherId(myTeacherId);

		} catch (Exception e) {
			throw e;
		}

	}

	public static void removeTeacher(TeacherDF teacher) {
		teachers.remove(teacher);
	}

	// private void removeCourseId(long courseId) {
	// for (Long course : courses) {
	// if (course == courseId) {
	// courses.remove(course);
	// break;
	// }
	//
	// }
	// }

	public void removeCourse(CourseDF course) {
		course.removeTeacher();
		for (CourseDF courses : this.courses) {
			if (courses.equals(course)) {
				this.courses.remove(course);
				break;
			}

		}
	}

	// public static void addTeacherToList(TeacherDF teacher) {
	// teachers.add(teacher);
	// }

	// USED??????
	// public static TeacherDF findTeacherById(long teacherId) {
	// TeacherDF foundTeacher = null;
	// for (TeacherDF teacher : teachers) {
	// if (teacher.getSchoolMemberId() == teacherId) {
	// foundTeacher = teacher;
	// break;
	// }
	// }
	// return foundTeacher;
	// }

	public static String generateNewName() {
		String[] array = new String[] { "Leerer Abwesend", "Musikleerer", "Deuschleerer", "Verleerer", "Entlährer",
				"Laubbläser", "Labersack", "Zutexter", "Volllaberer", "Berieseler", "Hintergrundrauschen", "Disturbing Noise" };
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
	public boolean deleteElement() {
		teachers.remove(this);
		return this.dataAccessObject.deleteElement(this);
	}

	@Override
	public boolean saveAll() {
		return this.dataAccessObject.saveAll();
	}

	@Override
	public boolean loadAll() {
		return this.dataAccessObject.loadAll();
	}

	private transient boolean saved = false;

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}
}
