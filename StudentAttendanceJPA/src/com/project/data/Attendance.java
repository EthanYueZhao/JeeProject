package com.project.data;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the attendance database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Attendance.findAll", query="SELECT a FROM Attendance a"),
	@NamedQuery(name = "Attendance.getAttendanceByUserAndSchedule", query = "Select a From Attendance a Where a.user = :userObj And a.courseschedule = :courseScheduleObj "),
	@NamedQuery(name="User.getAllAttendanceForStudent", query="SELECT a FROM Attendance a JOIN a.user u WHERE u.iduser = :id")
	//SELECT p FROM Teacher t JOIN t.phones p WHERE t.firstName = :firstName
})public class Attendance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idattendance;

	private byte attendance;

	//bi-directional many-to-one association to Courseschedule
	@ManyToOne
	private Courseschedule courseschedule;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

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

	public Courseschedule getCourseschedule() {
		return this.courseschedule;
	}

	public void setCourseschedule(Courseschedule courseschedule) {
		this.courseschedule = courseschedule;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}