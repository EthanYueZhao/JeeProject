package com.project.model;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Schedule;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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

	public boolean signAttendance(User userInput,Courseschedule scheduleInput) {
		EntityManager em = emf.createEntityManager();
		try {
			EntityTransaction et = em.getTransaction();
			et.begin();
			Attendance att = new Attendance();
			att.setAttendance((byte) 1);	
			att.setUser(userInput);
			att.setCourseschedule(scheduleInput);
			em.persist(att);
			et.commit();
			em.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public ArrayList<Attendance> getAttendanceHistory(int course, User user) {
		EntityManager em = emf.createEntityManager();
		ArrayList<Attendance> attendanceReturn = new ArrayList<Attendance>();
		// get the student
		User student = user;

		// get course schedule of this course, so we can get attendance for this
		// get the schedule for this course
		ArrayList<Courseschedule> shcedule = getScheduleByCourse(course);

		// get attendance for all courses
		List<Attendance> attendanceGet = (List<Attendance>) student
				.getAttendances();
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

		// student this course
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
