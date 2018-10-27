package com.example.sting.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayTaskActivity extends AppCompatActivity {

    private ListView listView;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_layout);
        myDB = new DBHelper(this);
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
        loadViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadViews();
    }

    private void loadViews(){
        final ArrayList<Tasks> arrayList = myDB.getAllTasks();
        TaskAdapter taskAdapter = new TaskAdapter(DisplayTaskActivity.this,arrayList);
        ListView listView = findViewById(R.id.taskListView);
        listView.setAdapter(taskAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final int idToDeleteUpdate = arrayList.get(position).getID();
                FragmentManager fm = getSupportFragmentManager();
                DialogFragment finalMessage = UpdateDeleteDialog.newInstance(idToDeleteUpdate);
                finalMessage.show(fm, "Message");
            }
        });
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
