package de.bbq.java.tasks.school;

import java.util.ArrayList;

public class Teacher {
	private static ArrayList<Teacher> teacherList = new ArrayList<>();
	private ArrayList<Course> courses = new ArrayList();
	private String name;

	Teacher(String name) {
		this.name = name;
	}

	public void addCourse(Course cours) {
		courses.add(cours);
	}

	public void remCourse(Course cours) {
		if (courses.contains(cours)) {
			courses.remove(cours);
		}
	}

	@Override
	public String toString() {
		return name;
	}
	
	public static String generateNewName() {
		String[] array = new String[] {"Leerer A", "Musikleerer", "Deuschleerer", "Fielleerer", "Entlährer" };
		int randomNum = 0 + (int)(Math.random()*array.length); 
		return array[randomNum];
	}
	
	public static void addTeacher(Teacher t) {
		teacherList.add(t);
	}

	public static void remCource(Teacher t) {
		if (teacherList.contains(t)) {
			teacherList.remove(t);
		}
	}
	public static ArrayList<Teacher> getTeacherList() {
		return teacherList;
	}
	public ArrayList<Course> getCourseList() {
		return this.courses;
	}
}
