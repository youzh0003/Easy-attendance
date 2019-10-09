package com.zhiyong.easy_attendance.ui.setting.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.zhiyong.easy_attendance.R;
import com.zhiyong.easy_attendance.data.models.Person;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.processors.PublishProcessor;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private PublishProcessor<Person> onUserClick;
    private PublishProcessor<Person> onUserDelete;
    private ViewBinderHelper viewBinderHelper;
    private List<Person> userList;

    public UserAdapter() {
        this.onUserClick = PublishProcessor.create();
        this.onUserDelete = PublishProcessor.create();
        this.viewBinderHelper = new ViewBinderHelper();
        this.viewBinderHelper.setOpenOnlyOne(true);
    }

    public PublishProcessor<Person> getOnUserDelete() {
        return onUserDelete;
    }

    public PublishProcessor<Person> getOnUserClick() {
        return onUserClick;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bindData(userList.get(position));
    }

    @Override
    public int getItemCount() {
        if(userList == null) return 0;
        return userList.size();
    }

    public void setList(List<Person> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public void deleteItem(String id) {
        if(userList == null) return;
        Iterator<Person> iterator = userList.iterator();
        while (iterator.hasNext()){
            if(iterator.next().getPersonalId().equalsIgnoreCase(id)) {
                iterator.remove();
                break;
            }
        }
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.swipe_reveal_layout)
        SwipeRevealLayout swipeRevealLayout;
        @BindView(R.id.ll_delete)
        LinearLayout llDelete;
        @BindView(R.id.ll_item)
        LinearLayout llItem;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_group_name)
        TextView tvGroupName;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(Person person) {
            if(person.getName() != null) tvName.setText(person.getName());
            if(person.getGroup() != null && person.getGroup().getName() != null) tvGroupName.setText(person.getGroup().getName());
            llItem.setOnClickListener(view -> {
                onUserClick.onNext(person);
            });
            llDelete.setOnClickListener(view -> {
                onUserDelete.onNext(person);
            });
            viewBinderHelper.bind(swipeRevealLayout, person.getPersonalId());
        }
    }
}

