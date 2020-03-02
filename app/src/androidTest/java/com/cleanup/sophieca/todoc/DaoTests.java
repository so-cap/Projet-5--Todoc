package com.cleanup.sophieca.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.sophieca.todoc.database.TodocDatabase;
import com.cleanup.sophieca.todoc.model.Employee;
import com.cleanup.sophieca.todoc.model.Project;
import com.cleanup.sophieca.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by SOPHIE on 26/02/2020.
 */
@RunWith(AndroidJUnit4.class)
public class DaoTests {
    private TodocDatabase database;

    private static int EMPLOYEE_ID = 1;
    private static Employee EMPLOYEE_DEMO = new Employee(EMPLOYEE_ID, "Sophie", "Cap", "sophie@email.com", "mdp");
    private static Task TASK_1 = new Task(1, 1, 1,"task 1", new Date().getTime());
    private static Task TASK_2 = new Task(2, 2, 1,"task 2", new Date().getTime());

    // TODO : Qu'est-ce que ça fait exactement ?
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() {
        database.close();
    }

    // -------------
    // FOR EMPLOYEE
    // -------------

    @Test
    public void insertAndGetEmployee() throws InterruptedException {
        // BEFORE : Adding a new user
        database.employeeDao().insert(EMPLOYEE_DEMO);
        // TEST
        Employee employee = LiveDataTestUtil.getValue(database.employeeDao().getEmployee(EMPLOYEE_DEMO.getEmail(), EMPLOYEE_DEMO.getPassword()));
        assertTrue(employee.getFirstName().equals(EMPLOYEE_DEMO.getFirstName()) && employee.getId() == EMPLOYEE_ID);
    }

    @Test
    public void deleteEmployee() throws InterruptedException {
        // BEFORE : Adding a new employee
        this.database.employeeDao().insert(EMPLOYEE_DEMO);
        Employee employee = LiveDataTestUtil.getValue(database.employeeDao().getEmployee(EMPLOYEE_DEMO.getEmail(), EMPLOYEE_DEMO.getPassword()));

        // Check employee has been added
        assertNotNull(employee);

        // THEN : deleting the employee
        database.employeeDao().deleteEmployee(EMPLOYEE_ID);

        employee = LiveDataTestUtil.getValue(database.employeeDao().getEmployee(EMPLOYEE_DEMO.getEmail(), EMPLOYEE_DEMO.getPassword()));

        // TEST : employee has been deleted
        assertNull(employee);
    }

    // -------------
    // FOR PROJECT DAO
    // -------------

    @Test
    public void insertAndGetProjects() throws InterruptedException {
        // BEFORE : Adding projects
        List<Project> items = LiveDataTestUtil.getValue(database.projectDao().getAllProjects());
        assertEquals(0, items.size());

        this.database.projectDao().insertProject(Project.getAllProjects()[0]);
        this.database.projectDao().insertProject(Project.getAllProjects()[1]);
        this.database.projectDao().insertProject(Project.getAllProjects()[2]);

        // TEST
        items = LiveDataTestUtil.getValue(database.projectDao().getAllProjects());
        assertEquals(3, items.size());
    }

    // -------------
    // FOR TASK DAO
    // -------------

    @Test
    public void insertAndGetTask() throws InterruptedException {
        // BEFORE : Adding a new employee and init tasks
        database.employeeDao().insert(EMPLOYEE_DEMO);
        // Add projects in database
        for (Project project : Project.getAllProjects())
            database.projectDao().insertProject(project);

        database.taskDao().insert(TASK_1);

        Task task1 = LiveDataTestUtil.getValue(database.taskDao().getTasks(EMPLOYEE_ID)).get(0);

        // TEST
        assertTrue(task1.getName().equals(TASK_1.getName())&& task1.getId() == TASK_1.getId());
    }

    @Test
    public void deleteTask() throws InterruptedException {
        // BEFORE : Adding a projects and tasks;
        for (Project project : Project.getAllProjects())
            database.projectDao().insertProject(project);

        database.employeeDao().insert(EMPLOYEE_DEMO);
        database.taskDao().insert(TASK_1);
        database.taskDao().insert(TASK_2);

        List<Task> tasks = LiveDataTestUtil.getValue(database.taskDao().getTasks(EMPLOYEE_ID));

        // Check tasks have been added
        assertEquals(2, tasks.size());

        // THEN : deleting TASK_1
        database.taskDao().delete(tasks.get(0).getId());

        tasks = LiveDataTestUtil.getValue(database.taskDao().getTasks(EMPLOYEE_ID));
        // TEST : task has been deleted
        assertEquals(tasks.get(0).getName(), TASK_2.getName());
        assertEquals(1, tasks.size());
    }
}




