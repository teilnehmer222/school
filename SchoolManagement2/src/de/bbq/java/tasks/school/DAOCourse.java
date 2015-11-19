package de.bbq.java.tasks.school;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public final class DAOCourse extends DAOSchool<CourseDF>  {
	@Override
	public boolean saveElement(CourseDF element) {
		JOptionPane.showMessageDialog(null, this.getClass().getName() + ".saveElement(CourseDF " + element.toString() +")");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadElement(CourseDF element) {
		JOptionPane.showMessageDialog(null, this.getClass().getName() + ".loadElement(CourseDF " + element.toString() +")");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveList(ArrayList<CourseDF> list) {
		JOptionPane.showMessageDialog(null, this.getClass().getName() + ".saveList(ArrayList<CourseDF> " + list.toString() +")");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadList(ArrayList<CourseDF> list) {
		JOptionPane.showMessageDialog(null, this.getClass().getName() + ".loadList(ArrayList<CourseDF> " + list.toString() +")");
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteElement(CourseDF element) {
		JOptionPane.showMessageDialog(null, this.getClass().getName() + ".deleteElement(CourseDF " + element.toString() +")");
		// TODO Auto-generated method stub
		return false;
	}

	
}
