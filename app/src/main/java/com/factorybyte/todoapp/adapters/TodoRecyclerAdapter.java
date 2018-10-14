package com.factorybyte.todoapp.adapters;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.factorybyte.todoapp.R;
import com.factorybyte.todoapp.models.Todo;
import com.factorybyte.todoapp.viewholders.TodoViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class TodoRecyclerAdapter extends FirestoreRecyclerAdapter<Todo, TodoViewHolder> {


    public TodoRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Todo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TodoViewHolder holder, int position, @NonNull Todo model) {
        holder.setTodoName(model.getName());
        holder.setCompleted(model.isCompleted());
        holder.setTodoOptions(model.getName());
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_todo, viewGroup, false);
        return new TodoViewHolder(view, viewGroup.getContext());
    }

    @Override
    public void onDataChanged() {

    }


}
