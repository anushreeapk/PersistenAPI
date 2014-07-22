/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.eclipse.persistence.annotations.CascadeOnDelete;

/**
 *
 * @author Anushree
 */

@NamedQueries({
    @NamedQuery(name = Course.GET_BY_NAME, query = "SELECT c FROM Course c WHERE c.courseName = :name"),
    @NamedQuery(name = Course.DELETE_BY_NAME, query = "DELETE FROM Course c WHERE c.courseName = :name"),
    @NamedQuery(name = Course.DELETE_ALL, query = "DELETE FROM Course c")
// NOTE: While tempting... this does not work b/c JPQL SELECT clause must specify only single-valued expressions:
// @NamedQuery(name = Team.GET_BY_NAME, query = "SELECT t.roster FROM Team t WHERE t.teamName = :name"),
})

@Entity
public class Course implements Serializable {

    /**
     * Name of JPQL query string to retrieve the players in a named team.
     */
    public static final String GET_BY_NAME = "Course.get_by_name";
    /**
     * Name of JPQL query string to delete a team given its name.
     */
    public static final String DELETE_BY_NAME = "Course.delete_by_name";
    /**
     * Name of JPQL query string to delete all teams.
     */
    public static final String DELETE_ALL = "Course.delete";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @javax.persistence.Column(name = "course_name", nullable = false, unique = true, length = 100)
    private String courseName;
    private String department;

    /* For a bidirectional relationship, the annotation below defines 
     * the inverse side of a ManyToOne relationship: a
     * team has many players and relates it to the owning side,
     * in this case the team field of a Player. Thus, given a team T, then
     * the following must be true for every player P in T.players: T == P.getTeam()
     */
    @OneToMany(mappedBy = "course", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @CascadeOnDelete   // NOTE: This is specific to JPA Provider EclipseLink, thus NOT portable.
    private Collection<Student> studentsEnrolled;


public class Course {
  studentsEnrolled = new HashSet<>();  
  
}

/**
 * 
 */

public Course(String name, String department) {
        this.courseName = name;
        this.department = department;
        studentsEnrolled = new HashSet<>();
    }

public Integer getId() {
        return this.id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

     public String getCourseName() {
        return courseName;
    }
     
     public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
      public String getDepartment() {
        return department;
    }
       public void setDepartment(String department) {
        this.department = department;
    }
        public Collection<Student> getStudentsEntrolled() {
        return studentsEnrolled;
    }
         public void setStudentsEnrolled(Collection<Student> students) {
        for (Student p : students) {
            addStudent(p);
        }
}
    
  public void addStudent(Student student) {
        studentsEnrolled.add(student);
        if (student.getCourse() != this) {
            student.setCourse(this);
        }
    }   
  
   public boolean removeStudent(Student student) {
        boolean success = studentsEnrolled.remove(student);

        if (success && student.getCourse() == this) {
            student.setCourse(null);
        }

        return success;
    }
   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

     @Override
    public boolean equals(Object object) {
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
        return this.id != null && this.id.equals(other.id);
    }
    
     @Override
    public String toString() {
        return "Course[name=" + courseName + ", Students Enrolled=" + studentsEnrolled + "]";
    }
}

     