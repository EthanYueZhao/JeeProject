package com.project.model;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

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
		try {
			EntityManager em = emf.createEntityManager();
			Query q = em.createNamedQuery("getUser", User.class);
			q.setParameter(1, user.getUsername());
			q.setParameter(2, user.getPassword());
			u = (User) q.getSingleResult();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("-----------------------------getUser() crashed------------------------------");
			e.printStackTrace();
		}

		return u;

	}

}
