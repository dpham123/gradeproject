package org.avid.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.avid.model.Assignment;
import org.avid.model.StudentClass;
import org.avid.model.User;

class LoginDao {
	private static String jdbc_url = "jdbc:mysql://localhost:3306/gradeSystem?user=default&password=test123&useSSL=false";

	static String createID(String firstName, String lastName, String password, String role) {
		Connection con = null;

		try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(jdbc_url);

			// Queries database for column size of table "credentials"
			Statement st = con.createStatement();
			String sql = "SELECT COUNT(*) FROM credentials";
			ResultSet rs = st.executeQuery(sql);

			// Checks for result from query and gets table size
			int tableSize = -1;
			if (rs.next()) {
				tableSize = rs.getInt(1) + 1;
			}

			// Creates new user ID in the database
			PreparedStatement ps = con
					.prepareStatement("INSERT INTO credentials (id, password, role) VALUES (NULL, ?, ?)");
			ps.setString(1, Encryptor.encrypt(password));
			ps.setString(2, role);
			ps.executeUpdate();
			
			if (role.equals("student")) {
				ps = con.prepareStatement("INSERT INTO student (student_id, first_name, last_name) VALUES (?, ?, ?)");
				ps.setInt(1, tableSize);
				ps.setString(2, firstName);
				ps.setString(3, lastName);
				ps.executeUpdate();
			} else if (role.equals("teacher")) {
				ps = con.prepareStatement("INSERT INTO teacher (teacher_id, first_name, last_name) VALUES (?, ?, ?)");
				ps.setInt(1, tableSize);
				ps.setString(2, firstName);
				ps.setString(3, lastName);
				ps.executeUpdate();
			}

			String username = Integer.toString(tableSize);
			int usernameLength = username.length();

			// Appends 0's in front of the user ID if the ID < 9 digits
			for (int i = 0; i < 9 - usernameLength; i++) {
				username = "0" + username;
			}
			return username;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	static String[] retrieveUserData(String username) {
		Connection con = null;
		String[] data = new String[2];

		try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(jdbc_url);

			// Attempts to retrieve password from database
			int userID = -1;
			try {
				userID = Integer.parseInt(username);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

			String sql = "SELECT PASSWORD, ROLE FROM credentials WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userID);

			ResultSet rs = ps.executeQuery();

			// Checks for result from query and gets password and user role
			if (rs.next()) {
				data[0] = rs.getString(1);
				data[1] = rs.getString(2);
			}
			return data;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	static List<Assignment> getAssignments(String userID, String className, User role){
		Connection con = null;
		List<Assignment> assignments = new ArrayList<>();
		
		try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(jdbc_url);
			
			PreparedStatement ps;
			ResultSet rs;
			if (role == User.TEACHER) {
				String sql = "SELECT DISTINCT a.name "
						+ "FROM assignment AS a "
						+ "INNER JOIN class AS c "
						+ "ON a.class_id = c.class_id "
						+ "WHERE a.teacher_id = ? AND c.name = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(userID));
				ps.setString(2, className);
				rs = ps.executeQuery();
			} else {
				String sql = "SELECT * FROM assignment WHERE student_id = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(userID));
				rs = ps.executeQuery();
			}
			
			while (rs.next()) {
				Assignment a = new Assignment();
				a.setName(rs.getString("name"));
				a.setTeacherID(Integer.parseInt(userID));
				assignments.add(a);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return assignments;
	}

	static List<StudentClass> getClasses(String userID, User role) {
		Connection con = null;
		List<StudentClass> classes = new ArrayList<>();

		if (role == User.STUDENT) {
			String sql = "SELECT DISTINCT name, grade FROM class "
					+ "WHERE student_id = ?";
			PreparedStatement ps;
			try {
				java.lang.Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(jdbc_url);

				ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(userID));
				ResultSet rs = ps.executeQuery();

				// Checks for result from query and gets password and user role
				while (rs.next()) {
					StudentClass c = new StudentClass();
					c.setClassName(rs.getString("name"));
					c.setGrade(rs.getString("grade"));
					classes.add(c);
				}
				return classes;
			} catch (SQLException | NumberFormatException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// Otherwise the user is a teacher
		String sql = "SELECT DISTINCT name FROM class WHERE teacher_id = ?";

		PreparedStatement ps;
		try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(jdbc_url);

			ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(userID));
			ResultSet rs = ps.executeQuery();

			// Checks for result from query and gets class name
			while (rs.next()) {
				StudentClass c = new StudentClass();
				c.setClassName(rs.getString("name"));
				c.setTeacherID(Integer.parseInt(userID));
				classes.add(c);
			}
			return classes;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	static List<Assignment> getStudents(String teacherID, String assignmentName) {
		Connection con = null;
		List<Assignment> students = new ArrayList<>();

		String sql = "SELECT a.student_id, a.grade "
				+ "FROM assignment AS a "
				+ "INNER JOIN class AS c "
				+ "ON a.class_id = c.class_id "
				+ "WHERE a.teacher_id = ? AND a.name = ?";

		PreparedStatement ps;
		try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(jdbc_url);

			ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(teacherID));
			ps.setString(2, assignmentName);
			ResultSet rs = ps.executeQuery();

			// Checks for result from query and gets class name
			while (rs.next()) {
				Assignment a = new Assignment();
				a.setStudentID(rs.getInt("student_id"));
				a.setGrade(rs.getString("grade"));
				students.add(a);
			}
			return students;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	static void deleteAllUsers() {
		Connection con = null;

		String sql = "DELETE FROM credentials WHERE id != 1";
		PreparedStatement ps;
		try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(jdbc_url);

			ps = con.prepareStatement(sql);
			ps.executeUpdate();

			sql = "ALTER TABLE credentials AUTO_INCREMENT = 1";
			ps = con.prepareStatement(sql);
			ps.executeUpdate();

		} catch (SQLException | NumberFormatException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	static void updateClass(StudentClass sc) {
		Connection con = null;

		String sql = "DELETE FROM credentials WHERE id != 1";
		PreparedStatement ps;
		try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(jdbc_url);

			ps = con.prepareStatement(sql);
			ps.executeUpdate();

			sql = "ALTER TABLE credentials AUTO_INCREMENT = 1";
			ps = con.prepareStatement(sql);
			ps.executeUpdate();

		} catch (SQLException | NumberFormatException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
