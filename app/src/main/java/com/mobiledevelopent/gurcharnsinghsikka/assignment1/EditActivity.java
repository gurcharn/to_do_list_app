package com.mobiledevelopent.gurcharnsinghsikka.assignment1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends Activity {


    private EditText taskName;
    private EditText taskPlace;
    private EditText taskMessage;
    private Button updateTaskButton;
    private Button deleteTaskButton;
    private Button goBackButton;
    private TaskDbOpenHelper taskDbOpenHelper;
    private TaskItem taskItem;
    private Intent intent;
    private int taskId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        taskName = (EditText) findViewById(R.id.add_task_name);
        taskPlace = (EditText) findViewById(R.id.add_task_place);
        taskMessage = (EditText) findViewById(R.id.add_task_message);
        updateTaskButton = (Button) findViewById(R.id.update_task_button);
        deleteTaskButton = (Button) findViewById(R.id.delete_task_button);
        goBackButton = (Button) findViewById(R.id.go_back_button);
        taskDbOpenHelper = new TaskDbOpenHelper(this);
        intent = getIntent();
        taskId = intent.getIntExtra("taskId", -1);

        retrieveTaskItem();
        initEditTaskFields();

        updateTaskButtonHandler();
        deleteTaskButtonHandler();
        goBackButtonHandler();
    }

    private void retrieveTaskItem(){
        taskItem = taskDbOpenHelper.getTask(taskId);
    }

    private void initEditTaskFields(){
        taskName.setText(taskItem.getName());
        taskPlace.setText(taskItem.getPlace());
        taskMessage.setText(taskItem.getMessage());
    }

    private void updateTaskButtonHandler(){
        updateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTaskItem();

                boolean taskUpdated =  taskDbOpenHelper.updateTask(taskItem);;

                if(taskUpdated){
                    Toast.makeText(EditActivity.this, "Updated task : " + taskName.getText().toString(), Toast.LENGTH_LONG).show();
                    startMainActivity();
                }else {
                    Toast.makeText(EditActivity.this, "Error : Task not created !!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void deleteTaskButtonHandler(){
        deleteTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDbOpenHelper.deleteTask(taskId);
                Toast.makeText(EditActivity.this, "Deleted task : " + taskName.getText().toString(), Toast.LENGTH_LONG).show();
                startMainActivity();
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
        Intent mainActivity = new Intent(EditActivity.this, MainActivity.class);
        startActivity(mainActivity);
    }

    private void updateTaskItem(){
        taskItem.setName(taskName.getText().toString());
        taskItem.setPlace(taskPlace.getText().toString());
        taskItem.setMessage(taskMessage.getText().toString());
    }
}
