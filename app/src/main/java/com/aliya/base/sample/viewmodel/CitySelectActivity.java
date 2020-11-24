package com.aliya.base.sample.viewmodel;

import android.os.Bundle;
import android.util.Log;

import com.aliya.base.sample.base.BaseActivity;
import com.aliya.base.sample.viewmodel.bean.CityEntity;
import com.aliya.base.sample.viewmodel.bean.ProvinceEntity;
import com.aliya.base.sample.R;

import androidx.lifecycle.ViewModelProvider;

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
        mViewModel = new ViewModelProvider(this).get(AreaSelectViewModel.class);
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
