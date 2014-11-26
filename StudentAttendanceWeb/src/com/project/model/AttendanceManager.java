package com.project.model;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
			emf = (EntityManagerFactory) ctx.lookup("java:/StudentsAttendanceEMF");
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
	}

	public ArrayList<Attendance> getAttendanceHistory(String course, String userName){
		EntityManager em = emf.createEntityManager();
		ArrayList<Attendance> attendanceReturn = null;
		//get the student
		User student = em.find(User.class, userName);
		
//		TypedQuery<Attendance> query = em.createNamedQuery("Attendance.findAttendance",
//				Attendance.class);
//		ArrayList<Attendance> attendanceReturn = (ArrayList<Attendance>) query
//				.getResultList();
		
		//get course schedule of this course, so we can get attendance for this student this course
		TypedQuery<Course> queryCourse = em.createNamedQuery("Course.getCoursebyName",
				Course.class);
		queryCourse.setParameter( 1, course);
		//get the course object
		Course c = (Course) queryCourse.getSingleResult();
		//get the schedule for this course
		ArrayList<Courseschedule> shcedule = getScheduleByCourse(c);
		
		//get attendance for all courses
		ArrayList<Attendance> attendanceGet = (ArrayList<Attendance>) student.getAttendances();
		if (attendanceGet.isEmpty()) {
			attendanceReturn = null;
		}
		else {
			//get attendance for this course
			for (Attendance loopValue : attendanceGet) {
				for(Courseschedule s : shcedule) {
					if (loopValue.getCourseschedule() == s) {
						attendanceReturn.add(loopValue);
					}
				}
			}
		}
		em.close();
		return attendanceReturn;
	}
	
	public ArrayList<Courseschedule> getScheduleByCourse(Course c)
	{
		EntityManager em = emf.createEntityManager();
		
		TypedQuery<Courseschedule> querySchedule = em.createNamedQuery("Courseschedule.getScheduleFromCourse",
				Courseschedule.class);
			//find schedule by course
			querySchedule.setParameter( 1, c);
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
