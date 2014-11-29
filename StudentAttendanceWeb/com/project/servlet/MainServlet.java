package com.project.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.data.Attendance;
import com.project.data.User;
import com.project.model.AttendanceManager;

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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//back to the main page
		if (request.getParameter("backToMain") != null) {
			RequestDispatcher rd = request
					.getRequestDispatcher("/MainPage.jsp");
			rd.forward(request, response);
		}else {//not back to main 
			String courseString = request.getParameter("course").trim();
			if (courseString.equals("none")) {
				request.setAttribute("message", "Please select one course!");
				request.getRequestDispatcher("/Error.jsp").forward(request,
						response);
			}
			else {
				HttpSession session = request.getSession(false);
				String type = (String) session.getAttribute("userType");
				switch (type) {
				case "student":
					int courseSelected = Integer.parseInt(request
							.getParameter("course").trim());
					ShowStudentAttendanceHelper helper = new ShowStudentAttendanceHelper();
					helper.showStudentAttendance(request, response,courseSelected);
					//showStudentAttendance(request, response);
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
		}
	}

	private void showGeneralStats(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("general stats");
	}

}
