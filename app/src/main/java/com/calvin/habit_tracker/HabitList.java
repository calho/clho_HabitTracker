package com.calvin.habit_tracker;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Calvin on 2016-09-29.
 */

public class HabitList {
    protected ArrayList<Habit> habitList;
    protected ArrayList<Listener> listeners;

    public HabitList() {
        habitList = new ArrayList<Habit>();
        listeners = new ArrayList<Listener>();
    }

    public Collection<Habit> getHabits() {
        return habitList;
    }

    public void setHabits(ArrayList<Habit> habitList) {
        this.habitList = habitList;
    }

    public void addHabit(Habit habit) {

        habitList.add(habit);
        notifyListener();
    }

    private void notifyListener() {
        for (Listener listener: listeners) {
            listener.update();
        }

    }

    public void deleteHabit(Habit habit) {
        habitList.remove(habit);
    }

    public Habit getHabit(Habit habit) {
        return habitList.get(habitList.indexOf(habit));
    }

    public void addListener(Listener l) {
        listeners.add(l);
    }

    public void removeListener(Listener l) {
        listeners.remove(l);
    }
}
