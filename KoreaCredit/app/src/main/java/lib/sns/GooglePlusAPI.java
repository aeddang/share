package lib.sns;


import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;


import com.credit.korea.KoreaCredit.Config;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lib.core.ActivityCore;

public class GooglePlusAPI {

    private static final String TAG = "GooglePlusAPI";


    private static GooglePlusAPI instence;


    private static final String APP_NAME = "test";
    private static final int REQUEST_ACCOUNT_PICKER = 1000;
    private static final int REQUEST_AUTHORIZATION = 1001;
    private static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static final String PREF_ACCOUNT_NAME = "GoogleDriveAPI_ACCOUNT_NAME";





    private static final String[] SCOPES = { DriveScopes.DRIVE_METADATA_READONLY };

    public SNSDelegate delegate;
    public GooglePlayDelegate googlePlayDelegate;
    public GoogleDriveDelegate googleDriveDelegate;
    public SNSUserObject userInfo;

    public interface GooglePlayDelegate
    {
        void playServicesOn(boolean able);
        void accountSelected(String accountName);


    }
    public interface GoogleDriveDelegate
    {

        void getFileListComplete(List<String> result);
    }



	public static GooglePlusAPI getInstence(){

         return instence;
	}

    public GoogleAccountCredential mCredential;
    private Activity context;
    private int taskType=-1;

