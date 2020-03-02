package com.cleanup.sophieca.todoc.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by SOPHIE on 21/02/2020.
 */
@Entity
public class Employee implements Serializable {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private int taskId;
    private boolean firstConnection = true;

    public Employee(int id, String firstname, String lastname, String email, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public boolean isNew(){
        return firstConnection;
    }
    
    public void setFirstConnection(boolean firstConnection){
        this.firstConnection = firstConnection;
    }

    public boolean getFirstConnection(){return firstConnection;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {this.password = password;}

    public void setTaskId(int taskId){
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }
}
