package com.cleanup.sophieca.todoc.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.sophieca.todoc.database.dao.EmployeeDao;
import com.cleanup.sophieca.todoc.database.dao.ProjectDao;
import com.cleanup.sophieca.todoc.database.dao.TaskDao;
import com.cleanup.sophieca.todoc.injection.DI;
import com.cleanup.sophieca.todoc.model.Employee;
import com.cleanup.sophieca.todoc.model.Project;
import com.cleanup.sophieca.todoc.model.Task;

/**
 * Created by SOPHIE on 21/02/2020.
 */
@Database(entities = {Employee.class, Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    private static volatile TodocDatabase INSTANCE;

    public abstract EmployeeDao employeeDao();
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();

    public static TodocDatabase getInstance(Context context){
        if(INSTANCE == null){
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
        private ProjectDao projectDao;
        private EmployeeDao employeeDao;

        private PopulateDBAsyncTask(TodocDatabase db) {
            projectDao = db.projectDao();
            employeeDao = db.employeeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 3; i++)
                projectDao.insertProject(Project.getAllProjects()[i]);

            employeeDao.insert(DI.getDummyEmployees().get(0));
            employeeDao.insert(DI.getDummyEmployees().get(1));
            return null;
        }
    }
}
