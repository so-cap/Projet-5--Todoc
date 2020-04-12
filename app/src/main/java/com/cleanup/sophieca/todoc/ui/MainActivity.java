package com.cleanup.sophieca.todoc.ui;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cleanup.sophieca.todoc.R;
import com.cleanup.sophieca.todoc.TaskViewModel;
import com.cleanup.sophieca.todoc.databinding.ActivityMainBinding;
import com.cleanup.sophieca.todoc.injection.Injection;
import com.cleanup.sophieca.todoc.model.Project;
import com.cleanup.sophieca.todoc.model.Task;
import com.cleanup.sophieca.todoc.model.TaskAndProject;
import com.cleanup.sophieca.todoc.utils.ViewModelFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener, DialogInterface.OnShowListener, DialogInterface.OnDismissListener {
    private TaskViewModel viewModel;

    /**
     * List of all projects available in the application
     */
    private List<Project> allProjects = new ArrayList<>();

    /**
     * List of all current tasks of the application
     */
    public List<TaskAndProject> allTasks = new ArrayList<>();

    /**
     * The adapter which handles the list of tasks
     */
    private TasksAdapter adapter = new TasksAdapter(allTasks, this);

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

    private ActivityMainBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBinding();
        setSupportActionBar(binding.myToolbar);
        configureViewModel();
        configureRecyclerView();
        observeTasks();
        initProjects();
        initSearchBar();
        setupListener();
    }

    private void setupBinding() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }


    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(TaskViewModel.class);
    }

    private void configureRecyclerView() {
        binding.listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.listTasks.setAdapter(adapter);
    }

    private void observeTasks() {
        viewModel.getTasksWithProjects().observe(this, this::updateTasks);
    }

    private void initProjects() {
        viewModel.getAllProjects().observe(this, projects -> {
            allProjects = projects;
        });
    }

    private void initSearchBar() {
        binding.searchBarText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.getFilteredTasks(s.toString()).observe(MainActivity.this, MainActivity.this::updateTasks);
            }
        });
    }

    private void setupListener() {
        binding.fabAddTask.setOnClickListener(view1 -> showAddTaskDialog());
        binding.searchBar.setOnClickListener(this::hideSearchBar);
    }

    public void hideSearchBar(View view) {
        binding.searchBar.setVisibility(View.GONE);
        binding.searchBarText.setText("");
        updateTasks(allTasks);
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
    public void onDismiss(DialogInterface dialog) {
        if (dialog == newTaskDialog) {
            dialogEditText = null;
            dialogSpinner = null;
            newTaskDialog = null;
        }
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        if (newTaskDialog != null) {
            Button button = newTaskDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> onTaskDialogPositiveButtonClick(newTaskDialog));
        }
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
                Task task = new Task(0, taskProject.getId(), taskName, new Date().getTime());

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
     * Adds the given task to the list of created tasks.
     *
     * @param task the task to be added to the list
     */
    private void addTask(@NonNull Task task) {
        viewModel.createTask(task);
    }

    @Override
    public void onDeleteTask(long id) {
        viewModel.deleteTask(id);
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
            viewModel.getTasksByAZ().observe(this, this::updateTasks);
        } else if (id == R.id.filter_alphabetical_inverted) {
            viewModel.getTasksByZA().observe(this, this::updateTasks);
        } else if (id == R.id.filter_oldest_first) {
            viewModel.getTasksByLessRecent().observe(this, this::updateTasks);
        } else if (id == R.id.filter_recent_first) {
            viewModel.getTasksByMostRecent().observe(this, this::updateTasks);
        } else if (id == R.id.search_bar_menu) {
            if (binding.searchBar.getVisibility() == View.GONE)
                binding.searchBar.setVisibility(View.VISIBLE);
            else
                hideSearchBar(binding.searchBar);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Updates the list of tasks in the UI
     */
    private void updateTasks(List<TaskAndProject> taskList) {
        allTasks = taskList;
        if (allTasks.isEmpty()) {
            binding.lblNoTask.setVisibility(View.VISIBLE);
            binding.listTasks.setVisibility(View.GONE);
        } else {
            binding.lblNoTask.setVisibility(View.GONE);
            binding.listTasks.setVisibility(View.VISIBLE);
        }
        adapter.updateTasks(allTasks);
    }
}
