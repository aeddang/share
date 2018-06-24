package lib.sns;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;


import com.kakao.auth.Session;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginDefine;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.core.ActivityCore;
import lib.xmlmanager.XmlInfo;


public class NaverAPI implements ISNSApi{

    private static final String TAG = "SNSNaver";

    private static NaverAPI instence;

    private static String OAUTH_CLIENT_ID = "aAPfZrzOQXIEEx3Qlm4P";
    private static String OAUTH_CLIENT_SECRET = "syId3i7LRV";
    private static String OAUTH_CLIENT_NAME = "CardCherry";


    private static String API_LOAD_PROFILE="https://openapi.naver.com/v1/nid/getUserProfile.xml";


    private static OAuthLogin mOAuthLoginInstance;
    private static Context mContext;

    public SNSDelegate delegate;

    public SNSUserObject userInfo;


	public static NaverAPI getInstence()
    {


        return instence;
	}



    public NaverAPI(Context _context) {
    	instence=this;
        OAuthLoginDefine.DEVELOPER_VERSION = true;

        mContext = _context;

        userInfo=new SNSUserObject();
        initData();
    }

    private void initData() {
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);

    }
    public boolean isLogin(){

        return userInfo.isLogin();
    }
    public void setOnDelegate(SNSDelegate d){

        delegate=d;
    }
    public SNSUserObject getUserInfo(){

        return userInfo;
    }
    public void login() {

       switch(mOAuthLoginInstance.getState(mContext))
       {
           case NEED_INIT:
               initData();
               mOAuthLoginInstance.startOauthLoginActivity(ActivityCore.getInstence(), mOAuthLoginHandler);
               break;
           case NEED_LOGIN:
               mOAuthLoginInstance.startOauthLoginActivity(ActivityCore.getInstence(), mOAuthLoginHandler);
               break;
           case NEED_REFRESH_TOKEN:
               new RefreshTokenTask().execute();
               break;
           case OK:
               onlogin();
               break;

       }


    }
    private void onlogin() {

        String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
        String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
        long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
        String tokenType = mOAuthLoginInstance.getTokenType(mContext);

        //Log.d(TAG, "accessToken:" + accessToken);
        //Log.d(TAG, "refreshToken:" + refreshToken);

        userInfo.userToken=accessToken;
        if( delegate!=null){

            delegate.onLogin(SNSInfo.TYPE_NAVER);
        }

    }
    public void logout() {
        if(!isLogin()){

            return;
        }
        mOAuthLoginInstance.logout(mContext);
        userInfo.logout();
        if( delegate!=null){

            delegate.onLogout(SNSInfo.TYPE_NAVER);
        }
    }


    public void loadProfile() {

        new RequestApiTask().execute(API_LOAD_PROFILE);

    }
    public void onDestroy() {

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    private void onRequestApi(String path,String result) {

         if(path.equals(API_LOAD_PROFILE)==true){

             if( delegate!=null){

                  XmlInfo xml=new XmlInfo();

                 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                 DocumentBuilder builder;
                 try
                 {
                     builder = factory.newDocumentBuilder();
                     Document document = builder.parse( new InputSource( new StringReader( result ) ) );
                     NodeList resultcodes =  document.getElementsByTagName("resultcode");


                     if(resultcodes.getLength()<1){

                         delegate.onLoadError(SNSInfo.TYPE_NAVER, SNSInfo.ERROR_TYPE_PROFILE);
                     }else{

                         String resultcode = resultcodes.item(0).getTextContent();

                         if(resultcode.equals("00")){
                             NodeList responses =  document.getElementsByTagName("response");

                             if(responses.getLength()<1){

                                 delegate.onLoadError(SNSInfo.TYPE_NAVER, SNSInfo.ERROR_TYPE_PROFILE);
                             }else{
                                 Node response = responses.item(0);
                                 int childNum = response.getChildNodes().getLength();
                                 for(int i=0; i<childNum ;++i){
                                     Node child= response.getChildNodes().item(i);
                                     if(child.getNodeName().equals("email")==true){
                                           userInfo.userID=child.getTextContent();

                                     }else if(child.getNodeName().equals("profile_image")==true){
                                         userInfo.userImg=child.getTextContent();

                                     }else if(child.getNodeName().equals("id")==true){

                                         userInfo.userSEQ=child.getTextContent();
                                     }else if(child.getNodeName().equals("name")==true){

                                         userInfo.userName=child.getTextContent();
                                     }else if(child.getNodeName().equals("birthday")==true){

                                         userInfo.userBirthday=child.getTextContent();
                                     }

                                 }
                                 delegate.onLoadProfile(SNSInfo.TYPE_NAVER);


                             }


                         }else{
                             delegate.onLoadError(SNSInfo.TYPE_NAVER, SNSInfo.ERROR_TYPE_PROFILE);
                         }
                     }




                 } catch (Exception e) {
                     delegate.onLoadError(SNSInfo.TYPE_NAVER, SNSInfo.ERROR_TYPE_PROFILE);
                 }
             }
         }else{
             //request api result

         }
    }


    /**
     * startOAuthLoginActivity() 호출시 인자로 넘기거나, OAuthLoginButton 에 등록해주면 인증이 종료되는 걸 알 수 있다.
     */
    static private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {

                NaverAPI.getInstence().onlogin();

            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);

                Log.i(TAG, "errorCode:" + errorCode +" errorDesc :"+errorDesc);
                if(errorCode.equals("user_cancel")==false){
                    if( NaverAPI.getInstence().delegate!=null){

                        NaverAPI.getInstence().delegate.onLoadError(SNSInfo.TYPE_NAVER, SNSInfo.ERROR_LOGIN);
                    }

                }

            }
        };
    };



    private class DeleteTokenTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            boolean isSuccessDeleteToken = mOAuthLoginInstance.logoutAndDeleteToken(mContext);

            if (!isSuccessDeleteToken) {

                //Log.d(TAG, "errorCode:" + mOAuthLoginInstance.getLastErrorCode(mContext));
                //Log.d(TAG, "errorDesc:" + mOAuthLoginInstance.getLastErrorDesc(mContext));
            }

            return null;
        }
        protected void onPostExecute(Void v) {
            if( NaverAPI.getInstence().delegate!=null){

                NaverAPI.getInstence().delegate.onLogout(SNSInfo.TYPE_NAVER);
            }
        }
    }

    private class RequestApiTask extends AsyncTask<String, Void, String> {
        private String dataUrl;

        @Override
        protected void onPreExecute() {
            //mApiResultText.setText((String) "");
        }
        @Override
        protected String doInBackground(String... params) {
            dataUrl = params[0];
            String at = mOAuthLoginInstance.getAccessToken(mContext);
            return mOAuthLoginInstance.requestApi(mContext, at, dataUrl);
        }
        protected void onPostExecute(String content) {
            Log.i(TAG, "getUserProfile :" + content);
            onRequestApi(dataUrl,content);

        }
    }

    private class RefreshTokenTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return mOAuthLoginInstance.refreshAccessToken(mContext);
        }
        protected void onPostExecute(String res) {
            onlogin();
        }
    }








}

