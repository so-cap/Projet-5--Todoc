package com.cleanup.todoc.repositories;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

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

    public LiveData<List<Task>> tasksInAZOrder() throws ExecutionException, InterruptedException {
        return new AZOrderAsyncTask(taskDao).execute().get();
    }

    public LiveData<List<Task>> tasksInZAOrder() throws ExecutionException, InterruptedException {
        return new ZAOrderAsyncTask(taskDao).execute().get();
    }

    public LiveData<List<Task>> tasksByMostRecent() throws ExecutionException, InterruptedException {
        return new MostRecentOrderAsyncTask(taskDao).execute().get();
    }

    public LiveData<List<Task>> tasksByLessRecent() throws ExecutionException, InterruptedException {
        return new LessRecentOrderAsyncTask(taskDao).execute().get();
    }

    private static class AZOrderAsyncTask extends AsyncTask<Void, Void,LiveData<List<Task>>> {
        private TaskDao taskDao;

        private AZOrderAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected LiveData<List<Task>> doInBackground(Void...voids) {
           return taskDao.getTasksByAscendingOrder();
        }
    }

    private static class ZAOrderAsyncTask extends AsyncTask<Void, Void, LiveData<List<Task>>> {
        private TaskDao taskDao;

        private ZAOrderAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected LiveData<List<Task>> doInBackground(Void... voids) {
            return taskDao.getTasksByDescendingOrder();
        }
    }

    private static class MostRecentOrderAsyncTask extends AsyncTask<Void, Void, LiveData<List<Task>>> {
        private TaskDao taskDao;

        private MostRecentOrderAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected LiveData<List<Task>> doInBackground(Void... voids) {
            return taskDao.getTasksByMostRecentOrder();
        }
    }

    private static class LessRecentOrderAsyncTask extends AsyncTask<Void, Void, LiveData<List<Task>>> {
        private TaskDao taskDao;

        private LessRecentOrderAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected LiveData<List<Task>> doInBackground(Void... voids) {
            return taskDao.getTasksByLessRecentOrder();
        }
    }
}
