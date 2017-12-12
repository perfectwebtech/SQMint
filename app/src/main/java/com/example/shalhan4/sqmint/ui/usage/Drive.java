package com.example.shalhan4.sqmint.ui.usage;

/**
 * Created by shalhan4 on 12/12/2017.
 */

public class Drive {
    int id;
    String driveName, availableSpace, totalSpace;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDriveName() {
        return driveName;
    }

    public void setDriveName(String driveName) {
        this.driveName = driveName;
    }

    public String getAvailableSpace() {
        return availableSpace;
    }

    public void setAvailableSpace(String availableSpace) {
        this.availableSpace = availableSpace;
    }

    public String getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(String totalSpace) {
        this.totalSpace = totalSpace;
    }
}
