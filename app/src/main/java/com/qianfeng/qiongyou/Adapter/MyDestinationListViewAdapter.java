package com.qianfeng.qiongyou.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.qianfeng.qiongyou.Bean.DestinationDataBean;
import com.qianfeng.qiongyou.R;

import java.util.List;

/**
 * Created by aaa on 16-3-29.
 */
public class MyDestinationListViewAdapter extends BaseAdapter {

    private List<DestinationDataBean.DataBean> data;
    private Context context;
    private int index;

    public MyDestinationListViewAdapter(List<DestinationDataBean.DataBean> data,Context context) {
        this.data = data;
        this.context = context;
    }

    public void setMySelection(int index) {
        this.index = index;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyViewHolder holder;
        if (convertView == null){
            holder = new MyViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_itemthree,null);
            holder.textView = (TextView) convertView.findViewById(R.id.list_item_textview);
            holder.imageView = (ImageView) convertView.findViewById(R.id.list_item_imageview);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        holder.textView.setText(data.get(position).getName());



        if (position == index){
            holder.imageView.setBackgroundResource(R.color.colorGray);
            holder.imageView.setImageResource(R.mipmap.poi_pressed);
            holder.textView.setTextColor(Color.rgb(64, 200, 149));
        }else {
            holder.imageView.setImageResource(R.mipmap.country_select_line);
            holder.imageView.setBackgroundResource(R.color.colorWhite);
            holder.textView.setTextColor(Color.rgb(65,65,65));
        }

        return convertView;
    }

    class MyViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
