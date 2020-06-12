package com.abdul.techm_abdul.utils

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import java.io.*
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.net.URLEncoder
import java.util.*

class JSONParser {

    var charset = "UTF-8"
    var conn: HttpURLConnection? = null
    var wr: DataOutputStream? = null
    var result: StringBuilder? = null
    var urlObj: URL? = null
    var jObj: JSONArray? = null
    var sbParams: StringBuilder? = null
    private lateinit var paramsString: String

    fun makeHttpRequest(
        url: String?, method: String,
        params: HashMap<String?, String?>
    ): JSONArray? {
        var url = url
        sbParams = StringBuilder()
        var i = 0
        for (key in params.keys) {
            try {
                if (i != 0) {
                    sbParams!!.append("&")
                }
                sbParams!!.append(key).append("=")
                    .append(URLEncoder.encode(params[key], charset))
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
            i++
        }
        if (method == "POST") {
            // request method is POST
            try {
                urlObj = URL(url)
                conn = urlObj!!.openConnection() as HttpURLConnection
                conn!!.doOutput = true
                conn!!.requestMethod = "POST"
                conn!!.setRequestProperty("Accept-Charset", charset)
                conn!!.connectTimeout = 60000
                conn!!.readTimeout = 60000
                conn!!.connect()
                paramsString = sbParams.toString()
                wr = DataOutputStream(conn!!.outputStream)
                wr!!.writeBytes(paramsString)
                wr!!.flush()
                wr!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (method == "GET") {
            // request method is GET
            if (sbParams!!.length != 0) {
                url += "?" + sbParams.toString()
            }
            try {
                urlObj = URL(url)
                conn = urlObj!!.openConnection() as HttpURLConnection
                conn!!.doOutput = false
                conn!!.requestMethod = "GET"
                conn!!.setRequestProperty("Accept-Charset", charset)
                conn!!.connectTimeout = 60000
                conn!!.readTimeout = 60000
                try {
                    conn!!.connect()
                } catch (e: SocketTimeoutException) {
                    e.printStackTrace()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        Log.d("JSON Params", params.toString())
        try {
            //Receive the response from the server
            val `in`: InputStream = BufferedInputStream(conn!!.inputStream)
            val reader =
                BufferedReader(InputStreamReader(`in`))
            result = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                result!!.append(line)
            }
            Log.d("JSON Result", result.toString())
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("JSON Parser", "Error parsing data $e")
        } catch (e: NullPointerException) {
            e.printStackTrace()
            Log.e("JSON Parser", "Error parsing data $e")
        }
        conn!!.disconnect()

        // try parse the string to a JSON object
        try {
            jObj = JSONArray(result.toString())
        } catch (e: JSONException) {
            Log.e("JSON Parser", "Error parsing data $e")
        } catch (e: NullPointerException) {
            Log.e("JSON Parser", "Error parsing data $e")
        }

        // return JSON Object
        return jObj
    }


}