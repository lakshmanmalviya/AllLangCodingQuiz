package com.example.allquiz.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.allquiz.Activities.AddLanguage;
import com.example.allquiz.Activities.SeeLanguage;
import com.example.allquiz.databinding.FragmentInsertOpsBinding;

public class InsertOps extends Fragment {
 FragmentInsertOpsBinding bnd;
 public InsertOps() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
     bnd = FragmentInsertOpsBinding.inflate(inflater,container,false);
     bnd.insertLangs.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(getContext(), AddLanguage.class));
         }
     });
     bnd.insertLevels.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getContext(),SeeLanguage.class);
             intent.putExtra("level","level");
             startActivity(intent);
         }
     });
     bnd.insertQs.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getContext(),SeeLanguage.class);
             intent.putExtra("question","question");
             startActivity(intent);
         }
     });

     return bnd.getRoot();
    }
}