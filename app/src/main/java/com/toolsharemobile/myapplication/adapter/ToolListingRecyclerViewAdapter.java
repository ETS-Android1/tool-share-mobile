package com.toolsharemobile.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Tool;
import com.toolsharemobile.myapplication.R;

import java.util.List;


public class ToolListingRecyclerViewAdapter extends RecyclerView.Adapter{

    List<Tool> toolList;
    Context callingActivity;



    public ToolListingRecyclerViewAdapter(List<Tool> toolList){
        this.toolList = toolList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View toolListingFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_tool_listing, parent, false);

        return new ToolListViewHolder(toolListingFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView textViewToolName = holder.itemView.findViewById(R.id.textViewFindToolName);
        textViewToolName.setText(toolList.get(position).getToolType().toString());

    }

    @Override
    public int getItemCount() {
        return toolList.size();
    }

    public static class ToolListViewHolder extends RecyclerView.ViewHolder{

        public ToolListViewHolder(View itemView){
            super(itemView);
        }
    }

}
