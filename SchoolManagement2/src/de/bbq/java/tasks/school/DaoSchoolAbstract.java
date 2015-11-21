package de.bbq.java.tasks.school;

import java.util.ArrayList;

/**
 * @author Thorsten2201
 *
 */
public abstract class DaoSchoolAbstract {

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	private EDaoSchool eDao = EDaoSchool.Abstract;
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
		case File:
			return new DaoSchoolFile();
		case JdbcMySql:
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

	public boolean saveAll() {
		for (ICourse c : Course.getCourses()) {
			c.saveElement();
			if (c.hasTeacher()) {
				c.getTeacher().saveElement();
			}
			for (IStudent s : SchoolLauncher.getStudentList()) {
				s.saveElement();
			}

		}
		for (ITeacher t : SchoolLauncher.getTeacherList()) {
			if (!(t.isSaved())) {
				t.saveElement();
			}
		}
		for (IStudent s : SchoolLauncher.getStudentList()) {
			if (!s.isSaved()) {
				s.saveElement();
			}
		}
		return true;
	}

	public boolean loadAll() {
		ArrayList<Course> MOCK = new ArrayList<>();
		for (Object element : MOCK) {
			((Course) element).loadElement();
		}
		return false;
	}
	/////////////////////////////////////////////////////////////////////////////////////

}