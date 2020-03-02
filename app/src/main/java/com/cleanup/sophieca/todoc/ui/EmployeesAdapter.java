package com.cleanup.sophieca.todoc.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.sophieca.todoc.R;
import com.cleanup.sophieca.todoc.TodocViewModel;
import com.cleanup.sophieca.todoc.model.Employee;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SOPHIE on 02/03/2020.
 */
public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeeViewHolder> {
    @NonNull
    private List<Employee> employees;

    /**
     * The listener for when an employee needs to be deleted
     */
    @NonNull
    private final DeleteEmployeeListener deleteEmployeeListener;


    EmployeesAdapter(@NonNull final List<Employee> employees, @NonNull final DeleteEmployeeListener deleteEmployeeListener) {
        this.employees = employees;
        this.deleteEmployeeListener = deleteEmployeeListener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view, deleteEmployeeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.bind(employees.get(position));
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    void updateList(@NonNull final List<Employee> employees) {
        this.employees = employees;
        notifyDataSetChanged();
    }

    public interface DeleteEmployeeListener {
        void onDeleteEmployee(int id);
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private DeleteEmployeeListener deleteEmployeeListener;
        private TextView fullName;
        private TextView email;
        private AppCompatImageView imgDelete;


        EmployeeViewHolder(@NonNull View itemView, @NonNull DeleteEmployeeListener deleteEmployeeListener) {
            super(itemView);

            fullName = itemView.findViewById(R.id.lbl_full_name);
            email = itemView.findViewById(R.id.lbl_email);
            imgDelete = itemView.findViewById(R.id.img_delete_employee);

            this.deleteEmployeeListener = deleteEmployeeListener;

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Object tag = v.getTag();
                    if (v.getTag() instanceof Employee) {
                        EmployeeViewHolder.this.deleteEmployeeListener.onDeleteEmployee(((Employee) tag).getId());
                    }
                }
            });

        }

        void bind(Employee employee) {
            fullName.setText(String.format("%s %s", employee.getFirstName(), employee.getLastName()));
            email.setText(employee.getEmail());
            imgDelete.setTag(employee);
        }

    }

}
