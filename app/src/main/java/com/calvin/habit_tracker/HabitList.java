package com.calvin.habit_tracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Calvin on 2016-09-29.
 */

//A list containing all habits. Able to add, delete, and get.

public class HabitList implements Serializable{
    protected ArrayList<Habit> habitList;

    public HabitList() {
        habitList = new ArrayList<Habit>();
    }

    public boolean hasHabit(Habit habit) {
        return habitList.contains(habit);
    }

    public ArrayList<Habit> getHabits() {

        return this.habitList;
    }

    public ArrayList<Habit> getHabits(String day){
        ArrayList<Habit> dayHabitList = new ArrayList<Habit>();
        if (day != "All") {
            for (Habit h : habitList) {
                if (h.hasDay(day)) {
                    dayHabitList.add(h);
                }
            }
            return dayHabitList;
        }
        else{
            return this.habitList;
        }
    }

    public void setHabits(ArrayList<Habit> habitList) {
        this.habitList = habitList;
    }

    public void addHabit(Habit habit) {

        habitList.add(habit);
    }

    public void deleteHabit(Habit habit) {

        habitList.remove(habit);
    }

    public int habitIndex(Habit habit){
        return habitList.indexOf(habit);
    }

    public void editHabit(int index, Habit habit) {
        habitList.set(index, habit);
    }

    public Habit getHabit(int index) {

        return habitList.get(index);
    }


}
