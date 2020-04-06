package com.cleanup.sophieca.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.cleanup.sophieca.todoc.model.Task;
import com.cleanup.sophieca.todoc.model.TaskAndProject;

import java.util.List;

/**
 * Created by SOPHIE on 21/02/2020.
 */
@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Query("DELETE FROM Task WHERE id = :id")
    void delete(long id);

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    @Transaction
    @Query("SELECT * FROM Task")
    LiveData<List<TaskAndProject>> getTasksWithProjects();

    @Query("SELECT * FROM Task ORDER BY name ASC")
    LiveData<List<TaskAndProject>> getTasksByAZ();

    @Query("SELECT * FROM Task ORDER BY name DESC")
    LiveData<List<TaskAndProject>> getTasksByZA();

    @Query("SELECT * FROM Task ORDER BY creationTimestamp DESC")
    LiveData<List<TaskAndProject>> getTasksByMostRecent();

    @Query("SELECT * FROM Task ORDER BY creationTimestamp ASC")
    LiveData<List<TaskAndProject>> getTasksByLessRecent();

}
