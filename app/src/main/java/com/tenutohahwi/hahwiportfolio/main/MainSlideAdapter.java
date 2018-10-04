package com.tenutohahwi.hahwiportfolio.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tenutohahwi.hahwiportfolio.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shyknight_m@naver.com
 * @class SevenSat
 * @date 2016-05-25
 * @see
 */
public class MainSlideAdapter extends BaseAdapter {
    Context context;
    int layout;
    private ArrayList<MainSlide> arrayList;

    public MainSlideAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
    }

    public void setData( ArrayList<MainSlide> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        if (arrayList == null || arrayList.get(position) == null) {
            return null;
        }
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if( null != convertView){
            holder = (ViewHolder) convertView.getTag();
        }else{
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }


        MainSlide data = arrayList.get(position);

        if (data != null) {
            holder.ivSlideImage.setVisibility(View.INVISIBLE);
            if (0 == data.getType()) {
                holder.ivSlideImage.setVisibility(View.VISIBLE);
                holder.llSlideItem.setVisibility(View.VISIBLE);
                holder.llSlideItemSetting.setVisibility(View.GONE);
                holder.tvSlideTitle.setText(data.getSlideText());
                holder.llSlideChildItem.setVisibility(View.GONE);
            } else if (1 == data.getType()) {
                holder.llSlideChildItem.setVisibility(View.VISIBLE);
                holder.llSlideItem.setVisibility(View.GONE);
                holder.llSlideItemSetting.setVisibility(View.GONE);
                holder.tvSlideChildTitle.setText(data.getSlideText());
            } else if (2 == data.getType()) {
                holder.llSlideChildItem.setVisibility(View.GONE);
                holder.llSlideItem.setVisibility(View.GONE);
                holder.llSlideItemSetting.setVisibility(View.VISIBLE);
                holder.tvSlideTitle.setText(data.getSlideText());
            }


            if (data.getSlideText().equals(context.getResources().getString(R.string.slide_menu_1))) {
                holder.ivSlideImage.setImageResource(R.drawable.slide_info);
            } else if (data.getSlideText().equals(context.getResources().getString(R.string.slide_menu_2))) {
                holder.ivSlideImage.setImageResource(R.drawable.slide_career);
            }else if (data.getSlideText().equals(context.getResources().getString(R.string.slide_menu_3))) {
                holder.ivSlideImage.setImageResource(R.drawable.slide_weather);
            }else if (data.getSlideText().equals(context.getResources().getString(R.string.slide_menu_4))) {
                holder.ivSlideImage.setImageResource(R.drawable.slide_card);
            }else if (data.getSlideText().equals(context.getResources().getString(R.string.slide_menu_5))) {
                holder.ivSlideImage.setImageResource(R.drawable.slide_food);
            }else if (data.getSlideText().equals(context.getResources().getString(R.string.slide_menu_6))) {
                holder.ivSlideImage.setImageResource(R.drawable.slide_card);
            }
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.llSlideItem) LinearLayout llSlideItem;
        @BindView(R.id.llSlideItemSetting) LinearLayout llSlideItemSetting;
        @BindView(R.id.ivSlideImage) ImageView ivSlideImage;
        @BindView(R.id.tvSlideTitle) TextView tvSlideTitle;

        @BindView(R.id.llSlideChildItem) LinearLayout llSlideChildItem;
        @BindView(R.id.tvSlideChildTitle) TextView tvSlideChildTitle;
        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
