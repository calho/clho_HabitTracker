package com.calvin.habit_tracker;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Calvin on 2016-10-01.
 */
//Keeps track of all completions of a habit. consist of two parts, the number and date of completions.
public class Completions {
    private int completionsTotal;
    private ArrayList<String> completionDate;

    public Completions() {
        this.completionsTotal = 0;
        this.completionDate = new ArrayList<String>();
    }

    public Completions(int completions, ArrayList<String> completionDate) {
        this.completionsTotal = completions;
        this.completionDate = completionDate;
    }

    public Completions(int completions) {
        this.completionsTotal = completions;
        this.completionDate = new ArrayList<String>();
    }

    public Completions(ArrayList<String> completionDate) {
        this.completionsTotal = completionDate.size();
        this.completionDate = completionDate;
    }

    public int getCompletionsTotal() {
        return completionsTotal;
    }

    public ArrayList<String> getCompletionDates() {
        return completionDate;
    }

    public void setCompletionDate(ArrayList<String> completionDate) {
        this.completionsTotal = completionDate.size();
        this.completionDate = completionDate;
    }

    public void setCompletionsTotal(int completionsTotal) {
        this.completionsTotal = completionsTotal;
    }

    public void addCompletionDate(String completionDate) {
        this.completionDate.add(completionDate);
        this.completionsTotal = this.completionDate.size();
    }

    public void deleteCompletionDate(int index) {
        this.completionDate.remove(index);
        this.completionsTotal = this.completionDate.size();
    }
}
