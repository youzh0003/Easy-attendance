package com.zhiyong.easy_attendance.ui.setting.user;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zhiyong.easy_attendance.data.models.Group;

import java.util.List;

public class GroupSpinnerAdapter extends ArrayAdapter<Group> {
    private Context context;
    private List<Group> groupList;

    public GroupSpinnerAdapter(Context context, int resource, List<Group> groups) {
        super(context, resource, groups);
        this.context = context;
        this.groupList = groups;
    }

    @Override
    public int getCount() {
        if(groupList == null) return 0;
        return groupList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setTextColor(Color.BLACK);
        if(groupList != null && groupList.size() > position)  textView.setText(groupList.get(position).getName());
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setTextColor(Color.BLACK);
        if(groupList != null && groupList.size() > position)  textView.setText(groupList.get(position).getName());
        return textView;
    }

    public Group getItemWithName(String name) {
        for (Group g:groupList
             ) {
            if(g.getName().equalsIgnoreCase(name)){
                return g;
            }
        }
        return null;
    }
}
