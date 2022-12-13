package com.example.goldfish.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.goldfish.bean.GoldFIshClick;
import com.example.myporject.R;

public class GoldFishAdapter extends BaseQuickAdapter<GoldFIshClick, BaseViewHolder> {
    private Context context;
    private IGoldFishListener listener;
    public GoldFishAdapter(Context context, IGoldFishListener listener) {
        super(R.layout.item_gold_fish);
        this.context =context;
        this.listener = listener;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, GoldFIshClick goldFishBean) {

        if (goldFishBean.isCheck()){
            baseViewHolder.getView(R.id.rl).setBackground(null);
            ImageView imageView = baseViewHolder.getView(R.id.image);
            Glide.with(context).load(goldFishBean.getPic()).into(imageView);
        }else {
            baseViewHolder.getView(R.id.rl).setBackgroundColor(R.color.purple_500);
            ImageView imageView = baseViewHolder.getView(R.id.image);
            Glide.with(context).load(R.drawable.tileback_g).into(imageView);
        }
        baseViewHolder.getView(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.OnFishClick(goldFishBean);
                }
            }
        });
    }
}
