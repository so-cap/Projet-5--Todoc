package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * Created by SOPHIE on 21/02/2020.
 */
@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    //TODO: research how exactly it updates the data
    @Update
    void update(Task task);

    @Query("DELETE FROM Task WHERE id = :id")
    void delete(long id);

    @Query("DELETE FROM Task")
    void deleteAllTasks();

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    @Query("SELECT * FROM Task WHERE projectId = :projectId")
    LiveData<List<Task>> getTasksByProject(long projectId);

    @Query("SELECT * FROM Task ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> getTasksByMostRecentOrder();

    @Query("SELECT * FROM Task ORDER BY creationTimestamp ASC")
    LiveData<List<Task>> getTasksByLessRecentOrder();

    @Query("SELECT * FROM Task ORDER BY name ASC")
    LiveData<List<Task>> getTasksByAscendingOrder();

    @Query("SELECT * FROM Task ORDER BY name DESC")
    LiveData<List<Task>> getTasksByDescendingOrder();

}
