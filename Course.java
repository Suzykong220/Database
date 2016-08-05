///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Database
// Files:            Course.java
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
 * This class represent a course including its department, number and credit
 * @author Scarlet
 *
 */
public class Course {
	
	/**
	 * data field
	 * dept: department of the course
	 * number: course number
	 * credit: credit of the course
	 */
	private String dept;
	private int number;
	private int credit;
	
	/**
	 * construct a course object using specified subject, number and credit
	 * @param subject
	 * @param number
	 * @param credit
	 */
	public Course( String subject, int number, int credit ) {
		
		this.dept = subject;
		this.number = number;
		this.credit = credit;
		
	}
	
	/**
	 * return a string containing course information
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("        Dept: " + this.dept + ", Course #: " + number + 
				", Credits: " + credit + "\n");
		return s.toString();
	}

	/**
	 * getter of the course department
	 * @return the name(department) of the course
	 */
	public String getName() {
		return this.dept;
	}

	/**
	 * getter of the course credit
	 * @return
	 */
	public int getCredit() {
		return this.credit;
	}

	/**
	 * getter of the course number 
	 * @return
	 */
	public int getNumber() {
		return this.number;
	}

	/**
	 * check if two courses are equal. they are equal only when they have same
	 * name, credit, number
	 * @param c
	 * @return
	 */
	public boolean equalsTo(Course c) {
		return this.dept.equalsIgnoreCase(c.dept) && (this.credit == c.credit) && (this.number == c.number);
	}
	
}
