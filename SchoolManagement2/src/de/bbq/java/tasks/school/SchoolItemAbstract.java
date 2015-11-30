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
	private transient boolean isLastObject;
	private transient boolean saved;
	protected static DaoSchoolAbstract dataAccessObject;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	protected static long getNewId() {
		return SchoolItemAbstract.highestMemberId++;
	}

	public SchoolItemAbstract(EDaoSchool eDataAccess) throws Exception {
		id = SchoolItemAbstract.highestMemberId++;
		DaoSchoolAbstract accessObject = null;
		try {
			accessObject = DaoSchoolAbstract.getDaoSchool(eDataAccess);
		} catch (Exception e) {
			throw e;
		}
		if (accessObject == null)
			throw new Exception("Datenzugriffs-Objekt \"" + eDataAccess.name() + "\2 nicht gefunden.");

		if (dataAccessObject != null && accessObject != null) {
			if (!dataAccessObject.getClass().equals(accessObject.getClass())) {
				dataAccessObject = accessObject;
			}
		} else if (dataAccessObject == null) {
			dataAccessObject = accessObject;
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

	protected boolean isLast() {
		return this.isLastObject;
	}

	public void setLast(boolean isLast) {
		this.isLastObject = isLast;
	}
	public abstract String getDescription();
	/////////////////////////////////////////////////////////////////////////////////////

}
