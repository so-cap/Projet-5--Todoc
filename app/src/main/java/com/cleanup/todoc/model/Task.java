package com.cleanup.todoc.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.cleanup.todoc.database.dao.ProjectDao;

import java.util.Comparator;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY
 * <p>
 * Changes 21/02/2020
 * @author Sophie
 */

@Entity
public class Task {
    /**
     * The unique identifier of the task
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * The unique identifier of the project associated to the task
     * and the identifier of the employee logged in.
     */
    private long projectId;

    /**
     * The name of the task
     */
    // Suppress warning because setName is called in constructor
    @SuppressWarnings("NullableProblems")
    @NonNull
    private String name;

    /**
     * The timestamp when the task has been created
     */
    private long creationTimestamp;

    /*
     * Instantiates a new Task.
     *
     * @param projectId         the unique identifier of the project associated to the task to set
     * @param id        the unique identifier of the task associated to the project
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     */
 /*   public Task(long id, long projectId,@NonNull String name, long creationTimestamp) {
        this.setId(id);
        this.setProjectId(projectId);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
    }

  */

    public Task()
    {}

    /**
     * Returns the unique identifier of the task.
     *
     * @return the unique identifier of the task
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the task.
     *
     * @param id the unique identifier of the task to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the unique identifier of the project associated to the task.
     *
     * @param projectId the unique identifier of the project associated to the task to set
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getProjectId() {
        return projectId;
    }


    /**
     * Returns the project associated to the task.
     *
     * @return the project associated to the task
     */
    @Nullable
    public Project getProject(long projectId) {
        return Project.getProjectById(projectId);
    }

    /**
     * Returns the name of the task.
     *
     * @return the name of the task
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task.
     *
     * @param name the name of the task to set
     */
    public void setName(@NonNull String name) {
        this.name = name;
    }

    /**
     * Sets the timestamp when the task has been created.
     *
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }
}
