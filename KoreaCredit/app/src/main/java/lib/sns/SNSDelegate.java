package lib.sns;


public interface SNSDelegate {



    void onLogin(int type);
    void onLogout(int type);
    void onLoadProfile(int type);
    void onLoadError(int type, int etype);

}
