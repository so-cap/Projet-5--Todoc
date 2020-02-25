package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Employee;

import java.util.List;

/**
 * Created by SOPHIE on 21/02/2020.
 */
@Dao
public interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Employee employee);

    @Query("SELECT * FROM Employee WHERE id = :employeeId")
    LiveData<Employee> getEmployee(int employeeId);

    @Query("SELECT * FROM Employee WHERE email = :email AND password = :password")
    LiveData<Employee> getEmployee(String email, String password);



    @Query("SELECT * FROM Employee")
    LiveData<List<Employee>> getEmployees();


}
