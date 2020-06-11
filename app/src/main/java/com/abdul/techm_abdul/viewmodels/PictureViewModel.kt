package com.abdul.techm_abdul.viewmodels

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdul.techm_abdul.models.Picture
import com.abdul.techm_abdul.utilities.JSONParser
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class PictureViewModel : ViewModel() {

    var jsonParser: JSONParser = JSONParser()

    var pictures: ArrayList<Picture> = ArrayList()
    private val userPictures = MutableLiveData<ArrayList<Picture>>()

    val urlGetPictures: String = "https://jsonplaceholder.typicode.com/photos"

    init {
        loadPictureList()
    }

    fun getUserPictures(albumId: Int): LiveData<ArrayList<Picture>> {
        var userPictureList: ArrayList<Picture> = ArrayList()
        userPictureList.clear()
        for (pic in pictures) {
            if (pic.albumId == albumId) {
                userPictureList.add(pic)
            }
        }
        userPictures.value = userPictureList
        return userPictures
    }

    private fun loadPictureList() {
        GetPictures().execute()
    }

    inner class  GetPictures : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            pictures.clear()
        }

        override fun doInBackground(vararg params: String?): String {

            // Building Parameters
            val httpParams = HashMap<String?, String?>()
            val json: JSONArray?

            // getting JSON string from URL
            json = jsonParser.makeHttpRequest(
                urlGetPictures,
                "GET",
                httpParams
            )

            if (json != null) {
                try {

                    for (i in 0 until json.length()) {
                        val pictureJSONObj: JSONObject = json.getJSONObject(i)

                        pictures.add(
                            Picture(
                                pictureJSONObj.getInt("albumId"),
                                pictureJSONObj.getInt("id"),
                                pictureJSONObj.getString("title"),
                                pictureJSONObj.getString("url"),
                                pictureJSONObj.getString("thumbnailUrl")
                            )
                        )
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
//            pictures.value = pictureList
        }

    }

}