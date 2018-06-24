

package com.credit.korea.KoreaCredit;



import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.credit.korea.KoreaCredit.alert.AlertObject;
import com.credit.korea.KoreaCredit.alert.AlertView;
import com.credit.korea.KoreaCredit.card.CardObject;
import com.credit.korea.KoreaCredit.card.PageCard;
import com.credit.korea.KoreaCredit.card.PopupAddCard;
import com.credit.korea.KoreaCredit.card.PopupCardCompare;
import com.credit.korea.KoreaCredit.card.PopupCardList;
import com.credit.korea.KoreaCredit.card.PopupDetailCard;
import com.credit.korea.KoreaCredit.card.PopupHitList;
import com.credit.korea.KoreaCredit.card.book.PopupBookAdded;
import com.credit.korea.KoreaCredit.card.book.PopupBookDesc;
import com.credit.korea.KoreaCredit.card.find.PopupCardFind;
import com.credit.korea.KoreaCredit.mypage.consume.PopupConsumeBenefitModify;
import com.credit.korea.KoreaCredit.mypage.consume.PopupConsumeModify;
import com.credit.korea.KoreaCredit.mypage.consume.PopupConsumeDefault;
import com.credit.korea.KoreaCredit.listresult.PopupListResult;
import com.credit.korea.KoreaCredit.member.MemberInfo;
import com.credit.korea.KoreaCredit.member.PageMember;
import com.credit.korea.KoreaCredit.member.PopupModify;
import com.credit.korea.KoreaCredit.mypage.MyCardInfo;
import com.credit.korea.KoreaCredit.mypage.PageMySearch;
import com.credit.korea.KoreaCredit.mypage.PageMypage;


import java.util.ArrayList;
import java.util.HashMap;

import lib.core.ActivityCore;
import lib.core.PageObject;
import lib.core.ViewCore;
import com.credit.korea.KoreaCredit.gcm.GCM;


public class MainActivity extends ActivityCore implements MemberInfo.LoginInfoDelegate {

    public static ArrayList<AlertView> alerts=new ArrayList<AlertView>();


