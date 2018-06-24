package lib;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Aes
{
    public static final String AES_ECB="ECB";
    public static final String AES_CBC="CBC";
    public static String encodeAesString(String key){
        String code="";

        int a=(int)Math.round(8*Math.random())+1;
        int b=(int)Math.round(8*Math.random())+1;
        int c=(int)Math.round(8*Math.random())+1;

        int d=a*b*c;
        int e=a+b+c;
        String f=String.valueOf(c)+String.valueOf(b)+String.valueOf(a);

        code=a+key+b+key+c+key+d+key+e+key+f;

        return code;
    }
    public static boolean decodeAesString(String code,String key){

        String[] codes=code.split(key);
        if(codes.length!=6){
            return false;
        }

        int a=Integer.parseInt(codes[0]);
        int b=Integer.parseInt(codes[1]);
        int c=Integer.parseInt(codes[2]);
        int d=Integer.parseInt(codes[3]);
        int e=Integer.parseInt(codes[4]);
        String f=codes[5];

        if((a*b*c)!=d){
            return false;
        }
        if((a+b+c)!=e){
            return false;
        }

        String ff=String.valueOf(c)+String.valueOf(b)+String.valueOf(a);
        if(ff.equals(f)==false){
            return false;
        }
        return true;
    }
    public static String aesEncrypt(String sKey, String sText, String type){

        byte[] encrypted;

        switch (type){
            case AES_CBC:
                encrypted=aesEncryptCbc(sKey, sText);
                break;
            case AES_ECB:
            default:
                encrypted=aesEncryptEcb(sKey, sText);
                break;

        }

        try {
            Log.i("","base64: "+new String(encrypted, "UTF-8"));
        } catch (UnsupportedEncodingException e) {

        }


        return Base64.encodeToString(encrypted, 0);
    }
    public static String aesDecrypt(String sKey, String sText, String type){


        byte[] encrypted= Base64.decode(sText,0);

        switch (type){
            case AES_CBC:
                encrypted=aesDecryptCbc(sKey, encrypted);
                break;
            case AES_ECB:
            default:
                encrypted=aesDecryptEcb(sKey, encrypted);
                break;

        }
        String returnValue="";

        try {
            returnValue=new String(encrypted, "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }

        return returnValue;
    }


    public static byte[] aesEncryptEcb(String sKey, String sText) {
        byte[] key = null;
        byte[] text = null;
        byte[] encrypted = null;
        final int AES_KEY_SIZE_128 = 128;

        try {
            // UTF-8
            key = sKey.getBytes("UTF-8");

            // Key size 맞춤 (128bit, 16byte)
            key = Arrays.copyOf(key, AES_KEY_SIZE_128 / 8);

            // UTF-8
            text = sText.getBytes("UTF-8");

            // AES/EBC/PKCS5Padding
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
            encrypted = cipher.doFinal(text);
        } catch (Exception e) {
            encrypted = null;
            e.printStackTrace();
        }

        return encrypted;
    }

    public static byte[] aesDecryptEcb(String sKey, byte[] encrypted) {
        byte[] key = null;
        byte[] decrypted = null;
        final int AES_KEY_SIZE_128 = 128;

        try {
            // UTF-8
            key = sKey.getBytes("UTF-8");

            // Key size 맞춤 (128bit, 16byte)
            key = Arrays.copyOf(key, AES_KEY_SIZE_128 / 8);

            // AES/EBC/PKCS5Padding
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
            decrypted = cipher.doFinal(encrypted);
        } catch (Exception e) {
            decrypted = null;
            e.printStackTrace();
        }

        return decrypted;
    }

    public static byte[] aesEncryptCbc(String sKey, String sText) {
        return aesEncryptCbc(sKey, sText, "");
    }

    public static byte[] aesDecryptCbc(String sKey, byte[] encrypted) {
        return aesDecryptCbc(sKey, encrypted, "");
    }

    public static byte[] aesEncryptCbc(String sKey, String sText, String sInitVector) {
        byte[] key = null;
        byte[] text = null;
        byte[] iv = null;
        byte[] encrypted = null;
        final int AES_KEY_SIZE_128 = 128;

        try {
            // UTF-8
            key = sKey.getBytes("UTF-8");

            // Key size 맞춤 (128bit, 16byte)
            key = Arrays.copyOf(key, AES_KEY_SIZE_128 / 8);

            // UTF-8
            text = sText.getBytes("UTF-8");

            if (sInitVector != null) {
                // UTF-8
                iv = sInitVector.getBytes("UTF-8");

                // Key size 맞춤 (128bit, 16byte)
                iv = Arrays.copyOf(iv, AES_KEY_SIZE_128 / 8);

                // AES/EBC/PKCS5Padding
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                IvParameterSpec ips = new IvParameterSpec(iv);
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), ips);
                encrypted = cipher.doFinal(text);
            } else {
                // AES/EBC/PKCS5Padding
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
                encrypted = cipher.doFinal(text);
            }
        } catch (Exception e) {
            encrypted = null;
            e.printStackTrace();
        }

        return encrypted;
    }

    public static byte[] aesDecryptCbc(String sKey, byte[] encrypted, String sInitVector) {
        byte[] key = null;
        byte[] iv = null;
        byte[] decrypted = null;
        final int AES_KEY_SIZE_128 = 128;

        try {
            // UTF-8
            key = sKey.getBytes("UTF-8");

            // Key size 맞춤 (128bit, 16byte)
            key = Arrays.copyOf(key, AES_KEY_SIZE_128 / 8);

            if (sInitVector != null) {
                // UTF-8
                iv = sInitVector.getBytes("UTF-8");

                // Key size 맞춤 (128bit, 16byte)
                iv = Arrays.copyOf(iv, AES_KEY_SIZE_128 / 8);

                // AES/EBC/PKCS5Padding
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                IvParameterSpec ips = new IvParameterSpec(iv);
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), ips);
                decrypted = cipher.doFinal(encrypted);
            } else {
                // AES/EBC/PKCS5Padding
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
                decrypted = cipher.doFinal(encrypted);
            }
        } catch (Exception e) {
            decrypted = null;
            e.printStackTrace();
        }

        return decrypted;
    }

    public static String toHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < b.length; i++) {
            sb.append(String.format("%02X", b[i]));
            if ((i + 1) % 16 == 0 && ((i + 1) != b.length)) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }
} 
