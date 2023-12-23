package com.example.allquiz.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allquiz.Adapters.LangAdapter;
import com.example.allquiz.Classes.RecyclerItemClickListener;
import com.example.allquiz.Modals.LangModal;
import com.example.allquiz.R;
import com.example.allquiz.databinding.ActivityUserSeeLanguageBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class UserSeeLanguage extends AppCompatActivity {
    ActivityUserSeeLanguageBinding bnd;
    FirebaseDatabase database;
    final String allLang = "allLang";
    String langId = "langId";
    LangAdapter langAdapter;
    ArrayList<LangModal> list;
    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityUserSeeLanguageBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        database = FirebaseDatabase.getInstance();
        list  = new ArrayList<>();
        langAdapter= new LangAdapter(list,getApplicationContext());
        bnd.userSeeLangRec.setAdapter(langAdapter);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        bnd.userSeeLangRec.setLayoutManager(gridLayoutManager);
        database.getReference().child(allLang).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        LangModal langModal =  dataSnapshot.getValue(LangModal.class);
                        list.add(langModal);
                    }
                    langAdapter.notifyDataSetChanged();
                    bnd.progressBar2.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(getApplicationContext(), "No item exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        bnd.userSeeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
            }
        });
        bnd.userSeeLangRec.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), bnd.userSeeLangRec, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LangModal modal = list.get(position);
                Intent intent = new Intent(getApplicationContext(),UserSeeLevel.class);
                intent.putExtra(langId,modal.getLangId());
                startActivity(intent);
            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }
    public void changeColor(){
        Random r = new Random();
        int color = Color.argb(255,r.nextInt(256),r.nextInt(256),r.nextInt(256));
        bnd.userSeeLayout.setBackgroundColor(color);
    }
}