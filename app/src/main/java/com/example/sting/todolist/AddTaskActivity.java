package com.example.sting.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    private DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        myDB = new DBHelper(AddTaskActivity.this);

        Button addTaskButton = findViewById(R.id.addTaskButton);
        final TextView addTaskTexView = findViewById(R.id.addTaskTextView);
        final TextView addDescriptionTextView = findViewById(R.id.addDescriptionTextView);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = addTaskTexView.getText().toString();
                String description = addDescriptionTextView.getText().toString();
                String status = "To do";
                if(TextUtils.isEmpty(task) || TextUtils.isEmpty(description)){
                    Toast.makeText(AddTaskActivity.this,"Please complete all fields",
                            Toast.LENGTH_SHORT).show();
                } else {
                    myDB.insertTask(task, description, status);
                    Toast.makeText(AddTaskActivity.this, "Task added",
                            Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(getApplicationContext(),
                            DisplayTaskActivity.class);
                    startActivity(myIntent);
                }
            }
        });

    }

    @Override
    public void onBackPressed(){

    }
}
