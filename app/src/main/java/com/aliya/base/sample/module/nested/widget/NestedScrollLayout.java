package com.aliya.base.sample.module.nested.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

public class NestedScrollLayout extends NestedScrollView {

    private static final String TAG = "NestedScrollLayout";
    private View mFixedLayout;
    private FlingHelper mFlingHelper;

    int mTotalDy = 0;
    private int mVelocityY = 0; // 记录 ScrollView 当前 fling 的y轴加速度

    public NestedScrollLayout(Context context) {
        this(context, null);
    }

    public NestedScrollLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mFlingHelper = new FlingHelper(getContext());
    }

    /**
     * 设置高度等于 ScrollView 高度的底部 layout
     *
     * @param fixedLayout
     */
    public void setFixedLayout(View fixedLayout) {
        mFixedLayout = fixedLayout;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mFixedLayout != null) { // 设置固定
            ViewGroup.LayoutParams lp = mFixedLayout.getLayoutParams();
            lp.height = getMeasuredHeight();
            mFixedLayout.setLayoutParams(lp);
        }
    }

    @Override
    protected void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY);
        if (mVelocityY > 0) {
            mTotalDy += scrollY - oldScrollY;
        }
        if (scrollY == 0) {
            // ScrollView scroll to the top
        }
        if (scrollY == (getChildAt(0).getMeasuredHeight() - getMeasuredHeight())) {
            // ScrollView scroll to the bottom
            if (mVelocityY != 0) {
                dispatchChildFling();
                mTotalDy = 0;
                mVelocityY = 0;
            }
        }
    }

    private void dispatchChildFling() {
        Double splineFlingDistance = mFlingHelper.getSplineFlingDistance(mVelocityY);
        // todo 待优化
        if (splineFlingDistance > mTotalDy) {
            int velocityY =
                    mFlingHelper.getVelocityByDistance(splineFlingDistance - Double.valueOf(mTotalDy));
            RecyclerView childRecyclerView = getChildRecyclerView(this);
            if (childRecyclerView != null) {
                childRecyclerView.fling(0, velocityY);
            }
        }
    }

    // ScrollView fling
    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        if (velocityY <= 0) {
            mVelocityY = 0;
        } else {
            mTotalDy = 0;
            mVelocityY = velocityY;
        }
    }


    @Override
    public void onNestedPreScroll(View target, int dx, int dy, @NonNull int[] consumed, int type) {
        int canScrolledY = getCanScrolledY();
        // child view(RecyclerView) 向上滑动，其 ScrollView 仍可向上滑动
        if (dy > 0 && getScrollY() < canScrolledY) {
            int scrollDy = dy;
            // 若滑动 dy 超出 canScrolledY, 则只消费刚好滑动到 canScrollY 位置
            if (getScrollY() + dy > canScrolledY) {
                scrollDy = canScrolledY - getScrollY();
            }
            scrollBy(0, scrollDy);
            consumed[1] = scrollDy;
        }
    }

    /**
     * 获取 ScrollView y 轴能滑动的距离
     */
    public int getCanScrolledY() {
        return getChildAt(0).getMeasuredHeight() - getMeasuredHeight();
    }

    private RecyclerView getChildRecyclerView(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof RecyclerView && view.getClass() == RecyclerView.class) {
                return (RecyclerView) viewGroup.getChildAt(i);
            } else if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                ViewGroup childRecyclerView =
                        getChildRecyclerView((ViewGroup) viewGroup.getChildAt(i));
                if (childRecyclerView instanceof RecyclerView) {
                    return (RecyclerView) childRecyclerView;
                }
            }
            continue;
        }
        return null;
    }

    static class FlingHelper {
        private static float DECELERATION_RATE = ((float) (Math.log(0.78d) / Math.log(0.9d)));
        private static float mFlingFriction = ViewConfiguration.getScrollFriction();
        private static float mPhysicalCoeff;

        public FlingHelper(Context context) {
            mPhysicalCoeff =
                    context.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
        }

        private double getSplineDeceleration(int i) {
            return Math.log((double) ((0.35f * ((float) Math.abs(i))) / (mFlingFriction * mPhysicalCoeff)));
        }

        private double getSplineDecelerationByDistance(double d) {
            return ((((double) DECELERATION_RATE) - 1.0d) * Math.log(d / ((double) (mFlingFriction * mPhysicalCoeff)))) / ((double) DECELERATION_RATE);
        }

        public double getSplineFlingDistance(int i) {
            return Math.exp(getSplineDeceleration(i) * (((double) DECELERATION_RATE) / (((double) DECELERATION_RATE) - 1.0d))) * ((double) (mFlingFriction * mPhysicalCoeff));
        }

        public int getVelocityByDistance(double d) {
            return Math.abs((int) (((Math.exp(getSplineDecelerationByDistance(d)) * ((double) mFlingFriction)) * ((double) mPhysicalCoeff)) / 0.3499999940395355d));
        }
    }
}
