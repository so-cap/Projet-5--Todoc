package com.cleanup.todoc.sophieca.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.sophieca.database.dao.TaskDao;
import com.cleanup.todoc.sophieca.model.Task;

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

    public void deleteTask(int id){
     taskDao.delete(id);
    }

    public LiveData<List<Task>> getTasks(int employeeId){
        return taskDao.getTasks(employeeId);
    }
}
