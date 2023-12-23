package com.example.allquiz.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allquiz.Adapters.LevelAdapter;
import com.example.allquiz.Classes.RecyclerItemClickListener;
import com.example.allquiz.Modals.LevelModal;
import com.example.allquiz.R;
import com.example.allquiz.databinding.ActivityUserSeeLevelBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserSeeLevel extends AppCompatActivity {
    ActivityUserSeeLevelBinding bnd;
    FirebaseDatabase database;
    DatabaseReference myref;
    final String allLang ="allLang";
    final String allLevel ="allLevel";
    String levelName="levelName";
    String levelId="levelId";
    String langId="langId";
    ArrayList<LevelModal> list;
    LevelAdapter adapter;
    GridLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd= ActivityUserSeeLevelBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        langId = getIntent().getStringExtra(langId);
        database = FirebaseDatabase.getInstance();
        list = new ArrayList<>();
        adapter = new LevelAdapter(list,getApplicationContext());
        bnd.userSeeLevelRec.setAdapter(adapter);
        layoutManager = new GridLayoutManager(getApplicationContext(),1);
        bnd.userSeeLevelRec.setLayoutManager(layoutManager);
        database.getReference().child(allLang).child(langId).child(allLevel).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 if (snapshot.exists()){
                      for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                          LevelModal levelModal = dataSnapshot.getValue(LevelModal.class);
                          list.add(levelModal);
                      }
                      adapter.notifyDataSetChanged();
                      bnd.progressBar.setVisibility(View.GONE);
                 }else{
                     Toast.makeText(getApplicationContext(), "No item exists", Toast.LENGTH_SHORT).show();
                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
          Toast.makeText(getApplicationContext(),"Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        bnd.userSeeLevelRec.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), bnd.userSeeLevelRec, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LevelModal modal = list.get(position);
                Intent intent = new Intent(getApplicationContext(),AttendQuiz.class);
                intent.putExtra("langId",langId);
                intent.putExtra(levelId,modal.getLevelId());
                startActivity(intent);
            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }
}