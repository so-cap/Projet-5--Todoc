package com.cleanup.todoc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.injection.Injection;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;
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

    //  public void createProject(Project project){ projectDataSource.createProject(project); }

    // TODO: peut être  à supprimer
   /* public LiveData<List<Project>> getAllProjects(){
        return projectDataSource.getAllProjects();
    }

    */

    // -------------
    // FOR TASK
    // -------------

    public LiveData<List<Task>> getTasks() {
        return taskDataSource.getTasks();
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

    public LiveData<List<Task>> tasksInAZOrder(){
            return Transformations.switchMap(taskDataSource.getTasks(), tasks -> taskDataSource.tasksInAZOrder());
    }

    public LiveData<List<Task>> tasksInZAOrder() {
            return taskDataSource.tasksInZAOrder();
    }

    public LiveData<List<Task>> tasksByMostRecent() {
            return taskDataSource.tasksByMostRecent();
    }

    public LiveData<List<Task>> tasksByLessRecent() {
            return taskDataSource.tasksByLessRecent();
    }

}
