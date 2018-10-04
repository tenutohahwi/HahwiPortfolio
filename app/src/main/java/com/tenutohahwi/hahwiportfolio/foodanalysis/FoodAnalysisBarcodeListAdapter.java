package com.tenutohahwi.hahwiportfolio.foodanalysis;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tenutohahwi.hahwiportfolio.R;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shyknight_m@naver.com
 * @class MakeAppListAdapter
 * @date 2018-08-24
 * @see
 */
public class FoodAnalysisBarcodeListAdapter extends BaseAdapter
{
    Context context;
    int     layout;
    private List<String> barcodeDataList;

    public FoodAnalysisBarcodeListAdapter( Context context, int layout )
    {
        this.context = context;
        this.layout = layout;
    }

    public void setData( List<String> barcodeDataList )
    {
        this.barcodeDataList = barcodeDataList;
    }

    @Override
    public int getCount()
    {
        if (barcodeDataList == null)
        {
            return 0;
        }
        return barcodeDataList.size();
    }

    @Override
    public Object getItem( int position )
    {
        if (barcodeDataList == null || barcodeDataList.get(position) == null)
        {
            return null;
        }
        return barcodeDataList.get(position);
    }

    @Override
    public long getItemId( int position )
    {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        FoodAnalysisBarcodeListAdapter.ViewHolder holder;
        if (null != convertView)
        {
            holder = (FoodAnalysisBarcodeListAdapter.ViewHolder) convertView.getTag();
        }
        else
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, parent, false);
            holder = new FoodAnalysisBarcodeListAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }


        String barcodeData = barcodeDataList.get(position);

        if (null != barcodeData)
        {
            String[] dataArray = barcodeData.split("-");
            holder.tvBarcode.setText(dataArray[0]);
            holder.tvName.setText(dataArray[1]);
            holder.tvCompany.setText(dataArray[2]);
            holder.tvType.setText(dataArray[3]);
            holder.tvCapacity.setText(dataArray[4]);
            holder.tvCal.setText(dataArray[5]);
        }
        return convertView;
    }

    static class ViewHolder
    {
        @BindView(R.id.tvBarcode)  TextView tvBarcode;
        @BindView(R.id.tvName)     TextView tvName;
        @BindView(R.id.tvCompany)  TextView tvCompany;
        @BindView(R.id.tvType)     TextView tvType;
        @BindView(R.id.tvCapacity) TextView tvCapacity;
        @BindView(R.id.tvCal)      TextView tvCal;

        public ViewHolder( View view )
        {
            ButterKnife.bind(this, view);
        }
    }
}
