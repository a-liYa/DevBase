package com.aliya.base.sample.module.mvvm.viewmodel;

import com.aliya.base.sample.module.mvvm.model.LoadStatus;
import com.aliya.base.sample.module.mvvm.model.User;
import com.aliya.base.sample.module.mvvm.model.UserRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * UserListViewModel
 *
 * @author a_liYa
 * @date 2021/6/1 22:39.
 */
public class UserListViewModel extends ViewModel {

    private MutableLiveData<List<User>> userListLiveData;
    private MutableLiveData<LoadStatus> loadingLiveData;

    public UserListViewModel() {
        userListLiveData = new MutableLiveData<>();
        loadingLiveData = new MutableLiveData<>();
    }

    /**
     * 获取用户列表信息, 这里可以做数据加工
     */
    public void getUserInfo() {
        loadingLiveData.setValue(new LoadStatus(-1, "加载中"));

        UserRepository.getUserRepository().getUsersFromServer()
                .observeForever(resultData -> {
                    if (resultData.isSuccess()) {
                        userListLiveData.setValue(resultData.data);
                    }
                    loadingLiveData.setValue(new LoadStatus(resultData.code, resultData.msg));
                });
    }

    public LiveData<LoadStatus> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<List<User>> getUserListLiveData() {
        return userListLiveData;
    }
}