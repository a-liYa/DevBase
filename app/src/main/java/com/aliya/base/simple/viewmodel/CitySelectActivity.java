package com.aliya.base.simple.viewmodel;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.util.Log;

import com.aliya.base.simple.R;
import com.aliya.base.simple.base.BaseActivity;
import com.aliya.base.simple.viewmodel.bean.CityEntity;
import com.aliya.base.simple.viewmodel.bean.ProvinceEntity;

/**
 * 城市选择页面
 *
 * @author a_liYa
 * @date 2019/2/26 下午3:12.
 */
public class CitySelectActivity extends BaseActivity {

    private AreaSelectViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);
        mViewModel = ViewModelProviders.of(this).get(AreaSelectViewModel.class);
    }

    @Override
    public void finish() {
        super.finish();
        CityEntity city = mViewModel.getSelectedCity().getValue();
        ProvinceEntity province = mViewModel.getSelectedProvince().getValue();
        if (city != null && province != null) {
            setResult(RESULT_OK);
            Log.e("TAG", "选择城市: " + province.getName() + " - " + city.getName());
        }

    }
}
