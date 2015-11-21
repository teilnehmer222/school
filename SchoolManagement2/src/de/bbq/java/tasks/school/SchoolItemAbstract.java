package de.bbq.java.tasks.school;

import java.io.Serializable;

/**
 * @author Thorsten2201
 *
 */
public abstract class SchoolItemAbstract implements Serializable {

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	protected transient long id;
	private transient boolean isInEdit;
	private transient boolean saved;
	protected static DaoSchoolAbstract dataAccessObject;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	public SchoolItemAbstract(DaoSchoolAbstract dataAccessObjectConrete) {
		id = SchoolItemAbstract.highestMemberId++;
		if (dataAccessObject != null && dataAccessObjectConrete != null) {
			if (!dataAccessObject.getClass().equals(dataAccessObjectConrete.getClass())) {
				dataAccessObject = dataAccessObjectConrete;
			}
		} else if (dataAccessObject == null) {
			dataAccessObject = dataAccessObjectConrete;
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Static
	private static final long serialVersionUID = -1582579411217913540L;
	private static long highestMemberId = 1000;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Getter / Setter
	public long getId() {
		return id;
	}

	public boolean isInEdit() {
		return isInEdit;
	}

	public void setInEdit(boolean isInEdit) {
		this.isInEdit = isInEdit;
	}

	public boolean isSaved() {
		return this.saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// IDaoSchool helper
	protected boolean saveElement() {
		return dataAccessObject.saveElement(this);
	}

	protected boolean loadElement() {
		return dataAccessObject.loadElement(this);
	}

	protected boolean deleteElement() {
		return dataAccessObject.deleteElement(this);
	}

	protected boolean saveAll() {
		return dataAccessObject.saveAll();
	}

	protected boolean loadAll() {
		return dataAccessObject.loadAll();
	}
	/////////////////////////////////////////////////////////////////////////////////////

}
