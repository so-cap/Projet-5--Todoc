package com.cleanup.sophieca.todoc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.sophieca.todoc.model.Project;
import com.cleanup.sophieca.todoc.model.Task;
import com.cleanup.sophieca.todoc.repositories.ProjectDataRepository;
import com.cleanup.sophieca.todoc.repositories.TaskDataRepository;

import java.util.List;

import java.util.concurrent.Executor;

/**
 * Created by SOPHIE on 21/02/2020.
 */
public class TaskViewModel extends ViewModel {
    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    public TaskViewModel(ProjectDataRepository projectDataSource,
                         TaskDataRepository taskDataSource, Executor executor) {
        this.projectDataSource = projectDataSource;
        this.taskDataSource = taskDataSource;
        this.executor = executor;
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

    public LiveData<List<Task>> getTasks() {
        return taskDataSource.getTasks();
    }

    public LiveData<List<Task>> getTasksByAZ(){
        return taskDataSource.getTasksByAZ();
    }

    public LiveData<List<Task>> getTasksByZA(){
        return taskDataSource.getTasksByZA();
    }

    public LiveData<List<Task>> getTasksByMostRecent(){
        return taskDataSource.getTasksByMostRecent();
    }

    public LiveData<List<Task>> getTasksByLessRecent(){
        return taskDataSource.getTasksByLessRecent();
    }



    public void createTask(final Task task) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskDataSource.createTask(task);
            }
        });
    }

    public void deleteTask(final long taskId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                taskDataSource.deleteTask(taskId);
            }
        });
    }


}
