package com.example.sting.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter <Tasks> {

    public TaskAdapter(Context context, ArrayList<Tasks> words) {
        super(context, 0,words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.task_layout, parent, false);
        }
        final Tasks currentTask = getItem(position);
        TextView taskTextView = listItemView.findViewById(R.id.taskTextView);
        taskTextView.setText(currentTask.getTask());
        TextView descriptionTextView = listItemView.findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(currentTask.getDescription());
        TextView statusTextView = listItemView.findViewById(R.id.statusTextView);
        statusTextView.setText(currentTask.getStatus());
        return listItemView;
    }
}
