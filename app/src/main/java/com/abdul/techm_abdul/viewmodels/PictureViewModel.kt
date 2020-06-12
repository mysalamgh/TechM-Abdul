package com.abdul.techm_abdul.viewmodels

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdul.techm_abdul.models.Picture
import com.abdul.techm_abdul.utilities.JSONParser
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class PictureViewModel : ViewModel() {

    var jsonParser: JSONParser = JSONParser()
    val urlGetPictures: String = "https://jsonplaceholder.typicode.com/photos"

    var pictureList: ArrayList<Picture> = ArrayList()
    private val userPictures = MutableLiveData<ArrayList<Picture>>()
    private val pictures: MutableLiveData<ArrayList<Picture>> by lazy {
        MutableLiveData<ArrayList<Picture>>().also {
            loadPictures()
        }
    }
    private val selectedPicture = MutableLiveData<Picture>()


    fun getPictures(): LiveData<ArrayList<Picture>> {
        return pictures
    }

    private fun loadPictures() {
        GetPictures().execute()
    }

    fun getUserPictures(albumId: Int): LiveData<ArrayList<Picture>> {
        val userPictureList: ArrayList<Picture> = ArrayList()
        userPictureList.clear()
        if (pictures.value != null) {
            for (pic in pictures.value!!) {
                if (pic.albumId == albumId) {
                    userPictureList.add(pic)
                }
            }
        }

        userPictures.value = userPictureList
        return userPictures
    }

    fun getSelectedPicture(): LiveData<Picture> {
        return selectedPicture
    }

    fun selectPicture(picture: Picture) {
        selectedPicture.value = picture
    }


    inner class GetPictures : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            pictureList.clear()
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

                        pictureList.add(
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
            pictures.value = pictureList
        }

    }

}