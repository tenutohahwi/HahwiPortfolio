package com.tenutohahwi.hahwiportfolio.make;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.tenutohahwi.hahwiportfolio.R;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shyknight_m@naver.com
 * @class MakeAppListAdapter
 * @date 2018-08-24
 * @see
 */
public class MakeAppListAdapter extends BaseAdapter {
    Context context;
    int layout;
    private ArrayList<MakeAppData> makeAppDataArrayList;

    public MakeAppListAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
    }

    public void setData(ArrayList<MakeAppData> makeAppDataArrayList) {
        this.makeAppDataArrayList = makeAppDataArrayList;
    }

    @Override
    public int getCount() {
        if (makeAppDataArrayList == null) {
            return 0;
        }
        return makeAppDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        if (makeAppDataArrayList == null || makeAppDataArrayList.get(position) == null) {
            return null;
        }
        return makeAppDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MakeAppListAdapter.ViewHolder holder;
        if (null != convertView) {
            holder = (MakeAppListAdapter.ViewHolder) convertView.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, parent, false);
            holder = new MakeAppListAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }


        MakeAppData data = makeAppDataArrayList.get(position);

        if (data != null) {
            if (0 == data.getType()) {
                holder.llCompany.setVisibility(View.VISIBLE);
                holder.llApp.setVisibility(View.GONE);
                try {
                    if (!"".equals(data.getAppIcon())) {
                        String appIcon = "icon_" + data.getAppIcon();
                        int resID = context.getResources().getIdentifier(appIcon, "drawable", context.getPackageName());
                        Picasso.with(context).load(resID).into(holder.ivCompanyIcon);
                        holder.ivCompanyIcon.setVisibility(View.VISIBLE);
                    } else {
                        holder.ivCompanyIcon.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    holder.ivCompanyIcon.setVisibility(View.INVISIBLE);
                }
                holder.tvCompanyName.setText(data.getCompanyName());
                holder.tvCompanyType.setText(data.getAppType());
                holder.tvCompanyExplain.setText(data.getAppExplain());
            } else {
                holder.llCompany.setVisibility(View.GONE);
                holder.llApp.setVisibility(View.VISIBLE);
                try {
                    if (!"".equals(data.getAppIcon())) {
                        String appIcon = "icon_" + data.getAppIcon();
                        int resID = context.getResources().getIdentifier(appIcon, "drawable", context.getPackageName());
                        Picasso.with(context).load(resID).into(holder.ivAppIcon);
                        holder.ivAppIcon.setVisibility(View.VISIBLE);
                    } else {
                        holder.ivAppIcon.setVisibility(View.INVISIBLE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    holder.ivAppIcon.setVisibility(View.INVISIBLE);
                }

                holder.tvAppName.setText(data.getAppName());
                holder.tvAppExplain.setText(data.getAppExplain());
                holder.tvAppType.setText(data.getAppType());
            }


        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.llCompany)
        LinearLayout llCompany;
        @BindView(R.id.ivCompanyIcon)
        ImageView ivCompanyIcon;
        @BindView(R.id.tvCompanyName)
        TextView tvCompanyName;
        @BindView(R.id.tvCompanyType)
        TextView tvCompanyType;
        @BindView(R.id.tvCompanyExplain)
        TextView tvCompanyExplain;
        @BindView(R.id.llApp)
        LinearLayout llApp;
        @BindView(R.id.ivAppIcon)
        ImageView ivAppIcon;
        @BindView(R.id.tvAppName)
        TextView tvAppName;
        @BindView(R.id.tvAppType)
        TextView tvAppType;
        @BindView(R.id.tvAppExplain)
        TextView tvAppExplain;



        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
