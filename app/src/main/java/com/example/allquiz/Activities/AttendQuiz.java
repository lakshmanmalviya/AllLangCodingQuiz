package com.example.allquiz.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allquiz.Adapters.LevelAdapter;
import com.example.allquiz.Modals.LevelModal;
import com.example.allquiz.Modals.QuestionModal;
import com.example.allquiz.R;
import com.example.allquiz.databinding.ActivityAttendQuizBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class AttendQuiz extends AppCompatActivity {
      ActivityAttendQuizBinding bnd;
      MediaPlayer mp;
    FirebaseDatabase database;
    DatabaseReference myref;
    final String allLang ="allLang";
    final String allLevel ="allLevel";
    final String allQuestion ="allQuestion";
    String levelId="levelId";
    String langId="langId";
    ArrayList<QuestionModal> list;
    int count =0;
    int check =0;
    int hide =0;
    int resultCount =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd =ActivityAttendQuizBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        database = FirebaseDatabase.getInstance();
        langId = getIntent().getStringExtra(langId);
        levelId = getIntent().getStringExtra(levelId);
        list = new ArrayList<>();
        getList();
        bnd.prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count!=0){
                    count--;
                    resultCount--;
                }
                bnd.questionTv.setText("Q"+count+".  "+list.get(count).getQuestion());
                bnd.option1Tv.setText(list.get(count).getOption1());
                bnd.option2Tv.setText(list.get(count).getOption2());
                bnd.option3Tv.setText(list.get(count).getOption3());
                bnd.option4Tv.setText(list.get(count).getOption4());
                bnd.explainTv.setText("Explaination : \n"+list.get(count).getExplaination());
            }
        });
        bnd.next.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
                hide=0;
                bnd.explainTv.setVisibility(View.GONE);
                if (count==list.size()-2){
                   bnd.next.setText("Finish");
                }
                if (count<list.size()-1){
                    count++;
                }else{
                    bnd.resultAnn.setVisibility(View.VISIBLE);
                    if (resultCount<=list.size()/2-1){
                        bnd.resultAnn.setText("You should try again \n You got \n"+"( "+resultCount+" / "+list.size()+" )");
                    }
                    else{
                        bnd.resultAnn.setText("Congratulations \n You got \n"+"( "+resultCount+" / "+list.size()+" )");
                    }
                    bnd.layoutToResult.setVisibility(View.GONE);
                    resultCount=0;
                }
                bnd.questionTv.setText("Q"+count+".  "+list.get(count).getQuestion());
                bnd.option1Tv.setText(list.get(count).getOption1());
                bnd.option2Tv.setText(list.get(count).getOption2());
                bnd.option3Tv.setText(list.get(count).getOption3());
                bnd.option4Tv.setText(list.get(count).getOption4());
            }
        });
        bnd.showExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hide==1){
                 bnd.explainTv.setText("Explaination : \n"+list.get(count).getExplaination());
                 bnd.explainTv.setVisibility(View.VISIBLE);
                }
            }
        });
        bnd.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText("copy",list.get(count).getQuestion());
                clipboardManager.setPrimaryClip(data);
