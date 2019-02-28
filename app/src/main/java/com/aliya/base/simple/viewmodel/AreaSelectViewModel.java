package com.aliya.base.simple.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.aliya.base.simple.viewmodel.bean.CityEntity;
import com.aliya.base.simple.viewmodel.bean.ProvinceEntity;

/**
 * 区域选择 ViewModel
 *
 * @author a_liYa
 * @date 2019/2/28 下午2:56.
 */
public class AreaSelectViewModel extends ViewModel {

    private final MutableLiveData<ProvinceEntity> selectedProvince = new MutableLiveData<>();
    private final MutableLiveData<CityEntity> selectedCity = new MutableLiveData<>();

    public void selectProvince(ProvinceEntity entity) {
        selectedProvince.setValue(entity);
    }

    public void selectCity(CityEntity entity) {
        selectedCity.setValue(entity);
    }

    public LiveData<ProvinceEntity> getSelectedProvince() {
        return selectedProvince;
    }

    public LiveData<CityEntity> getSelectedCity() {
        return selectedCity;
    }
}