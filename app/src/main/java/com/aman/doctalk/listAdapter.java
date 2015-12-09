package com.aman.doctalk;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class listAdapter extends ArrayAdapter<list> {

    Context context;
    int layoutResourceId;
    list data[] = null;




    public listAdapter(Context context, int layoutResourceId, List<list> items) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        list list = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.file_list_row, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imgIcon);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtTitle.setText(list.getTitle());
        holder.imageView.setImageResource(list.getImageId());

        return convertView;
    }
}
    