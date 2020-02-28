package com.cleanup.todoc.sophieca.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by SOPHIE on 21/02/2020.
 */
@Entity
public class Employee {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private int taskId;

    public Employee(int id, String firstname, String lastname, String email, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

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

    public void setTaskId(int taskId){
        this.taskId = taskId;
    }

    public int getTaskId() {
        return taskId;
    }
}
