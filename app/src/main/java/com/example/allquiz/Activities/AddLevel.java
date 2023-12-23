package com.example.allquiz.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allquiz.databinding.ActivityAddLevelBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddLevel extends AppCompatActivity {
   ActivityAddLevelBinding bnd;
    FirebaseDatabase database;
    DatabaseReference myref;
    final String allLang ="allLang";
    final String allLevel ="allLevel";
    String levelName="levelName";
    String levelId="levelId";
    String langId="langId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd= ActivityAddLevelBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        langId = getIntent().getStringExtra(langId);
        database = FirebaseDatabase.getInstance();
        bnd.insertLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bnd.levelName.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Something is empty", Toast.LENGTH_SHORT).show();
                }else{
                    myref = database.getReference().child(allLang).child(langId).child(allLevel).push();
                    Map<String,Object> map  = new HashMap<>();
                    map.put(levelName, bnd.levelName.getText().toString());
                    map.put(levelId, myref.getKey());
                    myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "The level is inserted 😍 🤩 🤍", Toast.LENGTH_SHORT).show();
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