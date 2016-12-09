package org.java.entity;

import java.sql.Timestamp;


/**
 * Pic entity. @author MyEclipse Persistence Tools
 */

public class Pic  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private User user;
     private String name;
     private String path;
     private Timestamp time;


    // Constructors

    /** default constructor */
    public Pic() {
    }

	/** minimal constructor */
    public Pic(User user) {
        this.user = user;
    }
    
    public Pic(User user, String name, String path) {
		super();
		this.user = user;
		this.name = name;
		this.path = path;
	}

	/** full constructor */
    public Pic(User user, String name, String path, Timestamp time) {
        this.user = user;
        this.name = name;
        this.path = path;
        this.time = time;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }

    public Timestamp getTime() {
        return this.time;
    }
    
    public void setTime(Timestamp time) {
        this.time = time;
    }
   








}