package com.factorybyte.todoapp.viewholders;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.factorybyte.todoapp.R;


public class TodoViewHolder extends RecyclerView.ViewHolder {

    private TextView todo_name;
    private RadioButton todo_completed_radio;
    private TextView todo_completed_text;
    private Context context;
    private ConstraintLayout todo_options;

    public TodoViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.todo_name = itemView.findViewById(R.id.todo_name);
        this.todo_completed_radio = itemView.findViewById(R.id.todo_completed_radio);
        this.todo_completed_text = itemView.findViewById(R.id.todo_completed_text);
        this.context = context;
        this.todo_options =  itemView.findViewById(R.id.todo_options);
    }

    public void setTodoName(String name){
        this.todo_name.setText(name);
    }

    public void setCompleted(boolean completed) {
        this.todo_completed_radio.setChecked(completed);
        this.todo_completed_radio.setClickable(false);
    }

    public void setCompletedText(){
        this.todo_completed_text.setText(this.context.getResources().getString(R.string.completed_text));
        this.todo_name.setPaintFlags(this.todo_name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        this.todo_name.setTextColor(context.getResources().getColor(R.color.completed));
    }


    public void setTodoOptions(final String name) {
        this.todo_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), name, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
