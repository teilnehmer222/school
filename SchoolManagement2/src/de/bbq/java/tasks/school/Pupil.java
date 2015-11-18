package de.bbq.java.tasks.school;

import java.util.ArrayList;

public class Pupil {
	private static ArrayList<Pupil> pupilList = new ArrayList<>();
	private Course course; // = new ArrayList<>();
	private String name;

	private Pupil(String name) {
		this.name = name;
	}
	public static Pupil createNewPupil(String name) {
		Pupil p = new Pupil(name);
		pupilList.add(p);
		return p;
	}

	public void addCourse(Course cours) {
		this.course = cours;
		// courseList.add(cours);
	}

	public void clearCourse() {
		this.course = null;
	}

	public void remCourse(Course cours) {
		if (this.course.equals(cours)) {
			this.course = null;
		}
		// if (courseList.contains(cours)) {
		// courseList.remove(cours);
		// }
	}

	@Override
	public String toString() {
		return name;
	}

	public static String generateNewName() {
		String[] array = new String[] { "Depp", "Trottel", "Idiot", "Armleuchter", "Hirni", "Totalversager",
				"Baumschulabbrecher", "Volltrottel", "Extremdepp", "Superidiot" };
		int randomNum = 0 + (int) (Math.random() * array.length);
		return array[randomNum];
	}

	public static void addPupil(Pupil p) {
		pupilList.add(p);
	}

	public static void remCource(Pupil p) {
		if (pupilList.contains(p)) {
			pupilList.remove(p);
		}
	}

	public static ArrayList<Pupil> getPupilList() {
		return pupilList;
	}

	public boolean hasCourse(Course course) {
		if (this.course == null) {
			return false;
		} else
			return this.course.equals(course);
	}

	public boolean hasCourse() {
		if (this.course == null) {
			return false;
		} else
			return true;
	}

	public Course getCourse() {
		return this.course;
	}
	// public ArrayList<Course> getCourseList() {
	// ArrayList<Course> courseList = new ArrayList<>();
	// courseList.add(this.course);
	// return courseList;
	// }
}
