package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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

    @Delete
    void delete(Task task);

    @Query("DELETE FROM Task WHERE employeeId = :employeeId")
    void deleteAllTasks(int employeeId);

    @Query("SELECT * FROM Task WHERE employeeId = :employeeId")
    LiveData<List<Task>> getTasks(int employeeId);

    @Query("SELECT * FROM Task WHERE employeeId = :employeeId ORDER BY creationTimestamp ASC")
    LiveData<List<Task>> getTasksByAscendingOrder(int employeeId);

    @Query("SELECT * FROM Task WHERE employeeId = :employeeId ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> getTasksByDescendingOrder(int employeeId);

}
