package com.epam.android.intervalslider;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;

/**
 * Created by Ivan_Kobzarev on 2/10/14.
 */
public class IntervalSliderIntRangeView extends IntervalSliderView implements IntervalSliderView.OnValueChangedListener {

    private static final int DEFAULT_RANGE_MIN = 0;
    private static final int DEFAULT_RANGE_MAX = 100;
    private static final String BUNDLE_INTERVAL_SLIDER_INT_MIN = "interval_slider_int_min";
    private static final String BUNDLE_INTERVAL_SLIDER_INT_MAX = "interval_slider_int_max";

    private int minValue = DEFAULT_RANGE_MIN;
    private int maxValue = DEFAULT_RANGE_MAX;

    private OnIntRangeValueChangedListener listener;

    //region constructors
    public IntervalSliderIntRangeView(Context context) {
        super(context);
        init();
    }

    public IntervalSliderIntRangeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IntervalSliderIntRangeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    //endregion

    private void init() {
        super.setListener(this);
    }

    public int getMinRegionValue() {
        return minValue + (int) (getMinProgress() * (maxValue - minValue));
    }

    public int getMaxRegionValue() {
        return minValue + (int) (getMaxProgress() * (maxValue - minValue));
    }

    public void setMinRegionValue(int value) {
        setMinProgress((float) (value - minValue) / (maxValue - minValue));
    }

    public void setMaxRegionValue(int value) {
        setMaxProgress((float) (value - minValue) / (maxValue - minValue));
    }

    @Override
    public void onValueChanged(IntervalSliderView slider, float minIntervalValue, float maxIntervalValue) {
        if (listener != null)
            listener.onValueChanged(this, getMinRegionValue(), getMaxRegionValue());
    }

    public static abstract class OnIntRangeValueChangedListener implements OnValueChangedListener {

        @Override
        public void onValueChanged(IntervalSliderView slider, float minIntervalValue, float maxIntervalValue) {
        }

        public abstract void onValueChanged(IntervalSliderIntRangeView slider, int minRegionValue, int maxRegionValue);
    }

    //region save/restore
    @Override
    public Parcelable onSaveInstanceState() {
        Log.d("interval_slider_int onSaveInstanceState");
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_SUPER_INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(BUNDLE_INTERVAL_SLIDER_INT_MIN, minValue);
        bundle.putInt(BUNDLE_INTERVAL_SLIDER_INT_MAX, maxValue);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        Log.d("interval_slider_int onRestoreInstanceState");
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            minValue = bundle.getInt(BUNDLE_INTERVAL_SLIDER_INT_MIN);
            maxValue = bundle.getInt(BUNDLE_INTERVAL_SLIDER_INT_MAX);
            state = bundle.getParcelable(BUNDLE_SUPER_INSTANCE_STATE);
        }
        super.onRestoreInstanceState(state);
    }
    //endregion

    //region getters/setters
    public void setListener(OnIntRangeValueChangedListener listener) {
        this.listener = listener;
    }

    public void setRange(int minValue, int maxValue) {

        if (this.minValue != minValue || this.maxValue != maxValue) {
            int previousMinRegionValue = getMinRegionValue();
            int previousMaxRegionValue = getMaxRegionValue();

            this.minValue = minValue;
            this.maxValue = maxValue;

            setMinRegionValue(previousMinRegionValue);
            setMaxRegionValue(previousMaxRegionValue);
            invalidate();
//            return;
        }
//
//        this.minValue = minValue;
//        this.maxValue = maxValue;
    }
    //endregion
}
