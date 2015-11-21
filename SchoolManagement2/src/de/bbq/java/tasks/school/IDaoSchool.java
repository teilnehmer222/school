package de.bbq.java.tasks.school;

/**
 * @author Thorsten2201
 *
 */
public interface IDaoSchool {
	boolean isSaved();

	void setSaved(boolean saved);

	boolean saveElement();

	boolean loadElement();

	boolean deleteElement();

	boolean saveAll();

	boolean loadAll();
}
