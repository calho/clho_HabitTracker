package com.calvin.habit_tracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import static android.R.id.list;

/**
 * Created by Calvin on 2016-09-28.
 */

//Habit class containing all details name,date...etc. able to get and set some attributes.
    //able to add completions.

public class Habit implements Serializable {

    private String name;
    private Date date;
    private ArrayList<String> days;
    private Completions completions;

    public Habit(String name) {
        this.name = name;
        this.date = new Date();
        this.days = new ArrayList<String>();
        this.completions = new Completions();
    }

    public Habit(String name, Date date) {
        this.date = date;
        this.name = name;
        this.days = new ArrayList<String>();
        this.completions = new Completions();
    }

    public Habit(String name, Date date, ArrayList<String> days) {
        this.name = name;
        this.date = date;
        this.days = days;
        this.completions = new Completions();
    }

    @Override
    public String toString() {return name; }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public int getCompletionsTotal() {
        return completions.getCompletionsTotal();
    }

    public void setCompletionsTotal(int completions) {
        this.completions.setCompletionsTotal(completions);
    }

    public void addCompletionsDate(String completionsDate) {
        this.completions.addCompletionDate(completionsDate);
    }

    public void deleteCompletionsDate(int completionsDate) {
        this.completions.deleteCompletionDate(completionsDate);
    }

    public ArrayList<String> getCompletionDates() {
        return completions.getCompletionDates();
    }

    public boolean hasDay(String day) {
        return this.days.contains(day);
    }

}
