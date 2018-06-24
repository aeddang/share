package com.credit.korea.KoreaCredit;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.credit.korea.KoreaCredit.member.MemberInfo;

import java.util.ArrayList;

import lib.sns.FaceBookAPI;
import lib.sns.GoogleSignAPI;

import lib.sns.ISNSApi;
import lib.sns.KakaoAPI;
import lib.sns.SNSDelegate;
import lib.sns.SNSInfo;
import lib.sns.NaverAPI;
import lib.sns.SNSUserObject;


public class SNSManager implements SNSDelegate
{
    /*
    google
    id : bonusplayer@gmail.com
    pw : youloveme21

    facebook
    id : autodoc@daum.net
    pw : met475yi

    naver
    id : cardcherryltd
    pw : iloveyou12

    kakao
    id : autodoc@daum.net
    pw : iloveyou12
    */
    private static SNSManager instence;

    public SNSManagerDelegate delegate;
    private ArrayList<ISNSApi> snsApis;
    private boolean isAutoLogin;
    public interface SNSManagerDelegate
    {
        void onLogin(SNSManager m, SNSUserObject obg, int type);
        void onLogout(SNSManager m, int type);
        void onError(SNSManager m,int type ,int eType);

    }


	public static SNSManager getInstence()
    {
        return instence;
	}


    public SNSManager(AppCompatActivity context) {
    	instence=this;
        snsApis=new ArrayList<ISNSApi>();

        snsApis.add(new GoogleSignAPI(context));
        snsApis.add(new NaverAPI(context));
        snsApis.add(new FaceBookAPI(context));
        snsApis.add(new KakaoAPI(context));

        for(int i=0;i<snsApis.size();++i){

            snsApis.get(i).setOnDelegate(this);
        }



    }
    public void autoLogin(int type){

         isAutoLogin=true;
         snsApis.get(type).login();



    }
    public void login(int type){

        isAutoLogin=false;
        for(int i=0;i<snsApis.size();++i){

            if(type==i){
                snsApis.get(type).login();
            }else{
                snsApis.get(i).logout();
            }

        }



    }
    public void logoutAll(){

        for(int i=0;i<snsApis.size();++i){

            snsApis.get(i).logout();
        }

    }

    public void logout(int type){

        snsApis.get(type).logout();

    }


    public void onLogin(int type){

        snsApis.get(type).loadProfile();



    }
    public void onLogout(int type)
    {
        MemberInfo.getInstence().unregisterSNS(type);
        if(delegate!=null){

            delegate.onLogout(this,type);
        }
    }
    public void onLoadProfile(int type){

        SNSUserObject data=snsApis.get(type).getUserInfo();

        Log.i("","user data : "+data.toString());
        MemberInfo.getInstence().registerSNS(type);
        if(delegate!=null){

            delegate.onLogin(this,data,type);
        }
    }
    public void onLoadError(int type, int etype)
    {
         MemberInfo.getInstence().unregisterSNS(type);
         if(delegate!=null){

             delegate.onError(this,type,etype);
         }
         MainActivity.getInstence().viewAlert("",R.string.msg_sns_login_fail,null);
    }

    public void onDestroy() {

        for(int i=0;i<snsApis.size();++i){

            snsApis.get(i).onDestroy();
        }
        snsApis.clear();

    }
    public void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        for(int i=0;i<snsApis.size();++i){

            snsApis.get(i).onActivityResult(requestCode , resultCode,  data);
        }

    }



}

