package com.cleanup.sophieca.todoc.repositories;


import androidx.lifecycle.LiveData;

import com.cleanup.sophieca.todoc.database.dao.ProjectDao;
import com.cleanup.sophieca.todoc.model.Project;

import java.util.List;

/**
 * Created by SOPHIE on 21/02/2020.
 */
public class ProjectDataRepository {
    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao){
        this.projectDao = projectDao;
    }

    public LiveData<List<Project>> getAllProjects(){
        return projectDao.getAllProjects();
    }
}
