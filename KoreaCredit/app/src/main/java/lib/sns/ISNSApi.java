package lib.sns;


import android.content.Intent;

public interface ISNSApi {


    boolean isLogin();
    SNSUserObject getUserInfo();
    void setOnDelegate(SNSDelegate d);
    void login();
    void logout();
    void loadProfile();
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void onDestroy();

}
