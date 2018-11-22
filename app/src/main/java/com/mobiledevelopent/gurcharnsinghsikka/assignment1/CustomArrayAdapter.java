package com.mobiledevelopent.gurcharnsinghsikka.assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomArrayAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TaskItem> taskListItems;

    public CustomArrayAdapter(Context context, ArrayList<TaskItem> taskListItems) {
        this.context = context;
        this.taskListItems = taskListItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.to_do_list_item_layout, parent, false);
            viewHolder.taskName = (TextView)convertView.findViewById(R.id.task_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.taskName.setText(taskListItems.get(position).getName());

        return convertView;
    }

    @Override
    public int getCount(){
        return taskListItems.size();
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public Object getItem(int position) {
        return taskListItems.get(position);
    }

    public int getTaskItemId(int position){
        return taskListItems.get(position).getId();
    }

    static class ViewHolder {
        public TextView taskName;
    }
}
