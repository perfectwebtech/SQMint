package com.example.shalhan4.sqmint.ui.usage;

import java.util.List;

/**
 * Created by shalhan4 on 8/9/2017.
 */

public interface UsageView {

    public void setResources(List<Usage> value);
    public void setMemoryUsageChart();
    public void setCpuUsageChart();
    public void addEntryMemoryUsage();
    public void addEntryCpuUsage();
    public void connectionError();
}
