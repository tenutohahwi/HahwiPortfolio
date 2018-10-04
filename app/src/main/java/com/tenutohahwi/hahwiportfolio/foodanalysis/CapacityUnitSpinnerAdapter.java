package com.tenutohahwi.hahwiportfolio.foodanalysis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tenutohahwi.hahwiportfolio.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author shyknight_m@naver.com
 * @class SevenSat
 * @date 2016-05-25
 * @see
 */
public class CapacityUnitSpinnerAdapter extends BaseAdapter {
    Context context;
    int layout;
    int selectedPosition = 0;
    //private Map<String, String> cityMap= null;
    private List<String> capacityUnitList= null;

    public CapacityUnitSpinnerAdapter( Context ctx, int layout, List<String>capacityUnitList){
        this.context = ctx;
        this.layout = layout;
        this.capacityUnitList = capacityUnitList;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }


    @Override
    public int getCount() {
        if(capacityUnitList == null){
            return 0;
        }
        return capacityUnitList.size();
    }

    @Override
    public Object getItem(int position) {
        if (capacityUnitList == null || capacityUnitList.get(position) == null) {
            return null;
        }
        return capacityUnitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CapacityUnitSpinnerAdapter.ViewHolder holder;
        if( null != convertView){
            holder = (CapacityUnitSpinnerAdapter.ViewHolder) convertView.getTag();
        }else{
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, parent, false);
            holder = new CapacityUnitSpinnerAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }

        String city = capacityUnitList.get(position);
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
