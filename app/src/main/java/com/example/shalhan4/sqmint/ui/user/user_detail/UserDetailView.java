package com.example.shalhan4.sqmint.ui.user.user_detail;

import java.util.List;

/**
 * Created by shalhan4 on 9/9/2017.
 */

public interface UserDetailView {
    public void setJobDetailListAdapter(List<UserDetail> response);
    public void userDetailListEmpty();
}
