package com.example.goldfish.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.goldfish.bean.GoldFIshClick;
import com.example.goldfish.GoldFishUtils;
import com.example.myporject.R;

import java.util.ArrayList;
import java.util.List;

public class Mode extends AppCompatActivity {

    private TextView easy,medium,hard;

    String detail = "";
    public static List<GoldFIshClick> fishJsonList = new ArrayList<>();
    public static List<GoldFIshClick> fishJsonList2 = new ArrayList<>();
    public static List<GoldFIshClick> fishJsonList3 = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        easy = findViewById(R.id.easy);
        medium = findViewById(R.id.medium);
        hard = findViewById(R.id.hard);


        fishJsonList = GoldFishUtils.goldfidhListCopy(4);
        fishJsonList2 = GoldFishUtils.goldfidhListCopy(8);
        fishJsonList3 = GoldFishUtils.goldfidhListCopy(12);




        detail = getIntent().getStringExtra("detail");
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    GoldFishUtils.fishJsonList = fishJsonList;
                    Intent intent = new Intent(Mode.this, PlayGame.class);
                    intent.putExtra("num",8);
                    startActivity(intent);
                    finish();
                    return;
            }
        });
        //difficult
       medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    GoldFishUtils.fishJsonList = fishJsonList2;
                    Intent intent = new Intent(Mode.this, PlayGame.class);
                    intent.putExtra("num",12);
                    startActivity(intent);
                    finish();
                    return;

            }
        });
        //hell
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoldFishUtils.fishJsonList = fishJsonList3;
                Intent intent = new Intent(Mode.this, PlayGame.class);
                intent.putExtra("num",18);
                startActivity(intent);
                finish();
            }
        });
    }

}
