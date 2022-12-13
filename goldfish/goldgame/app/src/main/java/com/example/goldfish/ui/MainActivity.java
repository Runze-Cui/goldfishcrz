package com.example.goldfish.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.goldfish.bean.Huancun;
import com.example.goldfish.HUancunDao;
import com.example.goldfish.bean.GoldFish;
import com.example.goldfish.bean.GoldFIshClick;
import com.example.goldfish.SwitchUtils;
import com.example.goldfish.GsonUtils;
import com.example.goldfish.GoldFishUtils;
import com.example.myporject.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView play;
    List<String> stringList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        play = findViewById(R.id.play);

        //start game
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoldFishUtils.goldFishBeanList.clear();
                List<Huancun> historyList = HUancunDao.getInstance(getApplicationContext()).load("goldFish");
                if (historyList.size() > 0) {
                    for (int i = 0; i < historyList.size(); i++) {
                        GoldFIshClick goldFishBean = new GoldFIshClick();
                        goldFishBean.setId(historyList.get(i).getId());
                        goldFishBean.setPic(historyList.get(i).getPic());
                        if ("true".equals(historyList.get(i).getCheck())) {
                            goldFishBean.setCheck(true);
                        } else {
                            goldFishBean.setCheck(false);
                        }
                        GoldFishUtils.goldFishBeanList.add(goldFishBean);
                    }
                    Intent intent = new Intent(MainActivity.this, PlayGame.class);
                    intent.putExtra("num", 8);
                    intent.putExtra("has", "has");
                    intent.putExtra("count", historyList.get(0).getDiff());
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(MainActivity.this, Mode.class);
                intent.putExtra("detail","goldfish");
                startActivity(intent);
            }
        });

        getGoldFish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.change:
                String sta= getResources().getConfiguration().locale.getLanguage();
                SwitchUtils.translateText(MainActivity.this,sta);
                break;
        }
        return true;
    }

    /**
     * 获取金鱼图片
     */
    public void getGoldFish(){
        final OkHttpClient client = new OkHttpClient();
        final Request request=new Request.Builder()
                .get()
                .tag(this)
                .url(GoldFishUtils.base_goldfish)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String data  = response.body().string();
                        GoldFish goldFishBean = GsonUtils.getInstance().fromJson(data, GoldFish.class);
                        if (goldFishBean !=null && goldFishBean.getPictureset()!=null){
                            for (int i = 0; i < goldFishBean.getPictureset().size(); i++) {
                                stringList.add(GoldFishUtils.goldfish+goldFishBean.getPictureset().get(i));
                            }
                        }
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}