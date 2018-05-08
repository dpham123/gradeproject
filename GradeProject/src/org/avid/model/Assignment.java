package org.avid.model;

public class Assignment {
	private int assignmentID;
	private int studentID;
	private int classID;
	private int teacherID;
	private String name;
	private String grade;
	
	public int getAssignmentID() {
		return assignmentID;
	}
	
	public String getStudentID() {
		String username = Integer.toString(studentID);
		int usernameLength = username.length();
		
		// Appends 0's in front of the user ID if the ID < 9 digits
		for (int i = 0; i < 9 - usernameLength; i++) {
			username = "0" + username;
		}
		return username;
	}
	
	public int getClassID() {
		return classID;
	}
	
	public String getTeacherID() {
		String username = Integer.toString(teacherID);
		int usernameLength = username.length();
		
		// Appends 0's in front of the user ID if the ID < 9 digits
		for (int i = 0; i < 9 - usernameLength; i++) {
			username = "0" + username;
		}
		return username;
	}
	
	public String getName() {
		return name;
	}
	
	public String getGrade() {
		return grade;
	}
	
	public void setAssignmentID(int assignmentID) {
		this.assignmentID = assignmentID;
	}
	
	public void setTeacherID(int teacherID) {
		this.teacherID = teacherID;
	}
	
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	
	public void setClassID(int classID) {
		this.classID = classID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setGrade(String grade) {
		this.grade = grade;
	}
}
