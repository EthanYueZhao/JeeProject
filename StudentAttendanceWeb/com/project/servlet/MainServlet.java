package com.project.servlet;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.project.data.Attendance;
import com.project.model.AttendanceManager;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
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
	
	protected void showStudentAttendance(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int attendedNum = 0;
		int attendanceSize = 0;
		AttendanceManager instance = new AttendanceManager();
		HttpSession session = request.getSession(false);
		if (session == null) {
			//error
			request.setAttribute("message", "session timeout");
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
		else {
			// user is student
			String courseSelected = ((String) session.getAttribute("course")).trim();
			String user = ((String) session.getAttribute("userName")).trim();
			ArrayList<Attendance> attendanceList= instance.getAttendanceHistory(courseSelected, user);
			if (attendanceList == null) {
				attendanceSize = 0;
				attendedNum = 0;
			}
			else {
				attendanceSize = attendanceList.size();
				attendedNum = instance.getAttendedNum(attendanceList);
			}
			request.setAttribute("attendanceList", attendanceList);
			request.setAttribute("attendanceSize", attendanceSize);
			request.setAttribute("attendedNum", attendedNum);
			request.getRequestDispatcher("/StudentsAttendance.jsp").forward(request,
					response);
		}
	}

	private void showGeneralStats(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("general stats");
	}

}
