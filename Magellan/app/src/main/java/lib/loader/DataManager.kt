package lib.loader
import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList
import java.util.HashMap


class DataManager(var type: String) : DataLoaderDelegate
{
    var jsonDelegate: JsonManagerDelegate? = null
    var dataDelegate: DataManagerDelegate? = null
    private var loaderA: MutableMap<String, DataLoader> = HashMap()
    var returnType: String = RETURN_TYPE_TXT

    init
    {

    }

    fun setOnJsonDelegate(_jsonDelegate: JsonManagerDelegate)
    {
        jsonDelegate = _jsonDelegate
        returnType = RETURN_TYPE_JSON
    }

    fun setOnDataDelegate(_dataDelegate: DataManagerDelegate)
    {
        dataDelegate = _dataDelegate
        returnType = RETURN_TYPE_TXT

    }

    fun removeAllLoader()
    {
        val iter = loaderA.keys.iterator()
        while (iter.hasNext()) loaderA[iter.next()]?.cancel(false)
        loaderA!!.clear()
    }

    fun removeLoader(dataUrl: String?) {
        val loader = loaderA[dataUrl]
        if (loader != null) {
            loader.cancel(false)
            loaderA.remove(dataUrl)
        }
    }

    fun loadData(dataUrl: String)
    {
        removeLoader(dataUrl)
        val loader = DataLoader()
        loaderA[dataUrl] = loader
        loader.start(this, null, type, null, "", null, "application/x-www-form-urlencoded")
        loader.execute(dataUrl)
    }

    fun loadData(dataUrl: String, param: Map<String, String>)
    {
        removeLoader(dataUrl)
        val loader = DataLoader()
        loaderA!![dataUrl] = loader
        loader.start(this, param, type, null, "", null, "application/x-www-form-urlencoded")
        loader.execute(dataUrl)
    }

    fun loadData(dataUrl: String, file: FileObject)
    {
        removeLoader(dataUrl)
        val loader = DataLoader()
        loaderA!![dataUrl] = loader
        val files = ArrayList<FileObject>()
        files.add(file)
        loader.start(this, null, "POST", files, "", null, "form-data")
        loader.execute(dataUrl)
    }

    fun loadData(dataUrl: String, param: Map<String, String>, files: ArrayList<FileObject>)
    {
        removeLoader(dataUrl)
        val loader = DataLoader()
        loaderA!![dataUrl] = loader
        loader.start(this, param, "POST", files, "", null, "multipart/form-data")
        loader.execute(dataUrl)
    }

    fun loadData(dataUrl: String, param: Map<String, String>, files: ArrayList<FileObject>, boundary: String)
    {
        removeLoader(dataUrl)
        val loader = DataLoader()
        loaderA!![dataUrl] = loader
        loader.start(this, param, "POST", files, boundary, null, "multipart/form-data")
        loader.execute(dataUrl)
    }

    fun loadData(dataUrl: String, param: Map<String, String>, files: ArrayList<FileObject>, boundary: String, headers: Map<String, String>)
    {
        removeLoader(dataUrl)
        val loader = DataLoader()
        loaderA!![dataUrl] = loader
        loader.start(this, param, "POST", files, boundary, headers, "multipart/form-data")
        loader.execute(dataUrl)
    }

    fun loadData(dataUrl: String, param: Map<String, String>, files: ArrayList<FileObject>, boundary: String, headers: Map<String, String>, contentType: String)
    {
        removeLoader(dataUrl)
        val loader = DataLoader()
        loaderA!![dataUrl] = loader
        loader.start(this, param, "POST", files, boundary, headers, contentType)
        loader.execute(dataUrl)
    }

    fun destory()
    {
        removeAllLoader()
        dataDelegate = null
        jsonDelegate = null
    }

    override fun onCompleted(dataUrl: String?, result: String)
    {
        removeLoader(dataUrl)
        dataDelegate?.onDataCompleted(this, dataUrl, result)
        if (jsonDelegate == null) return
        try
        {
            Log.i(TAG, "result : $result")
            val responseJSON = JSONObject(result)
            jsonDelegate!!.onJsonCompleted(this, dataUrl, responseJSON)
        }
        catch (e: JSONException)
        {
            Log.e(TAG, "Error in responseJSON parser ", e)
            jsonDelegate!!.onJsonParseErr(this, dataUrl)

        }
    }

    override fun onLoadErr(dataUrl: String?)
    {
        removeLoader(dataUrl)
        dataDelegate?.onDataLoadErr(this, dataUrl)
        jsonDelegate?.onJsonLoadErr(this, dataUrl)
    }


    interface DataManagerDelegate
    {
        fun onDataCompleted(manager: DataManager, path: String?, result: String){}
        fun onDataLoadErr(manager: DataManager, path: String?){}
    }

    interface JsonManagerDelegate
    {
        fun onJsonCompleted(manager: DataManager, path: String?, result: JSONObject){}
        fun onJsonParseErr(manager: DataManager, path: String?){}
        fun onJsonLoadErr(manager: DataManager, path: String?){}
    }

    companion object
    {
        private val TAG = "dataManager"
        private val RETURN_TYPE_JSON = "JSON"
        private val RETURN_TYPE_TXT = "TXT"
    }
}


