package com.project.model;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.Order;

import com.project.data.Course;
import com.project.data.User;

public class UserManager {
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

	public UserManager() {
	}

	public User getUser(User user) {
		User u = null;
		User usr = null;
		EntityManager em = emf.createEntityManager();
		EntityManager emm = emf.createEntityManager();
		try {
			
			Query q = em.createNamedQuery("getUser", User.class);
			q.setParameter(1, user.getUsername());
			q.setParameter(2, user.getPassword());
			u = (User) q.getSingleResult();
			
			usr = emm.find(User.class, u.getIduser());
			System.out.println("Product  : " + usr.getCourses());
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out
					.println("-----------------------------getUser() crashed------------------------------");
			e.printStackTrace();
		} finally {
			em.close();
			emm.close();
		}

		return usr;

	}

	public List<Course> getAllCourses() {
		List<Course> courses = new ArrayList<Course>();

		EntityManager em = emf.createEntityManager();
		try {

			em.getTransaction().begin();
			courses = em.createQuery("Course.findAll").getResultList();
			// em.getTransaction().commit();

		} finally {
			em.close();
		}
		return courses;
	}

	public Course getCourse(int courseCode) {

		EntityManager em = emf.createEntityManager();
		EntityManager emm = emf.createEntityManager();
		Course c, course = null;
		try {
			//em.getTransaction().begin();
			c = em.find(Course.class, courseCode);
			//em.detach(c);
			course = emm.find(Course.class, c.getIdcourse());
			
		} finally {
			em.close();
		}
		return course;
	}
}
