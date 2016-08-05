import java.util.ArrayList;

///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Database
// Files:            Student.java
// Semester:         Summer 2016
//
// Author:           Scarlet Guo zguo74@wisc.edu
// CS Login:         zguo74
// Lecturer's Name:  GERALD
////////////////////PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//Pair Partner:     Qingxu Kong
//Email:            qkong5@wisc.edu
//CS Login:         qkong5
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * this class represent a student object, which includes a unique ID, a name, 
 * birth year and an array list of the courses the student takes.
 * @author Scarlet
 *
 */
public class Student implements Comparable<Student> {
	
	/**
	 * data field
	 * ID: unique student ID
	 * name: student's name
	 * year: student birth year
	 * courses: an array list of the courses the student is taking
	 * THISYEAR: an constant representing this year
	 */
	 private int ID;
	 private String name;
	 private int year;
	 private ArrayList<Course> courses;
	 public static final int THISYEAR = 2016;
	 
	 /**
	  * construct a new student project using specified name, year, and unique ID
	  * @param name
	  * @param year
	  * @param ID
	  */
	 public Student (String name, int year, int ID) {
		 this.ID = ID;
		 this.name = name;
		 this.year = year;
		 this.courses = new ArrayList<Course> ();
		 
	 }
	 
	 /**
	  * this method add a course to a student's course list
	  * @param c
	 * @throws CourseExistException 
	  */
	 public void addCourse(Course c) throws CourseExistException {
		 for (Course e: this.courses) {
			 if (c.equalsTo(e) ) { 
			 throw new CourseExistException();
			 }
		 }
		 this.courses.add(c);
		 
		 
		 
	 }
	 
	 /**
	  * a getter of student's name
	  * @return
	  */
	 public String getName() {
		return name;
	}


	 /**
	  * setter of student's name
	  * @param name
	  */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * getter of student's birth year
	 * @return
	 */
	public int getYear() {
		return this.year;
	}


	/**
	 * getter of the courses list of the student's
	 * @return
	 */
	public ArrayList<Course> getCourse() {
		return this.courses;
	}
	
	/**
	 * getter of the student ID
	 * @return
	 */
	public int getID() {
		return this.ID;
	}
	 
	/**
	 * return a string of the formatted student information
	 */
	public String toString() {
		 StringBuilder s  = new StringBuilder();
		 s.append("Name: " + this.name + "\nID: " + this.ID + "\nAge: " 
				 + (THISYEAR - this.year) + "\nCourses: \n");
		 for (int i = 0; i < courses.size(); i++){
			 s.append(this.courses.get(i).toString());
		 }
		return s.toString();
		 
	 }
	/**
	 * setter of the students' birth year
	 * @param parseInt
	 */
	public void setYear(int parseInt) {
		this.year = parseInt;
	}

	
	/**
	 * this method implements the comparable students
	 */
	@Override
	public int compareTo(Student s) {
		int compareID = s.getID();
		return this.ID-compareID;
	}

}
