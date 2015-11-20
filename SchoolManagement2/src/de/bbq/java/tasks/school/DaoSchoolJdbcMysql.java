package de.bbq.java.tasks.school;

import javax.swing.JOptionPane;

public class DaoSchoolJdbcMysql extends DaoSchoolJdbc {

	@Override
	public boolean saveElement(CourseDF course) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, this.getClass().getName() + ".saveElement(CourseDF " + course.toString() + ")");
		return false;
	}

	@Override
	public boolean saveElement(TeacherDF teacher) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, this.getClass().getName() + ".saveElement(TeacherDF " + teacher.toString() + ")");

		return false;
	}

	@Override
	public boolean saveElement(StudentDF student) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, this.getClass().getName() + ".saveElement(StudentDF " + student.toString() + ")");

		return false;
	}

	@Override
	public boolean loadElement(CourseDF course) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadElement(TeacherDF teacher) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadElement(StudentDF student) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteElement(CourseDF course) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteElement(TeacherDF teacher) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteElement(StudentDF student) {
		// TODO Auto-generated method stub
		return false;
	}

}
