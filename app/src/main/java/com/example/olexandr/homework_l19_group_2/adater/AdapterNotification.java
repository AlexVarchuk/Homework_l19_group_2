package com.example.olexandr.homework_l19_group_2.adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.olexandr.homework_l19_group_2.R;
import com.example.olexandr.homework_l19_group_2.model.ItemNotification;

import java.util.List;

public class AdapterNotification extends BaseAdapter {

    private Context mContext;
    private List<ItemNotification> mData;
    public AdapterNotification(Context _context, List<ItemNotification> _data) {
        this.mContext = _context;
        this.mData = _data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        ItemNotification item = mData.get(position);
        holder.tvFirst.setText(item.getTitle());
        holder.tvSecond.setText(item.getSubtitle());



        return convertView;
    }

    private class ViewHolder{
        TextView tvFirst;
        TextView tvSecond;
        public ViewHolder(View v){
            tvFirst = (TextView) v.findViewById(R.id.tv_item_text1_IL);
            tvSecond = (TextView) v.findViewById(R.id.tv_item_text2_IL);
        }
    }
}
