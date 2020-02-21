package com.cleanup.todoc;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Employee;
import com.cleanup.todoc.repositories.EmployeeDataRepository;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.concurrent.Executor;

/**
 * Created by SOPHIE on 21/02/2020.
 */
public class TaskViewModel {
    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final EmployeeDataRepository employeeDataSource;
    private final Executor executor;

    private LiveData<Employee> currentEmployee;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource,
                         EmployeeDataRepository employeeDataSource, Executor executor){
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.employeeDataSource = employeeDataSource;
        this.executor = executor;
    }
}
