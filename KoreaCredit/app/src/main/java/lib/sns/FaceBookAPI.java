package lib.sns;



import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


import lib.CommonUtil;



public class FaceBookAPI implements ISNSApi{

    private static final String TAG = "SNSFB";

    private static FaceBookAPI instence;


    private static AppCompatActivity mContext;

    public SNSDelegate delegate;
    public SNSUserObject userInfo;
    private AccessToken accessToken;

	public static FaceBookAPI getInstence()
    {


        return instence;
	}


    CallbackManager callbackManager;
    ProfileTracker profileTracker;
    AccessTokenTracker accessTokenTracker;
    public FaceBookAPI(AppCompatActivity _context) {
    	instence=this;

        mContext = _context;

        userInfo=new SNSUserObject();
        FacebookSdk.sdkInitialize(mContext.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        onlogin(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        if( delegate!=null){

                            delegate.onLoadError(SNSInfo.TYPE_FACEBOOK,SNSInfo.ERROR_LOGIN);
                        }
                    }
                });


        /*
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }



       profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code
                onLoginProfile(currentProfile);
            }
        };
        */
    }
    public SNSUserObject getUserInfo(){

        return userInfo;
    }
    public void setOnDelegate(SNSDelegate d){

        delegate=d;
    }
    public boolean isLogin(){

        return userInfo.isLogin();
    }
    public void login() {

        LoginManager.getInstance().logInWithReadPermissions(mContext, Arrays.asList("public_profile","email"));

    }

    public void loginReadPermissions(java.util.Collection<java.lang.String> permissions) {

        LoginManager.getInstance().logInWithReadPermissions(mContext, permissions);

    }
    public void loginPublishPermissions(java.util.Collection<java.lang.String> permissions) {

        LoginManager.getInstance().logInWithPublishPermissions(mContext, permissions);

    }



    private void onlogin(LoginResult loginResult) {

        if(!isLogin()){

           // return;
        }
        accessToken=loginResult.getAccessToken();
        userInfo.userToken=accessToken.getToken();
        userInfo.userSEQ=accessToken.getUserId();

       onLoginProfile(Profile.getCurrentProfile());






    }
    private void onLoginProfile(Profile currentProfile) {


        if(currentProfile.getName()!=null){

            userInfo.userName=currentProfile.getName();
        }

        Uri photo=currentProfile.getProfilePictureUri(320,320);
        if(photo!=null){

            userInfo.userImg= CommonUtil.getPathFromUri(mContext,photo);
        }

        if( delegate!=null){

            delegate.onLogin(SNSInfo.TYPE_FACEBOOK);
        }


    }
    public void loadProfile() {

       // Log.i(TAG,"load profile");
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        onLoadProfile(response);

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
        //onLoadProfile(Profile.getCurrentProfile());


    }
    private void onLoadProfile(GraphResponse response) {

        Log.i(TAG, response.toString());


        if(response.getError()!=null){

            if( delegate!=null){

                delegate.onLoadError(SNSInfo.TYPE_FACEBOOK, SNSInfo.ERROR_TYPE_PROFILE);
            }
            Log.i(TAG, "response error");
        }
        JSONObject data = response.getJSONObject();
        if(data==null){
            if( delegate!=null){

                delegate.onLoadError(SNSInfo.TYPE_FACEBOOK, SNSInfo.ERROR_TYPE_PROFILE);
            }
            Log.i(TAG, "data  null");
            return;
        }
        try {

            userInfo.userID  = data.getString("email");

        } catch (JSONException e) {

            Log.i(TAG, "profile null"  );
            if( delegate!=null){

                delegate.onLoadError(SNSInfo.TYPE_FACEBOOK, SNSInfo.ERROR_TYPE_PROFILE);
            }
            return;
        }
        Log.i(TAG,"onlogin : "+userInfo.toString());

        if( delegate!=null){

            delegate.onLoadProfile(SNSInfo.TYPE_FACEBOOK);
        }

    }

    public void logout() {

        LoginManager.getInstance().logOut();
        userInfo.logout();
        if( delegate!=null){

            delegate.onLogout(SNSInfo.TYPE_FACEBOOK);
        }
    }

    public void onDestroy() {

        if(profileTracker!=null) {
            profileTracker.stopTracking();
            profileTracker=null;
        }
        if(accessTokenTracker!=null){

            accessTokenTracker.stopTracking();
            accessTokenTracker=null;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }










}

