package com.cleanup.sophieca.todoc.repositories;


import androidx.lifecycle.LiveData;

import com.cleanup.sophieca.todoc.database.dao.TaskDao;
import com.cleanup.sophieca.todoc.model.Task;

import java.util.List;

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

    public LiveData<List<Task>> getTasks(){
        return taskDao.getTasks();
    }

    public LiveData<List<Task>> getTasksByAZ(){
        return taskDao.getTasksByAZ();
    }

    public LiveData<List<Task>> getTasksByZA(){
        return taskDao.getTasksByZA();
    }

    public LiveData<List<Task>> getTasksByMostRecent(){
        return taskDao.getTasksByMostRecent();
    }

    public LiveData<List<Task>> getTasksByLessRecent(){
        return taskDao.getTasksByLessRecent();
    }

}
