package com.cleanup.sophieca.todoc.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class TaskAndProject {
     @Embedded private Task task;
     @Relation(
             parentColumn = "projectId",
             entityColumn = "id"
     ) private Project project;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
