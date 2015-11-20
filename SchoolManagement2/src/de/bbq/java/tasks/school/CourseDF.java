package de.bbq.java.tasks.school;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * @author teilnehmer
 *
 */
public class CourseDF implements ICourse, Serializable, DaoSchoolInterface {
	final static String[] names = new String[] { "Schlafuntericht", "Nichtstun 2.0", "Däumchendrehen",
			"Aus dem Fenster schauen", "Stinken für Dummies", "Wie saue ich das Klo voll", "Waschbecken verstopfen II",
			"Feueralarm drücken", "Aufzug blockieren", "Türen verkeilen", "Kernschmelze leichtgemacht", "Deppenradar",
			"Mülleimer vollkotzen", "Beamer sabotieren", "Ans Fenster rotzen" };

	private static final long serialVersionUID = -4457277057001458163L;
	private static long highestCourseId = 1000;
	private static ArrayList<CourseDF> courses = new ArrayList<>();

	private TeacherDF teacher;
	private ArrayList<StudentDF> students = new ArrayList<>();

	private static DaoSchoolAbstract dataAccessObject;

	// Serializable Properties
	private transient long id;
	private String courseName;

	private String topic;
	private Date endTime;
	private Date startTime;
	private Integer roomNumber;
	private Boolean needsBeamer = false;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setStudents(ArrayList<StudentDF> students) {
		this.students = students;
	}

	public void setNeedsBeamer(Boolean needsBeamer) {
		this.needsBeamer = needsBeamer;
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

	private String language;

	private static long createNewID() {
		return CourseDF.highestCourseId++;
	}

	private CourseDF(String courseName) {
		this.id = CourseDF.createNewID();
		this.courseName = courseName;
		courses.add(this);
	}

	public static CourseDF createCourse(String courseName, DaoSchoolAbstract store) {
		CourseDF c = new CourseDF(courseName);
		if (dataAccessObject == null) {
			dataAccessObject = store;
		} else if (!dataAccessObject.getClass().equals(store.getClass())) {
			dataAccessObject = store;
		}
		return c;
	};

	public ArrayList<StudentDF> getStudents() {
		return this.students;
	}

	@Override
	public String toString() {
		return this.courseName;

	}

	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setTeacher(TeacherDF t) {
		this.teacher = t;
	}

	public TeacherDF getTeacher() {
		return this.teacher;
	}

	public void removeTeacher() {
		this.teacher = null;
	}

	public void addStudent(StudentDF student) {
		this.students.add(student);
		if (student.hasCourse()) {
			CourseDF oldCourse = student.getCourse();
			oldCourse.removeStudent(student);
		}
		student.setCourse(this);
	}

	public void removeStudent(StudentDF student) {
		if (this.students.contains(student)) {
			this.students.remove(student);
		}
		student.setCourse(null);
	}

	public boolean hasTeacher() {
		return (this.getTeacher() != null);
	}

	public static ArrayList<CourseDF> getCourses() {
		return courses;
	}

	public static String generateNewName() {
		int randomNum = 0 + (int) (Math.random() * names.length);
		return names[randomNum];
	}

	@Override
	public boolean saveElement() {
		return this.dataAccessObject.saveElement(this);
	}

	@Override
	public boolean loadElement() {
		return this.dataAccessObject.loadElement(this);
	}

	@Override
	public boolean deleteElement() {
		boolean ret = dataAccessObject.deleteElement(this);
		courses.remove(this);
		return ret;
	}

	public Boolean getNeedsBeamer() {
		return this.needsBeamer;
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

	@Override
	public boolean saveAll() {
		return this.dataAccessObject.saveAll();
	}

	@Override
	public boolean loadAll() {
		return this.dataAccessObject.loadAll();
	}
}
