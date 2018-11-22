package com.mobiledevelopent.gurcharnsinghsikka.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView toDoListText;
    private ListView toDoList;
    private Button addTaskButton;

    private ArrayList<TaskItem> taskItems;
    private CustomArrayAdapter customArrayAdapter;
    private TaskDbOpenHelper taskDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDoListText = (TextView) findViewById(R.id.to_do_list_text);
        toDoList = (ListView) findViewById(R.id.to_do_list);
        addTaskButton = (Button) findViewById(R.id.add_task_button);

        taskItems = new ArrayList<TaskItem>();
        customArrayAdapter = new CustomArrayAdapter(this, taskItems);
        taskDbOpenHelper = new TaskDbOpenHelper(this);

        toDoList.setAdapter(customArrayAdapter);

        retrieveTaskList();

        addTaskButtonHandler();
        toDoListItemClickHandler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        if(menuItemId == R.id.reset_to_do_list)
            taskDbOpenHelper.resetDb();
        else if(menuItemId == R.id.create_dummy_list)
            createDummyList();

        retrieveTaskList();
        customArrayAdapter.notifyDataSetChanged();
        return true;
    }

    private void retrieveTaskList(){
        taskItems.clear();
        taskItems.addAll(taskDbOpenHelper.getAllTasks());

        if(taskItems.isEmpty())
            toDoListText.setText(R.string.to_do_list_empty_text);
        else
            toDoListText.setText(R.string.to_do_list_text);
    }

    private void toDoListItemClickHandler(){
        toDoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editActivity = new Intent(MainActivity.this, EditActivity.class);
                editActivity.putExtra("taskId", customArrayAdapter.getTaskItemId(position));
                startActivityForResult(editActivity, 16);
            }
        });
    }

    private void addTaskButtonHandler(){
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTaskActivity = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(addTaskActivity);
            }
        });
    }

    private void createDummyList(){
        for(int i=0 ; i<3 ; i++)
            taskDbOpenHelper.insertTask(new TaskItem(0, "Dummy task " + i, "Dublin", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi eu ultrices eros. Curabitur sed purus leo."));
    }
}
