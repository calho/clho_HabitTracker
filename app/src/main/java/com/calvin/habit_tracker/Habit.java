package com.calvin.habit_tracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import static android.R.id.list;

/**
 * Created by Calvin on 2016-09-28.
 */

public class Habit implements Serializable {

    private String name;
    private Date date;
    private ArrayList<String> days;
    private int completions;

    public Habit(String name) {
        this.name = name;
        this.date = new Date();
        this.days = new ArrayList<String>();
        this.completions = 0;
    }

    public Habit(String name, Date date) {
        this.date = date;
        this.name = name;
        this.days = new ArrayList<String>();
        this.completions = 0;
    }

    public Habit(String name, Date date, ArrayList<String> days) {
        this.name = name;
        this.date = date;
        this.days = days;
        this.completions = 0;
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

    public int getCompletions() {
        return completions;
    }

    public void setCompletions(int completions) {
        this.completions = completions;
    }
}
