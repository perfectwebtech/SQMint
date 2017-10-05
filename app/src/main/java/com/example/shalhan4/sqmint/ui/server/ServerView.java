package com.example.shalhan4.sqmint.ui.server;

import java.util.List;

/**
 * Created by shalhan4 on 10/1/2017.
 */

public interface ServerView {
    public void setServerListAdapter(List<Server> mServerList);
    public void deleteServer(int id);
    public void deleteSuccess();
    public void deleteFailed();
    public void addServerSuccess();
    public void addServerFailed();

}
