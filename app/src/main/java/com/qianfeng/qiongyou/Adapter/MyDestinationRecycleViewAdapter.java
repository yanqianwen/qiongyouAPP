package com.qianfeng.qiongyou.Adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianfeng.qiongyou.Bean.DestinationDataBean;
import com.qianfeng.qiongyou.MyApp;
import com.qianfeng.qiongyou.R;

import java.util.List;

/**
 * Created by aaa on 16-3-30.
 */
public class MyDestinationRecycleViewAdapter extends RecyclerView.Adapter<MyDestinationRecycleViewAdapter.MyRecycleViewHolder> {

//    private List<DestinationDataBean.DataBean.DestinationsBean> destinations;
    private List<DestinationDataBean.DataBean> data;
    private MyRecycleViewItemClickListener mItemClickListener;
    private int item;

    public void setOnRecycleViewItemClickListener(MyRecycleViewItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }


    public void setRecycleShow(int item){
        this.item = item;
        notifyDataSetChanged();
    }


    public MyDestinationRecycleViewAdapter(List<DestinationDataBean.DataBean> data) {
        this.data = data;
    }

    @Override
    public MyRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Fresco.initialize(MyApp.getContext());
        View view = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.recycle_item, null);

        return new MyRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyRecycleViewHolder holder, final int position) {
        if (position == item){
            holder.simpleDraweeView.setImageURI(Uri.parse(data.get(item).getDestinations().get(position).getPic()));
            holder.tv_name.setText(data.get(item).getDestinations().get(position).getName());
            holder.tv_name_en.setText(data.get(item).getDestinations().get(position).getName_en());
        }else {
//            holder.simpleDraweeView.setImageURI(Uri.parse(destinations.get(position).getPic()));
//            holder.tv_name.setText(destinations.get(position).getName());
//            holder.tv_name_en.setText(destinations.get(position).getName_en());
            holder.simpleDraweeView.setImageURI(Uri.parse(data.get(item).getDestinations().get(position).getPic()));
            holder.tv_name.setText(data.get(item).getDestinations().get(position).getName());
            holder.tv_name_en.setText(data.get(item).getDestinations().get(position).getName_en());
        }

        if (mItemClickListener != null){
            holder.frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.get(item).getDestinations() == null ? 0 : data.get(item).getDestinations().size();
    }




    public class MyRecycleViewHolder extends RecyclerView.ViewHolder{



        private SimpleDraweeView simpleDraweeView;
        private TextView tv_name;
        private TextView tv_name_en;
        private FrameLayout frameLayout;
        public MyRecycleViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.simple_item_image);
            tv_name = (TextView) itemView.findViewById(R.id.simple_item_text_name);
            tv_name_en = (TextView) itemView.findViewById(R.id.simple_item_text_nameen);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.frame);
        }

    }


    public interface MyRecycleViewItemClickListener{
        void onItemClick(View view, int position);
    }
}
