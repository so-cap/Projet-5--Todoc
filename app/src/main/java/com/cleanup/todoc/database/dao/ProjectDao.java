package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * Created by SOPHIE on 21/02/2020.
 */
@Dao
public interface ProjectDao {
    @Query("SELECT * FROM Project WHERE id = :projectId AND employeeId = :employeeId ")
    LiveData<Project> getProject(int projectId, int employeeId);

    @Query("SELECT * FROM Project WHERE employeeId = :employeeId ")
    LiveData<List<Project>> getAllProjects(int employeeId);

    @Query("SELECT * FROM Project WHERE id = :projectId AND employeeId = :employeeId")
    LiveData<List<Task>> getTasksByProject(long projectId, int employeeId);

}
