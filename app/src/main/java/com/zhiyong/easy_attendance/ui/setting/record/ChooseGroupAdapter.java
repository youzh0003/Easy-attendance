package com.zhiyong.easy_attendance.ui.setting.record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhiyong.easy_attendance.R;
import com.zhiyong.easy_attendance.data.models.Group;
import com.zhiyong.easy_attendance.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

public class ChooseGroupAdapter extends RecyclerView.Adapter<ChooseGroupAdapter.GroupViewHolder> {


    private List<Group> groupList = new ArrayList<>();
    private Context context;
    private PublishSubject<Group> onClickChooseGroup;

    public ChooseGroupAdapter() {
        onClickChooseGroup = PublishSubject.create();
    }

    public void setData(List<Group> list) {
        groupList.clear();
        groupList.addAll(list);
        notifyDataSetChanged();
    }

    public void refreshData(Group unit) {
        if (groupList != null && groupList.size() > 0) {
            for (int i = 0; i < groupList.size(); i++) {
                if (groupList.get(i).getName().equalsIgnoreCase(unit.getName())) {
                    groupList.get(i).setCheck(true);
                } else {
                    groupList.get(i).setCheck(false);
                }
            }
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_choose_segment, parent, false);
        return new GroupViewHolder(view);
    }

    public PublishSubject<Group> getOnClickChooseGroup() {
        return onClickChooseGroup;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group unit = groupList.get(position);
        if (unit != null) {
            if (AppUtils.validateString(unit.getName())) {
                holder.tvName.setText(unit.getName());
            }
            if (unit.isCheck()) {
                holder.ivCheckbox.setImageResource(R.drawable.ic_radio_button_checked_black_24dp);
            } else {
                holder.ivCheckbox.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
            }

            holder.parrentView.setOnClickListener(view -> onClickChooseGroup.onNext(unit));
        }
    }


    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.root_view)
        RelativeLayout parrentView;
        @BindView(R.id.checkbox)
        ImageView ivCheckbox;

        public GroupViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

