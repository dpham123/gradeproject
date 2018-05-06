package org.avid.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateIDServlet
 */
@WebServlet("/CreateIDServlet")
public class CreateIDServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateIDServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String button1 = request.getParameter("Create New ID");
		String p = request.getParameter("userpass");
		String r = request.getParameter("role");
		
		if (button1 != null) {
			if (p.equals("")) {
				RequestDispatcher rd = request.getRequestDispatcher("admin/admin.html");
				rd.include(request,response);
				out.print("<p style=\"color:red;\">Password cannot be blank</p>");
				return;
			}
			
			String username = LoginDao.createID(p, r);
			RequestDispatcher rd = request.getRequestDispatcher("admin/admin.html");
			rd.include(request,response);
			out.printf("<p style=\"color:green;\">User created! Your user id is %s</p>", username);
		} else {
			LoginDao.deleteAllUsers();
			RequestDispatcher rd = request.getRequestDispatcher("admin/admin.html");
			rd.include(request,response);
			out.print("Users have been deleted!");
		}
		
		
		
	}

}
