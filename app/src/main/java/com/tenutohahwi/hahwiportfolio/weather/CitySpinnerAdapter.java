package com.tenutohahwi.hahwiportfolio.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tenutohahwi.hahwiportfolio.R;
import com.tenutohahwi.hahwiportfolio.main.MainSlideAdapter;


import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author shyknight_m@naver.com
 * @class SevenSat
 * @date 2016-05-25
 * @see
 */
public class CitySpinnerAdapter extends BaseAdapter {
    Context context;
    int layout;
    int selectedPosition = 0;
    //private Map<String, String> cityMap= null;
    private List<String> cityList= null;

    public CitySpinnerAdapter(Context ctx, int layout, List<String>cityList){
        this.context = ctx;
        this.layout = layout;
        this.cityList = cityList;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }


    @Override
    public int getCount() {
        if(cityList == null){
            return 0;
        }
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        if (cityList == null || cityList.get(position) == null) {
            return null;
        }
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CitySpinnerAdapter.ViewHolder holder;
        if( null != convertView){
            holder = (CitySpinnerAdapter.ViewHolder) convertView.getTag();
        }else{
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, parent, false);
            holder = new CitySpinnerAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }

        String city = cityList.get(position);
        holder.tvTitle.setText( city.toString());

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
