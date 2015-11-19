package de.bbq.java.tasks.school;

import java.awt.List;
import java.util.ArrayList;

public interface DAInterface{
	boolean saveElement();
	boolean loadElement();
	boolean deleteElement();
	
	<E> boolean saveList(ArrayList<E> list);
	<E> boolean loadList(ArrayList<E> list);
}
