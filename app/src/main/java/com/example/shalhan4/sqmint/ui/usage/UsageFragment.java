package com.example.shalhan4.sqmint.ui.usage;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shalhan4.sqmint.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsageFragment extends Fragment implements UsageView{

    private LineChart memoryUsageChart, cpuUsageChart;
    private List<Usage> mUsage;
    private UsagePresenter mUsagePresenter;


    public UsageFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_usage, container, false);
        this.mUsagePresenter = new UsagePresenter(this);


        memoryUsageChart = (LineChart) v.findViewById(R.id.memory_chart);
        cpuUsageChart = (LineChart) v.findViewById(R.id.cpu_chart);
        setMemoryUsageChart();
        setCpuUsageChart();


        feedMultiple();

        return v;
    }

    @Override
    public void addEntryMemoryUsage() {

        LineData data = memoryUsageChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(), (float) this.mUsage.get(0).getAvailableMemory()), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            memoryUsageChart.notifyDataSetChanged();

            // limit the number of visible entries
            memoryUsageChart.setVisibleXRangeMaximum(120);
            // memoryUsageChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            memoryUsageChart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // memoryUsageChart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }

    public void addEntryCpuUsage() {

        LineData data = cpuUsageChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(), (float) this.mUsage.get(0).getProcessorUsage()), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            cpuUsageChart.notifyDataSetChanged();

            // limit the number of visible entries
            cpuUsageChart.setVisibleXRangeMaximum(120);
            // memoryUsageChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            cpuUsageChart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // memoryUsageChart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }


    @Override
    public void setResources(List<Usage> value)
    {
        this.mUsage =  value;
        addEntryMemoryUsage();
        addEntryCpuUsage();
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    private Thread thread;

    private void feedMultiple() {

        if (thread != null)
            thread.interrupt();

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                mUsagePresenter.getUsage();
//                addEntry();
            }
        };

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {

                    // Don't generate garbage runnables inside the loop.
                    getActivity().runOnUiThread(runnable);

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }

    @Override
    public void setMemoryUsageChart() {
        // enable description text
        memoryUsageChart.getDescription().setEnabled(true);

        // enable touch gestures
        memoryUsageChart.setTouchEnabled(true);

        // enable scaling and dragging
        memoryUsageChart.setDragEnabled(true);
        memoryUsageChart.setScaleEnabled(true);
        memoryUsageChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        memoryUsageChart.setPinchZoom(true);

        // set an alternative background color
        memoryUsageChart.setBackgroundColor(Color.LTGRAY);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        memoryUsageChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = memoryUsageChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);

        XAxis xl = memoryUsageChart.getXAxis();
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = memoryUsageChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(4000f);
        leftAxis.setAxisMinimum(1000f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = memoryUsageChart.getAxisRight();
        rightAxis.setEnabled(false);

    }

    @Override
    public void setCpuUsageChart() {
        // enable description text
        cpuUsageChart.getDescription().setEnabled(true);

        // enable touch gestures
        cpuUsageChart.setTouchEnabled(true);

        // enable scaling and dragging
        cpuUsageChart.setDragEnabled(true);
        cpuUsageChart.setScaleEnabled(true);
        cpuUsageChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        cpuUsageChart.setPinchZoom(true);

        // set an alternative background color
        cpuUsageChart.setBackgroundColor(Color.LTGRAY);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        cpuUsageChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = cpuUsageChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);

        XAxis xl = cpuUsageChart.getXAxis();
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = cpuUsageChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(4000f);
        leftAxis.setAxisMinimum(1000f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = cpuUsageChart.getAxisRight();
        rightAxis.setEnabled(false);
    }
}
