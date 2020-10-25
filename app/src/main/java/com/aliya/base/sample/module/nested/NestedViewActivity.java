package com.aliya.base.sample.module.nested;

import android.os.Bundle;

import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivityNestedViewBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * NestedScrollView + ViewPager + RecyclerView
 *
 * @author a_liYa
 * @date 2020/10/24 17:16.
 */
public class NestedViewActivity extends ActionBarActivity {

    private ActivityNestedViewBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityNestedViewBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.scrollLayout.setFixedLayout(mBinding.fixedLayout);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(this, getPageFragments());
        mBinding.viewpagerView.setAdapter(pagerAdapter);

        final String[] labels = new String[]{"linear", "scroll", "recycler"};
        new TabLayoutMediator(mBinding.tabLayout, mBinding.viewpagerView,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(labels[position]);
            }
        }).attach();
    }


    class ViewPagerAdapter extends FragmentStateAdapter {

        private List<Fragment> data;

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> data) {
            super(fragmentActivity);
            this.data = data;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return data.get(position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private List<Fragment> getPageFragments() {
        List<Fragment> data = new ArrayList<>();
        data.add(new RecyclerViewFragment());
        data.add(new RecyclerViewFragment());
        data.add(new RecyclerViewFragment());
        return data;
    }

}