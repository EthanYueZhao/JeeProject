package com.project.servlet;

import java.io.IOException;
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
		RequestDispatcher rd = null;
		// GET a course or ADD a course - sets new course to work on
		String command = request.getParameter("submit");
		try {
			switch (command) {
				case "faculty": {
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
					request.setAttribute("students", students );
					
					rd = request.getRequestDispatcher("/AttendanceGeneral.jsp");
					rd.forward(request, response);

					return;
				}
				case "student": {
					
					return;
				}
				default: {
					
				}
			}
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

}
