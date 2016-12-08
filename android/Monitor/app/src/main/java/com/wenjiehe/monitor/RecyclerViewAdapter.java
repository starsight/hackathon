package com.wenjiehe.monitor;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by zhangpengcheng on 2016/12/8.
 */

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NewsViewHolder> {

        final String TAG = "SignRecyclerViewAdapter";
        protected List<MonitorObject> monitorObject;
        protected Context context;

        public RecyclerViewAdapter() {

        }

        public RecyclerViewAdapter(List<MonitorObject> monitorObject, Context context) {
            this.monitorObject = monitorObject;
            this.context = context;
        }


        //自定义ViewHolder类
        static class NewsViewHolder extends RecyclerView.ViewHolder {

            CardView cardView;
            ImageView news_photo;
            TextView news_title;

            public NewsViewHolder(final View itemView, int type) {

                super(itemView);
                cardView = (CardView) itemView.findViewById(R.id.card_view);
                news_photo = (ImageView) itemView.findViewById(R.id.news_photo);
                news_title = (TextView) itemView.findViewById(R.id.news_title);

                //设置TextView背景为半透明
                news_title.setBackgroundColor(Color.argb(20, 0, 0, 0));

            }


        }

        @Override
        public RecyclerViewAdapter.NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(context).inflate(R.layout.recylerview_monitor, viewGroup, false);

            NewsViewHolder nvh = new NewsViewHolder(v, i);
            return nvh;
        }

        @Override
        public void onBindViewHolder(RecyclerViewAdapter.NewsViewHolder personViewHolder, int i) {
            final int j = i;

            personViewHolder.news_photo.setImageBitmap(BitmapFactory.decodeByteArray(monitorObject.get(i).bytesImage, 0, monitorObject.get(i).bytesImage.length));
            personViewHolder.news_title.setText("监控");

//            Glide.with(context)
//                        .load(monitorObject.get(i).bytesImage)
//                        .into(personViewHolder.news_photo);

            //为btn_share btn_readMore cardView设置点击事件
            personViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, SignInfoDetailActivity.class);
//                    SignInfo s = signInfo.get(j);
//                    s.username = AVUser.getCurrentUser().getUsername();
//                    intent.putExtra("SignInfo", s);
//                    intent.putExtra("isFromMe", true);
//                    context.startActivity(intent);
                }
            });

//            if (personViewHolder.news_photo.getVisibility() == View.VISIBLE)
//                Glide.with(context)
//                        .load(Environment.getExternalStorageDirectory() +
//                                "/xingji/" + AVUser.getCurrentUser().getUsername() + "/Signs/" + signInfo.get(j).getPhotoId())
//                        .into(personViewHolder.news_photo);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getItemCount() {
            return monitorObject.size();
        }


        @Override
        public int getItemViewType(int position) {
//            if (!signInfo.get(position).getPhotoId().equals("0")) {
//                return 1;//有图
//            } else {
//                return 0;//无图
//            }
            return 1;
        }

        public void addItem(MonitorObject si, int position) {
            Log.d(TAG, "add" + String.valueOf(position));
            monitorObject.add(position, si);
            notifyItemInserted(position); //Attention!
        }

        public void removeItem(int position) {
            Log.d(TAG, "delete" + String.valueOf(position));
            monitorObject.remove(position);
            notifyItemRemoved(position);
        }
/*
    public void updateRecyclerView(){
        Log.d(TAG,TAG);
        ArrayList<Integer> isInfoExist = new ArrayList();
        for (int i = 0; i < signInfo.size(); i++)
            isInfoExist.add(i, 0);
        String str;
        List<SignInfo> signInfoTmp = MainActivity.arraylistHistorySign;
        for (SignInfo si : signInfoTmp
             ) {
             str = si.getObjectId();
            for (int i = 0; i < signInfo.size(); i++){
                if(signInfo.get(i).getObjectId().equals(str)){
                    isInfoExist.set(i,1);
                    break;
                }else if (i == (signInfo.size() - 1)) {
                    if (!signInfo.get(i).getObjectId().equals(str)) {
                        isInfoExist.add(signInfo.size(), 1);
                        addItem(si,getItemCount());
                    } else
                        isInfoExist.set(i, 1);
                }

            }

        }
        for (int i = 0; i < isInfoExist.size(); i++) {
            if (isInfoExist.get(i) == 0) {
                Log.d(TAG, "delete" + String.valueOf(i));
                removeItem(i);
            }
        }
        //addItem(signinfo,getItemCount());
    }*/
    }

