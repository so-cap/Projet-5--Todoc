package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.EmployeeDao;
import com.cleanup.todoc.model.Employee;

import java.util.List;

/**
 * Created by SOPHIE on 21/02/2020.
 */
public class EmployeeDataRepository {
    private final EmployeeDao employeeDao;

    public EmployeeDataRepository(EmployeeDao employeeDao){
        this.employeeDao = employeeDao;
    }

    public void createEmployee(Employee employee){
        employeeDao.insert(employee);
    }

    public LiveData<Employee> getEmployee(String email, String password){
        return employeeDao.getEmployee(email, password);
    }
}
