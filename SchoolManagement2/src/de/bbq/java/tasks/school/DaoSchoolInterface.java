package de.bbq.java.tasks.school;

import java.util.ArrayList;

public interface DaoSchoolInterface {
	boolean saveElement();

	boolean loadElement();

	boolean deleteElement();

	boolean saveAll();

	boolean loadAll();
}
