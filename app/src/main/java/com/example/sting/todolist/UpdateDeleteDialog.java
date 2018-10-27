package com.example.sting.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateDeleteDialog extends DialogFragment {

    private DBHelper myDB;

    public static UpdateDeleteDialog newInstance(int idToDeleteUpdate) {
        UpdateDeleteDialog frag = new UpdateDeleteDialog();
        Bundle args = new Bundle();
        args.putInt("id", idToDeleteUpdate);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final int idToDeleteUpdate = getArguments().getInt("id");
        myDB = new DBHelper(getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Action");
        TextView msg = new TextView(getActivity());
        String text = "What would you like to do with this task?";
        msg.setText(text);
        builder.setMessage(msg.getText())
                .setCancelable(false)
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myDB.deleteContact(idToDeleteUpdate);
                        Toast.makeText(getActivity(), "Deleted Successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("COMPLETE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myDB.updateTaskStatus(idToDeleteUpdate,"Completed");
                        Toast.makeText(getActivity(), "Marked as completed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        builder.create();
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

