package org.avid.model;

public class StudentClass {
	private String className;
	private int teacherID;
	private String grade;
	private int studentID;
	
	public String getClassName() {
		return className;
	}
	
	public String getGrade() {
		return grade;
	}
	
	public int getStudentID() {
		return studentID;
	}
	
	public int getTeacherID() {
		return teacherID;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public void setStudentID(int id) {
		this.studentID = id;
	}
	
	public void setTeacherID(int id) {
		this.teacherID = id;
	}
}
