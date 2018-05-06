package org.avid.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.avid.model.StudentClass;
import org.avid.model.User;

class LoginDao {
	private static String jdbc_url = "jdbc:mysql://localhost:3306/gradeSystem?user=default&password=test123&useSSL=false";

	static String createID(String password, String role) {
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

	static List<StudentClass> getClasses(String userID, User role) {
		Connection con = null;
		List<StudentClass> classes = new ArrayList<>();

		if (role == User.STUDENT) {
			String sql = "SELECT c.name, sc.grade " 
					+ "FROM classes as c " 
					+ "INNER JOIN students_classes as sc "
					+ "ON c.cid = sc.cid " 
					+ "WHERE sc.sid = ?";
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
		String sql = "SELECT name FROM classes WHERE tid = ?";

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

	static List<StudentClass> getStudents(String teacherID, String className) {
		Connection con = null;
		List<StudentClass> classes = new ArrayList<>();

		// Otherwise the user is a teacher
		String sql = "SELECT sc.sid, sc.grade " 
				+ "FROM students_classes as sc " 
				+ "INNER JOIN classes as c "
				+ "ON sc.cid = c.cid " 
				+ "WHERE tid = ? AND c.name = ?";

		PreparedStatement ps;
		try {
			java.lang.Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(jdbc_url);

			ps = con.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(teacherID));
			ps.setString(2, className);
			ResultSet rs = ps.executeQuery();

			// Checks for result from query and gets class name
			while (rs.next()) {
				StudentClass c = new StudentClass();
				c.setStudentID(rs.getInt("sid"));
				c.setGrade(rs.getString("grade"));
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
}
