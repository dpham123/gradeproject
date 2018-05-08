package org.avid.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String n = request.getParameter("username");
		String p = request.getParameter("userpass");
		
		String[] data = LoginDao.retrieveUserData(n);
		
		// The username doesn't exist in the database
		if (data[0] == null) {
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request,response);
			out.print("<p style=\"color:red;\">Incorrect username or password</p>");
			return;
		}
		
		// Encrypts the password and checks with password in database
		if(data[0].equals(Encryptor.encrypt(p))){
			
			// Checks the role of the user
			if (data[1].equals("admin")){
				RequestDispatcher rd = request.getRequestDispatcher("admin/admin.html");
				rd.forward(request,response);
			} else if (data[1].equals("teacher")) {
				RequestDispatcher rd = request.getRequestDispatcher("/TeacherController");
				rd.forward(request,response);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/StudentController");
				rd.forward(request,response);
			}
			
		// Incorrect password	
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("index.html");
			rd.include(request,response);
			out.print("<p style=\"color:red;\">Incorrect username or password</p>");
		}
		
		out.close();
	}

}
