package com.aliya.base.gather;

import android.graphics.Point;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对{@link android.widget.EditText} 进行正则表达式配字符过滤
 *
 * @author a_liYa
 * @date 2019-09-18 10:34.
 */
public class FilterTextWatcher implements TextWatcher {

    /**
     * emoji表情 Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|
     * [\ud83e\udc00-\ud83e\udfff]|[\u2100-\u32ff]|[\u0030-\u007f][\u20d0-\u20ff]|[\u0080-\u00ff]");
     */
    Pattern mPattern;
    List<Point> mSpeChars;

    public FilterTextWatcher(Pattern pattern) {
        mPattern = pattern;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if (count - before >= 1) {
            CharSequence input = charSequence.subSequence(start, start + count);
            mSpeChars = matchSpeChars(input.toString());
            if (mSpeChars != null) {
                for (Point point : mSpeChars) {
                    point.offset(start, start);
                }
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        final List<Point> speChars = mSpeChars;
        if (speChars != null) {
            mSpeChars = null; // 必须提前置空，否则删除时陷入循环
            for (int i = speChars.size() - 1; i >= 0; i--) {
                Point point = speChars.get(i);
                editable.delete(point.x, point.y);
            }
        }
    }

    /**
     * 匹配返回指定字符位置集合
     */
    private List<Point> matchSpeChars(String input) {
        Matcher matcher = mPattern.matcher(input);
        List<Point> matchers = null;
        while (matcher.find()) {
            if (matchers == null) matchers = new ArrayList<>();
            matchers.add(new Point(matcher.start(), matcher.end()));
        }
        return matchers;
    }
}
