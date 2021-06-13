package com.aliya.base.sample.module.mvvm;

import android.view.ViewGroup;

import com.aliya.adapter.RecyclerAdapter;
import com.aliya.adapter.RecyclerViewHolder;
import com.aliya.base.sample.R;
import com.aliya.base.sample.databinding.ItemUserMvvmBinding;
import com.aliya.base.sample.module.mvvm.model.User;

import java.util.List;

/**
 * UserAdapter
 *
 * @author a_liYa
 * @date 2021/6/1 22:35.
 */
class UserAdapter extends RecyclerAdapter<User> {

    public UserAdapter(List<User> data) {
        super(data);
    }

    @Override
    public RecyclerViewHolder onAbsCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder<User>(parent, R.layout.item_user_mvvm) {

            ItemUserMvvmBinding mViewBinding;

            @Override
            public void bindData(User data) {
                if (mViewBinding == null) mViewBinding = ItemUserMvvmBinding.bind(itemView);

                mViewBinding.tvUserAge.setText("age : " + data.getAge());
                mViewBinding.tvUserName.setText("name : " + data.getName());

            }
        };
    }

}