//                Toast.makeText(v.getContext(), "Question Copied....", Toast.LENGTH_SHORT).show();
                showMessage();
            }
        });
        bnd.option1Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hide==0){
                    hide =1;
                    if (!list.get(count).getAnswer().equalsIgnoreCase(bnd.option4Tv.getText().toString())){
                        playWrong();
                    }
                    if (list.get(count).getAnswer().equalsIgnoreCase(bnd.option1Tv.getText().toString())){
                        bnd.option1Tv.setText("✔   "+list.get(count).getAnswer());
                        resultCount++;
                        playRight();
                        checkAll(bnd.option1Tv);
                    }
                    else if(list.get(count).getAnswer().equalsIgnoreCase(bnd.option2Tv.getText().toString())){
                        bnd.option2Tv.setText("✔   "+list.get(count).getAnswer());
                        checkAll(bnd.option2Tv);
                    }
                    else if(list.get(count).getAnswer().equalsIgnoreCase(bnd.option3Tv.getText().toString())){
                        bnd.option3Tv.setText("✔   "+list.get(count).getAnswer());
                        checkAll(bnd.option3Tv);
                    }
                    else if (list.get(count).getAnswer().equalsIgnoreCase(bnd.option4Tv.getText().toString())){
                        bnd.option4Tv.setText("✔   "+list.get(count).getAnswer());
                        checkAll(bnd.option4Tv);
                    }
                }

            }
        });
        bnd.option2Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hide==0){
                    hide =1;
                    if (!list.get(count).getAnswer().equalsIgnoreCase(bnd.option4Tv.getText().toString())){
                        playWrong();
                    }
                    if (list.get(count).getAnswer().equalsIgnoreCase(bnd.option2Tv.getText().toString())){
                    bnd.option2Tv.setText("✔   "+list.get(count).getAnswer());
                        resultCount++;
                        playRight();
                        checkAll(bnd.option2Tv);
                }
                else if (list.get(count).getAnswer().equalsIgnoreCase(bnd.option1Tv.getText().toString())){
                    bnd.option1Tv.setText("✔   "+list.get(count).getAnswer());
                        checkAll(bnd.option1Tv);
                }
                else if(list.get(count).getAnswer().equalsIgnoreCase(bnd.option3Tv.getText().toString())){
                    bnd.option3Tv.setText("✔   "+list.get(count).getAnswer());
                        checkAll(bnd.option3Tv);
                }
                else if (list.get(count).getAnswer().equalsIgnoreCase(bnd.option4Tv.getText().toString())){
                    bnd.option4Tv.setText("✔   "+list.get(count).getAnswer());
                        checkAll(bnd.option4Tv);
                }
            }
            }
        });
        bnd.option3Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hide==0){
                    hide =1;
                    if (!list.get(count).getAnswer().equalsIgnoreCase(bnd.option4Tv.getText().toString())){
                        playWrong();
                    }
                    if (list.get(count).getAnswer().equalsIgnoreCase(bnd.option3Tv.getText().toString())){
                    bnd.option3Tv.setText("✔   "+list.get(count).getAnswer());
                        resultCount++;
                        playRight();
                    checkAll(bnd.option3Tv);

                } else if (list.get(count).getAnswer().equalsIgnoreCase(bnd.option2Tv.getText().toString())){
                    bnd.option2Tv.setText("✔   "+list.get(count).getAnswer());
                    checkAll(bnd.option2Tv);
                }
                else if (list.get(count).getAnswer().equalsIgnoreCase(bnd.option1Tv.getText().toString())){
                    bnd.option1Tv.setText("✔   "+list.get(count).getAnswer());
                    checkAll(bnd.option1Tv);
                }

                else if (list.get(count).getAnswer().equalsIgnoreCase(bnd.option4Tv.getText().toString())){
                    bnd.option4Tv.setText("✔   "+list.get(count).getAnswer());
                    checkAll(bnd.option4Tv);
                }
            }
            }
        });
        bnd.option4Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hide==0) {
                    hide =1;
                   if (!list.get(count).getAnswer().equalsIgnoreCase(bnd.option4Tv.getText().toString())){
                       playWrong();
                   }
                  if (list.get(count).getAnswer().equalsIgnoreCase(bnd.option4Tv.getText().toString())) {
                        bnd.option4Tv.setText("✔   " + list.get(count).getAnswer());
                        resultCount++;
                         playRight();
                        checkAll(bnd.option4Tv);
                    } else if (list.get(count).getAnswer().equalsIgnoreCase(bnd.option3Tv.getText().toString())) {
                        bnd.option3Tv.setText("✔   " + list.get(count).getAnswer());
                        checkAll(bnd.option3Tv);

                    } else if (list.get(count).getAnswer().equalsIgnoreCase(bnd.option2Tv.getText().toString())) {
                        bnd.option2Tv.setText("✔   " + list.get(count).getAnswer());
                        checkAll(bnd.option2Tv);
                    } else if (list.get(count).getAnswer().equalsIgnoreCase(bnd.option1Tv.getText().toString())) {
                        bnd.option1Tv.setText("✔   " + list.get(count).getAnswer());
                        checkAll(bnd.option1Tv);
                    }
                }
            }
        });
    }
    public void getList(){
        database.getReference().child(allLang).child(langId).child(allLevel).child(levelId).child(allQuestion).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        QuestionModal questionModal = dataSnapshot.getValue(QuestionModal.class);
                        list.add(questionModal);
                    }
                    bnd.questionTv.setText("Q"+count+".  "+list.get(0).getQuestion());
                    bnd.option1Tv.setText(list.get(0).getOption1());
                    bnd.option2Tv.setText(list.get(0).getOption2());
                    bnd.option3Tv.setText(list.get(0).getOption3());
                    bnd.option4Tv.setText(list.get(0).getOption4());
                    bnd.explainTv.setText("Explaination : \n "+list.get(0).getExplaination());
                }else{
                    Toast.makeText(getApplicationContext(), "No item exists", Toast.LENGTH_SHORT).show();
                    bnd.resultAnn.setVisibility(View.VISIBLE);
                    bnd.resultAnn.setText("No Quiz is there right now .\n\n Hopefully we will be adding soon \n\n"+"We appreciate for your patience\n😎 🤑 😃 ☺ ✔");
                    bnd.layoutToResult.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void checkAll(View v){
        if (v!=bnd.option1Tv){
            bnd.option1Tv.setText("❌   "+list.get(count).getOption1());
        }
         if (v!=bnd.option2Tv){
            bnd.option2Tv.setText("❌   "+list.get(count).getOption2());
        }
        if (v!=bnd.option3Tv){
            bnd.option3Tv.setText("❌   "+list.get(count).getOption3());
        }
        if (v!=bnd.option4Tv){
            bnd.option4Tv.setText("❌   "+list.get(count).getOption4());
        }
    }
   public  void playRight(){
       if (mp!=null){
           mp.release();
       }
        mp = MediaPlayer.create(getApplicationContext(),R.raw.click);
        mp.start();
   }
   public void playWrong(){
        if (mp!=null){
            mp.release();
        }
       mp= MediaPlayer.create(getApplicationContext(),R.raw.gameover);
       mp.start();
   }
   public void showMessage(){
        Toast toast = new Toast(getApplicationContext());
        View view = getLayoutInflater().inflate(R.layout.toast_msg, findViewById(R.id.toastLay));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        TextView toastMg = view.findViewById(R.id.toastMsg);
        toastMg.setText("The Question is Copied");
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
   }
}