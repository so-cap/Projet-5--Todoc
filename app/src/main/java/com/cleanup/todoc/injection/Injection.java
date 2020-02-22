package com.cleanup.todoc.injection;

import android.content.Context;

import com.cleanup.todoc.ViewModelFactory;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Employee;
import com.cleanup.todoc.repositories.EmployeeDataRepository;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by SOPHIE on 21/02/2020.
 */
public class Injection {

    public static EmployeeDataRepository provideEmployeeDataSource(Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new EmployeeDataRepository(database.employeeDao());
    }

    public static ProjectDataRepository provideProjectDataSource(Context context){
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new ProjectDataRepository(database.projectDao());
    }

    public static TaskDataRepository provideTaskDataSource(Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new TaskDataRepository(database.taskDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        EmployeeDataRepository employeeDataSource = provideEmployeeDataSource(context);
        ProjectDataRepository projectDataSource = provideProjectDataSource(context);
        TaskDataRepository taskDataSource = provideTaskDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(employeeDataSource, projectDataSource, taskDataSource, executor);
    }


    public static Employee getDummyEmployee(){
        return new Employee(1,"Sophie","Cap", "sophie@email.com", "mdp" );
    }
}
