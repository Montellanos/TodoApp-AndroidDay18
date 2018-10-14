package com.factorybyte.todoapp;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.factorybyte.todoapp.adapters.TodoRecyclerAdapter;
import com.factorybyte.todoapp.models.Todo;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    private RecyclerView todoRecyclerView;
    private FirebaseFirestore todoRef = FirebaseFirestore.getInstance();
    private FirestoreRecyclerAdapter todoAdapter;
    private static final CollectionReference todoCollection =
            FirebaseFirestore.getInstance().collection("todos");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(R.string.app_name);

        initAddTodo();


        todoRecyclerView = findViewById(R.id.todo_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        todoRecyclerView.setHasFixedSize(true);
        todoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        todoRecyclerView.setLayoutManager(mLayoutManager);

        setupView();

    }

    private void setupView() {

        Query query = todoRef
                .collection("todos")
                .orderBy("completed", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Todo> options = new FirestoreRecyclerOptions.Builder<Todo>()
                .setQuery(query, new SnapshotParser<Todo>() {
                    @NonNull
                    @Override
                    public Todo parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        Todo todo = snapshot.toObject(Todo.class);
                        assert todo != null;
                        todo.setDocumentId(snapshot.getId());
                        return todo;
                    }
                })
                .setLifecycleOwner(this)
                .build();

        todoAdapter = new TodoRecyclerAdapter(options);
        todoAdapter.notifyDataSetChanged();
        todoRecyclerView.setAdapter(todoAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        todoAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        todoAdapter.stopListening();
    }



    private void initAddTodo() {

        FloatingActionButton add_todo = findViewById(R.id.add_todo);

        add_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ViewGroup nullParent = null;
                LayoutInflater li = LayoutInflater.from(v.getContext());
                final View inputView = li.inflate(R.layout.input_todo, nullParent);


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        v.getContext());

                alertDialogBuilder.setView(inputView);

                alertDialogBuilder.setTitle(v.getContext().getResources().getString(R.string.new_todo));
                final EditText new_todo_name =  inputView.findViewById(R.id.new_todo_name);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton(v.getContext().getResources().getString(R.string.save_todo),new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                String todo_name = new_todo_name.getText().toString();
                                Todo newTodo = new Todo(todo_name, false);
                                todoCollection.add(newTodo);
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
    }
}
