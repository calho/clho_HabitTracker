package com.calvin.habit_tracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ListView oldHabitList;
    public static ArrayAdapter<Habit> adapter;
    private static final String FILENAME = "file.sav";
    private static ArrayList<Habit> habitList = new ArrayList<Habit>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveInFile();

        //http://stackoverflow.com/questions/9596663/how-to-make-items-clickable-in-list-view Sept 28, 2016
        oldHabitList = (ListView) findViewById(R.id.habitList);
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

        loadFromFile();

        oldHabitList = (ListView) findViewById(R.id.habitList);
        adapter = new ArrayAdapter<Habit>(this,
                R.layout.list_item, habitList);
        oldHabitList.setAdapter(adapter);
    }

    public void addHabit(View view) {
        Intent intent = new Intent(MainActivity.this, AddHabitActivity.class);
        startActivity(intent);
    }

    public void selectItem(View view,int position) {
        Intent intent = new Intent (MainActivity.this, IndividualHabit.class);
        Habit habit = adapter.getItem(position);
        intent.putExtra("Habit", habit);
        startActivity(intent);

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
            throw new RuntimeException("FUCK ME WHERE IS THE FILE");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("FUCK ME WHO KNOWS");
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
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

}
