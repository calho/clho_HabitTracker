package com.calvin.habit_tracker;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Calvin on 2016-10-01.
 */

public class HabitListTest{

    @Test
    public void testAddHabit(){
        HabitList habitList = new HabitList();
        Habit habit = new Habit("test");
        habitList.addHabit(habit);
        assertTrue(habitList.hasHabit(habit));
    }

    @Test
    public void testGetHabits(){
        HabitList habitList = new HabitList();
        ArrayList<Habit> habitList2 = new ArrayList<Habit>();
        Date date = new Date();
        ArrayList<String> days = new ArrayList<String>();
        days.add("Monday");
        Habit habit = new Habit("test",date,days);
        habitList.addHabit(habit);
        habitList2.add(habit);
        assertEquals(habitList.getHabits("Monday"), habitList2);
    }

    @Test
    public void testDeleteHabits(){
        HabitList habitList = new HabitList();
        ArrayList<Habit> habitList2 = new ArrayList<Habit>();
        Date date = new Date();
        ArrayList<String> days = new ArrayList<String>();
        days.add("Monday");
        Habit habit = new Habit("test",date,days);
        habitList.addHabit(habit);
        habitList.deleteHabit(habit);
        assertFalse(habitList.hasHabit(habit));
    }

    @Test
    public void testGetHabit(){
        HabitList habitList = new HabitList();
        Date date = new Date();
        ArrayList<String> days = new ArrayList<String>();
        days.add("Monday");
        Habit habit = new Habit("test",date,days);
        habitList.addHabit(habit);
        assertEquals(habitList.getHabit(0), habit);
    }
}
