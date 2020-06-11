package com.abdul.techm_abdul.viewmodels

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdul.techm_abdul.models.Address
import com.abdul.techm_abdul.models.Company
import com.abdul.techm_abdul.models.Geo
import com.abdul.techm_abdul.models.User
import com.abdul.techm_abdul.utilities.JSONParser
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class UserViewModel : ViewModel() {

    var userList: ArrayList<User> = ArrayList<User>()
    var jsonParser: JSONParser = JSONParser()

    val urlGetUsers: String = "https://jsonplaceholder.typicode.com/users"


    private val selectedUser = MutableLiveData<User>()
    private val users: MutableLiveData<ArrayList<User>> by lazy {
        MutableLiveData<ArrayList<User>>().also {
            loadUserList()
        }
    }

    fun getUsers(): LiveData<ArrayList<User>> {
        return users
    }

    fun selectUser(user: User) {
        selectedUser.value = user
    }

    fun getSelectedUser(): LiveData<User> {
        return selectedUser
    }

    private fun loadUserList() {
        // Do an asynchronous operation to fetch users.
        GetUsers().execute()
    }

    inner class GetUsers : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            userList.clear()
        }

        override fun doInBackground(vararg params: String?): String {

            // Building Parameters
            val httpParams = HashMap<String?, String?>()
            val json: JSONArray?

            // getting JSON string from URL
            json = jsonParser.makeHttpRequest(
                urlGetUsers,
                "GET",
                httpParams
            )

            if (json != null) {
                try {

                    for (i in 0 until json.length()) {
                        val userJSONObj: JSONObject = json.getJSONObject(i)

                        val addressJSONObj: JSONObject = userJSONObj.getJSONObject("address")
                        val geoJSONObj: JSONObject = addressJSONObj.getJSONObject("geo")
                        val userAddressGeo = Geo(
                            geoJSONObj.getDouble("lat"),
                            geoJSONObj.getDouble("lng")
                        )
                        val userAddress = Address(
                            addressJSONObj.getString("street"),
                            addressJSONObj.getString("suite"),
                            addressJSONObj.getString("city"),
                            addressJSONObj.getString("zipcode"),
                            userAddressGeo
                        )
                        val companyJSONObj: JSONObject = userJSONObj.getJSONObject("company")
                        val userCompany = Company(
                            companyJSONObj.getString("name"),
                            companyJSONObj.getString("catchPhrase"),
                            companyJSONObj.getString("bs")
                        )

                        userList.add(
                            User(
                                userJSONObj.getInt("id"),
                                userJSONObj.getString("name"),
                                userJSONObj.getString("username"),
                                userJSONObj.getString("email"),
                                userAddress,
                                userJSONObj.getString("phone"),
                                userJSONObj.getString("website"),
                                userCompany
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
            users.value = userList
        }

    }

}