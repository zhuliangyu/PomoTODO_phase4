package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTaskPhase3 {

    // TODO: design tests for new behaviour added to Task class

    private Task task0;
    private Task task1;
    private Task task11;
    private Task task2;
    private Task task3;
    private Task task4;
    public Tag tag1;
    public Tag tag11;
    public Tag tag3;


    @BeforeEach
    void runBefore() {

        //        Description(same): Read collaboration policy of the term project
        //        Due date(same): Sat Feb 02 2019 11:59 PM
        //        Status(same): IN PROGRESS
        //        Priority(same): IMPORTANT & URGENT
        //        Tags(Diff): #cpsc210, #project
        task0 = new Task("test ## tomorrow; in progress; important");
        task1 = new Task("test ## tomorrow; tag4; tag5; tomorrow; in progress; important");
        task11 = new Task("test ## tomorrow; tag4; tag6; tomorrow; in progress; important");
        task2 = new Task("test2 ## tomorrow; tag4; tag6; tomorrow; in progress; important");
        task3 = new Task("test ## today; tag4; tag6; tomorrow; in progress; important");
        task4 = new Task("test ## tomorrow; tag4; tag5; tomorrow; todo; default");

        tag1 = new Tag("asd");
        tag11 = new Tag("asd");
        tag3 = new Tag("a");

    }

    @Test
    void testSameTask() {
        assertTrue(task1.equals(task11));
    }

    @Test
    void testDiffTask() {
        assertFalse(task1.equals(task2));
        assertFalse(task1.equals(task3));
        assertFalse(task1.equals(task4));
    }

    @Test
    void TestAddtag() {
        task1.addTag(tag1);
        assertTrue(task1.containsTag(tag1));
        assertFalse(task1.containsTag(tag3));
        assertTrue(tag1.containsTask(task1));
        assertFalse(tag3.containsTask(task2));
    }

    @Test
    void TestAddtag2() {
        task1.addTag(tag1);
        assertTrue(task1.containsTag(tag1));
        assertTrue(task1.containsTag(tag11));
        assertTrue(tag1.containsTask(task1));
        assertFalse(tag11.containsTask(task1));
    }

    @Test
    void TestAddtagNull() {
        try {
            task1.addTag("");
            fail();
        } catch (EmptyStringException e) {
        }

        try {
            task1.addTag((Tag) null);
            fail();
        } catch (NullArgumentException e) {
        }
    }

    @Test
    void TestAddtagStr() {
        task0.addTag("asd");
        task0.addTag("asd");
        assertTrue(task0.getTags().size() == 1);
    }

    @Test
    void TestRemovetagStr() {
        task0.addTag(tag1);
        task0.removeTag("asd");
        assertTrue(task0.getTags().size() == 0);
    }
    @Test
    void TestRemovetagStr3() {
        task0.addTag(tag1);
        task0.removeTag(tag1);
        assertTrue(task0.getTags().size() == 0);
        assertTrue(tag1.getTasks().size() == 0);
    }


    @Test
    void TestRemovetagStr2() {
        task0.addTag(tag1);
        task0.removeTag("asd1");
        assertTrue(task0.getTags().size() == 1);
    }

    @Test
    void TestRemovetagNull() {
        try {
            task1.removeTag("");
            fail();
        } catch (EmptyStringException e) {
        }

        try {
            task1.removeTag((Tag) null);
            fail();
        } catch (NullArgumentException e) {
        }
        try {
            task1.removeTag((String) null);
            fail();
        } catch (EmptyStringException e) {
        }
    }


}