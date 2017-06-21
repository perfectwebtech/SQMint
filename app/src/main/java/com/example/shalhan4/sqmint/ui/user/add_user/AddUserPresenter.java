package com.example.shalhan4.sqmint.ui.user.add_user;

/**
 * Created by shalhan4 on 6/21/2017.
 */

public class AddUserPresenter implements AddUserPresenterIntf {
    private AddUserView mAddUser;

    public AddUserPresenter(AddUserView addUser)
    {
        this.mAddUser = addUser;
    }

    @Override
    public boolean isUsernameValid(String username)
    {
        return true;
    }
}
