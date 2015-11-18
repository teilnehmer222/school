package de.bbq.java.tasks.school;

import java.util.ArrayList;
import java.util.Iterator;

public class Course {
	private static ArrayList<Course> courseList = new ArrayList<>();

	final static String[] names = new String[] { "Schlafuntericht", "Nichtstun 2.0", "Däumchendrehen",
			"Aus dem Fenster schauen", "Stinken für Dummies", "Wie saue ich das Klo voll", "Waschbecken verstopfen II",
			"Feueralarm drücken", "Aufzug blockieren", "Türen verkeilen", "Kernschmelze leichtgemacht", "Deppenradar" };
	private ArrayList<Pupil> pupils = new ArrayList<>();
	private Teacher teacher;
	private String name;

	public Course(String name) {
		this.name = name;
	}

	public void addPupil(Pupil pupil) {
		pupils.add(pupil);
	}

	public void remPupil(Pupil pupil) {
		if (pupils.contains(pupil)) {
			pupils.remove(pupil);
		}
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public ArrayList<Pupil> getPupils() {
		ArrayList<Pupil> ret = new ArrayList<>();
		for (Pupil pupil : pupils) {
			ret.add(pupil);
		}
		return ret;
	}

	@Override
	public String toString() {
		return name;
	}

	public static String generateNewName() {
		int randomNum = 0 + (int) (Math.random() * names.length);
		return names[randomNum];
	}

	public static void addCource(Course c) {
		courseList.add(c);
	}

	public static void remCource(Course c) {
		if (courseList.contains(c)) {
			courseList.remove(c);
		}
	}

	public static ArrayList<Course> getCourseList() {
		return courseList;
	}

	public static void writeBack(Teacher teacher) {
		for (Course c : teacher.getCourseList()) {
			if (Course.getCourseList().contains(c)) {
				Course.getCourseList().get(Course.getCourseList().indexOf(c)).setTeacher(teacher);
			}
		}
	}

	public static void writeBack(Pupil pupil) throws Exception {
		// for (Course c : pupil.getCourseList()) {
		Course c = pupil.getCourse();
		if (Course.getCourseList().contains(c)) {
			if (!Course.getCourseList().get(Course.getCourseList().indexOf(c)).getPupils().contains(pupil)) {
				Course.getCourseList().get(Course.getCourseList().indexOf(c)).addPupil(pupil);
			} else {
				throw new Exception(pupil.toString() + " schon vorhanden.");
			}
		}
		// }
	}

}
