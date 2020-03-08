package com.cleanup.sophieca.todoc.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

/**
 * Created by SOPHIE on 02/03/2020.
 */
public class AdminActivity extends AppCompatActivity implements EmployeesAdapter.DeleteEmployeeListener {
    private List<Employee> employeesList = new ArrayList<>();
    private EmployeesAdapter adapter = new EmployeesAdapter(employeesList, this);
    private TodocViewModel viewModel;
    private ProgressDialog progressDialog;

    @BindView(R.id.list_employees)
    RecyclerView listEmployees;

    @BindView(R.id.my_toolbar)
    Toolbar mainToolbar;
    @BindView(R.id.search_bar)
    EditText searchBar;
    @BindView(R.id.fab_add_employee)
    FloatingActionButton addEmployee;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);

        configureViewModel();
        observeEmployees();

        progressDialog = new ProgressDialog(this);
        DI.initProgressDialog(progressDialog);

        setSupportActionBar(mainToolbar);
        listEmployees.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listEmployees.setAdapter(adapter);



        searchBar.addTextChangedListener(new TextWatcher() {
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
                searchBar.setVisibility(View.GONE);
        } else if (id == R.id.logout_btn_admin) {
            backToHomePage();
        }

        if (id == R.id.filter_alphabetical_admin || id == R.id.filter_alphabetical_inverted_admin
                || id == R.id.logout_btn_admin || searchBar.getVisibility() == View.GONE)
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
