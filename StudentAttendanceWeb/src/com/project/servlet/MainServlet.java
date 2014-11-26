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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userType = (String) getServletContext().getAttribute("userType");
		int attendedNum = 0;
		int attendanceSize = 0;
		
		HttpSession session = request.getSession();
		if (userType.equals("0"))
		{
			AttendanceManager instance = new AttendanceManager();
			// user is student
			String courseSelected = (request.getParameter("course")).trim();
			String user = ((String) request.getSession().getAttribute("userName")).trim();
			ArrayList<Attendance> attendanceList= instance.getAttendanceHistory(courseSelected, user);
			if (attendanceList == null) {
				attendanceSize = 0;
				attendedNum = 0;
			}
			else {
				attendanceSize = attendanceList.size();
				attendedNum = instance.getAttendedNum(attendanceList);
			}
			session.setAttribute("attendanceList", attendanceList);
			session.setAttribute("attendanceSize", attendanceSize);
			session.setAttribute("attendedNum", attendedNum);
			request.getRequestDispatcher("/StudentsAttendance.jsp").forward(request,
					response);
		}
		else if (userType.equals("1")) {
			//user is faculty
		}
		else {
			//error
			request.setAttribute("message", "Please log in first");
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
}
