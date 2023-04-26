package com.example.notificationlistener

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class StringViewModel(application: Application) : AndroidViewModel(application) {
    private val response = MutableLiveData<String>()
    fun postString(string: String?) {
        val queue = Volley.newRequestQueue(getApplication())
        val jsonObject = JSONObject()
        try {
            jsonObject.put("body", string)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val request = JsonObjectRequest(
            Request.Method.POST,
            "http://192.168.0.105:8000/blog",
            jsonObject,
            { response ->
                println(response.toString())
                this@StringViewModel.response.value = response.toString()
            }
        ) { error -> response.value = error.toString() }
        queue.add(request)
    }

    fun getResponse(): LiveData<String> {
        return response
    }
}