    private GCM gcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isInit==true){
            return;

        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);





        DataFactory dataFactory=new DataFactory();
        FontFactory fontFactory=new FontFactory(this);
        SNSManager snsManager=new SNSManager(this);
        gcm=new GCM(this);


        //auto login
        MemberInfo minfo= MemberInfo.getInstence();

        if(minfo.getLoginState()==true){
            minfo.loginDelegate=this;
            minfo.loadLoginState();
        }else{
            DataFactory.getInstence().loadSMSNumber();
            //appStart();
        }


    }


    public void onLogin(boolean result){

        MemberInfo.getInstence().loginDelegate=null;
        if(result==false){
            MainActivity.getInstence().viewAlert("",R.string.msg_autologin_fail,null);
        }

        DataFactory.getInstence().loadSMSNumber();
    }
    public void onLogout(boolean result){

    }

    public void appStart() {


        removeIntroStart(2000);
        Bundle extras = getIntent().getExtras();
        int pid=HOME;
        if(extras != null){
            pid=extras.getInt("pageID",HOME);
        }

        if(currentView==null){
            PageObject pobj=new PageObject(pid);
            pobj.dr=0;
            changeView( pobj);
        }
        // GoogleAPI.getInstence().refreshResults();
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(SNSManager.getInstence()!=null){
            SNSManager.getInstence().onActivityResult(requestCode,resultCode, data) ;

        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(alerts.size()>0){

            AlertView alert= alerts.get(0);
            alerts.remove(0);
            alert.removeBox();
            return true;
        }



        return super.onKeyDown(keyCode,event);
    }




    public void callSms(CardObject card) {
        Intent intent = new Intent( Intent.ACTION_CALL );
        intent.setData( Uri.parse("tel:" + card.tel) );
        startActivity( intent );
    }


    public void callCounsel(final CardObject  card) {

        MemberInfo minfo= MemberInfo.getInstence();
        hideKeyBoard();
        if(minfo.getLoginState()==false){
            checkLoginPage();
            return;

        }

        AlertObject aInfo=new AlertObject();
        aInfo.iconID=R.drawable.icon_call;
        aInfo.isDimed=true;
        aInfo.titleStr="상담을 요청합니다";
        aInfo.subTitleStr=card.title+"\n(해당 신용카드사 또는 카드 설계사에게 귀하의 회신번호가 제공됩니다)";

        AlertView alertView = new AlertView(MainActivity.getInstence(),aInfo,new AlertView.AlertViewDelegate(){

            @Override
            public void onSelected(AlertView v, int selectIdx) {
                   if(selectIdx==0){
                       MyCardInfo.getInstence().requestCard(card,MyCardInfo.REQUEST_TYPE_COUNSEL);
                   }else{


                   }

            }
        });
        MainActivity.getInstence().body.addView(alertView);

    }

    public void prevChangeViewBack(PageObject info) {

        if(info.pageID == Config.PAGE_CARD ||  info.pageID == Config.PAGE_MYPAGE){
            info.info=null;

        }

    }

    public void changeView(PageObject info) {

        MemberInfo minfo= MemberInfo.getInstence();
        hideKeyBoard();
        if(minfo.getLoginState()==false){
          if(info.pageID>=400){
              checkLoginPage();
              return;

          }



        }


        super.changeView(info);
    }
    public void checkLoginPage(){

        MainActivity.getInstence().viewAlertSelect("",R.string.msg_no_login,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                PageObject pInfo;
                if(which==-1){

                    goLoginPage();

                }else{


                }

            }
        });
    }

    public void goLoginPage(){

        PageObject pInfo=new PageObject(Config.PAGE_MEMBER);
        pInfo.info=new HashMap<String,Object>();
        pInfo.info.put("pageIdx",3);
        changeView(pInfo);
    }
    @Override
    protected void creatView(){

        super.creatView();

        switch(currentPageObj.pageID){
            case HOME:
            case Config.PAGE_HOME:
                currentPageObj.isHome=true;
                currentView=new PageHome(this, currentPageObj);

                break;
            case Config.PAGE_MEMBER:
                currentView=new PageMember(this, currentPageObj);

                break;
            case Config.PAGE_CARD:
                currentView=new PageCard(this, currentPageObj);

                break;
            case Config.PAGE_MYPAGE:
                currentView=new PageMypage(this, currentPageObj);

                break;


        }


    }
    @Override
    protected void configurationChanged() {
        super.configurationChanged();
    }

    public ViewCore addPopup(PageObject info) {
        hideKeyBoard();
        MemberInfo minfo= MemberInfo.getInstence();
        if(minfo.getLoginState()==false){
            if(info.pageID>=3000){
                checkLoginPage();
                return null;

            }
        }
        return super.addPopup(info);
    }


    @Override
    protected ViewCore creatPopup(PageObject pinfo) {

        ViewCore pop=null;

        switch(pinfo.pageID){

            case Config.POPUP_WEB:
                pop=new PopupWeb(this, pinfo);
                break;
            case Config.POPUP_LIST_RESULT:
                pop=new PopupListResult(this, pinfo);
                break;
            case Config.POPUP_MODIFY:
                pop=new PopupModify(this, pinfo);
                break;

            case Config.POPUP_PAGE_MYSEARCH:
                pop=new PageMySearch(this, pinfo);
                break;
            case Config.POPUP_CONSUME_DEFAULT:
                pop=new PopupConsumeDefault(this, pinfo);
                break;
            case Config.POPUP_CONSUME_MODIFY:
                pop=new PopupConsumeModify(this, pinfo);
                break;
            case Config.POPUP_CONSUME_MODIFY_DETAIL:
                pop=new PopupConsumeBenefitModify(this, pinfo);
                break;

            case Config.POPUP_CARD_ADD:
                pop=new PopupAddCard(this, pinfo);
                break;
            case Config.POPUP_CARD_BOOK_DESC:
                pop=new PopupBookDesc(this, pinfo);
                break;
            case Config.POPUP_CARD_BOOK_ADDED:
                pop=new PopupBookAdded(this, pinfo);
                break;
            case Config.POPUP_CARD_LIST:
                pop=new PopupCardList(this, pinfo);
                break;
            case Config.POPUP_CARD_DETAIL:
                pop=new PopupDetailCard(this, pinfo);

                break;
            case Config.POPUP_CARD_COMPARE:
                pop=new PopupCardCompare(this, pinfo);

                break;
            case Config.POPUP_CARD_HIT:
                pop=new PopupHitList(this, pinfo);

                break;
            case Config.POPUP_CARD_FIND:
                pop=new PopupCardFind(this, pinfo);

                break;


        }

        return pop;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    protected void onPause() {
        super.onPause();

        if (gcm!=null) {
            gcm.onPause();
        }

        if(currentView!=null){
            currentView.onPause();

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (gcm!=null) {
            gcm.onResume();
        }

        if(currentView!=null){
            currentView.onResume();

        }

    }
}