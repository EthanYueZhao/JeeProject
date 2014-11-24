package com.project.data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the courseschedule database table.
 * 
 */
@Entity
@NamedQuery(name="Courseschedule.findAll", query="SELECT c FROM Courseschedule c")
public class Courseschedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idcourseschedule;

	@Temporal(TemporalType.DATE)
	private Date coursedate;

	//bi-directional many-to-one association to Attendance
	@OneToMany(mappedBy="courseschedule")
	private List<Attendance> attendances;

	//bi-directional many-to-one association to Course
	@ManyToOne
	private Course course;

	public Courseschedule() {
	}

	public int getIdcourseschedule() {
		return this.idcourseschedule;
	}

	public void setIdcourseschedule(int idcourseschedule) {
		this.idcourseschedule = idcourseschedule;
	}

	public Date getCoursedate() {
		return this.coursedate;
	}

	public void setCoursedate(Date coursedate) {
		this.coursedate = coursedate;
	}

	public List<Attendance> getAttendances() {
		return this.attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	public Attendance addAttendance(Attendance attendance) {
		getAttendances().add(attendance);
		attendance.setCourseschedule(this);

		return attendance;
	}

	public Attendance removeAttendance(Attendance attendance) {
		getAttendances().remove(attendance);
		attendance.setCourseschedule(null);

		return attendance;
	}

	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}