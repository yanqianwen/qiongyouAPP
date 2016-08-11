package com.qianfeng.qiongyou.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.facebook.drawee.view.SimpleDraweeView;
import com.qianfeng.qiongyou.Bean.NextListInfo.NextListDataBean;
import com.qianfeng.qiongyou.R;

import java.util.List;

/**
 * Created by feng on 2016/3/29.
 */
public class MyNextListAdapter extends BaseAdapter {
    private List<NextListDataBean> mDatas;
    private Context mContext;

    public MyNextListAdapter(List<NextListDataBean> data, Context context) {
        this.mDatas = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas==null?0:mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_next,parent,false);
            holder=new MyViewHolder();
            holder.image_item_next= (SimpleDraweeView) convertView.findViewById(R.id.image_item_next);
            holder.text_item_next= (TextView) convertView.findViewById(R.id.text_item_next);
            holder.text_item_next_date= (TextView) convertView.findViewById(R.id.text_item_next_date);
            holder.text_item_next_price= (TextView) convertView.findViewById(R.id.text_item_next_price);
            convertView.setTag(holder);
        }else{
            holder= (MyViewHolder) convertView.getTag();
        }
        holder.text_item_next.setText(mDatas.get(position).getTitle());

        holder.text_item_next_price.setText(mDatas.get(position).getPrice().substring(4,8)+"元起");
        holder.text_item_next_date.setText(mDatas.get(position).getDepartureTime());
        //下载图片
        String url =mDatas.get(position).getPic();
        holder.image_item_next.setImageURI(Uri.parse(url));
        return convertView;
    }
    class MyViewHolder{
         SimpleDraweeView image_item_next;
         TextView text_item_next;
         TextView text_item_next_date;
         TextView text_item_next_price;
    }
}
