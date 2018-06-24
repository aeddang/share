package lib.sns;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;


import lib.core.ActivityCore;



public class KakaoAPI implements ISNSApi{

    private static final String TAG = "SNSKakao";

    private static KakaoAPI instence;


    private SessionCallback callback;
    private static AppCompatActivity mContext;

    public SNSDelegate delegate;

    public SNSUserObject userInfo;


	public static KakaoAPI getInstence()
    {
        return instence;
	}



    public KakaoAPI(AppCompatActivity _context) {
    	instence=this;
        mContext = _context;
        userInfo=new SNSUserObject();
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

    }
    public void setOnDelegate(SNSDelegate d){

        delegate=d;
    }
    public boolean isLogin(){

        return userInfo.isLogin();
    }
    public SNSUserObject getUserInfo(){

        return userInfo;
    }
    public void login() {

        Session.getCurrentSession().open(AuthType.KAKAO_TALK_EXCLUDE_NATIVE_LOGIN,mContext);


    }
    private void onlogin() {

        userInfo.userToken=Session.getCurrentSession().getAccessToken();

        if(delegate!=null){
            delegate.onLogin(SNSInfo.TYPE_KAKAO);
        }
    }
    public void logout() {
        if(!isLogin()){

            return;
        }

        userInfo.logout();
        if(delegate!=null){

            delegate.onLogout(SNSInfo.TYPE_KAKAO);
        }
        UserManagement.requestLogout(new LogoutResponseCallback()
        {
            @Override
            public void onCompleteLogout() {

            }
        });
    }

    public void loadProfile() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {

                if(delegate!=null){
                    delegate.onLoadError(SNSInfo.TYPE_KAKAO,SNSInfo.ERROR_TYPE_PROFILE);
                }

            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.i("","onSessionClosed : " + errorResult.getErrorCode()+" "+errorResult.getErrorMessage());

                if(delegate!=null){
                    delegate.onLoadError(SNSInfo.TYPE_KAKAO,SNSInfo.ERROR_TYPE_PROFILE);
                }
            }



            @Override
            public void onSuccess(UserProfile userProfile) {
                Log.i("","UserProfile : " + userProfile);
                userInfo.userImg=userProfile.getProfileImagePath();
               
                userInfo.userSEQ=String.valueOf(userProfile.getId());
                userInfo.userName=userProfile.getNickname();

                if(delegate!=null){
                    delegate.onLoadProfile(SNSInfo.TYPE_KAKAO);
                }
            }

            @Override
            public void onNotSignedUp() {
                login();
            }
        });
    }

    public void onDestroy() {
        Session.getCurrentSession().removeCallback(callback);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data);
    }


    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {

            Log.i("","onSessionOpened");
            onlogin();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {

                Log.i("","exception.getErrorType() : "+exception.getErrorType());
                Log.i("","exception.getErrorType() : "+exception.getMessage());
                if(exception.getErrorType().equals("CANCELED_OPERATION")==false) {
                    if (delegate != null) {
                        delegate.onLoadError(SNSInfo.TYPE_KAKAO, SNSInfo.ERROR_LOGIN);
                    }
                }
            }
        }
    }








}

