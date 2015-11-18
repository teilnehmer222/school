package de.bbq.java.tasks.school;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class School implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5102428526913361257L;

	static long highestCourseId = 1000;
	static long highestMemberId = 1000;

	public static void addCourseToTeacher(long courseId, long teacherId) {
		if (!CourseDF.hasTeacher(courseId)) {
			CourseDF course = CourseDF.findCourseById(courseId);
			TeacherDF teacher = TeacherDF.findTeacherById(teacherId);
			teacher.addCourseId(courseId);
			course.setMyTeacherId(teacherId);
		}

	}

	public static void removeCourseFromTeacher(long courseId, long teacherId) {
		CourseDF course = CourseDF.findCourseById(courseId);
		TeacherDF teacher = TeacherDF.findTeacherById(teacherId);
		teacher.removeCourseId(courseId);
		course.setMyTeacherId(-1);

	}

	public static void addStudentToCourse(long studentId, long courseId) {
		if (!StudentDF.hasCourse(studentId)) {
			StudentDF student = StudentDF.findStudentById(studentId);
			CourseDF course = CourseDF.findCourseById(courseId);
			course.addStudent(studentId);
			student.setMyCourseId(courseId);
		}

	}

	public static void removeStudentFromCourse(long studentId, long courseId) {
		StudentDF student = StudentDF.findStudentById(studentId);
		CourseDF course = CourseDF.findCourseById(courseId);
		course.removeStudent(studentId);
		student.setMyCourseId(-1);

	}

	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
		stream.writeObject(highestCourseId);
		stream.writeObject(highestMemberId);
	}

	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		highestCourseId = (long) stream.readObject();
		highestMemberId = (long) stream.readObject();
	}

	/*public static void main(String arg[]) {
		School school = null;
		try {
			FileInputStream fileIn = new FileInputStream("C:/Users/teilnehmer/Downloads/stud.txt");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			school = (School) in.readObject();
			in.close();
			fileIn.close();
			System.out.printf("Serialized data is read from /tmp/employee.ser");
			System.out.println(School.highestCourseId);
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (school == null) {
			school = new School();
			Calendar cal = Calendar.getInstance();
			// Teacher
			TeacherDF teacher = new TeacherDF();
			teacher.setFirstName("Allan");
			teacher.setFirstName("Heinz");
			cal.set(1980, 11, 2);
			teacher.setBirthDate(cal.getTime());
			Adress adress2 = new Adress();
			adress2.setCity("Berlin");
			adress2.setCountry("Deuitschland");
			adress2.setHouseNumber(11);
			adress2.setStreetName("Bundesallee");
			adress2.setZipCode(12345);
			teacher.setAdress(adress2);
			TeacherDF.addTeacherToList(teacher);

			// Course
			CourseDF course = new CourseDF("super kurs");
			CourseDF.addCourseToList(course);
			
			addCourseToTeacher(teacher.getId(), course.getId());

			// Student
			StudentDF student = new StudentDF();
			student.setFirstName("David");
			student.setFirstName("Fridman");
			cal.set(1975, 22, 4);
			student.setBirthDate(cal.getTime());
			Adress adress = new Adress();
			adress.setCity("Berlin");
			adress.setCountry("Deutschland");
			adress.setHouseNumber(20);
			adress.setStreetName("Kudamm");
			adress.setZipCode(12162);
			student.setAdress(adress);
			StudentDF.addStudentToList(student);
			addStudentToCourse(student.getId(), course.getId());


			try {
				FileOutputStream fileOut = new FileOutputStream("C:/Users/teilnehmer/Downloads/stud.txt");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(school);
				out.close();
				fileOut.close();
				System.out.printf("Serialized data is saved in /tmp/employee.ser");
			} catch (IOException i) {
				i.printStackTrace();
			}
		}

	}*/
}
