package de.bbq.java.tasks.school;

import java.util.ArrayList;
import java.util.List;

public class TeacherDF extends SchoolMember implements ITeacher, DAInterface {
	private static final long serialVersionUID = -3548796163205043453L;
	
	private static ArrayList<TeacherDF> teachers = new ArrayList<>();
	//private List<Long> courses = new ArrayList<>();
	private ArrayList<CourseDF> coursesDF = new ArrayList<>();
	private DAOSchool dataAccessObject;
	
	// private boolean addCourseId(Long courseId) {
	// return courses.add(courseId);
	// }

	private TeacherDF(String firstName){
		super();
		super.setFirstName(firstName);
	}
	
	public static TeacherDF createTeacher(String firstName, DAOSchool store) {
		TeacherDF teacher = new TeacherDF(firstName);
		teacher.dataAccessObject = store;
		teachers.add(teacher);
		return teacher;
	}

	public static ArrayList<TeacherDF> getTeachers() {
		return teachers;
	}
	
	public void addCourse(CourseDF course) throws Exception {
		try {
			coursesDF.add(course);
			course.setTeacher(this);
			// course.setMyTeacherId(myTeacherId);

		} catch (Exception e) {
			throw e;
		}

	}

	public static void removeTeacher(TeacherDF teacher) {
		teachers.remove(teacher);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getFirstName() + " " + this.getLastName();
	}

//	private void removeCourseId(long courseId) {
//		for (Long course : courses) {
//			if (course == courseId) {
//				courses.remove(course);
//				break;
//			}
//
//		}
//	}

	public void removeCourse(CourseDF course) {
		for (CourseDF courseIt : this.coursesDF) {
			if (courseIt.equals(course)) {
				coursesDF.remove(course);
				// BAD:
				//removeCourseId(this.getId());
				break;
			}

		}
	}

//	public static void addTeacherToList(TeacherDF teacher) {
//		teachers.add(teacher);
//	}

	//USED??????
//	public static TeacherDF findTeacherById(long teacherId) {
//		TeacherDF foundTeacher = null;
//		for (TeacherDF teacher : teachers) {
//			if (teacher.getSchoolMemberId() == teacherId) {
//				foundTeacher = teacher;
//				break;
//			}
//		}
//		return foundTeacher;
//	}


	public static String generateNewName() {
		String[] array = new String[] { "Leerer A", "Musikleerer", "Deuschleerer", "Verleerer", "Entlährer", "Laubbläser", "Labersack", "Zutexter", "Volllaberer" };
		int randomNum = 0 + (int) (Math.random() * array.length);
		return array[randomNum];
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
	public <E> boolean saveList(ArrayList<E> list) {
		return this.dataAccessObject.saveList(teachers); 
	}

	@Override
	public <E> boolean loadList(ArrayList<E> list) {
		return this.dataAccessObject.loadList(teachers); 
	}

	@Override
	public boolean deleteElement() {
		teachers.remove(this);
		return this.dataAccessObject.deleteElement(this); 
	}

}
