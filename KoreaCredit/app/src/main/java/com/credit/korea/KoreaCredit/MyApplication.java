package com.credit.korea.KoreaCredit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;



public class MyApplication extends Application
{
    private static Activity currentActivity;
    private static Context currentContext;
    private static class KakaoSDKAdapter extends KakaoAdapter {
        /**
         * Session Config에 대해서는 default값들이 존재한다.
         * 필요한 상황에서만 override해서 사용하면 됨.
         * @return Session의 설정값.
         */
        @Override
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }

                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }

        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Activity getTopActivity() {
                    return MyApplication.getCurrentActivity();
                }

                @Override
                public Context getApplicationContext() {
                    return MyApplication.getGlobalApplicationContext();
                }
            };
        }
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }
    // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.
    public static void setCurrentActivity(Activity currentActivity) {
        MyApplication.currentActivity = currentActivity;
    }
    public static Context getGlobalApplicationContext() {
        return currentContext;
    }
    public void onCreate() {
        super.onCreate();
        currentContext=this.getApplicationContext();




        Log.i("", "MyApplication call");
        KakaoSDK.init(new KakaoSDKAdapter());

    }
    

}
