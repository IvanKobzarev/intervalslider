package com.epam.android.intervalslidertest.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epam.android.intervalslider.IntervalSliderIntRangeView;
import com.epam.android.intervalslider.IntervalSliderView;
import com.epam.android.intervalslider.Log;
import com.epam.android.intervalslidertest.R;


public class MainFragment extends Fragment {

    private IntervalSliderView intervalSliderView;
    private TextView intervalSliderProgressValuesText;
    private TextView intervalSliderIntRangeValuesText;
    private IntervalSliderIntRangeView intervalSliderIntRangeView;
    private IntervalSliderView intervalSliderLinearView;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initInflate(inflater, container);

        init();

        return view;
    }

    private View initInflate(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        intervalSliderView = (IntervalSliderView) view.findViewById(R.id.interval_slider);
        intervalSliderIntRangeView = (IntervalSliderIntRangeView) view.findViewById(R.id.interval_slider_int_range);
        intervalSliderLinearView = (IntervalSliderView) view.findViewById(R.id.interval_slider_linear);

        intervalSliderProgressValuesText = (TextView) view.findViewById(R.id.interval_progress_values_text);
        intervalSliderIntRangeValuesText = (TextView) view.findViewById(R.id.interval_range_values_text);

        return view;
    }

    private void init() {
        Log.d("main-fragment init");

        // base interval slider
        intervalSliderView.setListener(new IntervalSliderOnValueChangeListener());
        intervalSliderView.setMinProgress(0.1f);
        intervalSliderView.setMaxProgress(0.6f);

        // int range interval slider
        intervalSliderIntRangeView.setListener(new IntervalSliderIntRangeOnValueChangeListener());

        intervalSliderIntRangeValuesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intervalSliderIntRangeView.setRange(0, 1000);
            }
        });
        intervalSliderIntRangeView.setRange(0, 100);
        intervalSliderIntRangeView.setMinRegionValue(10);
        intervalSliderIntRangeView.setMaxRegionValue(66);


        intervalSliderLinearView.setMinProgress(0.25f);
        intervalSliderLinearView.setMaxProgress(0.66f);

    }

    private void setIntervalRangeText(int minRegionValue, int maxRegionValue) {
        intervalSliderIntRangeValuesText.setText(String.format("[%d, %d]", minRegionValue, maxRegionValue));
    }

    private class IntervalSliderOnValueChangeListener implements IntervalSliderView.OnValueChangedListener {

        @Override
        public void onValueChanged(IntervalSliderView slider, float minIntervalValue, float maxIntervalValue) {
            setIntervalValuesText(minIntervalValue, maxIntervalValue);
        }
    }

    private void setIntervalValuesText(float minValue, float maxValue) {
        intervalSliderProgressValuesText.setText(String.format("[%f, %f]", minValue, maxValue));
    }

    private class IntervalSliderIntRangeOnValueChangeListener extends IntervalSliderIntRangeView.OnIntRangeValueChangedListener implements IntervalSliderView.OnValueChangedListener {
        @Override
        public void onValueChanged(IntervalSliderIntRangeView slider, int minRegionValue, int maxRegionValue) {
            setIntervalRangeText(minRegionValue, maxRegionValue);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("main-frgament onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        setIntervalValuesText(intervalSliderView.getMinProgress(), intervalSliderView.getMaxProgress());
        setIntervalRangeText(intervalSliderIntRangeView.getMinRegionValue(), intervalSliderIntRangeView.getMaxRegionValue());
    }
}
