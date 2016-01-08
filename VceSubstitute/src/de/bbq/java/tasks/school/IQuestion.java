package de.bbq.java.tasks.school;

/**
 * @author Thorsten2201
 *
 */
public interface IQuestion { 
	String toString();

	void addSolution(ISolution exam) throws Exception;

	void deleteSolution(ISolution exam);

	int getSolutionCount();

	String getDescription();
}
