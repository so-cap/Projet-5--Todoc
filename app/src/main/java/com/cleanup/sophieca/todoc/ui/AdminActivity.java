package com.cleanup.sophieca.todoc.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.sophieca.todoc.R;
import com.cleanup.sophieca.todoc.TodocViewModel;
import com.cleanup.sophieca.todoc.injection.DI;
import com.cleanup.sophieca.todoc.injection.Injection;
import com.cleanup.sophieca.todoc.model.Employee;
import com.cleanup.sophieca.todoc.utils.Comparator;
import com.cleanup.sophieca.todoc.utils.ViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.cleanup.sophieca.todoc.ui.MainActivity.CURRENT_EMPLOYEE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SOPHIE on 02/03/2020.
 */
public class AdminActivity extends AppCompatActivity implements EmployeesAdapter.DeleteEmployeeListener, DialogInterface.OnDismissListener, DialogInterface.OnShowListener {
    private List<Employee> employeesList = new ArrayList<>();
    private EmployeesAdapter adapter = new EmployeesAdapter(employeesList, this);
    private TodocViewModel viewModel;
    private ProgressDialog progressDialog;

    @BindView(R.id.list_employees)
    RecyclerView listEmployees;

    @BindView(R.id.my_toolbar)
    Toolbar mainToolbar;

    @BindView(R.id.search_bar_text)
    EditText searchBarInput;
    @BindView(R.id.search_bar)
    ConstraintLayout searchBar;

    @BindView(R.id.fab_add_employee)
    FloatingActionButton addEmployee;

    EditText lastNameInput;
    EditText firstNameInput;
    EditText emailInput;
    EditText passwordInput;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);

        configureViewModel();
        observeEmployees();
        initSearchBar();

        progressDialog = new ProgressDialog(this);
        DI.initProgressDialog(progressDialog);

        setSupportActionBar(mainToolbar);
        listEmployees.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listEmployees.setAdapter(adapter);

        findViewById(R.id.fab_add_employee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddEmployeeDialog();
            }
        });
    }

    private void showAddEmployeeDialog() {
        AlertDialog newEmployeeDialog;
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);
        alertBuilder.setTitle(R.string.add_employee);
        alertBuilder.setView(R.layout.dialog_add_employee);
        alertBuilder.setPositiveButton(R.string.save_employee, null);
        alertBuilder.setOnDismissListener(this);

        newEmployeeDialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        newEmployeeDialog.setOnShowListener(this);

        newEmployeeDialog.show();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        lastNameInput = null;
        firstNameInput = null;
        emailInput = null;
        passwordInput = null;
    }

    @Override
    public void onShow(DialogInterface dialog) {
        Button button =  ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onSaveEmployee(((AlertDialog)dialog));
            }
        });
    }

    private void onSaveEmployee(AlertDialog newEmployeeDialog) {
        lastNameInput = newEmployeeDialog.findViewById(R.id.new_lastname);
        firstNameInput = newEmployeeDialog.findViewById(R.id.new_firstname);
        emailInput = newEmployeeDialog.findViewById(R.id.new_email);
        passwordInput = newEmployeeDialog.findViewById(R.id.default_password);

        viewModel.createEmployee(new Employee(0,firstNameInput.getText().toString(),
                lastNameInput.getText().toString(),emailInput.getText().toString(), passwordInput.getText().toString()));
        newEmployeeDialog.hide();

        Toast.makeText(newEmployeeDialog.getContext(), R.string.account_saved, Toast.LENGTH_SHORT).show();
    }

    private void initSearchBar() {
        searchBarInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterList(s.toString());
            }
        });
    }

    @Override
    public void onDeleteEmployee(int id) {
        viewModel.deleteEmployee(id);
    }


    private void filterList(String text) {
        ArrayList<Employee> filteredList = new ArrayList<>();

        for (Employee employee : employeesList) {
            if (employee.getFirstName().toLowerCase().contains(text.toLowerCase()) ||
                    employee.getLastName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(employee);
            }
        }
        adapter.updateList(filteredList);
    }

    @OnClick
    public void hideSearchBar(View view) {
        searchBar.setVisibility(View.GONE);
        searchBarInput.setText("");
        adapter.updateList(employeesList);
    }

    public void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(TodocViewModel.class);
    }

    private void observeEmployees() {
        viewModel.getEmployees().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
                employeesList.clear();
                for (Employee employee : employees)
                    if (!employee.isAdmin())
                        employeesList.add(employee);

                adapter.updateList(employeesList);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical_admin) {
            Collections.sort(employeesList, new Comparator.AZComparator());
        } else if (id == R.id.filter_alphabetical_inverted_admin) {
            Collections.sort(employeesList, new Comparator.ZAComparator());
        } else if (id == R.id.search_bar_menu) {
            if (searchBar.getVisibility() == View.GONE)
                searchBar.setVisibility(View.VISIBLE);
            else
                hideSearchBar(searchBar);
        } else if (id == R.id.logout_btn_admin) {
            backToHomePage();
        }

        if (id == R.id.filter_alphabetical_admin || id == R.id.filter_alphabetical_inverted_admin
                || id == R.id.logout_btn_admin)
            adapter.updateList(employeesList);

        return super.onOptionsItemSelected(item);
    }

    private void backToHomePage() {
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CURRENT_EMPLOYEE = null;
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                progressDialog.dismiss();
            }
        }, 1000);
    }
}
