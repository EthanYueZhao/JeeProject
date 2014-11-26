package com.project.data;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the course database table.
 * 
 */
@Entity
@NamedQuery(name="Course.findAll", query="SELECT c FROM Course c")
@NamedQueries({
	@NamedQuery(name = "getCoursebyName", query = "Select c From Course c Where c.coursename=?1")
})
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idcourse;

	private String coursename;

	//bi-directional many-to-many association to User
	@ManyToMany
	@JoinTable(
		name="course_user"
		, joinColumns={
			@JoinColumn(name="course_idcourse")
			}
		, inverseJoinColumns={
			@JoinColumn(name="user_iduser")
			}
		)
	private List<User> users;

	//bi-directional many-to-one association to Courseschedule
	@OneToMany(mappedBy="course")
	private List<Courseschedule> courseschedules;

	public Course() {
	}

	public int getIdcourse() {
		return this.idcourse;
	}

	public void setIdcourse(int idcourse) {
		this.idcourse = idcourse;
	}

	public String getCoursename() {
		return this.coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Courseschedule> getCourseschedules() {
		return this.courseschedules;
	}

	public void setCourseschedules(List<Courseschedule> courseschedules) {
		this.courseschedules = courseschedules;
	}

	public Courseschedule addCourseschedule(Courseschedule courseschedule) {
		getCourseschedules().add(courseschedule);
		courseschedule.setCourse(this);

		return courseschedule;
	}

	public Courseschedule removeCourseschedule(Courseschedule courseschedule) {
		getCourseschedules().remove(courseschedule);
		courseschedule.setCourse(null);

		return courseschedule;
	}

}