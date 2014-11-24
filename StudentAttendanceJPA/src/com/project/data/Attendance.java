package com.project.data;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the attendance database table.
 * 
 */
@Entity
@NamedQuery(name="Attendance.findAll", query="SELECT a FROM Attendance a")
public class Attendance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idattendance;

	private byte attendance;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	//bi-directional many-to-one association to Courseschedule
	@ManyToOne
	private Courseschedule courseschedule;

	public Attendance() {
	}

	public int getIdattendance() {
		return this.idattendance;
	}

	public void setIdattendance(int idattendance) {
		this.idattendance = idattendance;
	}

	public byte getAttendance() {
		return this.attendance;
	}

	public void setAttendance(byte attendance) {
		this.attendance = attendance;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Courseschedule getCourseschedule() {
		return this.courseschedule;
	}

	public void setCourseschedule(Courseschedule courseschedule) {
		this.courseschedule = courseschedule;
	}

}