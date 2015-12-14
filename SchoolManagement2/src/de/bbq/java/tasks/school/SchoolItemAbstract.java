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
	private transient boolean isSingle = false; // used by serialize
	private transient boolean isLastObject; // used by serialize
	private transient boolean saved; // used by serialize
	protected static DaoSchoolAbstract dataAccessObject;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	protected static long getNewId() {
		return SchoolItemAbstract.highestMemberId++;
	}

	public SchoolItemAbstract(EDaoSchool eDataAccess) {
		id = SchoolItemAbstract.highestMemberId++;
		DaoSchoolAbstract accessObject = null;
		accessObject = DaoSchoolAbstract.getDaoSchool(eDataAccess);

		if (accessObject == null) {
			Kursverwaltung.showErrorMessage("Datenzugriffs-Objekt \"" + eDataAccess.name() + "\2 nicht gefunden.");
		}
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
	protected static long highestMemberId = 1000;
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

	protected void afterChange() {
		this.isLastObject = true;
		this.isSingle = true;
		if (dataAccessObject != null) {
			dataAccessObject.saveElement(this);
		} else {
			dataAccessObject = DaoSchoolAbstract.getDaoSchool(Kursverwaltung.getSelectedDao());
		}
	}

	public boolean isSingle() {
		return isSingle;
	}

	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}

	public abstract String getDescription();
	/////////////////////////////////////////////////////////////////////////////////////

}
