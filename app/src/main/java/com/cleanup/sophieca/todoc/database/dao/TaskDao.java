package com.cleanup.sophieca.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.cleanup.sophieca.todoc.model.Task;

import java.util.List;

/**
 * Created by SOPHIE on 21/02/2020.
 */
@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Query("DELETE FROM Task WHERE id = :id")
    void delete(int id);

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();
}
