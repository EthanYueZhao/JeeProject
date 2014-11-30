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
 * Servlet implementation class FacultyServlet
 */
@WebServlet("/Faculty")
public class FacultyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// private int courseId = 1;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FacultyServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// make sure user is logged in
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return;
		}
		RequestDispatcher rd = null;
		// GET a course or ADD a course - sets new course to work on
		// String courseId = request.getParameter("submit");
		try {
			int courseSchId = Integer.parseInt(request
					.getParameter("courseSch"));
			showByDate(request, response, courseSchId);
			// UserManager um = new UserManager();
			// Course course = um.getCourse(courseId);
			//
			// List<User> users = course.getUsers();
			// List<User> students =new ArrayList<User>();
			// List<Courseschedule> sch = course.getCourseschedules();
			// List<Attendance> newAtt = new ArrayList<Attendance>();
			//
			// for (User user : users) {
			// if(user.getType() == 0){
			// newAtt.clear();
			// for (Courseschedule courseschedule : sch) {
			// List<Attendance> atten = courseschedule.getAttendances();
			// for (Attendance attendance : atten) {
			// if(attendance.getUser().getIduser() == user.getIduser()){
			// newAtt.add(attendance);
			// }
			// }
			// }
			// user.setAttendances(newAtt);
			// students.add(user);
			// }
			// }
			//
			// request.setAttribute("courseName",
			// course.getIdcourse()+" "+course.getCoursename() );
			// request.setAttribute("courseId", courseId );
			//
			// request.setAttribute("courseschedules", sch );
			// if(courseSchId > 0){
			// request.setAttribute("Idcourseschedule", sch.get(courseSchId -
			// 1).getIdcourseschedule() );
			// }else{
			// request.setAttribute("Idcourseschedule",
			// sch.get(0).getIdcourseschedule() );
			// }
			// request.setAttribute("courseSchId", courseSchId );
			// request.setAttribute("students", students );
			//
			// rd = request.getRequestDispatcher("/AttendanceByDate.jsp");
			// rd.forward(request, response);
			// return;
		} catch (Exception e) {
			if (e.getMessage() == null) {
				e.printStackTrace(System.out);
			}
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request,
					response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// make sure user is logged in
		HttpSession session = request.getSession();
		if (session.getAttribute("userName") == null) {
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
			return;
		}
		showByDate(request, response, 0);
		// RequestDispatcher rd = null;
		// // GET a course or ADD a course - sets new course to work on
		// //String courseId = request.getParameter("submit");
		// try {
		// courseId = Integer.parseInt(request.getParameter("courseId"));
		// UserManager um = new UserManager();
		// Course course = um.getCourse(courseId);
		//
		// List<User> users = course.getUsers();
		// List<User> students =new ArrayList<User>();
		// List<Courseschedule> sch = course.getCourseschedules();
		// List<Attendance> newAtt = new ArrayList<Attendance>();
		//
		// for (User user : users) {
		// if(user.getType() == 0){
		// newAtt.clear();
		// for (Courseschedule courseschedule : sch) {
		// List<Attendance> atten = courseschedule.getAttendances();
		// for (Attendance attendance : atten) {
		// if(attendance.getUser().getIduser() == user.getIduser()){
		// newAtt.add(attendance);
		// }
		// }
		// }
		// user.setAttendances(newAtt);
		// students.add(user);
		// }
		// }
		//
		// request.setAttribute("courseName",
		// course.getIdcourse()+" "+course.getCoursename() );
		// request.setAttribute("courseId", courseId );
		//
		// request.setAttribute("courseschedules", sch );
		// request.setAttribute("Idcourseschedule",
		// sch.get(0).getIdcourseschedule() );
		//
		// request.setAttribute("courseSchId", 0 );
		// request.setAttribute("students", students );
		//
		// rd = request.getRequestDispatcher("/AttendanceByDate.jsp");
		// rd.forward(request, response);
		// return;
		// } catch (Exception e) {
		// if (e.getMessage() == null) {
		// e.printStackTrace(System.out);
		// }
		// request.setAttribute("message", e.getMessage());
		// request.getRequestDispatcher("/error.jsp").forward(request,
		// response);
		// return;
		// }
	}

	private void showByDate(HttpServletRequest request,
			HttpServletResponse response, int courseSchId)
			throws ServletException, IOException {
		UserManager um = new UserManager();
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		Course course = um.getCourse(courseId);
		AttendanceManager instance = new AttendanceManager();
		ArrayList<Courseschedule> shcedules = instance
				.getScheduleByCourse(courseId);

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

		request.setAttribute("courseschedules", shcedules);

		if (courseSchId > 0) {
			for (Courseschedule courseschedule : shcedules) {
				if (courseschedule.getIdcourseschedule() == courseSchId) {
					request.setAttribute("Idcourseschedule",
							courseschedule.getIdcourseschedule());
				}
			}
		} else {
			request.setAttribute("Idcourseschedule", shcedules.get(0)
					.getIdcourseschedule());
		}
		request.setAttribute("courseSchId", courseSchId);
		request.setAttribute("students", students);

		request.getRequestDispatcher("/AttendanceByDate.jsp").forward(request,
				response);
		return;

	}

}
