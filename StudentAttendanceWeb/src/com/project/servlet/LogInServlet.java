package com.project.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.data.User;
import com.project.model.UserManager;

/**
 * Servlet implementation class LogInServlet
 */
@WebServlet("/LogIn")
public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogInServlet() {
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
		HttpSession session=request.getSession(false);
		session.invalidate();
		request.getRequestDispatcher("/LogIn.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String s1 = request.getParameter("username");
		String s2 = request.getParameter("password");
		System.out.println(s1 + "-----" + s2);
		User login = new User();
		login.setUsername(s1);
		login.setPassword(s2);

		RequestDispatcher rd=null;
		UserManager um = new UserManager();
		User result = um.getUser(login);
		if (result != null) {
			HttpSession session = request.getSession();
			session.setAttribute("userName", result.getUsername());
			if (result.getType() == 0) {
				session.setAttribute("userType", "student");
			} else {
				session.setAttribute("userType", "faculty");
			}
			
			Date current = Calendar.getInstance().getTime();
			session.setAttribute("currentTime", current);
			rd = request.getRequestDispatcher("/MainPage.jsp");
		}else{
			request.setAttribute("message", "User doesn't exist!");
			rd=request.getRequestDispatcher("/LogIn.jsp");
		}
		rd.forward(request, response);

		
	}

}
