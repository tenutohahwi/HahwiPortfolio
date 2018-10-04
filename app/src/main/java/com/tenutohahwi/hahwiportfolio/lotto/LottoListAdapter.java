package com.tenutohahwi.hahwiportfolio.lotto;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tenutohahwi.hahwiportfolio.R;
import com.tenutohahwi.hahwiportfolio.make.MakeAppData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shyknight_m@naver.com
 * @class MakeAppListAdapter
 * @date 2018-08-24
 * @see
 */
public class LottoListAdapter extends BaseAdapter
{
    Context context;
    int     layout;
    private Map<Integer, int[]> lottoMap;

    public LottoListAdapter( Context context, int layout )
    {
        this.context = context;
        this.layout = layout;
    }

    public void setData( Map<Integer, int[]> lottoMap )
    {
        this.lottoMap = lottoMap;

        Log.e("m-Log", "lottoMap size : "+lottoMap.size());
    }

    @Override
    public int getCount()
    {
        if (lottoMap == null)
        {
            return 0;
        }
        return lottoMap.size();
    }

    @Override
    public Object getItem( int position )
    {
        if (lottoMap == null || lottoMap.get(position) == null)
        {
            return null;
        }
        return lottoMap.get(position);
    }

    @Override
    public long getItemId( int position )
    {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent )
    {
        LottoListAdapter.ViewHolder holder;
        if (null != convertView)
        {
            holder = (LottoListAdapter.ViewHolder) convertView.getTag();
        }
        else
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, parent, false);
            holder = new LottoListAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }


        int[] lottoNumber = lottoMap.get(position);

        if (lottoNumber != null)
        {
            holder.tvNumber.setText(position + 1 + "회");
            holder.tvLottoNumber1.setText(lottoNumber[0] + "");
            holder.tvLottoNumber2.setText(lottoNumber[1] + "");
            holder.tvLottoNumber3.setText(lottoNumber[2] + "");
            holder.tvLottoNumber4.setText(lottoNumber[3] + "");
            holder.tvLottoNumber5.setText(lottoNumber[4] + "");
            holder.tvLottoNumber6.setText(lottoNumber[5] + "");
        }
        return convertView;
    }

    static class ViewHolder
    {
        @BindView(R.id.tvNumber)       TextView tvNumber;
        @BindView(R.id.tvLottoNumber1) TextView tvLottoNumber1;
        @BindView(R.id.tvLottoNumber2) TextView tvLottoNumber2;
        @BindView(R.id.tvLottoNumber3) TextView tvLottoNumber3;
        @BindView(R.id.tvLottoNumber4) TextView tvLottoNumber4;
        @BindView(R.id.tvLottoNumber5) TextView tvLottoNumber5;
        @BindView(R.id.tvLottoNumber6) TextView tvLottoNumber6;

        public ViewHolder( View view )
        {
            ButterKnife.bind(this, view);
        }
    }
}
