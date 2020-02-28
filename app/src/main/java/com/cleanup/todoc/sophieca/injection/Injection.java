package com.cleanup.todoc.sophieca.injection;

import android.content.Context;

import com.cleanup.todoc.sophieca.utils.ViewModelFactory;
import com.cleanup.todoc.sophieca.database.TodocDatabase;
import com.cleanup.todoc.sophieca.repositories.EmployeeDataRepository;
import com.cleanup.todoc.sophieca.repositories.ProjectDataRepository;
import com.cleanup.todoc.sophieca.repositories.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by SOPHIE on 21/02/2020.
 */
public class Injection {

    private static EmployeeDataRepository provideEmployeeDataSource(Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new EmployeeDataRepository(database.employeeDao());
    }

    private static ProjectDataRepository provideProjectDataSource(Context context){
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new ProjectDataRepository(database.projectDao());
    }

    private static TaskDataRepository provideTaskDataSource(Context context) {
        TodocDatabase database = TodocDatabase.getInstance(context);
        return new TaskDataRepository(database.taskDao());
    }

    private static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        EmployeeDataRepository employeeDataSource = provideEmployeeDataSource(context);
        ProjectDataRepository projectDataSource = provideProjectDataSource(context);
        TaskDataRepository taskDataSource = provideTaskDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(employeeDataSource, projectDataSource, taskDataSource, executor);
    }
}
