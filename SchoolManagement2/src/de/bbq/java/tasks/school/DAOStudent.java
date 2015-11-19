package de.bbq.java.tasks.school;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public final class DAOStudent extends DAOSchool<StudentDF>  {

	@Override
	public boolean saveElement(StudentDF element) {
		JOptionPane.showMessageDialog(null, this.getClass().getName() + "saveElement(StudentDF " + element.toString());
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadElement(StudentDF element) {
		JOptionPane.showMessageDialog(null, this.getClass().getName() + "loadElement(StudentDF " + element.toString());
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveList(ArrayList<StudentDF> list) {
		JOptionPane.showMessageDialog(null, this.getClass().getName() + "saveList(ArrayList<StudentDF " + list.toString());
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadList(ArrayList<StudentDF> list) {
		JOptionPane.showMessageDialog(null, this.getClass().getName() + "loadList(ArrayList<StudentDF " + list.toString());
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteElement(StudentDF element) {
		JOptionPane.showMessageDialog(null, this.getClass().getName() + ".deleteElement(StudentDF " + element.toString() +")");
		// TODO Auto-generated method stub
		return false;
	}

}
