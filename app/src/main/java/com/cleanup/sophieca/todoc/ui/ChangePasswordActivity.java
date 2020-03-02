package com.cleanup.sophieca.todoc.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cleanup.sophieca.todoc.R;
import com.cleanup.sophieca.todoc.TodocViewModel;
import com.cleanup.sophieca.todoc.injection.DI;
import com.cleanup.sophieca.todoc.model.Employee;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cleanup.sophieca.todoc.ui.MainActivity.EMPLOYEE_EXTRA;

/**
 * Created by SOPHIE on 26/02/2020.
 */
public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private TodocViewModel viewModel;
    private String password;
    private Employee currentEmployee;

    @BindView(R.id.new_password)
    EditText passwordInput;
    @BindView(R.id.confirm_password_input)
    EditText confirmPasswordInput;
    @BindView(R.id.save_password)
    Button savePassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        viewModel = (TodocViewModel) DI.getViewModel();

        if (getIntent().hasExtra(EMPLOYEE_EXTRA)) {
            currentEmployee = (Employee) getIntent().getSerializableExtra(EMPLOYEE_EXTRA);

        }
        savePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (checkEntries()) {
            currentEmployee.setPassword(password);
            currentEmployee.setFirstConnection(false);
            viewModel.updateEmployee(currentEmployee);
            Toast.makeText(this, getString(R.string.saved_password), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private boolean checkEntries() {
        password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        if (password.isEmpty() || confirmPassword.isEmpty() || !password.equals(confirmPassword)) {
            if (password.isEmpty())
                passwordInput.setError(getString(R.string.empty_field));
            if (confirmPassword.isEmpty())
                confirmPasswordInput.setError(getString(R.string.empty_field));
            else if (!password.equals(confirmPassword))
                confirmPasswordInput.setError(getString(R.string.different_password));
            return false;
        }
        return true;
    }

}
