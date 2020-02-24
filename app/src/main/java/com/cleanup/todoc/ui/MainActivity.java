package com.cleanup.todoc.ui;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.TaskViewModel;
import com.cleanup.todoc.ViewModelFactory;
import com.cleanup.todoc.injection.Injection;
import com.cleanup.todoc.model.Employee;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener, DialogInterface.OnShowListener, DialogInterface.OnDismissListener {
    private TaskViewModel taskViewModel;
    private LiveData<Employee> currentEmployee;
    private static final int dummyEmployeeId = 1;

    /**
     * List of all projects available in the application
     */
    private final Project[] allProjects = Project.getAllProjects();

    /**
     * List of all current tasks of the application
     */
    public List<Task> allTasks = new ArrayList<>();

    /**
     * The adapter which handles the list of tasks
     */
    private TasksAdapter adapter = new TasksAdapter(allTasks, this);

    /**
     * The sort method to be used to display tasks
     */
    @NonNull
    private SortMethod sortMethod = SortMethod.NONE;

    /**
     * Dialog to create a new task
     */
    @Nullable
    public AlertDialog newTaskDialog = null;

    /**
     * EditText that allows user to set the name of a task
     */
    @Nullable
    private EditText dialogEditText = null;

    /**
     * Spinner that allows the user to associate a project to a task
     */
    @Nullable
    private Spinner dialogSpinner = null;

    /**
     * The RecyclerView which displays the list of tasks
     */
    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private RecyclerView listTasks;

    /**
     * The TextView displaying the empty state
     */
    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView lblNoTasks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //this.deleteDatabase("TodocDatabase.db");
        configureViewModel();
        loginEmployee();

        listTasks = findViewById(R.id.list_tasks);
        lblNoTasks = findViewById(R.id.lbl_no_task);


        listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listTasks.setAdapter(adapter);

        if (currentEmployee.getValue() != null) {
            taskViewModel.getTasks(currentEmployee.getValue().getId()).observe(this, new Observer<List<Task>>() {
                @Override
                public void onChanged(List<Task> tasks) {
                    allTasks = tasks;
                    adapter.updateTasks(tasks);
                    showOrHideList(tasks);
                }
            });

            updateTasks();
        }

        findViewById(R.id.fab_add_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskDialog();
            }
        });

    }

    private void loginEmployee() {
        //int employeeId = findViewById(R.id.)
        currentEmployee = taskViewModel.getEmployee(dummyEmployeeId);
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        taskViewModel = new ViewModelProvider(this, viewModelFactory).get(TaskViewModel.class);
        taskViewModel.initEmployee(currentEmployee);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
            sortMethod = SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            sortMethod = SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            sortMethod = SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            sortMethod = SortMethod.RECENT_FIRST;
        }

        if (id == R.id.filter_alphabetical || id == R.id.filter_alphabetical_inverted
                || id == R.id.filter_oldest_first || id == R.id.filter_recent_first)
            updateTasks();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteTask(int id) {
        taskViewModel.deleteTask(id);
    }

    /**
     * Called when the user clicks on the positive button of the Create Task Dialog.
     *
     * @param dialogInterface the current displayed newTaskDialog
     */
    private void onTaskDialogPositiveButtonClick(DialogInterface dialogInterface) {
        // If newTaskDialog is open
        if (dialogEditText != null && dialogSpinner != null) {
            // Get the name of the task
            String taskName = dialogEditText.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;
            if (dialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) dialogSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }
            // If both project and name of the task have been set
            else if (taskProject != null) {
                // TODO: Replace this by id of persisted task
            /*    Task task = new Task(0,
                        taskProject.getId(),
                        taskName,
                        new Date().getTime()
                );

             */

                Task task = new Task();
                task.setName(taskName);
                task.setProjectId(taskProject.getId());
                task.setEmployeeId(1);
                task.setCreationTimestamp(new Date().getTime());

                taskViewModel.getEmployee(dummyEmployeeId).observe(this, new Observer<Employee>() {
                    @Override
                    public void onChanged(Employee employee) {
                        System.out.println(employee.getId());
                    }
                });

                addTask(task);

                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else {
                dialogInterface.dismiss();
            }
        }
        // If newTaskDialog is already closed
        else {
            dialogInterface.dismiss();
        }
    }

    /**
     * Shows the Dialog for adding a Task
     */
    private void showAddTaskDialog() {
        final AlertDialog dialog = getAddTaskDialog();

        dialog.show();

        dialogEditText = dialog.findViewById(R.id.txt_task_name);
        dialogSpinner = dialog.findViewById(R.id.project_spinner);

        populateDialogSpinner();
    }

    /**
     * Adds the given task to the list of created tasks.
     *
     * @param task the task to be added to the list
     */
    private void addTask(@NonNull Task task) {
        taskViewModel.createTask(task);
        lblNoTasks.setVisibility(View.GONE);
        listTasks.setVisibility(View.VISIBLE);
    }

    /**
     * Updates the list of tasks in the UI
     */
    private void updateTasks() {
        switch (sortMethod) {
            case ALPHABETICAL:
                Collections.sort(allTasks, new Task.TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(allTasks, new Task.TaskZAComparator());
                break;
            case RECENT_FIRST:
                Collections.sort(allTasks, new Task.TaskRecentComparator());
                break;
            case OLD_FIRST:
                Collections.sort(allTasks, new Task.TaskOldComparator());
                break;
            case NONE:
                break;
        }
        showOrHideList(allTasks);
        adapter.updateTasks(allTasks);
    }

    private void showOrHideList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            lblNoTasks.setVisibility(View.VISIBLE);
            listTasks.setVisibility(View.GONE);
        } else {
            lblNoTasks.setVisibility(View.GONE);
            listTasks.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Returns the newTaskDialog allowing the user to create a new task.
     *
     * @return the newTaskDialog allowing the user to create a new task
     */
    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(this);

        newTaskDialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        newTaskDialog.setOnShowListener(this);

        return newTaskDialog;
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        if (newTaskDialog != null) {
            Button button = newTaskDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    onTaskDialogPositiveButtonClick(newTaskDialog);
                }
            });
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (dialog == newTaskDialog) {
            dialogEditText = null;
            dialogSpinner = null;
            newTaskDialog = null;
        }
    }

    /**
     * Sets the data of the Spinner with projects to associate to a new task
     */
    private void populateDialogSpinner() {
        final ArrayAdapter<Project> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter);
        }
    }

    /**
     * List of all possible sort methods for task
     */
    private enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }

}
