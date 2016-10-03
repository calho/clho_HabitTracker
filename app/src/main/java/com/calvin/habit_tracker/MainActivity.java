/*
 Copyright 2016 Calvin Ho clho@ualberta.ca

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
limitations under the License.
*/

package com.calvin.habit_tracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

//displays all habits created initially.
//going to menu brings down a menu to filter to what day of habits you like to see
//pressing add button allows for adding of a  habit


public class MainActivity extends AppCompatActivity {
    private static ListView oldHabitList;
    private static ListView MOldHabitList;
    private static ListView TOldHabitList;
    private static ListView WOldHabitList;
    private static ListView ROldHabitList;
    private static ListView FOldHabitList;
    private static ListView SatOldHabitList;
    private static ListView SunOldHabitList;
    private static ArrayAdapter<Habit> adapter;
    private static final String FILENAME = "file.sav";
    private static HabitList habitList = new HabitList();
    private static String currentViewDay = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //http://stackoverflow.com/questions/9596663/how-to-make-items-clickable-in-list-view Sept 28, 2016
        oldHabitList = (ListView) findViewById(R.id.habitList);
        MOldHabitList = (ListView) findViewById(R.id.habitList);
        TOldHabitList = (ListView) findViewById(R.id.habitList);
        WOldHabitList = (ListView) findViewById(R.id.habitList);
        ROldHabitList = (ListView) findViewById(R.id.habitList);
        FOldHabitList = (ListView) findViewById(R.id.habitList);
        SatOldHabitList = (ListView) findViewById(R.id.habitList);
        SunOldHabitList = (ListView) findViewById(R.id.habitList);

        TextView selectedHabit = (TextView) findViewById(R.id.habitListTitle);
        selectedHabit.setText("List of all Habits");

        oldHabitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectItem(view,position);
            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        while (true) {
            try {
                loadFromFile();
                break;
            } catch (Exception e) {
                saveInFile();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter = new ArrayAdapter<Habit>(MainActivity.this,
                R.layout.list_item, habitList.getHabits(currentViewDay));
        oldHabitList.setAdapter(adapter);

        TextView selectedHabit = (TextView) findViewById(R.id.habitListTitle);
        String string = String.format("List of %s Habits", currentViewDay);
        selectedHabit.setText(string);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    public void MondayOption(MenuItem menu) {
        currentViewDay = "Monday";
        adapter = new ArrayAdapter<Habit>(MainActivity.this,
                R.layout.list_item, habitList.getHabits(currentViewDay));
        oldHabitList.setAdapter(adapter);
        saveInFile();

        TextView selectedHabit = (TextView) findViewById(R.id.habitListTitle);
        selectedHabit.setText("List of Monday Habits");
    }

    public void TuesdayOption(MenuItem menu) {
        currentViewDay = "Tuesday";
        adapter = new ArrayAdapter<Habit>(MainActivity.this,
                R.layout.list_item, habitList.getHabits(currentViewDay));
        oldHabitList.setAdapter(adapter);
        saveInFile();

        TextView selectedHabit = (TextView) findViewById(R.id.habitListTitle);
        selectedHabit.setText("List of Tuesday Habits");
    }

    public void WednesOption(MenuItem menu) {
        currentViewDay = "Wednesday";
        adapter = new ArrayAdapter<Habit>(MainActivity.this,
                R.layout.list_item, habitList.getHabits(currentViewDay));
        oldHabitList.setAdapter(adapter);
        saveInFile();

        TextView selectedHabit = (TextView) findViewById(R.id.habitListTitle);
        selectedHabit.setText("List of Wednesday Habits");
    }

    public void ThursdayOption(MenuItem menu) {
        currentViewDay = "Thursday";
        adapter = new ArrayAdapter<Habit>(MainActivity.this,
                R.layout.list_item, habitList.getHabits(currentViewDay));
        oldHabitList.setAdapter(adapter);
        saveInFile();

        TextView selectedHabit = (TextView) findViewById(R.id.habitListTitle);
        selectedHabit.setText("List of Thursday Habits");
    }

    public void FridayOption(MenuItem menu) {
        currentViewDay = "Friday";
        adapter = new ArrayAdapter<Habit>(MainActivity.this,
                R.layout.list_item, habitList.getHabits(currentViewDay));
        oldHabitList.setAdapter(adapter);
        saveInFile();

        TextView selectedHabit = (TextView) findViewById(R.id.habitListTitle);
        selectedHabit.setText("List of Friday Habits");
    }

    public void SaturdayOption(MenuItem menu) {
        currentViewDay = "Saturday";
        adapter = new ArrayAdapter<Habit>(MainActivity.this,
                R.layout.list_item, habitList.getHabits(currentViewDay));
        oldHabitList.setAdapter(adapter);
        saveInFile();

        TextView selectedHabit = (TextView) findViewById(R.id.habitListTitle);
        selectedHabit.setText("List of Saturday Habits");
    }

    public void SundayOption(MenuItem menu) {
        currentViewDay = "Sunday";
        adapter = new ArrayAdapter<Habit>(MainActivity.this,
                R.layout.list_item, habitList.getHabits(currentViewDay));
        oldHabitList.setAdapter(adapter);
        saveInFile();

        TextView selectedHabit = (TextView) findViewById(R.id.habitListTitle);
        selectedHabit.setText("List of Sunday Habits");
    }

    public void AllOption(MenuItem menu) {
        currentViewDay = "All";
        adapter = new ArrayAdapter<Habit>(MainActivity.this,
                R.layout.list_item, habitList.getHabits());
        oldHabitList.setAdapter(adapter);
        saveInFile();

        TextView selectedHabit = (TextView) findViewById(R.id.habitListTitle);
        selectedHabit.setText("List of All Habits");
    }

    public void addHabit(View view) {
        Intent intent = new Intent(MainActivity.this, AddHabitActivity.class);
        startActivity(intent);
    }

    public void selectItem(View view,int position) {
        Intent intent = new Intent (MainActivity.this, IndividualHabitActivity.class);
        Habit habit = adapter.getItem(position);
        intent.putExtra("Habit", position);
        startActivity(intent);

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
                saveInFile();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(habitList, writer);
            writer.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
