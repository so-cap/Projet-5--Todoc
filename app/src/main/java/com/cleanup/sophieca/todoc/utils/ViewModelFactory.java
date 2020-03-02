package com.cleanup.sophieca.todoc.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.sophieca.todoc.TodocViewModel;
import com.cleanup.sophieca.todoc.repositories.EmployeeDataRepository;
import com.cleanup.sophieca.todoc.repositories.ProjectDataRepository;
import com.cleanup.sophieca.todoc.repositories.TaskDataRepository;

import java.util.concurrent.Executor;

/**
 * Created by SOPHIE on 22/02/2020.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final EmployeeDataRepository employeeDataSource;
    private final ProjectDataRepository projectDataSource;
    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    public ViewModelFactory(EmployeeDataRepository employeeDataSource, ProjectDataRepository projectDataSource,
                            TaskDataRepository taskDataSource, Executor executor) {
        this.employeeDataSource = employeeDataSource;
        this.projectDataSource = projectDataSource;
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TodocViewModel.class)) {
            return (T) new TodocViewModel(employeeDataSource, projectDataSource, taskDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
