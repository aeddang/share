package lib.datamanager;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;


public class DataLoaderUtil
{
    private static final String TAG = "DataLoaderUtil";

    public static String setValue(String key, String value) {
        return "Content-Disposition: form-data; name=\"" + key + "\"\r\n"
                + value;
    }


    public static String setFile(String key, String fileName) {
        return "Content-Disposition: form-data; name=\"" + key
                + "\"; filename=\"" + fileName + "\"\r\n";
    }

    public static void setFileBuffer(FileObject file,final DataOutputStream wr,String delimiter) {


        FileInputStream in=null;
        try {
            in = new FileInputStream(file.file);
        } catch (FileNotFoundException e) {
            Log.i(TAG, "DataLoader Error FileNotFoundException ");
            return;
        }
        // 파일 복사 작업 시작

        try {

            int bufferSize = 0;
            wr.writeBytes(delimiter); // 반드시 작성해야 한다.
            String cond = setFile(file.key,file.name);
            wr.writeBytes(cond);
            Log.i(TAG, "cond : " + cond);
            wr.writeBytes("\r\n");
            bufferSize = in.available();
            byte[] buffer = new byte[bufferSize];
            Log.i(TAG, "buffer : " + buffer.length);
            int l;
            while ((l = in.read(buffer)) != -1) {
                wr.write(buffer, 0, l);
            }
            in.close();
        } catch (IOException e) {
            Log.i(TAG, "DataLoader Error File write fail");
        }


    }

    public static String getParamsString(Map<String,String> urlParam,String delimiter) {
        StringBuffer sb = new StringBuffer();
        Object[] keySet = urlParam.keySet().toArray();
        String key;

        for (int i = 0; i < keySet.length; ++i) {
            key = (String) keySet[i];
            sb.append(delimiter);
            sb.append(setValue(key, urlParam.get(key)));

        }
        return sb.toString();
    }

    
    
	
}








