package de.bbq.java.tasks.school;

public enum EStoreMode {
	DISK_IDLE(0), RAM_IDLE(1), MYSQL_IDLE(2), DISK_LOAD(3), RAM_LOAD(4), MYSQL_LOAD(5), DISK_SAVE(6), RAM_SAVE(
			7), MYSQL_SAVE(8), DISK_IMPORT(9), MYSQL_IMPORT(10);

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	int enumId = 0;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct

	EStoreMode(int enumId) {
		this.enumId = enumId;
	}

	public String toString() {
		switch (enumId) {
		case 0:
			return "Warten auf Festplatte";
		case 1:
			return "Warten auf Speicher";
		case 2:
			return "Warten auf MySql-Datenbank";
		case 3:
			return "Laden von Festplatte";
		case 4:
			return "Laden aus Speicher";
		case 5:
			return "Laden aus MySql-Datenbank";
		case 6:
			return "Speichern auf Festplatte";
		case 7:
			return "Speichern in Speicher";
		case 8:
			return "Speichern in MySql-Datenbank";
		case 9:
			return "Importieren von Festplatte";
		case 10:
			return "Importieren aus anderer Datenbank";
		default:
			return "Warten";
		}

	}
	/////////////////////////////////////////////////////////////////////////////////////
}
