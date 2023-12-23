package com.example.allquiz.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allquiz.databinding.ActivityAddLanguageBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddLanguage extends AppCompatActivity {
  ActivityAddLanguageBinding bnd;
  FirebaseDatabase database;
  DatabaseReference myref;
     final String allLang ="allLang";
    String langName="langName";
    String langId="langId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityAddLanguageBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        database = FirebaseDatabase.getInstance();
        bnd.insertLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bnd.langName.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Something is empty", Toast.LENGTH_SHORT).show();
                }else{
                    myref = database.getReference().child(allLang).push();
                    Map<String,Object> map  = new HashMap<>();
                    map.put(langName, bnd.langName.getText().toString());
                    map.put(langId, myref.getKey());
                    myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "The Language is inserted 😍 🤩 🤍", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}