    public GooglePlusAPI(Activity _context) {
    	instence=this;
        context=_context;
        userInfo=new SNSUserObject();
        SharedPreferences settings = context.getPreferences(Context.MODE_PRIVATE);
        mCredential = GoogleAccountCredential.usingOAuth2(
                context.getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));
        if(mCredential.getSelectedAccountName()!=null) {
            userInfo.userID = mCredential.getSelectedAccountName();
        }

    }

    public void onActivityResult(
            int requestCode, int resultCode, Intent data) {

        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                boolean able=false;
                if (resultCode != context.RESULT_OK) {
                    able=isGooglePlayServicesAvailable();
                    Log.i(TAG,"REQUEST_GOOGLE_PLAY_SERVICES : "+able);
                }else{
                    Log.i(TAG,"REQUEST_GOOGLE_PLAY_SERVICES : resultCode error");

                }
                if(googlePlayDelegate!=null){

                    googlePlayDelegate.playServicesOn(able);
                }

                break;
            case REQUEST_ACCOUNT_PICKER:


                if (resultCode == context.RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    Log.i("","REQUEST_ACCOUNT_PICKER : "+accountName);

                    if (accountName != null) {
                        mCredential.setSelectedAccountName(accountName);
                        SharedPreferences settings =
                                context.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();

                        switch (taskType){

                            case MakeRequestTask.TASK_TYPE_FILELIST:
                                loadFileList();
                                break;
                            case MakeRequestTask.TASK_TYPE_TOKEN:
                                login();
                                break;

                        }
                        taskType=-1;

                    }
                    if(googlePlayDelegate!=null){
                        userInfo.userID=accountName;
                        googlePlayDelegate.accountSelected(accountName);
                    }


                } else if (resultCode == context.RESULT_CANCELED) {

                }

                break;
            case REQUEST_AUTHORIZATION:
                Log.i("","REQUEST_AUTHORIZATION");
                if (resultCode != context.RESULT_OK) {
                    chooseAccount();
                }
                break;
        }


    }


    public void login() {

        if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {
            new MakeRequestTask(mCredential,MakeRequestTask.TASK_TYPE_TOKEN).execute();

        }
    }
    public void logout() {
        userInfo=null;
        SharedPreferences settings = context.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_ACCOUNT_NAME,null);
        editor.commit();
    }


    /**
     * Attempt to get a set of data from the Drive API to display. If the
     * email address isn't known yet, then call chooseAccount() method so the
     * user can pick an account.
     */
    public void loadFileList() {

        if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {
            new MakeRequestTask(mCredential,MakeRequestTask.TASK_TYPE_FILELIST).execute();

        }
    }
    /**
     * Starts an activity in Google Play Services so the user can pick an
     * account.
     */
    public void chooseAccount() {
        context.startActivityForResult(
                mCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }



    /**
     * Check that Google Play services APK is installed and up to date. Will
     * launch an error dialog for the user to update Google Play Services if
     * possible.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    public  boolean isGooglePlayServicesAvailable() {
        final int connectionStatusCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
            return false;
        } else if (connectionStatusCode != ConnectionResult.SUCCESS ) {
            return false;
        }
        return true;
    }

    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     * @param connectionStatusCode code describing the presence (or lack of)
     *     Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                connectionStatusCode,
                context,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    /**
     * An asynchronous task that handles the Drive API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {

        public  static final int TASK_TYPE_FILELIST=0;
        public  static final int TASK_TYPE_TOKEN=1;

        private com.google.api.services.drive.Drive mService = null;
        private Exception mLastError = null;
        private int type;
        public MakeRequestTask(GoogleAccountCredential credential,int _type) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            type=_type;

            mService = new com.google.api.services.drive.Drive.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName(GooglePlusAPI.APP_NAME)
                    .build();
        }

        /**
         * Background task to call Drive API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                switch (type){
                    case TASK_TYPE_TOKEN:
                        return getToken();
                    case TASK_TYPE_FILELIST:
                        return getDataFromApi();
                    default:
                        cancel(true);
                        return null;
                }

            } catch (Exception e) {
                mLastError = e;
                cancel(true);

                return null;
            }
        }

        /**
         * Fetch a list of up to 10 file names and IDs.
         * @return List of Strings describing files, or an empty list if no files
         *         found.
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            // Get a list of up to 10 files.
            List<String> fileInfo = new ArrayList<String>();
            FileList result = mService.files().list()
                    .setMaxResults(10)
                    .execute();
            List<File> files = result.getItems();
            if (files != null) {
                for (File file : files) {
                    fileInfo.add(String.format("%s (%s)\n",
                            file.getTitle(), file.getId()));
                }
            }
            return fileInfo;
        }

        private List<String> getToken() throws IOException,GoogleAuthException{

            List<String> tokenInfo = new ArrayList<String>();
            String token="";
            token=mCredential.getToken();
            tokenInfo.add(token);
            return tokenInfo ;
        }


        @Override
        protected void onPreExecute() {
            //mOutputText.setText("");
            //mProgress.show();
        }

        @Override
        protected void onPostExecute(List<String> output) {
           // mProgress.hide();
            if (output == null || output.size() == 0) {
               // mOutputText.setText("No results returned.");
            } else {

                String result= TextUtils.join("\n", output);
                Log.i("","Data retrieved using the Drive API:"+result);

                    switch (type){
                        case TASK_TYPE_TOKEN:
                            if(delegate!=null) {
                                userInfo.userToken=output.get(0);
                                delegate.onLogin(SNSInfo.TYPE_GOOGLE);
                            }
                            break;
                        case TASK_TYPE_FILELIST:
                            if(googleDriveDelegate!=null) {
                                googleDriveDelegate.getFileListComplete(output);
                            }
                            break;
                        default:
                            break;
                    }



               // mOutputText.setText(TextUtils.join("\n", output));
            }
        }

        @Override
        protected void onCancelled() {
           // mProgress.hide();
            if (mLastError != null) {
                Log.i("","mLastError : "+mLastError.toString());
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {

                    Log.i("","GooglePlayServicesAvailabilityIOException");

                            showGooglePlayServicesAvailabilityErrorDialog(
                                    ((GooglePlayServicesAvailabilityIOException) mLastError)
                                            .getConnectionStatusCode());



                } else if (mLastError instanceof UserRecoverableAuthIOException)
                {
                    Log.i("","UserRecoverableAuthIOException");

                           ActivityCore.getInstence().startActivityForResult(
                                    ((UserRecoverableAuthIOException) mLastError).getIntent(),
                                    REQUEST_AUTHORIZATION);


                } else {
                    //mOutputText.setText("The following error occurred:\n" + mLastError.getMessage());
                }
            } else {

                Log.i("","Request cancelled");
                //mOutputText.setText("Request cancelled.");
            }
        }
    }
}

