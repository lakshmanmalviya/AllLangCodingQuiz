package com.example.allquiz.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allquiz.Adapters.LangAdapter;
import com.example.allquiz.Classes.RecyclerItemClickListener;
import com.example.allquiz.Modals.LangModal;
import com.example.allquiz.databinding.ActivitySeeLanguageBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class SeeLanguage extends AppCompatActivity {
  ActivitySeeLanguageBinding bnd;
  FirebaseDatabase database;
  final String allLang = "allLang";
   String langId = "langId";
  LangAdapter langAdapter;
  ArrayList<LangModal> list;
  GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivitySeeLanguageBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        database = FirebaseDatabase.getInstance();
        list  = new ArrayList<>();
        langAdapter= new LangAdapter(list,getApplicationContext());
         bnd.seeLangRec.setAdapter(langAdapter);
          gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
          bnd.seeLangRec.setLayoutManager(gridLayoutManager);
         database.getReference().child(allLang).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        LangModal langModal =  dataSnapshot.getValue(LangModal.class);
                        list.add(langModal);
                    }
                    langAdapter.notifyDataSetChanged();
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
        bnd.seeLangLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
            }
        });
        bnd.seeLangRec.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), bnd.seeLangRec, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LangModal modal = list.get(position);
                if ("level".equals(getIntent().getStringExtra("level"))){
                    Intent intent = new Intent(getApplicationContext(),AddLevel.class);
                    intent.putExtra(langId,modal.getLangId());
                    startActivity(intent);
                }
                if("question".equals(getIntent().getStringExtra("question"))){
                    Intent intent = new Intent(getApplicationContext(),SeeLevel.class);
                    intent.putExtra(langId,modal.getLangId());
                    startActivity(intent);
                }

            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }
    public void changeColor(){
        Random r = new Random();
        int color = Color.argb(255,r.nextInt(256),r.nextInt(256),r.nextInt(256));
        bnd.seeLangLayout.setBackgroundColor(color);
    }
}