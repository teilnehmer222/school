package de.bbq.java.tasks.school;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.management.ImmutableDescriptor;

public class CourseDF implements Serializable {
	private static List<CourseDF> courses = new ArrayList<>();
	/**
	 * 
	 */
	private static final long serialVersionUID = -4457277057001458163L;
	private long id;
	private long myTeacherId = -1;
	private List<Long> studentIds = new ArrayList<>();
	private String courseName;

	public CourseDF() {
		this.id = School.highestCourseId++;
	}

	public CourseDF(String courseName) {
		this.id = School.highestCourseId++;
		this.courseName = courseName;
	}

	public List<Long> getStudentIds() {
		return studentIds;
	}

	public static List<CourseDF> getCourses() {
		return courses;
	}

	public static void removeCourse(CourseDF course) {
		courses.remove(course);
	}

	public String toString() {
		return courseName;

	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public static void addCourseToList(CourseDF course) {
		courses.add(course);
	}

	public static CourseDF findCourseById(long courseId) {
		CourseDF foundCourse = null;
		for (CourseDF course : courses) {
			if (course.getId() == courseId) {
				foundCourse = course;
				break;
			}
		}
		return foundCourse;
	}

	public static boolean hasTeacher(long courseId) {
		boolean hasTeaacher = false;
		CourseDF course = findCourseById(courseId);
		if (course.getMyTeacherId() != -1)
			hasTeaacher = true;
		return hasTeaacher;
	}

	public long getId() {
		return id;
	}

	public long getMyTeacherId() {
		return myTeacherId;
	}

	public void setMyTeacherId(long myTeacherId) {
		this.myTeacherId = myTeacherId;
	}

	public void addStudent(Long studentId) {
		studentIds.add(studentId);
	}

	public void removeStudent(long id) {
		for (long studentEntry : studentIds) {
			if (studentEntry == id) {
				studentIds.remove(id);
			}
		}
	}

}
