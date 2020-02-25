package com.cleanup.todoc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Employee;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.EmployeeDataRepository;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by SOPHIE on 21/02/2020.
 */
public class TaskViewModel extends ViewModel {
    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final EmployeeDataRepository employeeDataSource;
    private final Executor executor;

    private Employee currentEmployee;

    public TaskViewModel(EmployeeDataRepository employeeDataSource, ProjectDataRepository projectDataSource,
                         TaskDataRepository taskDataSource, Executor executor) {
        this.employeeDataSource = employeeDataSource;
        this.projectDataSource = projectDataSource;
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    // -------------
    // FOR EMPLOYEE
    // -------------

    public LiveData<Employee> getEmployee(int id) {
        return employeeDataSource.getEmployee(id);
    }

    public LiveData<List<Employee>> getEmployees() {
        return employeeDataSource.getEmployees();
    }

    public void initEmployee(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

    public void createEmployee(final Employee employee) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                employeeDataSource.createEmployee(employee);
            }
        });
    }

    // -------------
    // FOR PROJECT
    // -------------

        public void createProject(final Project project){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    projectDataSource.createProject(project);
                }
            });
    }

    // TODO: peut être  à supprimer
   /* public LiveData<List<Project>> getAllProjects(){
        return projectDataSource.getAllProjects();
    }

    */

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
