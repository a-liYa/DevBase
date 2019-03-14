package com.aliya.base.sample.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.aliya.base.sample.util.JsonUtils;
import com.aliya.base.sample.viewmodel.bean.CityEntity;
import com.aliya.base.sample.viewmodel.bean.ProvinceEntity;
import com.aliya.base.util.IOs;

import java.util.List;

/**
 * 区域选择 ViewModel
 *
 * @author a_liYa
 * @date 2019/2/28 下午2:56.
 */
public class AreaSelectViewModel extends AndroidViewModel {

    private final MutableLiveData<ProvinceEntity> mSelectedProvince = new MutableLiveData<>();
    private final MutableLiveData<CityEntity> mSelectedCity = new MutableLiveData<>();
    private final MutableLiveData<List<ProvinceEntity>> mProvinces = new MutableLiveData<>();

    public AreaSelectViewModel(@NonNull Application application) {
        super(application);
        // 此处模拟数据获取
        String json = IOs.getAssetsText(application, "city_json.json");
        List<ProvinceEntity> list = JsonUtils.parseArray(json, ProvinceEntity.class);
        mProvinces.setValue(list);
    }


    public void selectProvince(ProvinceEntity entity) {
        mSelectedProvince.setValue(entity);
    }

    public void selectCity(CityEntity entity) {
        mSelectedCity.setValue(entity);
    }

    public LiveData<ProvinceEntity> getSelectedProvince() {
        return mSelectedProvince;
    }

    public LiveData<CityEntity> getSelectedCity() {
        return mSelectedCity;
    }

    public LiveData<List<ProvinceEntity>> getProvinces() {
        return mProvinces;
    }

}