package com.example.shalhan4.sqmint.ui.usage;


import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.shalhan4.sqmint.R;
import com.example.shalhan4.sqmint.ui.job.JobListAdapter;
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
    private Handler mHandler = new Handler();
    private int SERVER_ID;
    private View view;
    private Thread threadUsage;
    private ListView mListView;
    private DriveListAdapter mDriveAdapter;

    public UsageFragment() {
        // Required empty public constructor
    }

    public void setServerId(int id){this.SERVER_ID = id;}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_usage, container, false);
        this.view = v;

        this.mUsagePresenter = new UsagePresenter(this);

        this.mUsagePresenter.setUsageContext(getActivity());
        this.mListView = (ListView) v.findViewById(R.id.drive_list);


        memoryUsageChart = (LineChart) v.findViewById(R.id.memory_chart);
        cpuUsageChart = (LineChart) v.findViewById(R.id.cpu_chart);
        setMemoryUsageChart();
        setCpuUsageChart();


        feedMultiple();

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("ON Detach", "YEAH");
        threadUsage.interrupt();
    }

    @Override
    public void addEntryMemoryUsage() {

        LineData data = memoryUsageChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet("MSSQL memory usage");
                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(), (float) this.mUsage.get(0).getAvailableMemory()), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            memoryUsageChart.notifyDataSetChanged();

            memoryUsageChart.setVisibleXRangeMaximum(6);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            memoryUsageChart.moveViewToX(data.getEntryCount());
        }
    }

    public void addEntryCpuUsage() {

        LineData data = cpuUsageChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet("CPU busy in seconds");
                data.addDataSet(set);
            }

            data.addEntry(new Entry(set.getEntryCount(), (float) this.mUsage.get(0).getProcessorUsage()), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            cpuUsageChart.notifyDataSetChanged();

            cpuUsageChart.setVisibleXRangeMaximum(6);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            cpuUsageChart.moveViewToX(data.getEntryCount());
        }
    }

    @Override
    public void setResources(List<Usage> value)
    {
        this.mUsage =  value;
        addEntryMemoryUsage();
        addEntryCpuUsage();
        setDriveListAdapter(value);
    }

    private LineDataSet createSet(String desc) {

        LineDataSet set = new LineDataSet(null, desc);
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


    private void feedMultiple() {

        if (threadUsage != null)
            threadUsage.interrupt();


        threadUsage = new Thread(new Runnable() {
            int i = 0;
            public void run() {
                while ( i < 100) {
                    i += 1;

                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mUsagePresenter.getUsage(SERVER_ID);
                        }
                    });

                    try{
                        Thread.sleep(1500);
                    }
                    catch(InterruptedException e)
                    {
                        break;
                    }
                }
            }
        });
        threadUsage.start();
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
        xl.setDrawGridLines(true);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = memoryUsageChart.getAxisLeft();
        leftAxis.setLabelCount(6);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        leftAxis.setEnabled(true);


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
        leftAxis.setLabelCount(6);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        leftAxis.setEnabled(true);


        YAxis rightAxis = cpuUsageChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    @Override
    public void setDriveListAdapter(List<Usage> mUsage) {
        this.mDriveAdapter = new DriveListAdapter(getActivity(), mUsage.get(0).getListDrive());
        this.mListView.setAdapter(mDriveAdapter);
    }

    @Override
    public void connectionError() {
//        LinearLayout errorMonitoring = (LinearLayout) getActivity().findViewById(R.id.bg_error_usage);
//        errorMonitoring.setVisibility(this.view.VISIBLE);
//        ScrollView scrollView = (ScrollView) this.view.findViewById(R.id.sv_usage);
//        scrollView.setVisibility(this.view.INVISIBLE);
    }
}
