package com.aliya.base.simple.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.aliya.base.simple.viewmodel.bean.ProvinceEntity;

/**
 * 省市联动 ViewModel
 *
 * @author a_liYa
 * @date 2019/2/28 下午2:56.
 */
public class AreaLinkageViewModel extends ViewModel {

    @Override
    protected void onCleared() {
        Log.e("TAG", "onCleared: 销毁时 " + hashCode());
        super.onCleared();
    }

    private final MutableLiveData<ProvinceEntity> selected = new MutableLiveData<>();

    public void select(ProvinceEntity item) {
        selected.setValue(item);
    }

    public LiveData<ProvinceEntity> getSelected() {
        return selected;
    }

}