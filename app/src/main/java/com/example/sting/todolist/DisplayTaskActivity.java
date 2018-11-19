package com.example.sting.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayTaskActivity extends AppCompatActivity {

    DBHelper myDB;
    private ArrayList<Tasks> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_layout);
        myDB = new DBHelper(this);
        checkTable();
        arrayList = myDB.getAllTasks();
        TaskAdapter taskAdapter = new TaskAdapter(DisplayTaskActivity.this,arrayList);
        ListView listView = findViewById(R.id.taskListView);
        listView.setAdapter(taskAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                arrayList = myDB.getAllTasks();
                final int idToDeleteUpdate = arrayList.get(position).getID();
                final String statusCheck = arrayList.get(position).getStatus();
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        DisplayTaskActivity.this);
                builder.setTitle("Action");
                builder.setCancelable(false);
                builder.setMessage("What would you like to do with this task?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myDB.deleteContact(idToDeleteUpdate);
                                Toast.makeText(DisplayTaskActivity.this,
                                        "Deleted Successfully",
                                        Toast.LENGTH_SHORT).show();
                                loadViews();
                                checkTable();
                            }
                        })
                        .setNegativeButton("COMPLETE", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (TextUtils.equals(statusCheck,"Completed")){
                                    Toast.makeText(DisplayTaskActivity.this,
                                            "Already completed",
                                            Toast.LENGTH_SHORT).show();
                                    loadViews();
                                    checkTable();
                                } else {
                                    myDB.updateTaskStatus(idToDeleteUpdate, "Completed");
                                    Toast.makeText(DisplayTaskActivity.this,
                                            "Marked as completed",
                                            Toast.LENGTH_SHORT).show();
                                    loadViews();
                                    checkTable();
                                }
                            }
                        })
                        .setNeutralButton("NOTHING", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                loadViews();
                                checkTable();
                            }
                        });
                builder.create();
                builder.show();
            }
        });
    }

    private void checkTable() {
        int numberOfTask = myDB.numberOfRows();
        if (numberOfTask == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Action");
            TextView msg = new TextView(this);
            String text = "It seems that you have no tasks";
            msg.setText(text);
            builder.setMessage(msg.getText())
                    .setCancelable(false)
                    .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent myIntent = new Intent(getApplicationContext(),
                                    AddTaskActivity.class);
                            startActivity(myIntent);
                        }
                    });
            builder.create();
            builder.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkTable();
        loadViews();
    }

    private void loadViews(){
        arrayList = myDB.getAllTasks();
        TaskAdapter taskAdapter = new TaskAdapter(DisplayTaskActivity.this,arrayList);
        ListView listView = findViewById(R.id.taskListView);
        listView.setAdapter(taskAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent myIntent = new Intent(getApplicationContext(),
                    AddTaskActivity.class);
            startActivity(myIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
