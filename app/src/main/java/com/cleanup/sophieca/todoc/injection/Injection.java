package com.cleanup.sophieca.todoc.injection;

import android.content.Context;

import androidx.annotation.NonNull;

import com.cleanup.sophieca.todoc.database.TodocDatabase;
import com.cleanup.sophieca.todoc.model.Project;
import com.cleanup.sophieca.todoc.repositories.ProjectDataRepository;
import com.cleanup.sophieca.todoc.repositories.TaskDataRepository;
import com.cleanup.sophieca.todoc.utils.ViewModelFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by SOPHIE on 21/02/2020.
 */
public class Injection {

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
        ProjectDataRepository projectDataSource = provideProjectDataSource(context);
        TaskDataRepository taskDataSource = provideTaskDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(projectDataSource, taskDataSource, executor);
    }


    /**
     * Returns all the projects of the application.
     *
     * @return all the projects of the application
     */

    @NonNull
    public static Project[] getAllProjects() {
        return new Project[]{
                new Project(0, "Projet Tartampion", "#FFEADAD1"),
                new Project(0, "Projet Lucidia", "#FFB4CDBA"),
                new Project(0, "Projet Circus", "#FFA3CED2")
        };
    }

}
