package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Employee;

/**
 * Created by SOPHIE on 21/02/2020.
 */
@Dao
public interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createEmployee(Employee employee);

    @Query("SELECT * FROM Employee WHERE id = :employeeId")
    LiveData<Employee> getEmployee(int employeeId);

}
