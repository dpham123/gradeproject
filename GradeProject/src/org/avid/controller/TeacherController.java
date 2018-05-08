package org.avid.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.avid.model.User;

/**
 * Servlet implementation class TeacherController
 */
@WebServlet("/TeacherController")
public class TeacherController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward = "teacher/teacher.jsp";
		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();
		
		
		if (action == null) {
			String teacherID = request.getParameter("username");
			request.setAttribute("classes", LoginDao.getClasses(teacherID, User.TEACHER));
			RequestDispatcher view = request.getRequestDispatcher(forward);
			view.forward(request, response);
		} else if (action.equals("viewAssignments")) {
			String teacherID = request.getParameter("teacherID");
			String className = request.getParameter("className");
			forward = "teacher/classGrades.jsp";
			
			request.setAttribute("assignments", LoginDao.getAssignments(teacherID, className, User.TEACHER));
			out.printf("<h1>%s</h1>", className);
			RequestDispatcher view = request.getRequestDispatcher(forward);
			view.include(request, response);
			
		} else {
			String teacherID = request.getParameter("teacherID");
			String assignmentName = request.getParameter("assignmentName");
			forward = "teacher/classAssignments.jsp";
			
			request.setAttribute("students", LoginDao.getStudents(teacherID, assignmentName));
			out.printf("<h1>%s</h1>", assignmentName);
			RequestDispatcher view = request.getRequestDispatcher(forward);
			view.include(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
