package com.example.allquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.allquiz.MainActivity;
import com.example.allquiz.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
       getSupportActionBar().hide();
       Thread thread = new Thread(){
           @Override
           public void run() {
               try {
                   sleep(4000);
               }catch (Exception e){
                   e.printStackTrace();
               }finally {
                   startActivity(new Intent(getApplicationContext(), UserSeeLanguage.class));
                   finish();
               }
           }
       }; thread.start();

    }
}