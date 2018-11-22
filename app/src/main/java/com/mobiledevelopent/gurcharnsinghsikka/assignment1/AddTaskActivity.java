package com.mobiledevelopent.gurcharnsinghsikka.assignment1;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends Activity{

    private EditText taskName;
    private EditText taskPlace;
    private EditText taskMessage;
    private Button addNewTaskButton;
    private Button goBackButton;
    private TaskDbOpenHelper taskDbOpenHelper;
    private TaskItem taskItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_layout);

        taskName = (EditText) findViewById(R.id.add_task_name);
        taskPlace = (EditText) findViewById(R.id.add_task_place);
        taskMessage = (EditText) findViewById(R.id.add_task_message);
        addNewTaskButton = (Button) findViewById(R.id.add_new_task_button);
        goBackButton = (Button) findViewById(R.id.go_back_button);
        taskDbOpenHelper = new TaskDbOpenHelper(this);
        taskItem = new TaskItem();

        addNewTaskButtonHandler();
        goBackButtonHandler();
    }

    private void addNewTaskButtonHandler(){
        addNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTaskItemObject();
                boolean taskInserted =  taskDbOpenHelper.insertTask(taskItem);

                if(taskInserted){
                    Toast.makeText(AddTaskActivity.this, "New Task created", Toast.LENGTH_LONG).show();
                    startMainActivity();
                }else {
                    Toast.makeText(AddTaskActivity.this, "Error : Task not created !!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void goBackButtonHandler(){
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });
    }

    private void startMainActivity(){
        Intent mainActivity = new Intent(AddTaskActivity.this, MainActivity.class);
        startActivity(mainActivity);
    }

    private void setTaskItemObject(){
        taskItem.setName(taskName.getText().toString());
        taskItem.setPlace(taskPlace.getText().toString());
        taskItem.setMessage(taskMessage.getText().toString());
    }
}