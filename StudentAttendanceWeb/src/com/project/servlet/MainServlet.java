package com.project.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.data.Attendance;
import com.project.data.Course;
import com.project.data.Courseschedule;
import com.project.data.User;
import com.project.model.AttendanceManager;
import com.project.model.UserManager;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return;
		}
		showGeneralAll(request, response);

		return;
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
					showGeneralAll(request, response);
					break;
				default:
					request.setAttribute("message", "Sorry, we encountered a problem.");
					request.getRequestDispatcher("/Error.jsp").forward(request,
							response);
				}
			}
		}
	}
	private void showGeneralAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserManager um = new UserManager();
		int courseId = Integer.parseInt(request.getParameter("course"));
		Course course = um.getCourse(courseId);
		AttendanceManager instance = new AttendanceManager();

		List<User> users = course.getUsers();
		List<User> students = new ArrayList<User>();

		for (User user : users) {
			if (user.getType() == 0) {
				students.add(user);
			}
		}

		for (User user : students) {
			// user is student
			ArrayList<Attendance> attendanceList = instance
					.getAttendanceHistory(courseId, user);
			if (attendanceList != null) {
				user.setAttendances(attendanceList);
			}
		}

		request.setAttribute("courseName",
				course.getIdcourse() + " " + course.getCoursename());
		request.setAttribute("courseId", courseId);

		request.setAttribute("students", students);

		request.getRequestDispatcher("/AttendanceGeneral.jsp").forward(request,
				response);
		return;

	}
	private void showGeneral(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		int courseId = Integer.parseInt(request.getParameter("course"));
		UserManager um = new UserManager();
		Course course = um.getCourse(courseId);

		List<User> users = course.getUsers();
		List<User> students =new ArrayList<User>();
		List<Courseschedule> sch = course.getCourseschedules();
		List<Attendance> newAtt = new ArrayList<Attendance>();
		
		for (User user : users) {
			if(user.getType() == 0){
				newAtt.clear();
				for (Courseschedule courseschedule : sch) {
					List<Attendance> atten = courseschedule.getAttendances();
					for (Attendance attendance : atten) {
						if(attendance.getUser().getIduser() == user.getIduser()){
							newAtt.add(attendance);
						}
					}
				}
				user.setAttendances(newAtt);
				students.add(user);
			}
		}
		
		request.setAttribute("courseName", course.getIdcourse()+" "+course.getCoursename() );
		request.setAttribute("courseId", courseId );
		request.setAttribute("students", students );
		
		request.getRequestDispatcher("/AttendanceGeneral.jsp").forward(request, response);
		return;
	}

}
