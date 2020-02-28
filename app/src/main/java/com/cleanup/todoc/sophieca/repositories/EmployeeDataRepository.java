package com.cleanup.todoc.sophieca.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.sophieca.database.dao.EmployeeDao;
import com.cleanup.todoc.sophieca.model.Employee;

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

    public void deleteEmployee(int employeeId) {
        employeeDao.deleteEmployee(employeeId);
    }
}
