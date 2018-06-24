package lib.loader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.os.AsyncTask
import android.util.Log
import lib.GraphicUtil
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSession

class ImageLoader(var delegate: ImageLoaderDelegate?) : AsyncTask<String, Int, ByteArray>()
{

    var image_path: String = ""
    var image: Bitmap? = null
    var size: Point? = null

    fun removeLoader()
    {
        delegate = null
        image = null
        this.cancel(true)
    }

    fun loadImg(imgPath: String)
    {
        clearMemory()
        if (imgPath == "")
        {
            delegate?.onImageLoadCompleted(this, null)
            return
        }
        image_path = imgPath
        val imageIdx = imgPaths.indexOf(imgPath)
        //Log.i("","cash size :"+imgCashs.size());
        if (imageIdx != -1)
        {
            image = imgCashs[imageIdx]
            val copyBitmap = image!!.copy(Bitmap.Config.ARGB_8888, true)
            delegate?.onImageLoadCompleted(this, copyBitmap)
        }
        else
        {
            this.execute(imgPath)
        }
    }

    override fun doInBackground(vararg params: String): ByteArray?
    {

        image_path = params[0]
        Log.i(TAG, "image_path = $image_path")
        var result: ByteArray? = null

        val conn = makeConnection(image_path, "GET")
        if (conn == null)
        {
            Log.d(TAG, "conn null")
            return null
        }
        try
        {
            conn.connect()
            if (conn.responseCode == HttpURLConnection.HTTP_OK)
            {
                val ism = conn.inputStream
                val os = ByteArrayOutputStream()
                val buff = ByteArray(2048)
                do
                {
                    var len = ism.read(buff)
                    os.write(buff, 0, len)
                }
                while (len  != -1)
                result = os.toByteArray()
                ism.close()
                os.close()
            }
            else
            {
                Log.i(TAG, "conn.getResponseCode=" + conn.responseCode)
            }
            conn.disconnect()
        }
        catch (e: Exception) {
            Log.e(TAG, "conn err", e)
        }

        if (result == null) Log.d(TAG, "result null") else Log.d(TAG, "result ok")
        return result
    }


    override fun onPostExecute(result: ByteArray)
    {

        //Log.i(TAG, "result="+result);
        try {
            var bais: ByteArrayInputStream? = ByteArrayInputStream(result)
            image = BitmapFactory.decodeStream(bais)
            if (size != null)
            {
                val w: Int
                val h: Int
                if (size!!.x == -1 && size!!.y != -1)
                {
                    h = size!!.y
                    w = Math.floor((size!!.y.toFloat() * image!!.width.toFloat() / image!!.height.toFloat()).toDouble()).toInt()

                }
                else if (size!!.y == -1 && size!!.x != -1)
                {
                    w = size!!.x
                    h = Math.floor((size!!.x.toFloat() * image!!.height.toFloat() / image!!.width.toFloat()).toDouble()).toInt()

                } else
                {
                    w = size!!.x
                    h = size!!.y
                }
                image = GraphicUtil.resizeImage(image!!, w, h)

            }

            bais?.close()
            bais = null
            imgPaths.add(image_path)
            imgCashs.add(image!!)

        }
        catch (e: Exception)
        {
            image = null
        }


        if (delegate != null)
        {
            var copyBitmap: Bitmap? = null
            try
            {
                copyBitmap = image!!.copy(Bitmap.Config.ARGB_8888, true)
            }
            catch (e: Exception)
            {
                copyBitmap = null
            }

            delegate!!.onImageLoadCompleted(this, copyBitmap)

        }
    }

    private fun makeConnection(uri: String, method: String): HttpURLConnection?
    {
        try
        {
            val url = URL(uri)
            var conn: HttpURLConnection? = null
            if (url.protocol.toLowerCase() == "https")
            {
                val https = url.openConnection() as HttpsURLConnection
                https.hostnameVerifier = DO_NOT_VERIFY
                conn = https
            }
            else
            {
                conn = url.openConnection() as HttpURLConnection
            }
            conn.allowUserInteraction = true
            conn.connectTimeout = 10000
            conn.requestMethod = method
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded")
            conn.setRequestProperty("Accept", "*/*")
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; i-NavFourF; .NET CLR 1.1.4322)")
            conn.useCaches = true
            return conn
        }
        catch (e: Exception)
        {
            Log.e(TAG, "makeConnection err", e)
            return null
        }

    }

    interface ImageLoaderDelegate
    {
        fun onImageLoadCompleted(loader: ImageLoader, image: Bitmap?){}
    }

    companion object
    {
        var MAX_CASH_NUM = 20

        private val imgPaths = ArrayList<String>()
        private val imgCashs = ArrayList<Bitmap>()
        private val TAG = "ImageLoader"

        fun removeAllCashs()
        {
            var btm: Bitmap?
            for (img in imgCashs) img?.recycle()
            imgPaths.clear()
            imgCashs.clear()
        }

        fun clearMemory()
        {
            var btm: Bitmap?
            var size = imgPaths.size
            if (size > MAX_CASH_NUM)
            {
                size = size - MAX_CASH_NUM
            }
            else
            {
                return
            }
            for (i in size - 1 downTo 0)
            {
                imgCashs[i]?.recycle()
                imgPaths.removeAt(i)
                imgCashs.removeAt(i)
            }
        }

        private val DO_NOT_VERIFY = HostnameVerifier { hostname, session -> true }
    }

}
