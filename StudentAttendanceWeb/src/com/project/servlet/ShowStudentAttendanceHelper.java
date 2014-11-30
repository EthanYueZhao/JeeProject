package com.project.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.data.Attendance;
import com.project.data.User;
import com.project.model.AttendanceManager;

public class ShowStudentAttendanceHelper {

	protected void showStudentAttendance(HttpServletRequest request,
			HttpServletResponse response,int courseSelected) throws ServletException, IOException {
		int attendedNum = 0;
		int attendanceSize = 0;
		AttendanceManager instance = new AttendanceManager();
		HttpSession session = request.getSession(false);
		if (session == null) {
			// error
			request.setAttribute("message", "session timeout");
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		} else {
			// user is student
			User user = (User) session.getAttribute("user");
			ArrayList<Attendance> attendanceList = instance
					.getAttendanceHistory(courseSelected, user);
			if (attendanceList == null) {
				attendanceSize = 0;
				attendedNum = 0;
			} else {
				attendanceSize = attendanceList.size();
				attendedNum = instance.getAttendedNum(attendanceList);
			}
			session.setAttribute("course", courseSelected);
			request.setAttribute("attendanceList", attendanceList);
			request.setAttribute("attendanceSize", attendanceSize);
			request.setAttribute("attendedNum", attendedNum);

			// check if today's attendance is allowed for this course this user
			Attendance att = null;
			try {
				att = instance.getAttendance(user, courseSelected);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (att == null) {
				// don't have class today
				request.setAttribute("attendanceNotAllowed", true);
				request.setAttribute("errorAttendance",
						"You can only sign attendance on class day!");
				request.getRequestDispatcher("/StudentsAttendance.jsp")
						.forward(request, response);
			} else if (att.getAttendance() == 1) {
				// already signed attendance
				request.setAttribute("attendanceNotAllowed", true);
				request.setAttribute("errorAttendance",
						"You already taken this attendance!");
				request.getRequestDispatcher("/StudentsAttendance.jsp")
						.forward(request, response);
			} else {
				// Good request, go ahead
				request.getRequestDispatcher("/StudentsAttendance.jsp")
						.forward(request, response);
			}
		}
	}

}
