package de.bbq.java.tasks.school;

import javax.swing.JOptionPane;

/**
 * @author Thorsten2201
 *
 */
public class DaoSchoolJdbcMysql extends DaoSchoolJdbcAbstract {

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	public DaoSchoolJdbcMysql() {
		super(EDaoSchool.JDBC_MYSQL);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// DaoSchoolAbstract properties
	@Override
	public boolean saveElement(SchoolItemAbstract schoolItemAbstract) {
		if (schoolItemAbstract instanceof ICourse) {
			// TODO Auto-generated method stub
			// StringBuffer
			JOptionPane.showMessageDialog(null,
					this.getClass().getName() + ".saveElement(CourseDF " + schoolItemAbstract.toString() + ")");
			return false;
		} else if (schoolItemAbstract instanceof ITeacher) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null,
					this.getClass().getName() + ".saveElement(TeacherDF " + schoolItemAbstract.toString() + ")");
			return false;

		} else if (schoolItemAbstract instanceof IStudent) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null,
					this.getClass().getName() + ".saveElement(StudentDF " + schoolItemAbstract.toString() + ")");
			return false;
		}
		return false;
	}

	@Override
	public boolean loadElement(SchoolItemAbstract schoolItemAbstract) {
		if (schoolItemAbstract instanceof ICourse) {
			// TODO Auto-generated method stub
			// StringBuffer
			JOptionPane.showMessageDialog(null,
					this.getClass().getName() + ".loadElement(CourseDF " + schoolItemAbstract.toString() + ")");
			return false;
		} else if (schoolItemAbstract instanceof ITeacher) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null,
					this.getClass().getName() + ".loadElement(TeacherDF " + schoolItemAbstract.toString() + ")");
			return false;

		} else if (schoolItemAbstract instanceof IStudent) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null,
					this.getClass().getName() + ".loadElement(StudentDF " + schoolItemAbstract.toString() + ")");
			return false;
		}
		return false;
	}

	@Override
	public boolean deleteElement(SchoolItemAbstract schoolItemAbstract) {
		if (schoolItemAbstract instanceof ICourse) {
			// TODO Auto-generated method stub
			// StringBuffer
			JOptionPane.showMessageDialog(null,
					this.getClass().getName() + ".deleteElement(CourseDF " + schoolItemAbstract.toString() + ")");
			return true;
		} else if (schoolItemAbstract instanceof ITeacher) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null,
					this.getClass().getName() + ".deleteElement(TeacherDF " + schoolItemAbstract.toString() + ")");
			return true;

		} else if (schoolItemAbstract instanceof IStudent) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null,
					this.getClass().getName() + ".deleteElement(StudentDF " + schoolItemAbstract.toString() + ")");
			return true;
		}
		return true;
	}

	@Override
	public boolean loadAll() {
		return false;
	}

	/////////////////////////////////////////////////////////////////////////////////////

}
