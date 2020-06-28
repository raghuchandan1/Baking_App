package com.example.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.data.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    List<Step> steps;
    private final StepAdapterOnClickHandler sClickHandler;

    public StepAdapter(StepAdapterOnClickHandler clickHandler){
        sClickHandler=clickHandler;
    }
    public interface StepAdapterOnClickHandler {
        void onClick(Step step, int stepPosition);
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
        String stepNo = (position == 0) ?"" :(position+". ") ;
        holder.stepsdescView.setText(stepNo+steps.get(position).getShortDescription());
        //holder.stepsdescView.setText("Steps"+position);
    }

    @Override
    public int getItemCount() {
        if(steps!=null)
            return steps.size();
        else
            return 0;
        //return 10;
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
            int adapterPosition=getAdapterPosition();
            Step stepDetails=steps.get(adapterPosition);
            sClickHandler.onClick(stepDetails,adapterPosition);
        }
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
