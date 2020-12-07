package com.aliya.base.sample.module.locale;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.LanguageLayoutFragmentZhBinding;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 适配多语言 - 示例页
 *
 * Activity设置为英文，Fragment设置为中文
 *
 * @author a_liYa
 * @date 2020/12/4 16:22.
 */
public class LanguageActivity extends ActionBarActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        // 不能直接修改，会修改全局 ResourcesImpl#mConfiguration
        Configuration configuration = new Configuration(newBase.getResources().getConfiguration());
        configuration.setLocale(Locale.ENGLISH);
        newBase = newBase.createConfigurationContext(configuration);
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        getSupportFragmentManager().beginTransaction().add(R.id.frame_zh, new ZHFragment()).commit();
    }

    /**
     * locale 为中文的 Fragment，实现 Activity 局部不同语言
     */
    public static class ZHFragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            // 若使用（context, theme）构造，则 theme 不能使用外部 theme，需要手动 Resources().newTheme()
            ContextThemeWrapper context =
                    new ContextThemeWrapper(container.getContext(), R.style.AppTheme);
            Configuration config = new Configuration(container.getResources().getConfiguration());
            config.setLocale(Locale.CHINESE);
            context.applyOverrideConfiguration(config);
            inflater = LayoutInflater.from(context);
            return inflater.inflate(R.layout.language_layout_fragment_zh, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            LanguageLayoutFragmentZhBinding binding = LanguageLayoutFragmentZhBinding.bind(view);
            binding.tvDescribe.setText("语言：中文");
        }
    }
}