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

    @Transaction
    @Query("SELECT * FROM Task")
    LiveData<List<TaskAndProject>> getTasksWithProjects();

    @Transaction
    @Query("SELECT * FROM Task ORDER BY name ASC")
    LiveData<List<TaskAndProject>> getTasksByAZ();

    @Transaction
    @Query("SELECT * FROM Task ORDER BY name DESC")
    LiveData<List<TaskAndProject>> getTasksByZA();

    @Transaction
    @Query("SELECT * FROM Task ORDER BY creationTimestamp DESC")
    LiveData<List<TaskAndProject>> getTasksByMostRecent();

    @Transaction
    @Query("SELECT * FROM Task ORDER BY creationTimestamp ASC")
    LiveData<List<TaskAndProject>> getTasksByLessRecent();

    @Transaction
    @Query("SELECT Task.* FROM Task INNER JOIN Project ON Task.projectId = Project.id AND Project.name LIKE '%' || :projectName || '%'")
    LiveData<List<TaskAndProject>> getFilteredTasks(String projectName);
}
