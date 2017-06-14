package com.example.shalhan4.sqmint.ui.usage;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shalhan4.sqmint.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsageFragment extends Fragment {

    LineChart cpuChart, memoryChart;

    public UsageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_usage, container, false);

        this.cpuChart = (LineChart) v.findViewById(R.id.cpu_chart);
        this.memoryChart = (LineChart) v.findViewById(R.id.memory_chart);

        float cpuUsage[] = {10,30,40,60};
        float memoryUsage[] = {10,20,20,30};

        setCpuChart(cpuUsage);
        setMemoryChart(memoryUsage);

        return v;
    }

    public void setCpuChart(float yValues[])
    {

        ArrayList<Entry> yData = new ArrayList<>();

        for (int i = 0; i<yValues.length; i++) {
            // turn your data into Entry objects
            yData.add(new Entry(yValues[i], i));
        }

        LineDataSet lineDataSet1 = new LineDataSet(yData, "CPU Usage"); // add entries to dataset
        lineDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        LineData lineData = new LineData(lineDataSet1);
        lineData.setValueTextSize(13f); // styling, ...
        lineData.setValueTextColor(Color.BLACK);

        this.cpuChart.setData(lineData);
        this.cpuChart.invalidate();

    }

    public void setMemoryChart(float yValues[])
    {

        ArrayList<Entry> yData = new ArrayList<>();

        for (int i = 0; i<yValues.length; i++) {
            // turn your data into Entry objects
            yData.add(new Entry(yValues[i], i));
        }

        LineDataSet lineDataSet1 = new LineDataSet(yData, "Memory Usage"); // add entries to dataset
        lineDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        LineData lineData = new LineData(lineDataSet1);
        lineData.setValueTextSize(13f); // styling, ...
        lineData.setValueTextColor(Color.BLACK);

        this.memoryChart.setData(lineData);
        this.memoryChart.invalidate();

    }
}
