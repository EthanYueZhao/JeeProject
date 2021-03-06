package com.project.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.data.Courseschedule;
import com.project.data.User;
import com.project.model.AttendanceManager;

/**
 * Servlet implementation class SignAttendanceServlet
 */
@WebServlet("/Sign")
public class SignAttendanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignAttendanceServlet() {
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
		HttpSession session = request.getSession();
		AttendanceManager instance = new AttendanceManager();
		
		Courseschedule scheduleGet = null;
		int courseSelected = (int) session.getAttribute("course");
		User user = (User) session.getAttribute("user");
		
		//get the schedule of the course first
		ArrayList<Courseschedule> scheduleArray = instance.getScheduleByCourse(courseSelected);
		//compare the date for allow sign attendance or not
		for (Courseschedule schedule : scheduleArray) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String current =df.format(new Date());
			String scheduledDate = df.format(schedule.getCoursedate());
		    if (current.equals(scheduledDate)) {
		    	scheduleGet = schedule;
			}
		}
		
		if (scheduleGet != null) {
			//sign attendance and get feedbackc bool
			boolean successful = instance.signAttendance(user,scheduleGet);
			request.setAttribute("attendanceNotAllowed", successful);	
			ShowStudentAttendanceHelper helper = new ShowStudentAttendanceHelper();
			helper.showStudentAttendance(request, response,courseSelected);
//			request.getRequestDispatcher("/StudentsAttendance.jsp").forward(request,
//					response);
//			Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());
//			request.getRequestDispatcher("Main").forward(request,
//					response);
		}else {
			request.setAttribute("errorAttendance", "Sorry, you can only sign attendance on the class day.");
			request.getRequestDispatcher("/StudentsAttendance.jsp").forward(request,
					response);
		}
	}

}
