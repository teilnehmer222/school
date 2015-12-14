package de.bbq.java.tasks.school;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Shell {
	private EMainSections section;
	private EMainSections selectedClass = EMainSections.DATASOURCE;

	private Course course;
	private Teacher teacher;
	private Student student;

	private Scanner scanner = new Scanner(System.in);
	private StringBuffer name = new StringBuffer();
	private StringBuffer header = new StringBuffer();

	private int selection;
	private int subsection;
	private int input;

	public boolean cont = true;

	public void Start(boolean init) {
		setSection(EMainSections.COURSE);
		setSubsection(1);
		if (init) {
			System.out.println("Willkommen zur konsolengesteuerten Kursverwaltung.");
			showPromt(getSection(), getSubsection());
			while (getInput() && this.cont) {
				navigate();
				showPromt(getSection(), getSubsection());
			}
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// Input handling
	private void showPromt(EMainSections section, int subsection) {
		String out = getSubSection(section, subsection, this.header);
		this.header.append("\r\n" + out);
		System.out.println();
		System.out.println(this.header.toString());
	}

	private boolean getInput() {
		this.input = scanInput();
		if (isExitInput(this.input)) {
			scanner.close();
			return false;
		}
		return true;
	}

	private int scanInput() {
		String s = "";
		try {
			s  = scanner.nextLine();
		} catch (Exception e) {
			s = "1";
		}
		StringTokenizer t = new StringTokenizer(s);
		Integer ret = 999;
		try {
			ret = Integer.parseUnsignedInt(s);
		} catch (Exception ex) {
		}
		try {
			ret = Integer.parseUnsignedInt(t.nextToken());
		} catch (Exception e) {

		}
		if (s.compareTo("gopro") == 0)
			Kursverwaltung.toggleFrame();
			Kursverwaltung.getInstance().setLocationRelativeTo(null);
		if ((getSection().equals(EMainSections.COURSE) || getSection().equals(EMainSections.TEACHER)
				|| getSection().equals(EMainSections.STUDENT)) && getSubsection() == 1 && ret == 2) {

			this.name.setLength(0);
			if (t.hasMoreElements()) {
				StringBuffer tok = new StringBuffer(t.nextToken());
				this.name.append(tok);
				while (t.hasMoreTokens()) {
					this.name.append(" " + tok);
					String next = t.nextToken();
					tok = new StringBuffer(next);
				}
			} else {
				this.name.append("");
			}
		} else if (((getSection().equals(EMainSections.COURSE) || getSection().equals(EMainSections.TEACHER)
				|| getSection().equals(EMainSections.STUDENT)) && (getSubsection() == 3 && ret == 1))

				|| ((getSection().equals(EMainSections.COURSE) || getSection().equals(EMainSections.TEACHER)
						|| getSection().equals(EMainSections.STUDENT)) && getSubsection() == 2 && ret != 4)

				|| (getSection().equals(EMainSections.TEACHER) && getSubsection() == 3 && ret == 1)
				|| (getSection().equals(EMainSections.TEACHER) && getSubsection() == 4 && ret == 1)) {
			if (t.hasMoreTokens()) {
				try {
					Integer sel = Integer.parseUnsignedInt(t.nextToken());
					setSelection(sel);
				} catch (Exception e) {
					setSelection(1);
				}
			} else {
				System.out.println("Bitte treffen sie eine Auswahl:");
				s = scanner.nextLine();
				t = new StringTokenizer(s);
				try {
					setSelection(Integer.parseUnsignedInt(t.nextToken()));
				} catch (Exception e) {
					setSubsection(1);
					ret = 1;
				}
			}
		} else if (getSection().equals(EMainSections.STUDENT) && (getSubsection() == 3) && ret == 2) {
			setSubsection(3);
			ret = 2;
		}
		return ret;
	}

	private boolean isExitInput(int i) {
		if (getSubsection() == 1 && (getSection().equals(EMainSections.COURSE)
				|| getSection().equals(EMainSections.TEACHER) || getSection().equals(EMainSections.STUDENT))) {
			if (i == 6)
				return true;
		} else if (getSubsection() == 1 && (getSection().equals(EMainSections.DATASOURCE))) {
			if (i == 9)
				return true;
		} else if (getSection().equals(EMainSections.EDITOR)) {
			if (this.getSelectedClass() == EMainSections.COURSE) {
				if (i == 11)
					return true;
			} else if ((this.getSelectedClass() == EMainSections.TEACHER
					|| this.getSelectedClass() == EMainSections.TEACHER)) {
				if (i == 12)
					return true;
			}
		}
		return false;
	}

	public void showMessage(String... messages) {
		StringBuffer st = new StringBuffer();
		for (int index = 0; index < messages.length; index++) {
			st.append(messages[index]);
			if (index != messages.length - 1) {
				st.append("\n\r");
			}
		}
		System.out.println(st.toString());
		getInput();
		// continue while loop
	}

	// MessageBox for external code
	public String showInput(String s) {
		System.out.println(s);
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();
		scanner.close();
		return input;
	}
	
	public int showConfirm(String s, boolean cancel) {
		System.out.println(s + "\r\n1:Ja 2:Nö" + (cancel?" 3:Abbruch!":""));
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();
		scanner.close();
		try {
			return Integer.parseInt(input); 
		} catch (Exception e) {
			return showConfirm(s,cancel); // (cancel?3:2);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Navigation-spaghetti
	private void navigate() {
		ICourse c = null;

		switch (getSection()) {
		case COURSE:
			switch (subsection) {
			case 1:
				switch (this.input) {
				case 1:
					setSubsection(2);
					break;
				case 2:
					if (this.name.length() > 0) {
						this.course = (Course) Kursverwaltung.getNewCourse(this.name.toString());
						setSelection(selection);
					} else {
						this.course = (Course) Kursverwaltung.getNewCourse(true);
					}
					setSelectedClass(EMainSections.COURSE);
					System.out.println(this.course.getCourseName() + " angelegt.");
					break;
				case 3:
					setSection(EMainSections.TEACHER);
					break;
				case 4:
					setSection(EMainSections.STUDENT);
					break;
				case 5:
					setSection(EMainSections.DATASOURCE);
					break;
				}
				break;
			case 2:
				switch (this.input) {
				case 1:
					try {
						this.course = (Course) getICourse(this.getSelection());
						setSelectedClass(EMainSections.COURSE);
					} catch (Exception e) {
						this.course = null;
						setSelectedClass(EMainSections.DATASOURCE);
					}
					setSection(EMainSections.EDITOR);
					setSubsection(1);
					break;
				case 2:
					try {
						this.course = (Course) getICourse(this.getSelection());
						setSelectedClass(EMainSections.COURSE);
						System.out.println(this.course.getDescription());
						if (this.course.hasTeacher()) {
							System.out.println("Leerer: " + this.course.getTeacher().toString());
						}
						if (this.course.hasStudents()) {
							System.out.println("Schüler:");
							for (IStudent s : this.course.getStudents()) {
								System.out.println(s.toString());
							}
						}
					} catch (Exception e) {
						this.course = null;
						setSelectedClass(EMainSections.DATASOURCE);
						System.out.println(this.getSelection() + " nicht gefunden.");
					}
					break;
				case 3:
					try {
						this.course = (Course) getICourse(this.getSelection());
						setSelectedClass(EMainSections.COURSE);
					} catch (Exception e) {
						this.course = null;
						setSelectedClass(EMainSections.DATASOURCE);
					}
					if (Kursverwaltung.deleteElement(this.course)) {
						this.course = null;
					} else {
						System.out.println("Fehler beim Löschen des Kurses.");
					}
					break;
				case 4:
					setSubsection(1);
					break;
				}
				break;
			}
			break;
		case TEACHER:
			switch (subsection) {
			case 1:
				switch (this.input) {
				case 1:
					setSubsection(2);
					break;
				case 2:
					if (this.name.length() > 0) {
						this.teacher = (Teacher) Kursverwaltung.getNewTeacher(this.name.toString());
						setSelection(selection);
					} else {
						this.teacher = (Teacher) Kursverwaltung.getNewTeacher(true);
					}
					setSelectedClass(EMainSections.TEACHER);
					System.out.println(this.teacher.toString() + " angelegt.");
					break;
				case 3:
					setSection(EMainSections.COURSE);
					break;
				case 4:
					setSection(EMainSections.STUDENT);
					break;
				case 5:
					setSection(EMainSections.DATASOURCE);
					break;
				}
				break;
			case 2:
				switch (this.input) {
				case 1:
					try {
						this.teacher = (Teacher) Kursverwaltung.getTeacherList().get(this.getSelection() - 1);
						setSelectedClass(EMainSections.TEACHER);
					} catch (Exception e) {
						this.teacher = null;
						setSelectedClass(EMainSections.DATASOURCE);
					}
					setSection(EMainSections.EDITOR);
					setSubsection(1);
					break;
				case 2:
					try {
						this.teacher = (Teacher) Kursverwaltung.getTeacherList().get(this.getSelection() - 1);
						setSelectedClass(EMainSections.TEACHER);
						System.out.println(this.teacher.getDescription());
						setSubsection(3);
					} catch (Exception e) {
						e.printStackTrace();
						this.teacher = null;
						setSelectedClass(EMainSections.DATASOURCE);
						System.out.println(this.getSelection() + " nicht gefunden.");
					}
					break;
				case 3:
					try {
						this.teacher = (Teacher) Kursverwaltung.getTeacherList().get(this.getSelection() - 1);
						setSelectedClass(EMainSections.TEACHER);
					} catch (Exception e) {
						this.teacher = null;
						setSelectedClass(EMainSections.DATASOURCE);
					}
					if (Kursverwaltung.deleteElement(this.teacher)) {
						this.teacher = null;
					} else {
						System.out.println("Fehler beim Löschen des Leerers.");
					}
					break;
				case 4:
					setSubsection(1);
					break;
				}
				break;
			case 3:
				try {
					c = this.getICourse(getSelection());
				} catch (Exception e) {

				}
				if (c == null) {
					System.out.println("Kurs " + this.getSelection() + " lesen fehlgeschlagen.");
					break;
				}
				switch (this.input) {
				case 1:
					try {
						this.teacher.addCourse(c);
						c.setTeacher(this.teacher);
					} catch (Exception e) {
						System.out.println("Kurs " + this.getSelection() + " setzen fehlgeschlagen.");
						e.printStackTrace();
					}
					break;
				case 2:
					setSubsection(4);
					break;
				case 3:
					setSubsection(2);
					break;
				}
				break;
			case 4:
				try {
					c = this.getICourse(getSelection());
				} catch (Exception e) {

				}
				if (c == null) {
					System.out.println("Kurs " + this.getSelection() + " lesen fehlgeschlagen.");
					break;
				}
				switch (this.input) {
				case 1:
					try {
						c = this.getICourse(getSelection());
						c.removeTeacher();
						this.teacher.removeCourse(c);
					} catch (Exception e) {
						System.out.println("Kurs " + this.getSelection() + " entfernen fehlgeschlagen.");
						e.printStackTrace();
					}
					break;
				case 2:
					setSubsection(3);
					break;
				}
				break;
			}
			break;
		case STUDENT:
			switch (subsection) {
			case 1:
				switch (this.input) {
				case 1:
					setSubsection(2);
					break;
				case 2:
					if (this.name.length() > 0) {
						this.student = (Student) Kursverwaltung.getNewStudent(this.name.toString());
						setSelection(selection);
					} else {
						this.student = (Student) Kursverwaltung.getNewStudent(true);
					}
					setSelectedClass(EMainSections.STUDENT);
					System.out.println(this.student.toString() + " angelegt.");
					break;
				case 3:
					setSection(EMainSections.COURSE);
					break;
				case 4:
					setSection(EMainSections.TEACHER);
					break;
				case 5:
					setSection(EMainSections.DATASOURCE);
					break;
				}
				break;
			case 2:
				switch (this.input) {
				case 1:
					try {
						this.student = (Student) this.getIStudent(this.getSelection());
						setSelectedClass(EMainSections.STUDENT);
						setSelection(selection);
					} catch (Exception e) {
						this.student = null;
						setSelectedClass(EMainSections.DATASOURCE);
					}
					setSection(EMainSections.EDITOR);
					setSubsection(1);
					break;
				case 2:
					try {
						this.student = (Student) this.getIStudent(this.getSelection());
						setSelectedClass(EMainSections.STUDENT);
						System.out.println(this.student.getDescription());
						setSubsection(3);
					} catch (Exception e) {
						this.student = null;
						setSelectedClass(EMainSections.DATASOURCE);
						System.out.println(this.getSelection() + " nicht gefunden.");
					}
					break;
				case 3:
					try {
						this.student = (Student) this.getIStudent(this.getSelection());
						setSelectedClass(EMainSections.STUDENT);
					} catch (Exception e) {
						this.student = null;
						setSelectedClass(EMainSections.DATASOURCE);
					}
					if (Kursverwaltung.deleteElement(this.student)) {
						this.student = null;
					} else {
						System.out.println("Fehler beim Löschen des Schülers.");
					}
					break;
				case 4:
					setSubsection(1);
					break;
				}
				break;
			case 3:
				if (this.input == 1 || this.input == 2) {
					try {
						c = this.getICourse(getSelection());
					} catch (Exception e) {

					}
					if (c == null) {
						System.out.println("Kurs " + this.getSelection() + " lesen fehlgeschlagen.");
						break;
					}
				}
				switch (this.input) {
				case 1:
					try {
						c.addStudent(this.student);
					} catch (Exception e) {
						System.out.println("Kurs " + this.getSelection() + " setzen fehlgeschlagen.");
						e.printStackTrace();
					}
					break;
				case 2:
					try {
						c.removeStudent(this.student);
					} catch (Exception e) {
						System.out.println("Kurs " + this.getSelection() + " entfernen fehlgeschlagen.");
						e.printStackTrace();
					}
					break;
				case 3:
					setSubsection(2);
					break;
				}
				break;
			}
			break;

		case DATASOURCE:
			switch (this.input) {
			case 1:
				Kursverwaltung.setSelectedDao(EDaoSchool.FILE);
				System.out.println("Datenquelle auf " + EDaoSchool.FILE.toString() + " gewechselt.");
				break;
			case 2:
				Kursverwaltung.setSelectedDao(EDaoSchool.JDBC_MYSQL);
				System.out.println("Datenquelle auf " + EDaoSchool.JDBC_MYSQL.toString() + " gewechselt.");
				break;
			case 3:
				DaoSchoolAbstract.getDaoSchool(Kursverwaltung.getSelectedDao()).loadAll();
				Kursverwaltung.getInstance().refresh();
				System.out.println("Daten geladen.");
				break;
			case 4:
				DaoSchoolAbstract.getDaoSchool(Kursverwaltung.getSelectedDao()).saveAll();
				System.out.println("Daten gespeichert.");
				break;
			case 5:
				Kursverwaltung.reset();
				System.out.println("Daten zurückgesetzt.");
				break;
			case 6:
				setSection(EMainSections.COURSE);
				break;
			case 7:
				setSection(EMainSections.TEACHER);
				break;
			case 8:
				setSection(EMainSections.STUDENT);
				break;
			}
			break;
		case EDITOR:
			setSection(EMainSections.COURSE);
			if (this.getSelectedClass() == EMainSections.COURSE) {
				switch (this.input) {
				case 1: // Kursname bearbeiten
					this.course.setCourseName(this.name.toString());
					break;
				case 2: // Fach bearbeiten
					this.course.setTopic(this.name.toString());
					break;
				case 3: // Start bearbeiten
					Date start = null;
					try {
						start = Kursverwaltung.parseDate(this.name.toString());
						this.course.setStartTime(start);
					} catch (Exception e) {
						Kursverwaltung.showException(e);
					}
					break;
				case 4: // Ende bearbeiten
					Date end = null;
					try {
						end = Kursverwaltung.parseDate(this.name.toString());
						this.course.setEndTime(end);
					} catch (Exception e) {
						Kursverwaltung.showException(e);
					}
					break;
				case 5: // Sprache bearbeiten
					this.course.setLanguage(this.name.toString());
					break;
				case 6: // Raum bearbeiten
					this.course.setRoomNumber(this.name.toString());
					break;

				case 7: // Fach bearbeiten
					this.course.setTopic(this.name.toString());
					break;
				case 8: // Beamer bearbeiten
					this.course.setNeedsBeamer(this.name.toString().equalsIgnoreCase("ja"));
					break;
				case 9:
					setSection(EMainSections.COURSE);
					break;
				case 10:
					setSection(EMainSections.TEACHER);
					break;
				case 11:
					setSection(EMainSections.STUDENT);
					break;
				default:
					System.out.println(this.course.getDescription());
				}
			} else if ((this.getSelectedClass() == EMainSections.TEACHER)
					|| (this.getSelectedClass() == EMainSections.STUDENT)) {
				switch (this.input) {
				case 1: // Nachname bearbeiten
					(this.getSelectedClass() == EMainSections.TEACHER ? this.teacher : this.student)
							.setLastName(this.name.toString());
					break;
				case 2: // Firstname bearbeiten
					(this.getSelectedClass() == EMainSections.TEACHER ? this.teacher : this.student)
							.setFirstName(this.name.toString());
					break;
				case 3: // Geburtsdatum bearbeiten
					Date birth = null;
					try {
						birth = Kursverwaltung.parseDate(this.name.toString());
						(this.getSelectedClass() == EMainSections.TEACHER ? this.teacher : this.student)
								.setBirthDate(birth);
					} catch (Exception e) {
						Kursverwaltung.showException(e);
					}
					break;
				case 4: // Straße bearbeiten
					(this.getSelectedClass() == EMainSections.TEACHER ? this.teacher : this.student).getAdress()
							.setStreetName(this.name.toString());
					break;
				case 5: // Hausnummer bearbeiten
					(this.getSelectedClass() == EMainSections.TEACHER ? this.teacher : this.student).getAdress()
							.setHouseNumber(this.name.toString());
					break;
				case 6: // Stadt bearbeiten
					(this.getSelectedClass() == EMainSections.TEACHER ? this.teacher : this.student).getAdress()
							.setCity(this.name.toString());
					break;
				case 7: // Zip bearbeiten
					try {
						(this.getSelectedClass() == EMainSections.TEACHER ? this.teacher : this.student).getAdress()
								.setZipCode(Integer.parseInt(this.name.toString()));
					} catch (Exception e) {
						Kursverwaltung.showException(e);
					}
					break;
				case 8: // Land bearbeiten
					(this.getSelectedClass() == EMainSections.TEACHER ? this.teacher : this.student).getAdress()
							.setCountry(this.name.toString());
					break;
				}
			}
			break;
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Returns promt-text
	private String getSubSection(EMainSections section, int subsection, StringBuffer header) {
		String name = null;
		switch (section) {
		case COURSE:
			switch (subsection) {
			case 1:
				header.setLength(0);
				header.append("Sie befinden sich auf <Kurse>");
				name = "1. Liste aller Kurse (" + Kursverwaltung.getCourseList().size() + ") anzeigen\r\n";
				name += "2. Einen Kurs <...> anlegen\r\n";
				name += "3. Zu Leerer wechseln\r\n";
				name += "4. Zu Schüler wechseln\r\n";
				name += "5. Zu Datenquelle wechseln\r\n";
				name += "6. Programm beenden";
				break;
			case 2:
				header.setLength(0);
				header.append("Sie befinden sich auf <Kurse><Liste>\r\n");
				header.append(getList());
				name = "1. Kurs <X> bearbeiten\r\n";
				name += "2. Kurs <X> anzeigen\r\n";
				name += "3. Kurs <X> löschen\r\n";
				name += "4. Zurück";
				break;
			}
			break;
		case DATASOURCE:
			switch (subsection) {
			case 1:
				header.setLength(0);
				header.append("Sie befinden sich auf <Datenquelle>\r\n");
				name = "1. Datenquelle auf <" + EDaoSchool.FILE.toString() + "> setzen\r\n";
				name += "2. Datenquelle auf <" + EDaoSchool.JDBC_MYSQL.toString() + "> setzen\r\n";
				name += "3. Datensatz per " + Kursverwaltung.getSelectedDao().toString() + " laden\r\n";
				name += "4. Komplette Daten per " + Kursverwaltung.getSelectedDao().toString() + " speichern\r\n";
				name += "5. Komplette Daten auf \"null\" setzen\r\n";
				name += "6. Zu Kurse wechseln\r\n";
				name += "7. Zu Leerer wechseln\r\n";
				name += "8. Zu Schüler wechseln\r\n";
				name += "9. Programm beenden";
				break;
			}
			break;
		case STUDENT:
			switch (subsection) {
			case 1:
				header.setLength(0);
				header.append("Sie befinden sich auf <Schüler>");
				name = "1. Liste aller Schüler(" + Kursverwaltung.getStudentList().size() + ") anzeigen\r\n";
				name += "2. Einen Schüler <...> anlegen\r\n";
				name += "3. Zu Kurse wechseln\r\n";
				name += "4. Zu Leerer wechseln\r\n";
				name += "5. Zu Datenquelle wechseln\r\n";
				name += "6. Programm beenden";
				break;
			case 2:
				header.setLength(0);
				header.append("Sie befinden sich auf <Schüler><Liste>\r\n");
				header.append(getList());
				name = "1. Schüler <X> bearbeiten\r\n";
				name += "2. Schüler <X> auswählen und anzeigen\r\n";
				name += "3. Schüler <X> löschen\r\n";
				name += "4. Zurück";
				break;
			case 3:
				header.setLength(0);
				header.append("Sie befinden sich auf <Schüler><Liste><" + getSelectedName()
						+ ((this.student.hasCourse()) ? "(*)" : "") + ">" + "\r\nDie verfügbaren Kurse sind:\r\n");
				setSection(EMainSections.COURSE);
				header.append(getList());
				setSection(EMainSections.STUDENT);
				name = "1. Kurs <X> hinzufügen\r\n";
				name += "2. Kurs "
						+ ((this.student.getCourse() == null) ? "" : this.student.getCourse().toString() + " ")
						+ "entfernen\r\n";
				name += "3. Zurück";
				break;
			}
			break;
		case TEACHER:
			switch (subsection) {
			case 1:
				header.setLength(0);
				header.append("Sie befinden sich auf <Leerer>");
				name = "1. Liste aller Leerer(" + Kursverwaltung.getTeacherList().size() + ") anzeigen\r\n";
				name += "2. Einen Leerer <...> anlegen\r\n";
				name += "3. Zu Kurse wechseln\r\n";
				name += "4. Zu Schüler wechseln\r\n";
				name += "5. Zu Datenquelle wechseln\r\n";
				name += "6. Programm beenden";
				break;
			case 2:
				header.setLength(0);
				header.append("Sie befinden sich auf <Leerer><Liste>\r\n");
				header.append(getList());
				name = "1. Leerer <X> bearbeiten\r\n";
				name += "2. Leerer <X> auswählen und anzeigen\r\n";
				name += "3. Leerer <X> löschen\r\n";
				name += "4. Zurück";
				break;
			case 3:
				header.setLength(0);
				header.append("Sie befinden sich auf <Leerer><Liste><" + getSelectedName()
						+ ((this.teacher.getCoursesCount() > 0) ? "(" + this.teacher.getCoursesCount() + ")" : "") + ">"

						+ "\r\nDie verfügbare Kurse sind:\r\n");
				setSection(EMainSections.COURSE);
				header.append(getList());
				setSection(EMainSections.TEACHER);
				name = "1. Kurs <X> hinzufügen\r\n";
				name += "2. Kurse anzeigen\r\n";
				name += "3. Zurück";
				break;
			case 4:
				header.setLength(0);
				header.append("Sie befinden sich auf <Leerer><Liste><" + getSelectedName() + ">"
						+ ((this.teacher.getCoursesCount() > 0) ? "(" + this.teacher.getCoursesCount() + ")" : "")
						+ "\r\nSeine gehaltenen Kurse sind:\r\n");
				setSection(EMainSections.DATASOURCE);
				header.append(getList());
				setSection(EMainSections.TEACHER);
				name = "1. Kurs <X> entfernen\r\n";
				name += "2. Zurück";
			}
			break;
		case EDITOR:
			int index = 0;
			switch (subsection) {
			case 1:
				header.setLength(0);
				header.append("Sie editieren gerade <" + this.getSelectedName() + ">)");
				if (this.getSelectedClass() == EMainSections.COURSE) {
					name = ++index + ". Kursname bearbeiten\r\n";
					name += ++index + ". Fach bearbeiten\r\n";
					name += ++index + ". Start bearbeiten\r\n";
					name += ++index + ". Ende bearbeiten\r\n";
					name += ++index + ". Sprache bearbeiten\r\n";
					name += ++index + ". Raum bearbeiten\r\n";
					name += ++index + ". hat Beamer bearbeiten\r\n";
				} else if ((this.getSelectedClass() == EMainSections.TEACHER
						|| this.getSelectedClass() == EMainSections.TEACHER)) {
					name = ++index + ". Nachname bearbeiten\r\n";
					name += ++index + ". Vorname bearbeiten\r\n";
					name += ++index + ". Geburtsdatum bearbeiten\r\n";
					name += ++index + ". Strasse bearbeiten\r\n";
					name += ++index + ". Hausnummer bearbeiten\r\n";
					name += ++index + ". Stadt bearbeiten\r\n";
					name += ++index + ". PLZ bearbeiten\r\n";
					name += ++index + ". Land bearbeiten\r\n";
				}
				name += ++index + ". Zu Kurse wechseln\r\n";
				name += ++index + ". Zu Schüler wechseln\r\n";
				name += ++index + ". Zu Datenquelle wechseln\r\n";
				name += ++index + ". Programm beenden";
				break;
			}
			break;
		}
		return name;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Getter / Setter
	private String getList() {
		StringBuffer ret = new StringBuffer();
		@SuppressWarnings("rawtypes")
		ArrayList list = null;
		switch (getSection()) {
		case COURSE:
			list = Kursverwaltung.getCourseList();
			for (int index = 1; index <= list.size(); index++) {
				ret.append(index + ". " + list.get(index - 1).toString() + "\n");
			}
			break;
		case DATASOURCE: // refurbished
			list = Kursverwaltung.getCourses(this.teacher);
			for (int index = 1; index <= list.size(); index++) {
				ret.append(index + ". " + list.get(index - 1).toString() + "\n");
			}
			break;
		case EDITOR:
			break;
		case STUDENT:
			list = Kursverwaltung.getStudentList();
			for (int index = 1; index <= list.size(); index++) {
				ret.append(index + ". " + list.get(index - 1).toString() + "\n");
			}
			break;
		case TEACHER:
			list = Kursverwaltung.getTeacherList();
			for (int index = 1; index <= list.size(); index++) {
				ret.append(index + ". " + list.get(index - 1).toString() + "\n");
			}
			break;
		}
		return ret.toString();
	}

	@SuppressWarnings("incomplete-switch")
	private String getSelectedName() {
		switch (section) {
		case COURSE:
			if (this.course != null) {
				return this.course.toString();
			}
		case STUDENT:
			if (this.student != null) {
				return this.student.toString();
			}
		case TEACHER:
			if (this.teacher != null) {
				return this.teacher.toString();
			}
		}
		return "";
	}

	private EMainSections getSelectedClass() {
		return this.selectedClass;
	}

	private void setSelectedClass(EMainSections ec) {
		this.selectedClass = ec;
	}

	public int getSelection() {
		return selection;
	}

	public void setSelection(int selection) {
		this.selection = selection;
	}

	public EMainSections getSection() {
		return section;
	}

	public ICourse getICourse(int i) {
		return (ICourse) Course.getCourses().get(i - 1);
	}

	public ITeacher getITeacher(int i) {
		return (ITeacher) Teacher.getTeachers().get(i - 1);
	}

	public IStudent getIStudent(int i) {
		return (IStudent) Student.getStudents().get(i - 1);
	}

	public void setSection(EMainSections section) {
		this.section = section;
	}

	public int getSubsection() {
		return subsection;
	}

	public void setSubsection(int subsection) {
		this.subsection = subsection;
	}
	/////////////////////////////////////////////////////////////////////////////////////
}
