package com.example.goldfish;



import com.example.goldfish.bean.GoldFIshClick;
import com.example.myporject.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoldFishUtils {

    public static List<GoldFIshClick> goldFishBeanList = new ArrayList<>();
    public static List<GoldFIshClick> fishJsonList = new ArrayList<>();

    public static String base_goldfish = "https://goparker.com/600096/memory/goldfish/index.json";

    public static String goldfish = "https://goparker.com/600096/memory/goldfish/";


    public static List<Integer> goldfidhList(){
        List<Integer> integerList = new ArrayList<>();
        integerList.add(R.drawable.goldfish1);
        integerList.add(R.drawable.goldfish2);
        integerList.add(R.drawable.goldfish3);
        integerList.add(R.drawable.goldfish4);
        integerList.add(R.drawable.goldfish5);
        integerList.add(R.drawable.goldfish6);
        integerList.add(R.drawable.goldfish7);
        integerList.add(R.drawable.goldfish8);
        integerList.add(R.drawable.goldfish9);
        integerList.add(R.drawable.goldfish10);
        integerList.add(R.drawable.goldfish11);
        integerList.add(R.drawable.goldfish12);
        integerList.add(R.drawable.goldfish13);
        integerList.add(R.drawable.goldfish14);
        integerList.add(R.drawable.goldfish15);
        integerList.add(R.drawable.goldfish16);
        integerList.add(R.drawable.goldfish17);
        integerList.add(R.drawable.goldfish18);
        integerList.add(R.drawable.goldfish19);
        integerList.add(R.drawable.goldfish20);
        //打乱顺序
        Collections.shuffle(integerList);
        return integerList;
    }

    /**
     * 随机选取 num张图片，并复制一份，合并复制份数据，返回
     * @param
     * @return
     */
    public static List<GoldFIshClick> goldfidhListCopy(int num){
        List<GoldFIshClick> goldFishBeanList = new ArrayList<>();
        List<GoldFIshClick> all = new ArrayList<>();
        List<Integer> integerList = goldfidhList().subList(0,num);

        for (int i = 0; i < integerList.size(); i++) {
            GoldFIshClick goldFishBean  =new GoldFIshClick();
            goldFishBean.setCheck(false);
            goldFishBean.setId(0);
            goldFishBean.setPic(integerList.get(i));
            goldFishBeanList.add(goldFishBean);
        }

        List<GoldFIshClick> integerList1 = goldFishBeanList;
        goldFishBeanList.addAll(integerList1);
        for (int i = 0; i < goldFishBeanList.size(); i++) {
            GoldFIshClick goldFishBean  =new GoldFIshClick();
            goldFishBean.setCheck(false);
            goldFishBean.setId(i);
            goldFishBean.setPic(goldFishBeanList.get(i).getPic());
            all.add(goldFishBean);
        }
        Collections.shuffle(all);
        return all;
    }

}
