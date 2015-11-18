package de.bbq.java.tasks.school;

import java.util.ArrayList;
import java.util.List;

public class StudentDF extends SchoolMember{
	private static List<StudentDF> students = new ArrayList<>(); 
	private long myCourseId =-1;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8838146635169751075L;

	public StudentDF(){
		
	}

	public static boolean hasCourse(long studentId){
		boolean hasCourse = false;
		StudentDF student = findStudentById(studentId);
		if(student.getMyCourseId()!=-1)
			hasCourse = true;
		return hasCourse;
	}
	
	public static List<StudentDF>getList(){
		return students;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getFirstName()+" "+this.getLastName();
	}

	public long getMyCourseId() {
		return myCourseId;
	}

	public void setMyCourseId(long myCourseId) {
		this.myCourseId = myCourseId;
	}
	
	public static void addStudentToList(StudentDF student){
		students.add(student);
	}
	
	public static StudentDF findStudentById(long studentId){
		StudentDF foundStudent = null;
		for(StudentDF student:students){
			if(student.getId() ==studentId){
				foundStudent = student;
				break;
			}
		}
		return foundStudent;
	}
	

}
