package com.example.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.data.Step;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    Step[] steps;
    private final StepAdapterOnClickHandler sClickHandler;

    public StepAdapter(StepAdapterOnClickHandler clickHandler){
        sClickHandler=clickHandler;
    }
    public interface StepAdapterOnClickHandler {
        void onClick(Step step);
    }

    @NonNull
    @Override
    public StepAdapter.StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        int stepLayout=R.layout.step_short_description;
        LayoutInflater inflater= LayoutInflater.from(context);
        View stepView=inflater.inflate(stepLayout, parent,false);

        return new StepViewHolder(stepView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapter.StepViewHolder holder, int position) {
        holder.stepsdescView.setText(steps[position].getShortDescription());
    }

    @Override
    public int getItemCount() {
        if(steps!=null)
            return steps.length;
        else
            return 0;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stepsdescView;

        public StepViewHolder(@NonNull View itemView) {
            super(itemView);
            stepsdescView = itemView.findViewById(R.id.short_description_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //TODO: Start the Step Detail Fragment
        }
    }
}
