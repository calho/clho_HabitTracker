package com.calvin.habit_tracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class IndividualHabit extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private static ArrayList<String> dayArrayList = new ArrayList<String>();
    private ArrayList<Habit> habitList = new ArrayList<Habit>();
    public static ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_habit);

        loadFromFile();

        displayStat();
    }

    public void completeHabit(View v){
        Habit habit = (Habit)getIntent().getSerializableExtra("Habit");
        int currCompletes = habit.getCompletions();
        currCompletes = currCompletes + 1;
        habit.setCompletions(currCompletes);
        Log.e("TAG", "habit completion: " + habit.getCompletions());
        int habitIndex = habitList.indexOf(habit);
        String completes = Integer.toString(habit.getCompletions());
        TextView inputCompletes = (TextView) findViewById(R.id.completionCounter);
        inputCompletes.setText(completes);
        saveInFile();
    }

    public void displayStat() {
        //http://stackoverflow.com/questions/32398668/how-to-pass-listview-selected-item-values-to-another-activity
        Habit habit = (Habit)getIntent().getSerializableExtra("Habit");
        String name = habit.getName();

        //http://stackoverflow.com/questions/5169839/display-variable-on-screen-using-android-by-textviews
        TextView selectedHabit = (TextView) findViewById(R.id.selectedHabit);
        selectedHabit.setText(name);

        TextView inputDate = (TextView) findViewById(R.id.inputDate);

        //http://stackoverflow.com/questions/27217280/how-to-get-date-extra-from-an-intent
        Date date = habit.getDate();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = df.format(date);
        inputDate.setText(stringDate);

        ArrayList<String> dayArrayList = habit.getDays();
        ListView dayList = (ListView) findViewById(R.id.dayList);
        adapter = new ArrayAdapter<String>(this,
                R.layout.day_list_item, dayArrayList);
        dayList.setAdapter(adapter);

        TextView inputCompletes = (TextView) findViewById(R.id.completionCounter);
        String completes = Integer.toString(habit.getCompletions());
        inputCompletes.setText(completes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    public void deleteOption(MenuItem menu) {
        Intent intent = new Intent(IndividualHabit.this, MainActivity.class);
        final Habit habit = (Habit)getIntent().getSerializableExtra("Habit");
        habitList.remove(habit);
        saveInFile();
        startActivity(intent);
    }

    private void saveInFile() {
        try {

            FileOutputStream fos = openFileOutput(FILENAME,0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(habitList, writer);
            writer.flush();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    private void loadFromFile() {
        ArrayList<String> tweets = new ArrayList<String>();
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //Code taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt Sept.22,2016
            Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();
            habitList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
