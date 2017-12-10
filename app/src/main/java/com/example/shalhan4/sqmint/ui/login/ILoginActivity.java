package com.example.shalhan4.sqmint.ui.login;

/**
 * Created by shalhan4 on 5/3/2017.
 */

public interface ILoginActivity {
    void loginIsValid();

    void loginNotValid();

    void fieldIsNull(int fieldUsername, int fieldPassword);

    void runProgressBar();

    void stopProgressBar();

    void disableComponent();

    void enableComponent();

    void saveFirebaseToken(String adminId, String token);
}