internal class DataLoader : AsyncTask<String, Int, String>()
{
    private var path: String? = null
    private var boundary = "gc0p4Jq0M2Yt08jU534c0p"
    private var delegate: DataLoaderDelegate? = null
    private var urlParam: Map<String, String>? = null
    private var headers: Map<String, String>? = null
    private var files: ArrayList<FileObject>? = null
    private var type: String? = null
    private var contentType: String? = null
    private var isMultipart = false

    fun start(_delegate: DataLoaderDelegate, param: Map<String, String>?, _type: String,
              _files: ArrayList<FileObject>?, _boundary: String,
              _headers: Map<String, String>?, _contentType: String)
    {
        path = ""
        delegate = _delegate
        urlParam = param
        contentType = _contentType
        if (contentType === "multipart/form-data") {
            isMultipart = true
        }

        headers = _headers
        if (_boundary == "" == false) boundary = _boundary
        files = _files
        type = _type
        Log.i(TAG, "DataLoader START $type mp:$isMultipart")
    }


    override fun doInBackground(vararg params: String): String?
    {
        Log.i(TAG, "BEGIN ParserThread")
        path = params[0]
        var uc: HttpURLConnection? = null
        var ism: InputStream? = null
        var responseStr = ""

        try {

            val text = URL(path!!)
            uc = text.openConnection() as HttpURLConnection

            uc.doInput = true
            if (type == "POST" || isMultipart == true) {
                uc.doOutput = true
            }
            uc.useCaches = false
            uc.requestMethod = type!!
            uc.connectTimeout = 10000  //
            uc.allowUserInteraction = true

            if (headers != null) {
                val keySet = headers!!.keys.toTypedArray()
                var key: String

                for (i in keySet.indices) {
                    key = keySet[i]
                    uc.setRequestProperty(key, headers!![key])
                    Log.i(TAG, "header = " + key + ":" + headers!![key])
                }

            }


            if (isMultipart == true) {

                val delimiter = "\r\n--$boundary\r\n"
                uc.setRequestProperty("Connection", "Keep-Alive")
                val ctype = "$contentType ;boundary=$boundary"
                uc.setRequestProperty("Content-Type", ctype)
                //uc.setRequestProperty("enctype", contentType);
                Log.i(TAG, "Content-Type =$ctype")
                val wr = DataOutputStream(BufferedOutputStream(
                        uc.outputStream))

                if (files != null)
                {
                    for (file in files!!)
                    {
                        Log.i(TAG, "file =" + file.key + " : " + file.name)
                        DataLoaderUtil.setFileBuffer(file, wr, delimiter)
                    }
                }
                if (urlParam != null)
                {
                    val write = DataLoaderUtil.getParamsString(urlParam!!, delimiter)
                    wr.writeUTF(write)
                    Log.i(TAG, "dataUrl=$path?$write")
                }
                else
                {
                    Log.i(TAG, "dataUrl=" + path!!)
                }
                wr.writeBytes("\r\n--$boundary--")
                wr.flush()
                wr.close()


            } else {

                uc.setRequestProperty("Content-type", contentType)

                if (urlParam != null) {

                    val sb = StringBuffer()
                    val keySet = urlParam!!.keys.toTypedArray()
                    var key: String
                    for (i in keySet.indices)
                    {
                        key = keySet[i]
                        if (i == keySet.size - 1)
                        {
                            sb.append(key).append("=").append(urlParam!![key])
                        }
                        else
                        {
                            sb.append(key).append("=").append(urlParam!![key]).append("&")
                        }
                    }

                    val wr = DataOutputStream(uc.outputStream)
                    val write = sb.toString()
                    wr.writeBytes(write)
                    wr.flush()
                    wr.close()
                    Log.i(TAG, "dataUrl=$path?$write")
                }
                else
                {
                    Log.i(TAG, "dataUrl=" + path!!)

                }
            }
            val responseCode = uc.responseCode
            Log.i(TAG, "responseCode =" + uc.responseMessage)
            if (responseCode == HttpURLConnection.HTTP_OK) ism = uc.inputStream
        }
        catch (e: Exception)
        {
            Log.e(TAG, "Error in parser run", e)
            return null
        }

        if (ism != null)
        {

            val baos = ByteArrayOutputStream()
            val byteBuffer = ByteArray(1024)
            var byteData: ByteArray? = null
            try
            {
                do
                {
                    var nLength = ism!!.read(byteBuffer, 0, byteBuffer.size)
                    baos.write(byteBuffer, 0, nLength)
                }
                while (nLength != -1)

            }
            catch (e: IOException)
            {
                Log.e(TAG, "Error in InputStream read ", e)
                return null
            }

            byteData = baos.toByteArray()
            responseStr = String(byteData!!)
            //Log.i(TAG, "response=" + responseStr);

        }
        uc?.disconnect()
        return responseStr
    }


    override fun onPostExecute(result: String?)
    {

        if (result == null) delegate?.onLoadErr(path) else delegate?.onCompleted(path, result)
        super.onPostExecute(result)
    }

    companion object
    {
        private val TAG = "dataLoader"
    }

}//package

internal interface DataLoaderDelegate
{
    fun onCompleted(path: String?, result: String)
    fun onLoadErr(path: String?)
}
