package com.calvin.habit_tracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Calvin on 2016-10-02.
 */

public class HabitTest {

    @Test
    public void testGetName() {
        Habit habit = new Habit("test");
        assertEquals(habit.getName(), "test");
    }

    @Test
    public void testGetDate() {
        Habit habit = new Habit("test");
        Date date = new Date();
        assertEquals(habit.getDate(), date);
    }

    @Test
    public void testGetDays() {
        Date date = new Date();
        ArrayList<String> days = new ArrayList<String>();
        days.add("Monday");
        Habit habit = new Habit("test", date, days);
        assertEquals(habit.getDays(), days);
    }

    @Test
    public void testGetCompletionsTotal() {
        Date date = new Date();
        ArrayList<String> days = new ArrayList<String>();
        days.add("Monday");
        Habit habit = new Habit("test", date, days);
        assertEquals(habit.getCompletionsTotal(),0);
    }

    @Test
    public void testAddAndGetCompletionDate() {
        Date date = new Date();
        ArrayList<String> days = new ArrayList<String>();
        days.add("Monday");
        Habit habit = new Habit("test", date, days);
        habit.addCompletionsDate("Today");
        assertEquals(habit.getCompletionsTotal(),1);
        ArrayList<String> day = new ArrayList<String>();
        day.add("Today");
        assertEquals(habit.getCompletionDates(), day);
    }

    @Test
    public void testDeleteAndGetCompletionDate() {
        Date date = new Date();
        ArrayList<String> days = new ArrayList<String>();
        days.add("Monday");
        Habit habit = new Habit("test", date, days);
        habit.addCompletionsDate("Today");
        habit.deleteCompletionsDate(0);
        assertEquals(habit.getCompletionsTotal(),0);
        ArrayList<String> day = new ArrayList<String>();
        assertEquals(habit.getCompletionDates(), day);
    }



}
