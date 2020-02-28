package com.cleanup.sophieca.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.sophieca.todoc.model.Project;

import java.util.List;

/**
 * Created by SOPHIE on 21/02/2020.
 */
@Dao
public interface ProjectDao {

    @Insert
    void insertProject(Project project);

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getAllProjects();
}
