package com.factorybyte.todoapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.factorybyte.todoapp.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("ValidFragment")
public class BottomDialogFragment extends BottomSheetDialogFragment {

    private Context mainContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainContext = context;
    }

    private static final CollectionReference todoCollection =
            FirebaseFirestore.getInstance().collection("todos");

    public static BottomDialogFragment getInstance() {
        return new BottomDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.todo_options, container, false);

        assert getArguments() != null;
        final String documentId = getArguments().getString("documentId");
        final String todo_name = getArguments().getString("todo_name");
        final boolean todo_completed = getArguments().getBoolean("todo_completed");


        TextView todo_name_options = view.findViewById(R.id.todo_name_options);
        todo_name_options.setText(todo_name);
        TextView action_completed = view.findViewById(R.id.action_completed);
        String action = "Tarea Completada";
        if(todo_completed){
            action = "Desmarcar Tarea Completada";
        }
        action_completed.setText(action);

        action_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> update_todo = new HashMap<>();
                update_todo.put("completed", !todo_completed);
                assert documentId != null;
                todoCollection.document(documentId).update(update_todo);
                dismiss();
            }
        });




        TextView delete = view.findViewById(R.id.delete_todo);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mainContext);


                alertDialogBuilder.setTitle(v.getContext().getResources().getString(R.string.delete_todo));

                alertDialogBuilder
                        .setCancelable(false)
                        .setMessage(todo_name)
                        .setPositiveButton(v.getContext().getResources().getString(R.string.delete),new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                assert documentId != null;
                                todoCollection.document(documentId).delete();
                            }
                        })
                        .setNegativeButton(v.getContext().getResources().getString(R.string.cancel_todo),new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        });


        TextView cancel = view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

}


