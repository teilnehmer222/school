package de.bbq.java.tasks.school;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public final class DAOTeacher extends DAOSchool<TeacherDF>{

	@Override
	public boolean saveElement(TeacherDF element) {
		JOptionPane.showMessageDialog(null, this.getClass().getName() + "saveElement(TeacherDF " + element.toString());
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadElement(TeacherDF element) {
		JOptionPane.showMessageDialog(null, this.getClass().getName() + "loadElement (TeacherDF " + element.toString());
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveList(ArrayList<TeacherDF> list) {
		JOptionPane.showMessageDialog(null, this.getClass().getName() + "(saveList(ArrayList<TeacherDF> " + list.toString());
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadList(ArrayList<TeacherDF> list) {
		JOptionPane.showMessageDialog(null, this.getClass().getName() + "loadList(ArrayList<TeacherDF> " + list.toString());
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteElement(TeacherDF element) {
		JOptionPane.showMessageDialog(null, this.getClass().getName() + ".deleteElement(TeacherDF " + element.toString() +")");
		// TODO Auto-generated method stub
		return false;
	}


}
