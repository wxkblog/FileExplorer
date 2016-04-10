package com.wang.xiaoke.fileexplorer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/9.
 */
public class Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Entity> list;

    public Adapter(Context context, ArrayList<Entity> entities) {
        this.inflater = LayoutInflater.from(context);
        this.list = entities;
    }

    public final class ViewHolder {
        public ImageView icon;
        public TextView fileName;
        public RadioButton radio;
        public LinearLayout layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        //判断是否缓存
        if (convertView == null) {
            holder = new ViewHolder();
            //通过LayoutInflater实例化布局
            convertView = inflater.inflate(R.layout.item, null);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.item);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.fileName = (TextView) convertView.findViewById(R.id.file_name);
            holder.radio = (RadioButton) convertView.findViewById(R.id.radio);
            convertView.setTag(holder);
        } else {
            //通过tag找到缓存的布局
            holder = (ViewHolder) convertView.getTag();
        }
        if (list.get(position).getState() == 0) {
            holder.icon.setImageResource(R.drawable.file);
        } else {
            holder.icon.setImageResource(R.drawable.folder);
        }

        holder.fileName.setText(list.get(position).getFileName());

        if (list.get(position).getIsCheck()) {
            convertView.setBackgroundColor(Color.RED);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
//        if(list.get(position).getIsCheck()){
//            convertView.setDrawingCacheBackgroundColor(R.color.itemColorClick);
//        }else {
//            convertView.setDrawingCacheBackgroundColor(R.color.itemColor);
//        }
//
//        if(list.get(position).getIsCheck()){
//            holder.layout.setBackgroundColor(R.color.itemColorClick);
//        }else {
//            holder.layout.setBackgroundColor(R.color.itemColor);
//        }
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
