package com.example.allquiz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allquiz.Modals.LevelModal;
import com.example.allquiz.R;

import java.util.ArrayList;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.levelHolder> {
    ArrayList<LevelModal> list;
    Context context;

    public LevelAdapter(ArrayList<LevelModal> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public levelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.level_row,parent,false);
        return new levelHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull levelHolder holder, int position) {
    LevelModal levelModal = list.get(position);
    holder.levelName.setText(levelModal.getLevelName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class levelHolder extends RecyclerView.ViewHolder {
        TextView levelName;
        public levelHolder(@NonNull View itemView) {
            super(itemView);
            levelName = itemView.findViewById(R.id.levelNameRow);
        }
    }
}
