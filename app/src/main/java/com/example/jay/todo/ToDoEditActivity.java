package com.example.jay.todo;


import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.text.ParseException;
import java.util.Date;

public class ToDoEditActivity extends AppCompatActivity implements View.OnClickListener{
    private ToDo toDoFill;
    EditText et_title, et_description;//, et_dueDate;
    Button buttonSave, buttonCancel;
    NumberPicker npMonth;
    NumberPicker npDay;
    NumberPicker npYear;
    int mDay,mMonth,mYear;
    long milliseconds_dueDate;
    private DatabaseAccessToDo databaseAccessToDo;
    final String[] monthValues= {"JAN","FEB", "MAR", "APR", "MAY","JUN","JULY", "AUG", "SEP", "OCT","NOV","DEC"};
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy 'at' hh:mm aaa");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_edit);
        databaseAccessToDo = DatabaseAccessToDo.getInstance(this);

        et_title = (EditText) findViewById(R.id.et_todo_edit_title);
        et_description = (EditText) findViewById(R.id.et_todo_edit_description);
        //et_dueDate = (EditText) findViewById(R.id.et_todo_edit_duedate);

        buttonSave = (Button) findViewById(R.id.todobtnSave);
        buttonCancel = (Button) findViewById(R.id.todobtnCancel);
        buttonSave.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);



        npMonth = (NumberPicker) findViewById(R.id.np_month);
        npDay = (NumberPicker) findViewById(R.id.np_day);
        npYear = (NumberPicker) findViewById(R.id.np_year);

        //Specify the minimum/maximum value/number of NumberPicker
        npMonth.setMinValue(0);
        npMonth.setMaxValue(monthValues.length-1);
        npMonth.setDisplayedValues(monthValues);
        npMonth.setWrapSelectorWheel(true);

        npDay.setMinValue(1);
        npDay.setMaxValue(31);
        npDay.setWrapSelectorWheel(true);


        npYear.setMinValue(2017);
        npYear.setMaxValue(2025);
        npYear.setWrapSelectorWheel(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            toDoFill = (ToDo) bundle.get("TODO");
            if (toDoFill != null) {
                this.et_title.setText(toDoFill.getTitle());
                this.et_description.setText(toDoFill.getDescription());
                Date dDate = new Date(toDoFill.getTimeDueDate());
                this.npDay.setValue(dDate.getDay());
                this.npMonth.setValue(dDate.getMonth());
                this.npYear.setValue(dDate.getYear());
            }
        }
        //Set a value change listener for NumberPicker
        npMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected value from picker
                //tv.setText("Selected value : " + values[newVal]);
                mMonth = newVal;
            }
        });

        npDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                mDay = newVal;
            }
        });

        npYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                mYear = newVal;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.todobtnSave: {
                onAddTodo();
                Log.e("TDA", "toDo Saved.");
                break;
            }
            case R.id.todobtnCancel: {
                onCancelClicked();
                Log.e("TDA", "Cancel Btn");
                break;
            }

        }
    }

    public void onCancelClicked() {
        this.finish();
    }

    public void onAddTodo() {
        ToDo toDo = new ToDo();
        toDo.setTitle(et_title.getText().toString());
        toDo.setDescription(et_description.getText().toString());
        toDo.setStatus(0);  //0->Not Done 1-> Done

        DateFormat formatter = null;
        Date convertedDate = null;

        String stringDateFormat = mDay+"/"+(mMonth+1)+"/"+mYear;
        //String stringDateFormat = "14/09/2011";
        formatter =new SimpleDateFormat("dd/MM/yyyy");
        try {
            convertedDate = formatter.parse(stringDateFormat);
            Log.e("onAddTodo : ","convertedDate Parsed");
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("onAddTodo : ","convertedDate NOT Parsed");
        }

        toDo.setTimeDueDate(convertedDate.getTime());
        //DatabaseAccessToDo databaseAccessToDo = DatabaseAccessToDo.getInstance(this);
        databaseAccessToDo.open();
        Log.e("onAddTodo : ","DB save started.");
        databaseAccessToDo.saveToDo(toDo);
        databaseAccessToDo.close();
        //this.finish();
        //onRestart();
        Log.e("onAddTodo : ","ToDo Saved in DB");
        this.finish();
    }
}
