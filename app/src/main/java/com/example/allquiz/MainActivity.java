package com.example.allquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allquiz.Activities.AdminActs;
import com.example.allquiz.Activities.SeeLanguage;
import com.example.allquiz.Activities.UserSeeLanguage;
import com.example.allquiz.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
   ActivityMainBinding  bnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        bnd.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
            }
        });
      bnd.admin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(getApplicationContext(), AdminActs.class));
          }
      });
          bnd.user.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startActivity(new Intent(getApplicationContext(), UserSeeLanguage.class));
              }
          });
    }
    public void changeColor(){
        Random r = new Random();
        int color = Color.argb(255,r.nextInt(256),r.nextInt(256),r.nextInt(256));
        bnd.layout.setBackgroundColor(color);
    }
}