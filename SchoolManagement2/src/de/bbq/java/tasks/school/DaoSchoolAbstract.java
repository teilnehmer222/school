package de.bbq.java.tasks.school;

/**
 * @author Thorsten2201
 *
 */
public abstract class DaoSchoolAbstract {

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	private EDaoSchool eDao = EDaoSchool.ABSTACT;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	protected DaoSchoolAbstract(EDaoSchool eDao) {
		this.seteDao(eDao);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Getter / Setter
	public EDaoSchool getDaoType() {
		return eDao;
	}

	private void seteDao(EDaoSchool eDao) {
		this.eDao = eDao;
	}

	public static DaoSchoolAbstract getDaoSchool(EDaoSchool eDao) {
		switch (eDao) {
		case FILE:
			return new DaoSchoolFile();
		case JDBC_MYSQL:
			return new DaoSchoolJdbcMysql();
		default:
			return null;
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// IDaoSchool
	public abstract boolean saveElement(SchoolItemAbstract schoolItemAbstract);

	public abstract boolean loadElement(SchoolItemAbstract schoolItemAbstract);

	public abstract boolean deleteElement(SchoolItemAbstract schoolItemAbstract);

	private boolean unsavedTeacher(SchoolItemAbstract seed) {
		for (ITeacher teacher : Teacher.getTeachers()) {
			if (!((SchoolItemAbstract) teacher).isSaved()) {
				if (!((SchoolItemAbstract) teacher).equals(seed)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean unsavedStudent(SchoolItemAbstract seed) {
		for (IStudent student : Student.getStudents()) {
			if (!((SchoolItemAbstract) student).isSaved()) {
				if (!((SchoolItemAbstract) student).equals(seed)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean unsavedCourse(SchoolItemAbstract seed) {
		for (ICourse course : Course.getCourses()) {
			if (!((SchoolItemAbstract) course).isSaved()) {
				if (!((SchoolItemAbstract) course).equals(seed)) {
					return true;
				}
			}
		}
		return false;
	}

	private void unset() {
		for (ICourse course : Course.getCourses()) {
			((SchoolItemAbstract) course).setSaved(false);
		}
		for (ITeacher teacher : Teacher.getTeachers()) {
			((SchoolItemAbstract) teacher).setSaved(false);
		}
		for (IStudent student : Student.getStudents()) {
			((SchoolItemAbstract) student).setSaved(false);
		}
	}

	public boolean saveAll() {
		for (int index = 0; index < Course.getCourses().size(); index++) {
			ICourse c = Course.getCourses().get(index);
			if (!unsavedTeacher((SchoolItemAbstract) c) && !unsavedCourse((SchoolItemAbstract) c)
					&& !unsavedStudent((SchoolItemAbstract) c)) {
				((SchoolItemAbstract) c).setLast(true);
			}
			c.saveElement();
			if (c.hasTeacher()) {
				if (!unsavedTeacher((SchoolItemAbstract) c.getTeacher())
						&& !unsavedCourse((SchoolItemAbstract) c.getTeacher())
						&& !unsavedStudent((SchoolItemAbstract) c.getTeacher())) {
					((SchoolItemAbstract) c.getTeacher()).setLast(true);
				}
				c.getTeacher().saveElement();
			}
			if (c.hasStudents()) {
				for (IStudent s : c.getStudents()) {
					if (!unsavedTeacher((SchoolItemAbstract) s) && !unsavedCourse((SchoolItemAbstract) s)
							&& !unsavedStudent((SchoolItemAbstract) s)) {
						((SchoolItemAbstract) s).setLast(true);
					}
					s.saveElement();
				}
			}
		}
		for (ITeacher t : SchoolLauncher.getTeacherList()) {
			if (!(t.isSaved())) {
				if (!unsavedTeacher((SchoolItemAbstract) t) && !unsavedStudent((SchoolItemAbstract) t)) {
					((SchoolItemAbstract) t).setLast(true);
				}
				t.saveElement();
			}
		}
		for (IStudent s : SchoolLauncher.getStudentList()) {
			if (!s.isSaved()) {
				if (!unsavedStudent((SchoolItemAbstract) s)) {
					((SchoolItemAbstract) s).setLast(true);
				}
				s.saveElement();
			}
		}
		unset();
		return true;
	}

	public abstract boolean loadAll();
	/////////////////////////////////////////////////////////////////////////////////////

}