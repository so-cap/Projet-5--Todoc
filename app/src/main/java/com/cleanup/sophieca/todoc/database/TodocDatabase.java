package com.cleanup.sophieca.todoc.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.sophieca.todoc.database.dao.ProjectDao;
import com.cleanup.sophieca.todoc.database.dao.TaskDao;
import com.cleanup.sophieca.todoc.injection.Injection;
import com.cleanup.sophieca.todoc.model.Project;
import com.cleanup.sophieca.todoc.model.Task;

import java.util.Date;

/**
 * Created by SOPHIE on 21/02/2020.
 */
@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    private static volatile TodocDatabase INSTANCE;

    public abstract ProjectDao projectDao();

    public abstract TaskDao taskDao();

    public static TodocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
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

    private static RoomDatabase.Callback roomCallback() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                new PopulateDBAsyncTask(INSTANCE).execute();
            }
        };
    }

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProjectDao projectDao;
        private TaskDao taskDao;

        private PopulateDBAsyncTask(TodocDatabase db) {
            projectDao = db.projectDao();
            taskDao = db.taskDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            projectDao.insertProject(Injection.getAllProjects()[0]);
            projectDao.insertProject(Injection.getAllProjects()[1]);
            projectDao.insertProject(Injection.getAllProjects()[2]);

            taskDao.insert(new Task(0, 1L, "Nettoyer les vitres", new Date().getTime()));
            taskDao.insert(new Task(0, 2L, "Vider le lave vaiselle", new Date().getTime()));
            taskDao.insert(new Task(0, 2L, "Passer l'aspirateur", new Date().getTime()));
            taskDao.insert(new Task(0, 1L, "Arroser les plantes", new Date().getTime()));
            taskDao.insert(new Task(0, 3L, "Nettoyer les toilettes", new Date().getTime()));
            return null;
        }
    }
}
