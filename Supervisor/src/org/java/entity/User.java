package org.java.entity;

import java.util.HashSet;
import java.util.Set;


/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private UserRight userRight;
     private String name;
     private String pwd;
     private Set pics = new HashSet(0);


    // Constructors

    /** default constructor */
    public User() {
    }

	/** minimal constructor */
    public User(UserRight userRight, String name, String pwd) {
        this.userRight = userRight;
        this.name = name;
        this.pwd = pwd;
    }
    
    /** full constructor */
    public User(UserRight userRight, String name, String pwd, Set pics) {
        this.userRight = userRight;
        this.name = name;
        this.pwd = pwd;
        this.pics = pics;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public UserRight getUserRight() {
        return this.userRight;
    }
    
    public void setUserRight(UserRight userRight) {
        this.userRight = userRight;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return this.pwd;
    }
    
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Set getPics() {
        return this.pics;
    }
    
    public void setPics(Set pics) {
        this.pics = pics;
    }
   








}