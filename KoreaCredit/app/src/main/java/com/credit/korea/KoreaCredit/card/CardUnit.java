package com.credit.korea.KoreaCredit.card;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CardUnit
{




    public String seq,name,imgPath;
    public boolean isMine;

    public CardUnit() {

        seq="";
        name="";
        imgPath="";
        isMine=false;

    }

    public void setData(String str){

        String[] sA=str.split("\\|");
        if(sA.length>0){
            seq=sA[0];
        }
        if(sA.length>1){
            imgPath=getImgPath(sA[1]);
        }
        if(sA.length>2){
            name=sA[2];
        }

    }

    private String getImgPath(String path){

        String sUrl="";
        String eUrl="";
        sUrl = path.substring(0, path.lastIndexOf("/")+1);
        eUrl = path.substring(path.lastIndexOf("/")+1, path.length()); // 한글과 공백을 포함한 부분
        try {
            eUrl = URLEncoder.encode(eUrl, "EUC-KR").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {

        }
        path = sUrl+ eUrl;
        return path;
    }







}






