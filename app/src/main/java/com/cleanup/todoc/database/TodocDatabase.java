package com.cleanup.todoc.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.EmployeeDao;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Employee;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

/**
 * Created by SOPHIE on 21/02/2020.
 */
@Database(entities = {Employee.class, Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    private static volatile TodocDatabase INSTANCE;

    // TODO: Qu'est-ce que ces déclarations font exactement ?
    public abstract EmployeeDao employeeDao();
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();

    public static TodocDatabase getInstance(Context context){
        if(INSTANCE == null){
            // TODO: A quoi sert synchronized ?
            synchronized (TodocDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "TodocDatabase.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                new PopulateDBAsyncTask(INSTANCE).execute();
            }
        };
    }

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void>{
        private EmployeeDao employeeDao;
        private ProjectDao projectDao;
        private TaskDao taskDao;

        private PopulateDBAsyncTask(TodocDatabase db) {
            employeeDao = db.employeeDao();
            taskDao = db.taskDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            employeeDao.createEmployee(new Employee (1,"Sophie","Cap", "sophie@email.com", "mdp" ));
            return null;
        }
    }
}
