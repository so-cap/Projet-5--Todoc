package com.cleanup.todoc.repositories;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by SOPHIE on 21/02/2020.
 */
public class TaskDataRepository {
    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao){
        this.taskDao = taskDao;

    }

    public void createTask(Task task){
        taskDao.insert(task);
    }

    public void deleteTask(long id){
     taskDao.delete(id);
    }

    public void deleteAllTasks(){
        taskDao.deleteAllTasks();
    }

    public LiveData<List<Task>> getTasks(){
        return taskDao.getTasks();
    }

    public LiveData<List<Task>> tasksInAZOrder(){
        return taskDao.getTasksByAscendingOrder();
    }

    public LiveData<List<Task>> tasksInZAOrder() {
        return taskDao.getTasksByDescendingOrder();
    }

    public LiveData<List<Task>> tasksByMostRecent() {
        return taskDao.getTasksByMostRecentOrder();
    }

    public LiveData<List<Task>> tasksByLessRecent() {
        return taskDao.getTasksByLessRecentOrder();
    }
}
