package de.bbq.java.tasks.school;

import java.util.ArrayList;

public abstract class DAOSchool<E>{
	public abstract boolean saveElement(E element);
	public abstract boolean loadElement(E element);
	public abstract boolean deleteElement(E element);
	
	public abstract boolean saveList(ArrayList<E> list);
	public abstract boolean loadList(ArrayList<E> list);
}