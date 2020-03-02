package com.cleanup.sophieca.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.sophieca.todoc.model.Employee;

import java.util.List;

/**
 * Created by SOPHIE on 21/02/2020.
 */
@Dao
public interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Employee employee);

    @Update
    void update(Employee employee);

    @Query("SELECT * FROM Employee")
    LiveData<List<Employee>> getEmployees();

    @Query("SELECT * FROM Employee WHERE email = :email AND password = :password")
    LiveData<Employee> getEmployee(String email, String password);

    @Query("DELETE FROM Employee WHERE id = :employeeId")
    void deleteEmployee(int employeeId);
}
