package de.bbq.java.tasks.school;

import java.util.ArrayList;
import java.util.List;

public class TeacherDF extends SchoolMember{
	private static List<TeacherDF> teachers = new ArrayList<>(); 
	private List<Long> courses = new ArrayList<>();


	public boolean addCourseId(Long courseId) {
		return courses.add(courseId);
	}


	public static List<TeacherDF> getTeachers() {
		return teachers;
	}


	public List<Long> getCourses() {
		return courses;
	}
	
	public static void removeTeacher(TeacherDF teacher){
		teachers.remove(teacher);
	}

@Override
public String toString() {
	// TODO Auto-generated method stub
	return this.getFirstName()+" "+this.getLastName();
}


	public void removeCourseId(long courseId) {
		for(Long course:courses){
			if(course == courseId){
				courses.remove(course);
				break;
			}
			
		}
	}
	
	public static void addTeacherToList(TeacherDF teacher){
		teachers.add(teacher);
	}
	
	public static TeacherDF findTeacherById(long teacherId){
		TeacherDF foundTeacher = null;
		for(TeacherDF teacher:teachers){
			if(teacher.getId() ==teacherId){
				foundTeacher = teacher;
				break;
			}
		}
		return foundTeacher;
	}
	


	/**
	 * 
	 */
	private static final long serialVersionUID = -3548796163205043453L;
	

}
