package com.example.goldfish.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import com.example.goldfish.bean.GoldFIshClick;
import com.example.myporject.R;

public class ModeAdapter extends BaseQuickAdapter<GoldFIshClick, BaseViewHolder> {
    private Context context;
    public ModeAdapter(Context context) {
        super(R.layout.item_gold_fish);
        this.context =context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, GoldFIshClick goldFishBean) {


            ImageView imageView = baseViewHolder.getView(R.id.image);
            Glide.with(context).load(goldFishBean.getPic()).into(imageView);
    }
}
