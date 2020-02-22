package com.cleanup.todoc.repositories;

import com.cleanup.todoc.database.dao.EmployeeDao;
import com.cleanup.todoc.model.Employee;

/**
 * Created by SOPHIE on 21/02/2020.
 */
public class EmployeeDataRepository {
    private final EmployeeDao employeeDao;

    public EmployeeDataRepository(EmployeeDao employeeDao){
        this.employeeDao = employeeDao;
    }


    public void createEmployee(Employee employee){
        employeeDao.createEmployee(employee);
    }

    public void getEmployee(int employeeId){
        employeeDao.getEmployee(employeeId);
    }
}
