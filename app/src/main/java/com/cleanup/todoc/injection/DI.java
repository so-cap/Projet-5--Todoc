package com.cleanup.todoc.injection;

import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Employee;

import java.util.Arrays;
import java.util.List;

/**
 * Created by SOPHIE on 25/02/2020.
 */
public class DI {
    private static ViewModel viewModel;
    private static List<Employee> dummyEmployee =
            Arrays.asList(new Employee(0, "Sophie", "Cap", "sophie@email.com", "mdp"),
            new Employee(0, "John", "Doe", "john@email.com", "mdp"));

    public static List<Employee> getDummyEmployees() {
        return dummyEmployee;
    }

    public static void setViewModel(ViewModel pViewModel) {
        viewModel = pViewModel;
    }

    public static ViewModel getViewModel() {
        return viewModel;
    }
}
