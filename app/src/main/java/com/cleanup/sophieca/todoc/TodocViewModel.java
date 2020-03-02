package com.cleanup.sophieca.todoc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.sophieca.todoc.model.Employee;
import com.cleanup.sophieca.todoc.model.Project;
import com.cleanup.sophieca.todoc.model.Task;
import com.cleanup.sophieca.todoc.repositories.EmployeeDataRepository;
import com.cleanup.sophieca.todoc.repositories.ProjectDataRepository;
import com.cleanup.sophieca.todoc.repositories.TaskDataRepository;

import java.util.List;

import java.util.concurrent.Executor;

/**
 * Created by SOPHIE on 21/02/2020.
 */
public class TodocViewModel extends ViewModel {
    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final EmployeeDataRepository employeeDataSource;
    private final Executor executor;

    public TodocViewModel(EmployeeDataRepository employeeDataSource, ProjectDataRepository projectDataSource,
                          TaskDataRepository taskDataSource, Executor executor) {
        this.employeeDataSource = employeeDataSource;
        this.projectDataSource = projectDataSource;
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    // -------------
    // FOR EMPLOYEE
    // -------------

    public LiveData<Employee> getEmployee(String email, String password) {
        return employeeDataSource.getEmployee(email, password);
    }

    public LiveData<List<Employee>> getEmployees() {
        return employeeDataSource.getEmployees();
    }

    void createEmployee(final Employee employee) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                employeeDataSource.createEmployee(employee);
            }
        });
    }

    public void updateEmployee(Employee employee){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                employeeDataSource.updateEmployee(employee);
            }
        });
    }

    public void deleteEmployee(final int employeeId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                employeeDataSource.deleteEmployee(employeeId);
            }
        });
    }

    // -------------
    // FOR PROJECT
    // -------------

    public LiveData<List<Project>> getAllProjects() {
        return projectDataSource.getAllProjects();
    }

    // -------------
    // FOR TASK
    // -------------

    public LiveData<List<Task>> getTasks(int employeeId) {
        return taskDataSource.getTasks(employeeId);
    }

    public void createTask(final Task task) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskDataSource.createTask(task);
            }
        });
    }

    public void deleteTask(final int taskId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskDataSource.deleteTask(taskId);
            }
        });
    }

}
