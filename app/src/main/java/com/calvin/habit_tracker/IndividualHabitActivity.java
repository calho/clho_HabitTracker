package com.calvin.habit_tracker;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


//Activity that shows the records of a habit selected from a list view.
//contains a menu to allow for option of deletion of habit
//has the ability to complete the habit and displays total of times completed and date of.
//pressing the completion date brings up a pop up menu to delete it.
//pressing back navigation button brings you back to main menu

public class IndividualHabitActivity extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private static ArrayList<String> dayArrayList = new ArrayList<String>();
    private static ArrayList<String> completedDayArrayList = new ArrayList<String>();
    private static ArrayList<String> completedDays;
    private static HabitList habitList = new HabitList();
    private static ArrayAdapter<String> dayAdapter;
    private static ArrayAdapter<Habit> habitAdapter;
    private static ArrayAdapter<String> completedDaysAdapter;
    private static ListView completedDayList;
    private static TextView inputCompletes;
    private static Habit habit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_habit);

        loadFromFile();

        int habitPosition = (int)getIntent().getSerializableExtra("Habit");
        habit = habitList.getHabit(habitPosition);

        inputCompletes = (TextView) findViewById(R.id.completionCounter);

        completedDays = habit.getCompletionDates();
        habitAdapter = new ArrayAdapter<Habit>(IndividualHabitActivity.this,
                R.layout.day_list_item, habitList.getHabits());
        completedDayList = (ListView)findViewById(R.id.completedDateList);
        completedDayList.setAdapter(completedDaysAdapter);
        completedDaysAdapter = new ArrayAdapter<String>(IndividualHabitActivity.this,
                R.layout.day_list_item, completedDays);

        displayStat();

        ListView completedDayList = (ListView)findViewById(R.id.completedDateList);
        completedDayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Popup(position);
            }
        });
    }

    //http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
    public void Popup (final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(IndividualHabitActivity.this);
        builder.setTitle("Completed date Options");
        builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteCompletionDateFromList(position);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();
    }

    public void deleteCompletionDateFromList(int position){
        int habitPosition = (int)getIntent().getSerializableExtra("Habit");
        Habit habit = habitList.getHabit(habitPosition);
        habit.deleteCompletionsDate(position);
        completedDaysAdapter.notifyDataSetChanged();
        String completes = Integer.toString(habit.getCompletionsTotal());
        inputCompletes.setText(completes);
        saveInFile();
    }

    public void completeHabit(View v){

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Calendar.getInstance().getTime();
        String completionDate = dateFormat.format(today);
        habit.addCompletionsDate(completionDate);

        String completes = Integer.toString(habit.getCompletionsTotal());

        habitAdapter.notifyDataSetChanged();

        inputCompletes.setText(completes);

        saveInFile();
    }

    public void displayStat() {
        //http://stackoverflow.com/questions/32398668/how-to-pass-listview-selected-item-values-to-another-activity
        int habitPosition = (int)getIntent().getSerializableExtra("Habit");
        Habit habit = habitList.getHabit(habitPosition);
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
        dayAdapter = new ArrayAdapter<String>(this,
                R.layout.day_list_item, dayArrayList);
        dayList.setAdapter(dayAdapter);

        TextView inputCompletes = (TextView) findViewById(R.id.completionCounter);
        String completes = Integer.toString(habit.getCompletionsTotal());
        inputCompletes.setText(completes);

        ArrayList<String> completedDays = habit.getCompletionDates();
        ListView completedDayList = (ListView)findViewById(R.id.completedDateList);
        completedDaysAdapter = new ArrayAdapter<String>(IndividualHabitActivity.this,
                R.layout.day_list_item, completedDays);
        completedDayList.setAdapter(completedDaysAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    public void deleteOption(MenuItem menu) {
        int habitPosition = (int)getIntent().getSerializableExtra("Habit");
        Habit habit = habitList.getHabit(habitPosition);
        habitList.deleteHabit(habit);
        habitAdapter.notifyDataSetChanged();
        saveInFile();
        finish();
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
                saveInFile();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }
}
