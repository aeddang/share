package lib.sns;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.credit.korea.KoreaCredit.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;



import lib.CommonUtil;

public class GoogleSignAPI implements GoogleApiClient.OnConnectionFailedListener ,ISNSApi{

    private static final String TAG = "GoogleAPI";


    private static final int RC_SIGN_IN = 9001;


    private static GoogleSignAPI instence;
    private AppCompatActivity context;
    private GoogleApiClient mGoogleApiClient;


    public SNSDelegate delegate;

    public SNSUserObject userInfo;


	public static GoogleSignAPI getInstence()
    {

         return instence;


	}


    public GoogleSignAPI(AppCompatActivity _context) {
    	instence=this;

        userInfo=new SNSUserObject();
        context=_context;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestEmail()
                .requestIdToken(context.getString(R.string.server_client_id))
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(context , this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }


    public boolean isLogin(){

        return userInfo.isLogin();
    }
    public void setOnDelegate(SNSDelegate d){

        delegate=d;
    }
    public void login() {


        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        context.startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    public SNSUserObject getUserInfo(){

        return userInfo;
    }

    public void loadProfile() {

        if(delegate!=null){
            delegate.onLoadProfile(SNSInfo.TYPE_GOOGLE);

        }

    }

    public  void logout() {
        if(!isLogin()){

            return;
        }
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        userInfo.logout();
                        if(delegate!=null){
                            delegate.onLogout(SNSInfo.TYPE_GOOGLE);

                        }
                    }
                });
    }

    public void onDestroy() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
       // Log.i(TAG, "handleSignInResult:" + result.isSuccess());

        Log.i(TAG, "handleSignInResult:" + result.getStatus());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            userInfo.userToken = acct.getIdToken();
            if(acct.getEmail()!=null) {
                userInfo.userID = acct.getEmail();
            }
            userInfo.userSEQ = acct.getId();
            if(acct.getPhotoUrl()!=null){
                userInfo.userImg = CommonUtil.getPathFromUri(context, acct.getPhotoUrl());
            }
            userInfo.userName = acct.getDisplayName();

            if(delegate!=null){
                delegate.onLogin(SNSInfo.TYPE_GOOGLE);

            }
            //ßmStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
        } else {

            if(delegate!=null){

                delegate.onLoadError(SNSInfo.TYPE_GOOGLE,SNSInfo.ERROR_LOGIN);

            }
        }
    }



    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        //updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.i(TAG, "onConnectionFailed:" + connectionResult);
        delegate.onLoadError(SNSInfo.TYPE_GOOGLE,SNSInfo.ERROR_LOGIN);
    }








}

