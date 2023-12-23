package com.example.allquiz.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allquiz.R;
import com.example.allquiz.databinding.ActivityAddQuestionBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddQuestion extends AppCompatActivity {
    ActivityAddQuestionBinding bnd;
    FirebaseDatabase database;
    DatabaseReference myref;
    final String allLang ="allLang";
    final String allLevel ="allLevel";
    final String allQuestion ="allQuestion";
    String levelName="levelName"; String levelId="levelId"; String langId="langId";
    String question="question"; String option1="option1"; String option2="option2";
    String option3="option3"; String option4="option4"; String answer="answer";
    String explaination="explaination"; String questionId="questionId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityAddQuestionBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        database = FirebaseDatabase.getInstance();
        langId = getIntent().getStringExtra(langId);
        levelId =getIntent().getStringExtra(levelId);
        bnd.insertQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bnd.question.getText().toString().isEmpty()||bnd.option1.getText().toString().isEmpty()||bnd.option2.getText().toString().isEmpty()
                        ||bnd.option3.getText().toString().isEmpty()||bnd.option4.getText().toString().isEmpty()||bnd.answer.getText().toString().isEmpty()
                ){
                    Toast.makeText(getApplicationContext(), "Something is empty", Toast.LENGTH_SHORT).show();
                }else{
                    myref = database.getReference().child(allLang).child(langId).child(allLevel).child(levelId).child(allQuestion).push();
                    Map<String,Object> map  = new HashMap<>();
                    map.put(question, bnd.question.getText().toString());
                    map.put(option1, bnd.option1.getText().toString());
                    map.put(option2, bnd.option2.getText().toString());
                    map.put(option3, bnd.option3.getText().toString());
                    map.put(option4, bnd.option4.getText().toString());
                    map.put(answer, bnd.answer.getText().toString());
                    map.put(explaination, bnd.explaination.getText().toString());
                    map.put(questionId, myref.getKey());
                    myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "The question is inserted 😍 🤩 🤍", Toast.LENGTH_SHORT).show();
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