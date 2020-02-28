package com.cleanup.todoc.sophieca.ui;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cleanup.todoc.R;
import com.cleanup.todoc.sophieca.TaskViewModel;
import com.cleanup.todoc.sophieca.injection.DI;
import com.cleanup.todoc.sophieca.model.Employee;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SOPHIE on 26/02/2020.
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private TaskViewModel viewModel;
    private String lastname;
    private String firstname;
    private String email;
    private String password;

    @BindView(R.id.lastname_input)
    EditText lastnameInput;
    @BindView(R.id.firstname_input)
    EditText firstnameInput;
    @BindView(R.id.new_email_input)
    EditText emailInput;
    @BindView(R.id.new_password_input)
    EditText passwordInput;
    @BindView(R.id.save_profil)
    Button saveProfil;
    @BindView(R.id.cancel_profil_creation)
    Button cancelProfilCreation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        viewModel = (TaskViewModel) DI.getViewModel();

        saveProfil.setOnClickListener(this);
        cancelProfilCreation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_profil) {
            if (checkEntries()) {
                viewModel.createEmployee(new Employee(0, firstname, lastname, email, password));
                Toast.makeText(this, "Profil enregistré ! Connectez-vous ", Toast.LENGTH_LONG).show();
                finish();
            }
        } else {
            finish();
        }

    }

    private boolean checkEntries() {
        firstname = firstnameInput.getText().toString();
        lastname = lastnameInput.getText().toString();
        email = emailInput.getText().toString();
        password = passwordInput.getText().toString();

        if (lastname.isEmpty() || firstname.isEmpty() || email.isEmpty()
                || !Patterns.EMAIL_ADDRESS.matcher(email).matches() || password.isEmpty()) {
            if (lastname.isEmpty())
                lastnameInput.setError("Veuillez remplir ce champs!");
            if (firstname.isEmpty())
                firstnameInput.setError("Veuillez remplir ce champs!");
            if (email.isEmpty())
                emailInput.setError("Veuillez remplir ce champs");
            if (!email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches())
                emailInput.setError("Adresse invalide!");
            if (password.isEmpty())
                passwordInput.setError("Veuillez remplir ce champs!");
            return false;
        }
        return true;
    }

}
