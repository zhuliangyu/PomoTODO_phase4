package model;

import model.exceptions.EmptyStringException;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public abstract class Todo extends Observable implements Observer {
    protected String description;
    protected int progress;
    protected int etcHours;  // Estimated Time To Complete
    protected Priority priority;


    // MODIFIES: this
    // EFFECTS: sets the "description" using the given description
    //          sets "progress" and "estimated time to complete" to zero
    // throws EmptyStringException if description is null or empty
    public Todo(String description) {
        // implement the constructor according to its specification.
        // TODO: 2019-03-22

        // throws EmptyStringException if description is null or empty
        if (description == null || description.isEmpty()) {
            throw new EmptyStringException();
        }

        //sets the "description" using the given description
        this.description = description;
        //sets "progress" and "estimated time to complete" to zero
        progress = 0;
        etcHours = 0;
        this.priority = new Priority(4);

    }

    // EFFECTS: returns the description
    public String getDescription() {
        return description;
    }

    // EFFECTS: return a non-negative integer as the Estimated Time To Complete
    // Note: Estimated time to complete is a value that is expressed in
    //       hours of work required to complete a task or project.
    public abstract int getEstimatedTimeToComplete();

    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completion (rounded down to the closest integer).
    public abstract int getProgress();

    public abstract Priority getPriority();


}