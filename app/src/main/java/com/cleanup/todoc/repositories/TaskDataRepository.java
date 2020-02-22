package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

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

    public void deleteTask(Task task){
     taskDao.delete(task);
    }

    public void deleteAllTasks(int employeeId){
        taskDao.deleteAllTasks(employeeId);
    }

    public LiveData<List<Task>> getTasks(int employeeId){
        return taskDao.getTasks(employeeId);
    }

    public void sortAZOrder(int employeeId){
        taskDao.getTasksByAscendingOrder(employeeId);
    }

    public void sortZAOrder(int employeeId){
        taskDao.getTasksByDescendingOrder(employeeId);
    }

    public void sortByMostRecent(int employeeId){
        taskDao.getTasksByMostRecentOrder(employeeId);
    }

    public void sortByLessRecent(int employeeId){
        taskDao.getTasksByLessRecentOrder(employeeId);
    }
}
