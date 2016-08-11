package com.qianfeng.qiongyou.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.qianfeng.qiongyou.Bean.FirstListInfo.FirstListData.FirstListHeaderRecycler;
import com.qianfeng.qiongyou.Bean.FirstListInfo.FirstListData.FirstListRecyclerColor;
import com.qianfeng.qiongyou.R;

import java.util.List;

/**
 * Created by feng on 2016/3/30.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
    private List<FirstListHeaderRecycler> list_header_recycler;
    private List<FirstListRecyclerColor> list_header_recycler_color;
    private Context mContext;
    private int pic[]={
            R.mipmap.ic_cate_europe_ticket,
            R.mipmap.ic_cate_plane,
            R.mipmap.ic_cate_local,
            R.mipmap.ic_cate_visa,
            R.mipmap.ic_cate_curise,
            R.mipmap.ic_cate_more_ticket,
            R.mipmap.ic_cate_visa,
            R.mipmap.ic_cate_hotel,
            R.mipmap.ic_hotel_small,
            R.mipmap.ic_cate_rent_car,
            R.mipmap.ic_cate_visa,
            R.mipmap.ic_cate_others_nor
    };
    public MyRecyclerAdapter(Context context,List<FirstListHeaderRecycler> list_header_recycler,List<FirstListRecyclerColor> list_header_recycler_color){
        this.mContext=context;
        this.list_header_recycler=list_header_recycler;
        this.list_header_recycler_color=list_header_recycler_color;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_recycler,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=false;
        Bitmap bitmap=BitmapFactory.decodeResource(mContext.getResources(), pic[position]);
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        float scaleWidth=(float)180/width;
        float scaleHeight=(float)180/height;
        Matrix matrix=new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizeBitmap=Bitmap.createBitmap(bitmap,0,0,width,height,matrix,false);
        bitmap.recycle();
        holder.imageView.setImageBitmap(resizeBitmap);
       // holder.imageView.setImageResource(pic[position]);
        holder.textView.setText(list_header_recycler.get(position).getName());
        holder.textView.setTextColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return list_header_recycler==null?0:list_header_recycler.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.text_item_recycler);
            imageView= (ImageView) itemView.findViewById(R.id.image_item_recycler);
        }
    }
}
