package com.cleanup.todoc.sophieca.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.sophieca.database.dao.ProjectDao;
import com.cleanup.todoc.sophieca.model.Project;

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
