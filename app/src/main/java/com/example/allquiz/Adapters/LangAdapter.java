package com.example.allquiz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allquiz.Modals.LangModal;
import com.example.allquiz.R;

import java.util.ArrayList;

public class LangAdapter extends RecyclerView.Adapter<LangAdapter.LangHolder> {
    ArrayList<LangModal> list;
    Context context;

    public LangAdapter(ArrayList<LangModal> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lang_row,parent,false);
        return new LangHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LangHolder holder, int position) {
       LangModal langModal = list.get(position);
       holder.langName.setText(langModal.getLangName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LangHolder extends RecyclerView.ViewHolder {
        TextView langName;
        public LangHolder(@NonNull View itemView) {
            super(itemView);
            langName  = itemView.findViewById(R.id.langNameRow);
        }
    }
}
