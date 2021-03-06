package com.calvin.habit_tracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//Activity that allows user to add a habit. Date is filled automatically. After add button is pressed
//it reads and save habit name, date input, what days are checked off.
//Activity is finished upon adding a habit.

public class AddHabitActivity extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private static HabitList habitList = new HabitList();
    private static ArrayList<String> days = new ArrayList<String>();
    private static ArrayAdapter<Habit> adapter;
    private static EditText habitName;
    private static EditText habitDate;
    private static CheckBox MCB;
    private static CheckBox TCB;
    private static CheckBox WCB;
    private static CheckBox RCB;
    private static CheckBox FCB;
    private static CheckBox SatCB;
    private static CheckBox SunCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        loadFromFile();

        setDefaultDate();

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        adapter = new ArrayAdapter<Habit>(AddHabitActivity.this,
                R.layout.list_item, habitList.getHabits());
    }

    public void mainMenu (View view) {
        Intent intent = new Intent(AddHabitActivity.this, MainActivity.class);
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
        while(true) {
            try {
                FileInputStream fis = openFileInput(FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                Gson gson = new Gson();
                //Code taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt Sept.22,2016
                Type listType = new TypeToken<HabitList>() {}.getType();
                habitList = gson.fromJson(in, listType);
                break;

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                saveInFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                throw new RuntimeException();
            }
        }
    }

    public void addHabit(View v) {
        habitName = (EditText) findViewById(R.id.editHabit);
        habitDate = (EditText) findViewById(R.id.dateInput);
        String name = habitName.getText().toString();
        String dateString = habitDate.getText().toString();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        days = new ArrayList<String>();
        MCB = (CheckBox) findViewById(R.id.McheckBox);
        TCB = (CheckBox) findViewById(R.id.TcheckBox);
        WCB = (CheckBox) findViewById(R.id.WcheckBox);
        RCB = (CheckBox) findViewById(R.id.RcheckBox);
        FCB = (CheckBox) findViewById(R.id.FcheckBox);
        SatCB = (CheckBox) findViewById(R.id.SatcheckBox);
        SunCB = (CheckBox) findViewById(R.id.SuncheckBox);
        if (MCB.isChecked()){
            days.add("Monday");
        }
        if (TCB.isChecked()){
            days.add("Tuesday");
        }
        if (WCB.isChecked()){
            days.add("Wednesday");
        }
        if (RCB.isChecked()){
            days.add("Thursday");
        }
        if (FCB.isChecked()){
            days.add("Friday");
        }
        if (SatCB.isChecked()){
            days.add("Saturday");
        }
        if (SunCB.isChecked()){
            days.add("Sunday");
        }
        try {
            Date date = dateFormat.parse(dateString);
            habitList.addHabit(new Habit(name,date,days));
            adapter.notifyDataSetChanged();
            saveInFile();
            finish();
        }catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultDate() {
        EditText defaultDate = (EditText) findViewById(R.id.dateInput);

        //http://stackoverflow.com/questions/5683728/convert-java-util-date-to-string
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        defaultDate.setText(reportDate);
    }

}
