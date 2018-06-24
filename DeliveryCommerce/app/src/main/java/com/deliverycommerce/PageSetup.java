package com.deliverycommerce;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.deliverycommerce.UserInfo.UserInfoDelegate;
import com.deliverycommerce.delivery.DeliveryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lib.CommonUtil;
import lib.core.PageObject;
import lib.core.ViewCore;


@SuppressLint("NewApi")
public class PageSetup extends ViewCore  implements OnClickListener{



	private Button pushBtn,idelBtn;
	private ImageButton backBtn;
	private ArrayList<Button> idelA,provisionA;
    private SetupInfo setupInfo;

    public PageSetup(Context context, PageObject pageInfo)
	{
		super(context, pageInfo); 
	    View.inflate(context, R.layout.page_setting,this);

        idelA=new ArrayList<Button>();
        setupInfo=SetupInfo.getInstence();
        int selIdx=setupInfo.getIdelTime();

	    for(int i=0;i<4;++i){
	    	 int bid = this.getResources().getIdentifier( "_idel"+i, "id", mainActivity.getPackageName());
	    	 Button idel= (Button)findViewById(bid);
             idelA.add(idel);
             idel.setOnClickListener(this);
             if(selIdx==i){
                 if(setupInfo.getIdel()==true) {
                     idel.setSelected(true);
                 }else{
                     idel.setSelected(false);
                 }
             }else{
                 idel.setSelected(false);
             }

	     }
         provisionA=new ArrayList<Button>();
         for(int i=0;i<3;++i){
            int bid = this.getResources().getIdentifier( "_provision"+i, "id", mainActivity.getPackageName());
            Button provision= (Button)findViewById(bid);
             provisionA.add(provision);
             provision.setOnClickListener(this);

        }


	    backBtn=(ImageButton) findViewById(R.id._backBtn);
        pushBtn=(Button) findViewById(R.id._pushBtn);
        idelBtn=(Button) findViewById(R.id._idelBtn);

	    backBtn.setOnClickListener(this);
        pushBtn.setOnClickListener(this);
        idelBtn.setOnClickListener(this);



        pushBtn.setSelected(setupInfo.getPush());
        idelBtn.setSelected(setupInfo.getIdel());
	    
    }
	
	
	
	
	protected void doMovedInit() { 
	    super.doMovedInit();

	   
	} 

    protected void doRemove() { 
    	
    	super.doRemove();

    
    } 
    private void idelTimeChange(int idx)
    {
        for(int i=0;i<idelA.size();++i){
            if(idx==i){
                idelA.get(i).setSelected(true);
                setupInfo.registerIdelTime(idx);
                BeaconInfo.getInstence().setAdDelayTime();
            }else{

                idelA.get(i).setSelected(false);
            }

        }
    }
	 public void onClick(View v) {

         final int idx=idelA.indexOf(v);
        if(v==pushBtn || v==idelBtn || idx!=-1){

            if(SetupInfo.isBeaconSuport==false){
                mainActivity.viewAlert("",R.string.msg_androidvs_low,null);
                return;
            }

        }

			 
		if(v==pushBtn){
			if(pushBtn.isSelected()==true){

                pushBtn.setSelected(false);
            }else{
                pushBtn.setSelected(true);

            }
            setupInfo.registerPush(pushBtn.isSelected());
            return;

		}else if(v==idelBtn){
            if(idelBtn.isSelected()==true){
                 idelTimeChange(-1);
                 idelBtn.setSelected(false);
            }else{
                 idelTimeChange(setupInfo.getIdelTime());
                 idelBtn.setSelected(true);

            }
            setupInfo.registerIdel(idelBtn.isSelected());
            return;

        }else if(v==backBtn){
			mainActivity.changeViewBack();
            return;
		}


        if(idx!=-1){
            if(setupInfo.getIdel()==false){
                mainActivity.viewAlertSelect("", R.string.msg_setup_idelon, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {

                        if(which==-1){
                            idelBtn.setSelected(true);
                            setupInfo.registerIdel(idelBtn.isSelected());
                            idelTimeChange(idx);
                        }else{


                        }

                    }
                });
                return;

            }
            idelTimeChange(idx);



            return;
        }

         int pidx=provisionA.indexOf(v);
         if(pidx!=-1) {
             String pageUrl="";
             switch (idx) {
                 case 0:
                     pageUrl=Config.WEB_PAGE_PROVISION0;
                     break;
                 case 1:
                     pageUrl=Config.WEB_PAGE_PROVISION1;
                     break;
                 case 2:
                     pageUrl=Config.WEB_PAGE_PROVISION2;
                     break;

             }
             Button btn=provisionA.get(pidx);

             Map<String, Object> pinfo = new HashMap<String, Object>();
             pinfo.put("titleStr", btn.getText().toString());
             pinfo.put("pageUrl", pageUrl);
             PageObject pInfo = new PageObject(Config.POPUP_WEB_INFO);
             pInfo.dr = 1;
             pInfo.info = pinfo;
             MainActivity.getInstence().addPopup(pInfo);
         }
	} 
    
    
    
    
}
