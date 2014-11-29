package com.project.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.project.data.Attendance;
import com.project.data.Course;
import com.project.data.Courseschedule;
import com.project.data.User;

public class AttendanceManager {

	private static EntityManagerFactory emf;
	static {
		try {
			InitialContext ctx = new InitialContext();
			emf = (EntityManagerFactory) ctx
					.lookup("java:/StudentsAttendanceEMF");
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
	}

	public boolean signAttendance(User userInput, Courseschedule scheduleInput) {
		EntityManager em = emf.createEntityManager();
		try {
			EntityTransaction et = em.getTransaction();
			et.begin();

			TypedQuery<Attendance> queryAttendance = em.createNamedQuery(
					"Attendance.getAttendanceByUserAndSchedule",
					Attendance.class);
			queryAttendance.setParameter("userObj", userInput);
			queryAttendance.setParameter("courseScheduleObj", scheduleInput);
			// get the course object
			Attendance att = (Attendance) queryAttendance.getSingleResult();
			att.setAttendance((byte) 1);
			em.merge(att);
			et.commit();
			em.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// check if have attendance today, for the course and the user
	public Attendance getAttendance(User userInput, int courseID)
			throws ParseException {
		Attendance att = null;
		EntityManager em = emf.createEntityManager();

		TypedQuery<Course> queryCourse = em.createNamedQuery(
				"Course.getCoursebyID", Course.class);
		queryCourse.setParameter(1, courseID);
		// get the course object
		Course c = (Course) queryCourse.getSingleResult();

		// get schedule first, by course and current date
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String currentString = df.format(new Date());
		Date CurrentDate = df.parse(currentString);
		TypedQuery<Courseschedule> querySchedule = em.createNamedQuery(
				"Courseschedule.getScheduleFromCourseIDAndDate",
				Courseschedule.class);
		querySchedule.setParameter(1, c);
		querySchedule.setParameter(2, CurrentDate);
		Courseschedule scheduleGet = null;
		try {
			// null means no schedule today
			scheduleGet = querySchedule.getSingleResult();
		} catch (NoResultException e) {
			// Ignore this exception, I will handle it in my code
		}

		if (scheduleGet != null) {
			TypedQuery<Attendance> queryAttendance = em.createNamedQuery(
					"Attendance.getAttendanceByUserAndSchedule",
					Attendance.class);
			queryAttendance.setParameter("userObj", userInput);
			queryAttendance.setParameter("courseScheduleObj", scheduleGet);
			// get the course object
			try {
				att = (Attendance) queryAttendance.getSingleResult();
			} catch (NoResultException e) {
				// Ignore this exception, I will handle it in my code
			}
		}
		em.close();
		return att;
	}

	public ArrayList<Attendance> getAttendanceHistory(int course, User user) {
		EntityManager em = emf.createEntityManager();

		ArrayList<Attendance> attendanceReturn = new ArrayList<Attendance>();
		// get the student
		User student = user;

		// get course schedule of this course, so we can get attendance for this
		// get the schedule for this course
		ArrayList<Courseschedule> shcedule = getScheduleByCourse(course);

		// Important, clear cache programmatically to update the backend User
		// Entity
		// em.getEntityManagerFactory().getCache().evictAll();
		// get attendance for all courses
		// List<Attendance> attendanceGet = (List<Attendance>) student
		// .getAttendances();
		List<Attendance> attendanceGet = null;
		TypedQuery<Attendance> queryAttendanceByUser = em.createNamedQuery(
				"User.getAllAttendanceForStudent", Attendance.class);
		queryAttendanceByUser.setParameter("id", student.getIduser());
		try {
			attendanceGet = (List<Attendance>) queryAttendanceByUser
					.getResultList();
		} catch (NoResultException e) {
			// Ignore this exception, I will handle it in my code
		}

		if (attendanceGet.isEmpty()) {
			attendanceReturn = null;
		} else {
			// get attendance for this course
			for (Attendance loopValue : attendanceGet) {
				for (Courseschedule s : shcedule) {
					if (loopValue.getCourseschedule().getIdcourseschedule() == s
							.getIdcourseschedule()) {
						attendanceReturn.add(loopValue);
					}
				}
			}
		}
		em.close();
		return attendanceReturn;
	}

	public ArrayList<Courseschedule> getScheduleByCourse(int courseIn) {
		EntityManager em = emf.createEntityManager();

		TypedQuery<Course> queryCourse = em.createNamedQuery(
				"Course.getCoursebyID", Course.class);
		queryCourse.setParameter(1, courseIn);
		// get the course object
		Course c = (Course) queryCourse.getSingleResult();

		TypedQuery<Courseschedule> querySchedule = em.createNamedQuery(
				"Courseschedule.getScheduleFromCourse", Courseschedule.class);
		// find schedule by course
		querySchedule.setParameter(1, c);
		ArrayList<Courseschedule> shcedule = (ArrayList<Courseschedule>) querySchedule
				.getResultList();
		em.close();

		return shcedule;
	}

	public int getAttendedNum(ArrayList<Attendance> attendanceList) {
		int count = 0;
		for (Attendance value : attendanceList) {
			if (value.getAttendance() == 1) {
				count++;
			}
		}
		return count;
	}

}
