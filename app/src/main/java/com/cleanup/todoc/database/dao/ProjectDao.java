package com.cleanup.todoc.database.dao;

import androidx.annotation.ColorInt;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

/**
 * Created by SOPHIE on 21/02/2020.
 */
@Dao
public interface ProjectDao {
    @Query("SELECT * FROM Project WHERE id = :projectId")
    Project getProjectById(long projectId);

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getAllProjects();

    @Query("SELECT * FROM Task WHERE projectId = :projectId AND employeeId = :employeeId")
    LiveData<List<Task>> getTasksByProject(long projectId, int employeeId);

    @Insert
    void insertProject(Project project);

    @Query("SELECT color FROM Project WHERE id = :id")
    Integer getProjectColor(long id);

    @Query("SELECT name FROM Project WHERE id = :id")
    String getProjectName(long id);

}
