package com.project.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/Main")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		String type = (String) session.getAttribute("userType");
		switch (type) {
		case "student":
			showStudentAttendance(request, response);
			break;
		case "faculty":
			showGeneralStats(request, response);
			break;
		default:
			request.setAttribute("message", "Sorry, we encountered a problem.");
			request.getRequestDispatcher("/Error.jsp").forward(request,
					response);
		}
	}

	private void showStudentAttendance(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("student attendance");
	}
	
	private void showGeneralStats(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("general stats");
	}

}
