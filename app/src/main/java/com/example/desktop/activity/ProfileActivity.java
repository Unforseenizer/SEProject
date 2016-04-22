package com.example.desktop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.desktop.project.R;
import com.example.desktop.setting.Settings;

public class ProfileActivity extends AppCompatActivity {

    ImageView profilepic, habit1, habit2, habit3, habit4, habit5, habit6;
    int current_image_index;
    int[] edu = {R.mipmap.edu1, R.mipmap.edu2};
    int[] love = {R.mipmap.love1, R.mipmap.love2};
    int[] music = {R.mipmap.music1, R.mipmap.music2};
    int[] sport = {R.mipmap.sport1, R.mipmap.sport2};
    int[] travel = {R.mipmap.travel1, R.mipmap.travel2};
    int[] other = {R.mipmap.other1, R.mipmap.other2};
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profilepic = (ImageView) findViewById(R.id.profilepic);
        habit1 = (ImageView) findViewById(R.id.habits1);
        habit2 = (ImageView) findViewById(R.id.habits2);
        habit3 = (ImageView) findViewById(R.id.habits3);
        habit4 = (ImageView) findViewById(R.id.habits4);
        habit5 = (ImageView) findViewById(R.id.habits5);
        habit6 = (ImageView) findViewById(R.id.habits6);


        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), 1);
            }
        });

        habit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_image_index++;
                current_image_index = current_image_index % edu.length;
                habit1.setImageResource(edu[current_image_index]);
            }
        });

        habit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_image_index++;
                current_image_index = current_image_index % love.length;
                habit2.setImageResource(love[current_image_index]);
            }
        });

        habit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_image_index++;
                current_image_index = current_image_index % music.length;
                habit3.setImageResource(music[current_image_index]);
            }
        });

        habit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_image_index++;
                current_image_index = current_image_index % sport.length;
                habit4.setImageResource(sport[current_image_index]);
            }
        });

        habit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_image_index++;
                current_image_index = current_image_index % travel.length;
                habit5.setImageResource(travel[current_image_index]);
            }
        });

        habit6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_image_index++;
                current_image_index = current_image_index % other.length;
                habit6.setImageResource(other[current_image_index]);
            }
        });

    }

    public void onActivityResult(int reqcode, int rescode, Intent data) {
        if (rescode == RESULT_OK) {
            if (reqcode == 1) {
                profilepic.setImageURI(data.getData());
                Settings.propic = data.getData();
            }
        }
    }
}
