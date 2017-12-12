package com.example.shalhan4.sqmint.ui.usage;

import java.util.List;

/**
 * Created by shalhan4 on 8/9/2017.
 */

public class Usage {
    private int id;
    private double availableMemory;
    private double processorUsage;
    private List<Drive> listDrive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAvailableMemory() {
        return availableMemory;
    }

    public void setAvailableMemory(double availableMemory) {
        this.availableMemory = availableMemory;
    }

    public double getProcessorUsage() {
        return processorUsage;
    }

    public void setProcessorUsage(double processorUsage) {
        this.processorUsage = processorUsage;
    }

    public List<Drive> getListDrive() {
        return listDrive;
    }

    public void setListDrive(List<Drive> listDrive) {
        this.listDrive = listDrive;
    }
}
