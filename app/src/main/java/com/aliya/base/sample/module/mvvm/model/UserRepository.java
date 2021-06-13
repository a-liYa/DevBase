package com.aliya.base.sample.module.mvvm.model;

import android.os.AsyncTask;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Repository 数据来源：网络 + 数据库
 *
 * @author a_liYa
 * @date 2021/6/1 22:38.
 */
public class UserRepository {
    private static UserRepository mUserRepository;

    public static UserRepository getUserRepository() {
        if (mUserRepository == null) {
            mUserRepository = new UserRepository();
        }
        return mUserRepository;
    }

    /**
     * 从服务端获取
     */
    public LiveData<ResultData<List<User>>> getUsersFromServer() {
        final MutableLiveData<ResultData<List<User>>> result = new MutableLiveData();

        forgeRequest(new Callback<List<User>>() {
            @Override
            public void onSuccess(List<User> users) {
                saveUsersToLocal(users);
                result.setValue(new ResultData<>(users));
            }

            @Override
            public void onFailed(String msg) {
                result.setValue(new ResultData<>(1, msg));
            }
        });
        return result;
    }

    // 模拟请求
    private void forgeRequest(Callback<List<User>> callback) {
        new AsyncTask<Void, Void, List<User>>() {

            @Override
            protected void onPostExecute(List<User> users) {
                if (users == null) {
                    callback.onFailed("服务异常");
                } else {
                    callback.onSuccess(users);
                }
            }

            @Override
            protected List<User> doInBackground(Void... voids) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (SystemClock.uptimeMillis() % 2 == 0) {
                    // 1/2概率失败
                    return null;
                } else {
                    // 假装从服务端获取的
                    List<User> users = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        User user = new User("user" + i, i);
                        users.add(user);
                    }
                    return users;
                }
            }

        }.execute();
    }

    /**
     * 从本地数据库获取
     */
    public void getUsersFromLocal() {

    }

    /**
     * 存入本地数据库
     *
     * @param users
     */
    private void saveUsersToLocal(List<User> users) {

    }

    public interface Callback<T> {

        void onSuccess(T t);

        void onFailed(String msg);
    }
}
