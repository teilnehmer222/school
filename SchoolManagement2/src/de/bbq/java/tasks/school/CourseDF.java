package de.bbq.java.tasks.school;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * @author teinlehmer222
 *
 */
public class CourseDF extends SchoolItemAbstract implements ICourse {

	/////////////////////////////////////////////////////////////////////////////////////
	// Class Properties
	private transient ITeacher teacher;
	private transient ArrayList<IStudent> students = new ArrayList<>();
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Properties to serialize
	private String courseName;
	private String topic;
	private Date endTime;
	private Date startTime;
	private Integer roomNumber;
	private Boolean needsBeamer = false;
	private String language;
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Construct
	private CourseDF(String courseName, DaoSchoolAbstract dataAccessObject) {
		super(dataAccessObject);
		this.courseName = courseName;
		allCourses.add(this);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Static
	private static final long serialVersionUID = -4457277057001458163L;
	private static ArrayList<ICourse> allCourses = new ArrayList<>();

	private static String generateNewName() {
		String[] names = new String[] { "Schlafuntericht", "Nichtstun 2.0", "Däumchendrehen", "Aus dem Fenster schauen",
				"Stinken für Dummies", "Wie saue ich das Klo voll", "Waschbecken verstopfen II", "Feueralarm drücken",
				"Aufzug blockieren", "Türen verkeilen", "Kernschmelze leichtgemacht", "Deppenradar",
				"Mülleimer vollkotzen", "Beamer sabotieren", "Ans Fenster rotzen" };
		int randomNum = 0 + (int) (Math.random() * names.length);
		return names[randomNum];
	}

	private static CourseDF createCourse(String courseName, DaoSchoolAbstract dataAccessObject) {
		CourseDF c = new CourseDF(courseName, dataAccessObject);
		return c;
	};

	public static ICourse createCourse(boolean random, DaoSchoolAbstract store) {
		String newName = CourseDF.generateNewName();
		CourseDF newCourse = null;
		if (!random) {
			newName = JOptionPane.showInputDialog("Bitte einen Kursnamen eingeben:");
		}
		newCourse = CourseDF.createCourse(newName, store);
		return newCourse;
	}

	public static ArrayList<ICourse> getCourses() {
		return allCourses;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Getter / Setter ICourse
	@Override
	public String toString() {
		return this.courseName;
	}

	@Override
	public ITeacher getTeacher() {
		return this.teacher;
	}

	@Override
	public void setTeacher(ITeacher t) {
		this.teacher = t;
	}

	@Override
	public void removeTeacher() {
		this.teacher = null;
	}

	@Override
	public boolean hasTeacher() {
		return (this.getTeacher() != null);
	}

	@Override
	public ArrayList<IStudent> getStudents() {
		return this.students;
	}

	@Override
	public void addStudent(IStudent student) {
		this.students.add(student);
		if (student.hasCourse()) {
			ICourse oldCourse = student.getCourse();
			oldCourse.removeStudent(student);
		}
		student.setCourse(this);
	}

	@Override
	public void removeStudent(IStudent student) {
		if (this.students.contains(student)) {
			this.students.remove(student);
		}
		student.setCourse(null);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// Getter / Setter Properties
	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTopic() {
		return this.topic;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public Date getSartTime() {
		return this.startTime;
	}

	public Integer getRoomNumber() {
		return this.roomNumber;
	}

	public String getLanguage() {
		return this.language;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Boolean getNeedsBeamer() {
		return this.needsBeamer;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setStudents(ArrayList<IStudent> students) {
		this.students = students;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setNeedsBeamer(Boolean needsBeamer) {
		this.needsBeamer = needsBeamer;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////////////////////////////////////////////////////////////
	// IDaoSchoolAbstract
	@Override
	public boolean saveElement() {
		return super.saveElement();
	}

	@Override
	public boolean loadElement() {
		return super.loadElement();
	}

	@Override
	public boolean deleteElement() {
		boolean ret = super.deleteElement();
		if (ret) {
			CourseDF.allCourses.remove(this);
		}
		return ret;
	}

	@Override
	public boolean saveAll() {
		return super.saveAll();
	}

	@Override
	public boolean loadAll() {
		return super.loadAll();
	}
	/////////////////////////////////////////////////////////////////////////////////////

}
