package com.aliya.base.sample.module.mvvm;

import android.os.Bundle;

import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivityUserListBinding;
import com.aliya.base.sample.module.mvvm.viewmodel.UserListViewModel;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


/**
 * User List
 *
 * @author a_liYa
 * @date 2021/6/12 11:57.
 */
public class UserListActivity extends ActionBarActivity {

    private UserListViewModel mViewModel;
    private UserAdapter mUserAdapter;

    private ActivityUserListBinding mViewBinding;
    private LoadPager mLoadPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityUserListBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());

        initView();

        initViewModel();

        requestData();
    }

    private void initView() {
        mViewBinding.recycler.setLayoutManager(new LinearLayoutManager(this));
        mViewBinding.recycler.setAdapter(mUserAdapter = new UserAdapter(null));

        mLoadPager = new LoadPager(mViewBinding.recycler);
        mLoadPager.setAction(() -> {
            requestData();
        });
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(UserListViewModel.class);
        mViewModel.getUserListLiveData().observe(this, users -> {
            mUserAdapter.setData(users, true);
        });
        mViewModel.getLoadingLiveData().observe(this,
                resultData -> {
                    if (resultData.isLoading()) {
                        mLoadPager.showLoading();
                    } else if (resultData.isFailed()) {
                        mLoadPager.showFailed();
                    } else {
                        mLoadPager.finishLoad();
                    }
                });
    }

    private void requestData() {
        mViewModel.getUserInfo();
    }
}