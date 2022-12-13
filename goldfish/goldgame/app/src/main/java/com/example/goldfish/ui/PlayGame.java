package com.example.goldfish.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.goldfish.bean.Huancun;
import com.example.goldfish.HUancunDao;
import com.example.goldfish.adapter.GoldFishAdapter;
import com.example.goldfish.adapter.IGoldFishListener;
import com.example.goldfish.bean.GoldFIshClick;
import com.example.goldfish.SharedPreferencesUtils;
import com.example.goldfish.GoldFishUtils;
import com.example.goldfish.RecyclerViewDecoration;
import com.example.myporject.R;

import java.util.ArrayList;
import java.util.List;

public class PlayGame extends AppCompatActivity implements IGoldFishListener {

    private TextView tvCount;
    RecyclerView goldfish;
    GoldFishAdapter goldFishAdapter;

    private boolean hasCheck = false;//whether the current flip
    private Integer integer = -1;//whether the current flip
    private int ID = -1;//whether the current flip
    private int COUNT = 0;
    List<GoldFIshClick> goldFishBeanList = new ArrayList<>();
    String sql_count;
    private int num = 8;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        tvCount = findViewById(R.id.tv_count);
        goldfish = findViewById(R.id.goldfish);

        goldFishAdapter = new GoldFishAdapter(PlayGame.this,this);
        num = getIntent().getIntExtra("num",8);
        if (num == 8){//easy
            goldfish.setLayoutManager(new GridLayoutManager(getApplicationContext(),4));
        }else if (num ==12){//difficult
            goldfish.setLayoutManager(new GridLayoutManager(getApplicationContext(),4));
        }else {//hell
            goldfish.setLayoutManager(new GridLayoutManager(getApplicationContext(),4));
        }
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext(),"inter");
        int current = sharedPreferencesUtils.getInt("integer",-1);
        goldfish.addItemDecoration(new RecyclerViewDecoration(10));
        goldfish.setAdapter(goldFishAdapter);
        if (GoldFishUtils.goldFishBeanList.size() > 0){
            if (current!= -2){
                integer = current;
                ID = sharedPreferencesUtils.getInt("id",0);
            }
            goldFishBeanList = GoldFishUtils.goldFishBeanList;
            int n = 0;
            for (int i = 0; i < goldFishBeanList.size(); i++) {
                if (goldFishBeanList.get(i).isCheck()){
                    ++n;
                }
            }
            if (n%2 == 0){
                hasCheck = false;
            }else {
                hasCheck = true;
            }
            COUNT = sharedPreferencesUtils.getInt("count",0);
            tvCount.setText(getString(R.string.count)+COUNT);
        }else {
            tvCount.setText(getString(R.string.count)+COUNT);
            goldFishBeanList =  GoldFishUtils.fishJsonList;
        }

        goldFishAdapter.setNewData(goldFishBeanList);
        goldFishAdapter.notifyDataSetChanged();





        String hasSql = getIntent().getStringExtra("has");
        if (TextUtils.isEmpty(hasSql)){
            for (int i = 0; i <goldFishBeanList.size() ; i++) {
                Huancun history = new Huancun();
                history.setDiff(COUNT+"");
                history.setType("goldFish");
                history.setId(goldFishBeanList.get(i).getId());
                history.setPic(goldFishBeanList.get(i).getPic());
                history.setCheck(goldFishBeanList.get(i).isCheck()+"");
                HUancunDao.getInstance(getApplicationContext()).save(history);
            }
        }

    }

    private boolean canCheck = false;//set images to be clicked only every second
    @Override
    public void OnFishClick(GoldFIshClick goldFishBean) {

        if (!canCheck){
            canCheck = true;
            checkImage(goldFishBean);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    canCheck =false;
                }
            },1000);
        }

    }


    private void checkImage(GoldFIshClick goldFishBean){
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext(),"inter");

        ++COUNT;
        sharedPreferencesUtils.putInt("count",COUNT);
        tvCount.setText(getString(R.string.count)+COUNT);
        //if the current first click,or click on a different image
        //first click
        if (!hasCheck && integer != goldFishBean.getPic()){
            //Save the currently selected image
            hasCheck = true;
            ID = goldFishBean.getId();
            sharedPreferencesUtils.putInt("id",ID);
            integer = goldFishBean.getPic();
            sharedPreferencesUtils.putInt("integer",integer);
            changeGoldFish(goldFishBean);
            return;
        }


        if ((integer+"").equals(goldFishBean.getPic()+"")){
            integer = -1;
            hasCheck = false;
            sharedPreferencesUtils.putInt("integer",integer);
            sharedPreferencesUtils.putInt("id",ID);
            Toast.makeText(getApplicationContext(),getString(R.string.great),Toast.LENGTH_SHORT).show();
            changeGoldFish1(goldFishBean);
            return;
        }

        notGoldFish(goldFishBean);

    }
    private void changeGoldFish(GoldFIshClick goldFishBean){
        for (int i = 0; i < goldFishBeanList.size(); i++) {

            if (goldFishBean.getId() == goldFishBeanList.get(i).getId()){
                goldFishBeanList.get(i).setCheck(true);
            }
        }
        goldFishAdapter.setNewData(goldFishBeanList);
        goldFishAdapter.notifyDataSetChanged();
        changSql(goldFishBean);
    }

    private void changSql(GoldFIshClick goldFishBean){
        List<Huancun> historyList = HUancunDao.getInstance(getApplicationContext()).load("goldFish");
        if (historyList.size() ==0){
            return;
        }
        for (int i = 0; i < historyList.size(); i++) {
            if (goldFishBean.getId() == historyList.get(i).getId()){
                historyList.get(i).setCheck("true");
                historyList.get(i).setDiff(COUNT+"");
                HUancunDao.getInstance(getApplicationContext()).change(getApplicationContext(),historyList.get(i));
            }
        }
    }
    private void changeGoldFish1(GoldFIshClick goldFishBean){
        for (int i = 0; i < goldFishBeanList.size(); i++) {
            if (goldFishBean.getId() == goldFishBeanList.get(i).getId()){
                goldFishBeanList.get(i).setCheck(true);
            }
        }

        goldFishAdapter.setNewData(goldFishBeanList);
        goldFishAdapter.notifyDataSetChanged();
        changSql(goldFishBean);
        int  has = 0;
        for (int i = 0; i < goldFishBeanList.size(); i++) {
            //if finish
            if (goldFishBeanList.get(i).isCheck()){
                ++has;
            }
        }
        Log.d("shuliang",has+"");
        //click
        if (has == goldFishBeanList.size()){
            showTips();
        }
    }

    //hint
    private void showTips(){
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext(),"user");
        int name = sharedPreferencesUtils.getInt("u",0);
        int fen = sharedPreferencesUtils.getInt("fen",0);

        if (name ==0){
            showNormalDialog();
            return;
        }
        int user_count = fen;
        if ( COUNT < user_count){
            showNormalDialog();
            return;
        }
        if (user_count > COUNT){
            showMorelDialog();
            return;
        }
        showNormalDialog();

    }

    private void inputData(){
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext(),"user");
        sharedPreferencesUtils.putInt("u",10);
        sharedPreferencesUtils.putInt("fen",COUNT);
        delete();
    }

    //success hint
    private void showNormalDialog(){

        SharedPreferencesUtils cheUtils = new SharedPreferencesUtils(getApplicationContext(),"scores");
          int score = cheUtils.getInt("score",0);
          int score1 = cheUtils.getInt("score1",0);
          if (score1 > 0){
              score1 = 1;
          }
          if (score == 0){//if the first ti9me, no cached score
              int start = 90;//create an int between 90 amd 100
              int end = 100;
              int num = (int) (Math.random()*(end-start+1)+start);
              score = num;
              cheUtils.putInt("score",COUNT);// cached score
              cheUtils.putInt("score1",score);// cached score
          }else {
              if (score>COUNT){
                  int start = 80;
                  int end = 90;
                  int num = (int) (Math.random()*(end-start+1)+start);
                  score = num- score1 < 80 ? 79 : num- score1;
                  cheUtils.putInt("score",COUNT);
                  cheUtils.putInt("score1",score);
              }else {
                  int start = 90;
                  int end = 100;
                  int num = (int) (Math.random()*(end-start+1)+start);
                  score = num+1 > 100 ?100 : num+1;
                  cheUtils.putInt("score",COUNT);
                  cheUtils.putInt("score1",score);
              }
          }
        final AlertDialog.Builder normalDialog =  new AlertDialog.Builder(PlayGame.this);
        normalDialog.setTitle(getString(R.string.hint));
        normalDialog.setMessage(getString(R.string.win) +","+getString(R.string.count)+score  );
        normalDialog.setPositiveButton(getString(R.string.ok),  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do

                inputData();
            }
        });
        normalDialog.setNegativeButton(getString(R.string.cancel),  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
                inputData();
            }
        });
        // show
        normalDialog.show();
    }

    //more score
    private void showMorelDialog(){
        SharedPreferencesUtils cheUtils = new SharedPreferencesUtils(getApplicationContext(),"scores");
        int score = cheUtils.getInt("score",0);
        int score1 = cheUtils.getInt("score1",0);
        if (score1 > 0){
            score1 = 1;
        }
        if (score == 0){
            int start = 90;
            int end = 100;
            int num = (int) (Math.random()*(end-start+1)+start);
            score = num;
            cheUtils.putInt("score",COUNT);
            cheUtils.putInt("score1",score);
        }else {
            if (score>COUNT){
                int start = 80;
                int end = 90;
                int num = (int) (Math.random()*(end-start+1)+start);
                score = num- score1 < 80 ? 79 : num- score1;
                cheUtils.putInt("score",COUNT);
                cheUtils.putInt("score1",score);
            }else {
                int start = 90;
                int end = 100;
                int num = (int) (Math.random()*(end-start+1)+start);
                score = num+1 > 100 ?100 : num+1;
                cheUtils.putInt("score",COUNT);
                cheUtils.putInt("score1",score);
            }
        }
        final AlertDialog.Builder normalDialog =  new AlertDialog.Builder(PlayGame.this);
        normalDialog.setTitle(getString(R.string.hint) );
        normalDialog.setMessage(getString(R.string.win) +","+getString(R.string.count)+score  );
        normalDialog.setPositiveButton(getString(R.string.ok),  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
                inputData();
            }
        });
        normalDialog.setNegativeButton(getString(R.string.cancel),  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
                inputData();

            }
        });
        // show
        normalDialog.show();
    }
    private void notGoldFish(GoldFIshClick goldFishBean){
        for (int i = 0; i < goldFishBeanList.size(); i++) {
            if (goldFishBean.getId() == goldFishBeanList.get(i).getId()){
                goldFishBeanList.get(i).setCheck(true);
            }
        }
        goldFishAdapter.setNewData(goldFishBeanList);
        goldFishAdapter.notifyDataSetChanged();
        goldfish.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < goldFishBeanList.size(); i++) {
                    if (goldFishBean.getId() == goldFishBeanList.get(i).getId()){
                        goldFishBeanList.get(i).setCheck(false);
                    }
                }
                goldfish.setEnabled(true);
                goldFishAdapter.setNewData(goldFishBeanList);
                goldFishAdapter.notifyDataSetChanged();
            }
        },800);

    }

    private void delete(){
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext(),"inter");
        sharedPreferencesUtils.putInt("integer",-2);
        sharedPreferencesUtils.putInt("count",0);
        List<Huancun> historyList = HUancunDao.getInstance(getApplicationContext()).load("goldFish");
        if (historyList.size() > 0){
            for (int i = 0; i < historyList.size(); i++) {
                HUancunDao.getInstance(getApplicationContext()).delete(getApplicationContext(),historyList.get(i).getUid()+"");
            }
        }
        sharedPreferencesUtils.clear();
        finish();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        GoldFishUtils.goldFishBeanList.clear();
    }
}

