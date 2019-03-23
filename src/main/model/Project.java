package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo> {
    private String description;

    private List<Todo> tasks;


    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        // TODO: 2019-03-22 add super abs class todo construct
        super(description);

        if (description == null || description.length() == 0) {
            throw new EmptyStringException("Cannot construct a project with no description");
        }
        this.description = description;
        tasks = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    public void add(Todo task) {
        if (!contains(task)) {
            tasks.add(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (contains(task)) {
            tasks.remove(task);

        }
    }

    // EFFECTS: returns the description of this project
    public String getDescription() {
        return description;
    }


    @Override
    //todo
    public int getEstimatedTimeToComplete() {

        int hours = 0;
        for (Todo t : tasks) {
            hours = hours + t.getEstimatedTimeToComplete();
        }


        return hours;
    }

    // EFFECTS: returns an unmodifiable list of tasks in this project.
//    public List<Task> getTasks() {
//        return Collections.unmodifiableList(tasks);
//    }

    // TODO: 2019-03-22
    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
//     the percentage of completion (rounded down to the nearest integer).
//     the value returned is the average of the percentage of completion of
//     all the tasks and sub-projects in this project.
    // TODO: 2019-03-22
    @Override
    public int getProgress() {

        // TODO: 2019-03-22  一个空的项目 (没有任务)的进度 = 0
        if (getNumberOfTasks() == 0) {
            return 0;
        }

        int progress = 0;
        for (Todo t : tasks) {
            progress = progress + t.getProgress();
        }

        return progress / tasks.size();


    }

    // TODO: 2019-03-22
    @Override
    public Priority getPriority() {
        return this.priority;
    }

    public Priority setPriority(Priority p) {
        return this.priority = p;
    }


    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completion (rounded down to the closest integer).
    //    public abstract int getProgress();


    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
// TODO: 2019-03-22 ???????????????????????
//        return tasks.size();
        int sum = 0;
        for (Todo todo : tasks
        ) {
            if (todo instanceof Project) {
                sum = sum + ((Project) todo).getNumberOfTasks();
            } else if ((todo instanceof Task)) {
                sum = sum + 1;
            }

        }
        return sum;
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
//     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }

    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

//    @Override
//    public String toString() {
//        return "Project{" + "description='" + description + '\'' + ", priority=" + priority + '}';
//    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Todo> iterator() {
        return new ProjectIterator();
    }

    private class ProjectIterator implements Iterator<Todo> {
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */


        int index = 0;

        @Override
        public boolean hasNext() {
            return index < tasks.size();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Todo next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            List<Integer> afterSorted = sorted();

            //output current index of afterSorted list
            int currentIndex = index;

            //move index to the right
            index = index + 1;
            return tasks.get(afterSorted.get(currentIndex));


        }

        private List<Integer> sorted() {
            List<Integer> listall = new ArrayList<>();

            for (int i = 1; i < 5; i++) {
                // i = priority number, from 1 to 4
                for (Integer ele : pickPriority(i)) {
                    // pick the specific priority index number
                    // add those into a new big list
                    listall.add(ele);
                }
            }

            //sorted index of list
            return listall;
        }

        // Effect: pick all priority of index and save it to a new list
        // according to its order
        private List<Integer> pickPriority(int priorityNumber) {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getPriority().equals(new Priority(priorityNumber))) {
                    list.add(i);
                }
            }
            return list;
        }

    }
}