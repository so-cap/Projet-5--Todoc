package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

/**
 * Created by SOPHIE on 21/02/2020.
 */
public class ProjectDataRepository {
    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao){
        this.projectDao = projectDao;
    }

   /* public void createProject(Project project){
        projectDao.insertProject(project);
    }

    public LiveData<List<Project>> getAllProjects(){
        return projectDao.getAllProjects();
    }
    */
}
