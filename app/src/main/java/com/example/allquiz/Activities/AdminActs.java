package com.example.allquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.allquiz.Fragments.DeleteOps;
import com.example.allquiz.Fragments.InsertOps;
import com.example.allquiz.R;
import com.example.allquiz.databinding.ActivityAdminActsBinding;

public class AdminActs extends AppCompatActivity {
    ActivityAdminActsBinding bnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd= ActivityAdminActsBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        replaceFrag(new InsertOps());
        bnd.insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFrag(new InsertOps());
            }
        });
        bnd.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFrag(new DeleteOps());
            }
        });
        bnd.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFrag(new DeleteOps());
            }
        });
    }
    public void  replaceFrag(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.replaout,fragment);
        transaction.commit();
    }
}