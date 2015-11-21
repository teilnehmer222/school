package de.bbq.java.tasks.school;

import java.io.File;
import javax.swing.JOptionPane;

/**
 * @author Thorsten2201
 *
 */
public class DaoSchoolFile extends DaoSchoolAbstract {

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	@SuppressWarnings("unused")
	private File safeFile;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	public DaoSchoolFile() {
		super(EDaoSchool.File);
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
		} else if (schoolItemAbstract instanceof Address) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null,
					this.getClass().getName() + ".saveElement(Address " + schoolItemAbstract.toString() + ")");
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
		} else if (schoolItemAbstract instanceof Address) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null,
					this.getClass().getName() + ".loadElement(Address " + schoolItemAbstract.toString() + ")");
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
			return false;
		} else if (schoolItemAbstract instanceof ITeacher) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null,
					this.getClass().getName() + ".deleteElement(TeacherDF " + schoolItemAbstract.toString() + ")");
			return false;

		} else if (schoolItemAbstract instanceof IStudent) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null,
					this.getClass().getName() + ".deleteElement(StudentDF " + schoolItemAbstract.toString() + ")");
			return false;
		} else if (schoolItemAbstract instanceof Address) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(null,
					this.getClass().getName() + ".deleteElement(Address " + schoolItemAbstract.toString() + ")");
			return false;
		}
		return false;
	}
	/////////////////////////////////////////////////////////////////////////////////////

}
