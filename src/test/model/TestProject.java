package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parsers.exceptions.ParsingException;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestProject {

    private Project project;
    private Project project1;
    private Project project2;


    @BeforeEach
    public void runBefore() {


    }

    @Test
    public void testGetProgress() {
        Task task1 = new Task("test1##123");
        task1.setEstimatedTimeToComplete(1);
        task1.setProgress(1);

        Task task2 = new Task("test2##123");
        task2.setEstimatedTimeToComplete(2);
        task2.setProgress(2);

        Task task3 = new Task("test3##123");
        task3.setEstimatedTimeToComplete(3);
        task3.setProgress(3);

        Task task4 = new Task("test4##123");
        task4.setEstimatedTimeToComplete(4);
        task4.setProgress(4);

        Task task5 = new Task("test5##123");
        task5.setEstimatedTimeToComplete(5);
        task5.setProgress(5);


        try {
            project = new Project("test");
        } catch (EmptyStringException e) {
            e.printStackTrace();
        }

        project1 = new Project("project1");
        project1.add(task1);
        project1.add(task2);

        project2 = new Project("project2");
        project2.add(task3);
        project2.add(task5);

        project.add(project1);
        project.add(project2);
        project.add(task4);
        assertEquals(15, project.getEstimatedTimeToComplete());
        assertEquals(3, project.getProgress());
        assertEquals(3, project.getProgress());

    }

    @Test
    public void testGetProgress2() {
        Task task1 = new Task("test1##123");
        Task task2 = new Task("test2##123");
        Task task3 = new Task("test3##123");
        Task task4 = new Task("test4##123");

        project1 = new Project("project1");

        project1.add(task1);
        project1.add(task2);
        project1.add(task3);
        assertEquals(0, project1.getProgress());

        task1.setProgress(100);
        assertEquals(33, project1.getProgress());

        task2.setProgress(50);
        task3.setProgress(25);

        assertEquals(58, project1.getProgress());

        project2 = new Project("project2");
        project2.add(task4);
        project2.add(project1);
        assertEquals(29, project2.getProgress());

    }


    @Test
    public void testProjectIterator() {
        Task task1 = new Task("test1##123");
        task1.setPriority(new Priority(1));

        Task task2 = new Task("test2##123");
        task2.setPriority(new Priority(2));

        Task task3 = new Task("test3##123");
        task3.setPriority(new Priority(2));

        Task task4 = new Task("test4##123");
        task4.setPriority(new Priority(1));

        Task task5 = new Task("test5##123");
        task5.setPriority(new Priority(4));


        project1 = new Project("project1");

        project2 = new Project("project2");
        project2.setPriority(new Priority(1));

        Project project3 = new Project("project3");

        project1.add(task1);
        project1.add(task2);
        project1.add(task3);
        project1.add(task4);
        project1.add(project2);
        project1.add(project3);
        project1.add(task5);

        for (Todo todo : project1) {
            System.out.println(todo);
        }

    }

    @Test
    public void testConstrorZero() {
        // TODO: 2019-03-22 project has no task
        try {
            project = new Project("test##123");
        } catch (EmptyStringException e) {
            fail("It shouldn't throw exception.");
        }
        assertEquals(0, project.getEstimatedTimeToComplete());
        assertEquals(0, project.getProgress());
    }


    @Test
    public void testEmptyStringException() {
        try {
            project = new Project(null);
            fail();
        } catch (EmptyStringException e) {
        }
        try {
            project = new Project("");
            fail();
        } catch (EmptyStringException e) {
        }
    }

    @Test
    public void testUnsupportedOperationException() {
        try {
            project = new Project("asd##123 ");
            project.getTasks();
            fail();
        } catch (UnsupportedOperationException e) {
        }
    }

    @Test
    public void testRemove() {
        Task task1 = new Task("test1##123");
        Task task2 = new Task("test2##123");
        Task task3 = new Task("test3##123");
        Task task4 = new Task("test4##123");

        project1 = new Project("project1");

        project1.add(task1);
        project1.add(task2);
        project1.add(task3);
        assertEquals(0, project1.getProgress());

        task1.setProgress(100);
        assertEquals(33, project1.getProgress());

        task2.setProgress(50);
        task3.setProgress(25);

        assertEquals(58, project1.getProgress());

        project2 = new Project("project2");
        project2.add(task4);
        project2.add(project1);
        assertEquals(29, project2.getProgress());
        project2.remove(project1);
        assertEquals(false, project2.contains(project1));
        assertEquals("project2", project2.getDescription());


    }

    @Test
    public void testisCompleted(){
        Task task1 = new Task("test1##123");
        Task task2 = new Task("test2##123");
        Task task3 = new Task("test3##123");
        Task task4 = new Task("test4##123");

        project1 = new Project("project1");

        project1.add(task1);
        project1.add(task2);
        project1.add(task3);

        assertEquals(false, project1.isCompleted());

        task1.setProgress(30);
        assertEquals(false, project1.isCompleted());

        Project project2 = new Project("project2");
        assertEquals(false, project2.isCompleted());

        project2.add(task4);
        task4.setProgress(100);
        assertEquals(true, project2.isCompleted());


    }

    @Test
    public void testContains(){
        Task task1 = new Task("test1##123");
        Task task2 = new Task("test2##123");
        Task task3 = new Task("test3##123");
        Task task4 = new Task("test4##123");

        project1 = new Project("project1");

        project1.add(task1);
        project1.add(task2);
        project1.add(task3);
        assertEquals(true, project1.contains(task1));

        try {
            project1.contains(null);
            fail();
        } catch (NullArgumentException e) {
        }

    }

    @Test
    public void testAdditself(){
        Task task1 = new Task("test1##123");
        Task task2 = new Task("test2##123");
        Task task3 = new Task("test3##123");
        Task task4 = new Task("test4##123");

        project1 = new Project("project1");

        project1.add(task1);
        project1.add(task2);
        project1.add(task3);

        assertEquals(false, project1.isCompleted());

        task1.setProgress(30);
        assertEquals(false, project1.isCompleted());

        Project project2 = new Project("project2");
        assertEquals(false, project2.isCompleted());

        project2.add(task4);
        task4.setProgress(100);
        assertEquals(true, project2.isCompleted());

        project2.add(project2);
        assertEquals(false, project2.contains(project2));




    }

    @Test
    public void testAddSubSingle(){

        project1 = new Project("project1");

        Project project2 = new Project("project2");

        project2.add(project1);
        project2.add(project2);
        assertEquals(false, project2.contains(project2));
        assertEquals(true, project2.contains(project1));


    }

    @Test
    public void testDuplicateProject(){

        project1 = new Project("project1");

        Project project2 = new Project("project2");
        Project project3 = new Project("project2");

        project1.add(project2);
        project1.add(project3);
        project1.add(project3);
        assertEquals(1, project1.getNumberOfTasks());


    }



//    @Test
//    public void testConstructorWithException() {
//        try {
//            project = new Project("");
//            fail("It didn't throw exception.");
//        } catch (EmptyStringException e) {"
//        }
//
//        try {
//            project = new Project(null);
//            fail("It didn't throw exception.");
//        } catch (EmptyStringException e) {
//        }
//
//
//    }
//
//    @Test
//    public void testAddTaskWithoutDueDateNoException() {
//        Task task = null;
//        Task task2 = null;
//        Task task3 = null;
//
//        try {
//            task = new Task("test##123");
//            task2 = new Task("test##123");
//
//        } catch (EmptyStringException e) {
//            fail();
//        } catch (Exception e) {
//            fail();
//
//        }
//
//        try {
//            project.add(task);
//            project.add(task2);
//        } catch (NullArgumentException e) {
//            fail();
//        }
//
//        assertEquals(1, project.getTasks().size());
//
//
//    }
//
//    @Test
//    public void testAddTaskWitException() {
//
//
//        try {
//            project.add(null);
//            fail();
//        } catch (NullArgumentException e) {
//        }
//
//
//    }
//
//    //
//    @Test
//    public void testAddTaskWithDueDate() {
//        Task task = null;
//        Task task2 = null;
//        Task task3 = null;
//        try {
//            task = new Task("test##123");
//            task2 = new Task("test2##123");
//            task3 = new Task("test3##123");
//        } catch (EmptyStringException e) {
//            fail();
//        } catch (Exception e) {
//            fail();
//        }
//
//        try {
//            task.setDueDate(new DueDate(new GregorianCalendar(2019, Calendar.JANUARY, 25, 10, 30).getTime()));
//            task2.setDueDate(new DueDate(new GregorianCalendar(2019, Calendar.JANUARY, 26, 10, 30).getTime()));
//
//        } catch (NullArgumentException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            project.add(task);
//            project.add(task2);
//        } catch (NullArgumentException e) {
//            e.printStackTrace();
//        }
//
//        assertEquals(2, project.getTasks().size());
//    }
//
//    //
//    @Test
//    public void testRemoveTaskNoException() {
//        Task task = null;
//        Task task2 = null;
//        Task task3 = null;
//        try {
//            task = new Task("test##123");
//            task2 = new Task("test##123");
//            task3 = new Task("test3##123");
//
//        } catch (EmptyStringException e) {
//            fail();
//        } catch (Exception e) {
//            fail();
//        }
//
//        try {
//            project.add(task);
//            project.add(task2);
//        } catch (NullArgumentException e) {
//            fail();
//        }
//
//        assertEquals(1, project.getTasks().size());
//
//        try {
//            project.add(task3);
//        } catch (NullArgumentException e) {
//            fail();
//        }
//        assertEquals(2, project.getTasks().size());
//
//        try {
//            project.remove(task3);
//        } catch (NullArgumentException e) {
//            fail();
//        }
//        assertEquals(1, project.getTasks().size());
//    }
//
//    @Test
//    public void testRemoveTaskWithException() {
//        try {
//            project.remove(null);
//            fail();
//        } catch (NullArgumentException e) {
//        }
//    }
//
//    @Test
//    public void testGetDescriptionTask() {
//
//        assertEquals("test", project.getDescription());
//
//    }
//
//    //
//    @Test
//    public void testGetTasks() {
//        Task task = null;
//        Task task999 = null;
//        Task task2 = null;
//        Task task3 = null;
//
//        try {
//            task = new Task("test##123");
//            task999 = new Task("999##123");
//            task2 = new Task("test2##123");
//            task3 = new Task("test3##123");
//        } catch (EmptyStringException e) {
//            fail();
//        } catch (Exception e) {
//            fail();
//        }
//
//
//        try {
//            project.add(task);
//            project.add(task999);
//            project.add(task2);
//            project.add(task3);
//        } catch (NullArgumentException e) {
//            e.printStackTrace();
//        }
//        assertEquals(4, project.getTasks().size());
//        assertEquals("test3", project.getTasks().get(3).getDescription());
//        assertEquals("999", project.getTasks().get(1).getDescription());
//
//        Task task4 = null;
//        try {
//            task4 = new Task("test4##123");
//        } catch (EmptyStringException e) {
//            fail();
//        } catch (Exception e) {
//            fail();
//        }
//
//        List<Todo> unmodifiedList = project.getTasks();
//
//
//        try {
//            unmodifiedList.add(task4);
//            fail("The return list is modified");
//        } catch (UnsupportedOperationException e) {
//
//        }
//
//        assertEquals(4, project.getTasks().size());
//
//    }
//
//    //
//    @Test
//    public void testGetProgressFraction() {
//        Task task = null;
//        try {
//            task = new Task("test##123");
//        } catch (EmptyStringException e) {
//            fail();
//        } catch (Exception e) {
//            fail();
//        }
//
//        try {
//            task.setStatus(Status.IN_PROGRESS);
//        } catch (NullArgumentException e) {
//            e.printStackTrace();
//        }
//
//        Task task2 = null;
//        try {
//            task2 = new Task("test2##123");
//        } catch (EmptyStringException e) {
//            fail();
//        } catch (Exception e) {
//            fail();
//        }
//
//        try {
//            task2.setStatus(Status.IN_PROGRESS);
//        } catch (NullArgumentException e) {
//            e.printStackTrace();
//        }
//        Task task3 = null;
//        try {
//            task3 = new Task("test3##123");
//        } catch (EmptyStringException e) {
//            fail();
//        } catch (Exception e) {
//            fail();
//        }
//
//
//        try {
//            task3.setStatus(Status.DONE);
//        } catch (NullArgumentException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            project.add(task);
//            project.add(task2);
//            project.add(task3);
//        } catch (NullArgumentException e) {
//            e.printStackTrace();
//        }
//
//        assertEquals(33, project.getProgress());
//
//    }
//
//    @Test
//    public void testGetProgressFractionDown() {
//        Task task = null;
//        Task task2 = null;
//        Task task3 = null;
//        Task task4 = null;
//        Task task5 = null;
//        Task task6 = null;
//
//        try {
//            task = new Task("test##123");
//            task.setStatus(Status.IN_PROGRESS);
//            task2 = new Task("test2##123");
//            task2.setStatus(Status.IN_PROGRESS);
//            task3 = new Task("test3##123");
//            task3.setStatus(Status.DONE);
//            task4 = new Task("test4##123");
//            task4.setStatus(Status.IN_PROGRESS);
//            task5 = new Task("test5##123");
//            task5.setStatus(Status.IN_PROGRESS);
//            task6 = new Task("test6##123");
//            task6.setStatus(Status.IN_PROGRESS);
//        } catch (EmptyStringException e) {
//            fail();
//        } catch (NullArgumentException e) {
//            fail();
//        } catch (Exception e) {
//            fail();
//        }
//
//        try {
//            project.add(task);
//            project.add(task2);
//            project.add(task3);
//            project.add(task4);
//            project.add(task5);
//            project.add(task6);
//        } catch (NullArgumentException e) {
//            e.printStackTrace();
//        }
//
//        assertEquals(16, project.getProgress());
//
//    }
//
//    //
//    @Test
//    public void testGetProgressNoTask() {
//        assertEquals(100, project.getProgress());
//    }
//
//    @Test
//    public void testGetProgressAllDone() {
//        Task task = null;
//        Task task2 = null;
//        Task task3 = null;
//        try {
//            task = new Task("test##123");
//            task.setStatus(Status.DONE);
//            task2 = new Task("test2##123");
//            task2.setStatus(Status.DONE);
//            task3 = new Task("test3##123");
//            task3.setStatus(Status.DONE);
//        } catch (EmptyStringException e) {
//            fail();
//        } catch (NullArgumentException e) {
//            fail();
//        } catch (Exception e) {
//            fail();
//        }
//
//        try {
//            project.add(task);
//            project.add(task2);
//            project.add(task3);
//        } catch (NullArgumentException e) {
//            e.printStackTrace();
//        }
//
//        assertEquals(100, project.getProgress());
//
//    }
//
//    @Test
//    public void testGetNumberOfTasks() {
//        Task task = null;
//        Task task2 = null;
//        Task task3 = null;
//        try {
//            task = new Task("test##123");
//            task.setStatus(Status.DONE);
//            task2 = new Task("test2##123");
//            task2.setStatus(Status.DONE);
//            task3 = new Task("test3##123");
//            task3.setStatus(Status.DONE);
//        } catch (EmptyStringException e) {
//            fail();
//        } catch (NullArgumentException e) {
//            fail();
//        } catch (Exception e) {
//            fail();
//        }
//
//        try {
//            project.add(task);
//            project.add(task2);
//            project.add(task3);
//        } catch (NullArgumentException e) {
//            e.printStackTrace();
//        }
//
//        assertEquals(3, project.getNumberOfTasks());
//
//    }
//
//    @Test
//    public void testIsCompletedTrue() {
//        Task task = null;
//        Task task2 = null;
//        Task task3 = null;
//        try {
//            task = new Task("test##123");
//            task.setStatus(Status.DONE);
//            task2 = new Task("test2##123");
//            task2.setStatus(Status.DONE);
//            task3 = new Task("test3##123");
//            task3.setStatus(Status.DONE);
//        } catch (EmptyStringException e) {
//            fail();
//        } catch (NullArgumentException e) {
//            fail();
//        } catch (Exception e) {
//            fail();
//        }
//
//        try {
//            project.add(task);
//            project.add(task2);
//            project.add(task3);
//        } catch (NullArgumentException e) {
//            e.printStackTrace();
//        }
//
//        assertEquals(true, project.isCompleted());
//
//    }
//
//    @Test
//    public void testIsCompletedFalse() {
//        Task task = null;
//        Task task2 = null;
//        Task task3 = null;
//        try {
//            task = new Task("test##123");
//            task.setStatus(Status.IN_PROGRESS);
//            task2 = new Task("test2##123");
//            task2.setStatus(Status.DONE);
//            task3 = new Task("test3##123");
//            task3.setStatus(Status.DONE);
//        } catch (EmptyStringException e) {
//            fail();
//        } catch (NullArgumentException e) {
//            fail();
//        } catch (Exception e) {
//            fail();
//        }
//
//        try {
//            project.add(task);
//            project.add(task2);
//            project.add(task3);
//        } catch (NullArgumentException e) {
//            e.printStackTrace();
//        }
//
//        assertEquals(false, project.isCompleted());
//
//    }
//
//    @Test
//    public void testIsCompletedNoTask() {
//
//        assertEquals(false, project.isCompleted());
//
//    }
//
//    @Test
//    public void testContainsNoException() {
//        Task task = null;
//        Task task2 = null;
//        Task task3 = null;
//        try {
//            task = new Task("test##123");
//            task.setStatus(Status.IN_PROGRESS);
//            task2 = new Task("test2##123");
//            task2.setStatus(Status.DONE);
//            task3 = new Task("test3##123");
//            task3.setStatus(Status.DONE);
//        } catch (EmptyStringException e) {
//            fail();
//        } catch (NullArgumentException e) {
//            fail();
//        } catch (Exception e) {
//            fail();
//        }
//
//        try {
//            project.add(task);
//            project.add(task2);
//        } catch (NullArgumentException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            assertEquals(true, project.contains(task));
//            assertEquals(true, project.contains(task2));
//            assertEquals(false, project.contains(task3));
//        } catch (NullArgumentException e) {
//            fail();
//        }
//
//    }
//
//    @Test
//    public void testContainsWithException() {
//
//        try {
//            assertEquals(true, project.contains(null));
//            fail();
//        } catch (NullArgumentException e) {
//        }
//
//    }
}
