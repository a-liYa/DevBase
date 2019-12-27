package com.aliya.base.sample.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.BaseActivity;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Class<Typeface> typefaceClass = Typeface.class;
//        try {
//            Field typefaceField = typefaceClass.getDeclaredField("sSystemFontMap");
//            typefaceField.setAccessible(true);
//            Map<String, Typeface> o = (Map<String, Typeface>) typefaceField.get(null);
//            Map<String, Typeface> newMap = new HashMap<>();
//            Set<Map.Entry<String, Typeface>> entries = o.entrySet();
//            for (Map.Entry<String, Typeface> entry : entries) {
//                if (entry.getKey().equals("sans-serif")) {
//                    newMap.put(entry.getKey(), ResourcesCompat.getFont(this, R.font.fzbiaoysk_zbjt));
//                } else {
//                    newMap.put(entry.getKey(), entry.getValue());
//                }
//                Log.e("TAG", "onCreate: " + entry.getKey() + " - " + newMap.get(entry.getKey()));
//            }
//            typefaceField.set(null, newMap);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("TAG", "Exception: sSystemFontMap" + e.toString());
//        }

        setContentView(R.layout.activity_second);
    }
}
