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

	public String getStudentID() {
		String username = Integer.toString(studentID);
		int usernameLength = username.length();
		
		// Appends 0's in front of the user ID if the ID < 9 digits
		for (int i = 0; i < 9 - usernameLength; i++) {
			username = "0" + username;
		}
		return username;
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
