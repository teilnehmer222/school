package de.bbq.java.tasks.school;

/**
 * @author Thorsten2201
 *
 */
public abstract class DaoSchoolAbstract {

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	public final static EDaoSchool eDao = EDaoSchool.ABSTACT;
	private static boolean canceled = false;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	// protected DaoSchoolAbstract(EDaoSchool eDao) {
	// this.seteDao(eDao);
	// }
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Getter / Setter
	public EDaoSchool getDaoType() {
		return eDao;
	}

	// private void seteDao(EDaoSchool eDao) {
	// this.eDao = eDao;
	// }

	private static DaoSchoolFile daoFile = new DaoSchoolFile();
	private static DaoSchoolJdbcMysql daoJdbcMysql = new DaoSchoolJdbcMysql();

	public static DaoSchoolAbstract getDaoSchool(EDaoSchool eDao) {
		switch (eDao) {
		case FILE:
			return daoFile;
		case JDBC_MYSQL:
			return daoJdbcMysql;
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
		boolean ret = false;
		if (Teacher.getTeachers().size() == 0) {
			return false;
		}
		for (ITeacher teacher : Teacher.getTeachers()) {
			if (!((SchoolItemAbstract) teacher).isSaved()) {
				if (!((SchoolItemAbstract) teacher).equals(seed)) {
					ret = true;
				}
			}
		}
		return ret;
	}

	private boolean unsavedStudent(SchoolItemAbstract seed) {
		boolean ret = false;
		if (Student.getStudents().size() == 0) {
			return false;
		}
		for (IStudent student : Student.getStudents()) {
			if (!((SchoolItemAbstract) student).isSaved()) {
				if (!((SchoolItemAbstract) student).equals(seed)) {
					ret = true;
				}
			}
		}
		return ret;
	}

	private boolean unsavedCourse(SchoolItemAbstract seed) {
		boolean ret = false;
		if (Course.getCourses().size() == 0) {
			return false;
		}
		for (ICourse course : Course.getCourses()) {
			if (!((SchoolItemAbstract) course).isSaved()) {
				if (!((SchoolItemAbstract) course).equals(seed)) {
					ret = true;
				}
			}
		}
		return ret;
	}

	private void unset() {
		for (ICourse course : Course.getCourses()) {
			((SchoolItemAbstract) course).setSaved(false);
			((SchoolItemAbstract) course).setLast(false);
		}
		for (ITeacher teacher : Teacher.getTeachers()) {
			((SchoolItemAbstract) teacher).setSaved(false);
			((SchoolItemAbstract) teacher).setLast(false);
		}
		for (IStudent student : Student.getStudents()) {
			((SchoolItemAbstract) student).setSaved(false);
			((SchoolItemAbstract) student).setLast(false);
		}
	}

	public boolean saveAllAhead() {
		boolean ret = false;
		if (!canceled) {
			DaoSchoolAbstract dao = DaoSchoolAbstract.getDaoSchool(Kursverwaltung.getSelectedDao());
			for (ITeacher t : Kursverwaltung.getTeacherList()) {
				if (!(((SchoolItemAbstract) t).isSaved())) {
					if (!unsavedTeacher((SchoolItemAbstract) t) && !unsavedStudent((SchoolItemAbstract) t)) {
						((SchoolItemAbstract) t).setLast(true);
					}
					ret &= dao.saveElement((SchoolItemAbstract) t);
				}
			}
			for (int index = 0; index < Course.getCourses().size(); index++) {
				ICourse c = Course.getCourses().get(index);
				if (!unsavedTeacher((SchoolItemAbstract) c) && !unsavedCourse((SchoolItemAbstract) c)
						&& !unsavedStudent((SchoolItemAbstract) c)) {
					((SchoolItemAbstract) c).setLast(true);
				}
				ret &= dao.saveElement((SchoolItemAbstract) c);
			}
			for (IStudent s : Kursverwaltung.getStudentList()) {
				if (!((SchoolItemAbstract) s).isSaved()) {
					if (!unsavedStudent((SchoolItemAbstract) s)) {
						((SchoolItemAbstract) s).setLast(true);
					}
					ret &= dao.saveElement((SchoolItemAbstract) s);
				}
			}
		}
		unset();
		return ret;
	}

	public boolean saveAll() {
		boolean ret = false;
		if (!canceled) {
			DaoSchoolAbstract dao = DaoSchoolAbstract.getDaoSchool(Kursverwaltung.getSelectedDao());
			for (int index = 0; index < Course.getCourses().size(); index++) {
				ICourse c = Course.getCourses().get(index);
				((SchoolItemAbstract) c).setSingle(false);
				if (!unsavedTeacher((SchoolItemAbstract) c) && !unsavedCourse((SchoolItemAbstract) c)
						&& !unsavedStudent((SchoolItemAbstract) c)) {
					((SchoolItemAbstract) c).setLast(true);
				} else {
					((SchoolItemAbstract) c).setLast(false);
				}
				ret &= dao.saveElement((SchoolItemAbstract) c);
				if (c.hasTeacher()) {
					((SchoolItemAbstract) c.getTeacher()).setSingle(false);
					if (!unsavedTeacher((SchoolItemAbstract) c.getTeacher())
							&& !unsavedCourse((SchoolItemAbstract) c.getTeacher())
							&& !unsavedStudent((SchoolItemAbstract) c.getTeacher())) {
						((SchoolItemAbstract) c.getTeacher()).setLast(true);
					} else {
						((SchoolItemAbstract) c.getTeacher()).setLast(false);
					}
					ret &= dao.saveElement((SchoolItemAbstract) c.getTeacher());
				}
				if (c.hasStudents()) {

					for (IStudent s : c.getStudents()) {
						((SchoolItemAbstract) s).setSingle(false);
						if (!unsavedTeacher((SchoolItemAbstract) s) && !unsavedCourse((SchoolItemAbstract) s)
								&& !unsavedStudent((SchoolItemAbstract) s)) {
							((SchoolItemAbstract) s).setLast(true);
						} else {
							((SchoolItemAbstract) s).setLast(false);
						}

						ret &= dao.saveElement((SchoolItemAbstract) s);
					}
				}
			}
			for (ITeacher t : Kursverwaltung.getTeacherList()) {
				if (!(((SchoolItemAbstract) t).isSaved())) {
					if (!unsavedTeacher((SchoolItemAbstract) t) && !unsavedStudent((SchoolItemAbstract) t)) {
						((SchoolItemAbstract) t).setLast(true);
					} else {
						((SchoolItemAbstract) t).setLast(false);
					}
					((SchoolItemAbstract) t).setSingle(false);
					ret &= dao.saveElement((SchoolItemAbstract) t);
				}
			}
			for (IStudent s : Kursverwaltung.getStudentList()) {
				if (!((SchoolItemAbstract) s).isSaved()) {
					if (!unsavedStudent((SchoolItemAbstract) s)) {
						((SchoolItemAbstract) s).setLast(true);
					} else {
						((SchoolItemAbstract) s).setLast(false);
					}
					((SchoolItemAbstract) s).setSingle(false);
					ret &= dao.saveElement((SchoolItemAbstract) s);
				}
			}
		}
		unset();
		return ret;
	}

	public static boolean closeConnections() {
		boolean ret = false;
		daoFile = null;
		if (daoJdbcMysql != null) {
			ret = daoJdbcMysql.dispose();
		} else {
			ret = true;
		}
		daoJdbcMysql = null;
		return ret;
	}

	public abstract boolean loadAll();

	public abstract boolean isConnected();

	public abstract boolean dispose();

	public abstract TriState connected();
	/////////////////////////////////////////////////////////////////////////////////////

	public static boolean isCanceled() {
		return canceled;
	}

	public static void setCanceled(boolean canceled) {
		DaoSchoolAbstract.canceled = canceled;
	}

}