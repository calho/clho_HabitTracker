package com.calvin.habit_tracker;

/**
 * Created by Calvin on 2016-09-29.
 */

public class HabitListController {
    private static HabitList habitList = null;
    static public HabitList getHabitList() {
        if (habitList == null){
            habitList = new HabitList();
        }
        return habitList;
    }

    public void addHabit(Habit habit) {
        getHabitList().addHabit(habit);
    }
}
