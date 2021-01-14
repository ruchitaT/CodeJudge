package org.codejudge.application

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.IntentSender
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import org.codejudge.application.adapter.AdapterRestaurants
import org.codejudge.application.helper.ConfigHelper
import org.codejudge.application.model.BySearchRequest
import org.codejudge.application.model.BySearchResponse
import org.codejudge.application.model.BySearchResult
import org.codejudge.application.network.APIClient
import org.codejudge.application.network.ApiInterface
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var rv_searchResult:RecyclerView
    lateinit var tv_no_location:TextView
    lateinit var iv_close:ImageView
    lateinit var edit_search:EditText
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv_searchResult = findViewById(R.id.rv_searchResult)
        tv_no_location = findViewById(R.id.tv_no_location)
        iv_close = findViewById(R.id.iv_close)

        progressDialog = ProgressDialog(this@MainActivity)
        //progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Restaurants are loading, please wait")


        edit_search = findViewById(R.id.edit_search)
//        val editText2 = findViewById<EditText>(R.id.txtNumber2)
//        val addButton = findViewById<Button>(R.id.btnAdd);
//        val txtResult = findViewById<TextView>(R.id.txtResult)
        Log.i("API URL : ", ConfigHelper.getConfigValue(this, "api_url"));
        // finding the edit text

        // Setting On Click Listener
//        addButton.setOnClickListener {
//
//            // Getting the user input
//            val txtNumber1 : String = editText1.text.toString()
//            val txtNumber2 : String = editText2.text.toString()
//
//            if (txtNumber1 == null || txtNumber2 == null || txtNumber1.equals("") || txtNumber2.equals("")) {
//                txtResult.text = "NaN"
//            }
//            else {
//                var number1: Double = editText1.text.toString().toDouble()
//                var number2: Double = editText2.text.toString().toDouble()
//                var sum : Double = number1 + number2;
//                txtResult.text = sum.toString()
//            }
//        }
        iv_close.setOnClickListener {
            if(edit_search.text.isNotBlank()){
                edit_search.text.clear()
                getSearchList()
            }
        }

        edit_search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(s?.length!=0){
                    getListByKeyword(s.toString())
                }else{
                    getSearchList()
                }
            }

        })

        if(getGPSStatus(this)){
            getSearchList()
        }else{
            buildAlertMessageNoGps(this)
        }

    }

    fun getSearchList(){
        progressDialog.show()
        APIClient.getClient()?.create(ApiInterface::class.java)
                ?.getSearchData("47.6204,-122.3491","2500","restaurant","AIzaSyBnEVruXG2tdtbadZtqLwt9EmFI6XYEOV0")
                ?.enqueue(object : retrofit2.Callback<BySearchResponse> {
                    override fun onFailure(call: Call<BySearchResponse>, t: Throwable) {
                        Log.e("failed", t.message.toString())
                        progressDialog.dismiss()
                    }

                    override fun onResponse(
                            call: Call<BySearchResponse>,
                            response: Response<BySearchResponse>
                    ) {
                        progressDialog.dismiss()
                        try {
                            if(response.body()!=null){
                                tv_no_location.visibility = View.GONE
                                var list:ArrayList<BySearchResult> = response.body()?.results as ArrayList<BySearchResult>
                                if(!list.isNullOrEmpty()){
                                    Log.e("success", response.body().toString())
                                    val adapterRest = AdapterRestaurants(
                                        this@MainActivity,
                                        list
                                    )
                                    rv_searchResult.layoutManager =
                                        LinearLayoutManager(this@MainActivity)
                                    rv_searchResult.adapter = adapterRest
                                }else{
                                    tv_no_location.visibility = View.VISIBLE
                                }
                            }else{
                                tv_no_location.visibility = View.VISIBLE
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                            Log.e("error", e.printStackTrace().toString())
                        }
                    }
                })
    }

    fun getListByKeyword(keyword:String){
        progressDialog.show()
        APIClient.getClient()?.create(ApiInterface::class.java)
            ?.getSearchDataByKeyword("47.6204,-122.3491","2500","restaurant",keyword,"AIzaSyBnEVruXG2tdtbadZtqLwt9EmFI6XYEOV0")
            ?.enqueue(object : retrofit2.Callback<BySearchResponse> {
                override fun onFailure(call: Call<BySearchResponse>, t: Throwable) {
                    Log.e("failed", t.message.toString())
                    progressDialog.dismiss()
                }

                override fun onResponse(
                    call: Call<BySearchResponse>,
                    response: Response<BySearchResponse>
                ) {
                    progressDialog.dismiss()
                    try {
                        if(response.body()!=null){
                            tv_no_location.visibility = View.GONE
                            var list:ArrayList<BySearchResult> = response.body()?.results as ArrayList<BySearchResult>
                            if(!list.isNullOrEmpty()){
                                Log.e("success", response.body().toString())
                                val adapterRest = AdapterRestaurants(
                                    this@MainActivity,
                                    list
                                )
                                rv_searchResult.layoutManager =
                                    LinearLayoutManager(this@MainActivity)
                                rv_searchResult.adapter = adapterRest
                            }else{
                                tv_no_location.visibility = View.VISIBLE
                            }
                        }else{
                            tv_no_location.visibility = View.VISIBLE
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e("error", e.printStackTrace().toString())
                    }
                }
            })
    }

    fun getGPSStatus(context: Activity): Boolean {
        var allowedLocationProviders = Settings.System.getString(
            context.contentResolver,
            Settings.System.LOCATION_PROVIDERS_ALLOWED
        )
        if (allowedLocationProviders == null) {
            allowedLocationProviders = ""
        }
        return allowedLocationProviders.contains(LocationManager.GPS_PROVIDER)
    }


    fun buildAlertMessageNoGps(context: Activity) {
        val mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval((10 * 1000).toLong())
            .setFastestInterval((1 * 1000).toLong())
        val settingsBuilder = LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequest)
        settingsBuilder.setAlwaysShow(true)
        val result: Task<LocationSettingsResponse> = getSettingsClient(this)
            .checkLocationSettings(settingsBuilder.build())
        result.addOnCompleteListener(object : OnCompleteListener<LocationSettingsResponse?> {
            override fun onComplete(task: Task<LocationSettingsResponse?>) {
                try {
                    val response = task.getResult(ApiException::class.java)
                } catch (ex: ApiException) {
                    when (ex.getStatusCode()) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                            val resolvableApiException: ResolvableApiException =
                                ex as ResolvableApiException
                            resolvableApiException
                                .startResolutionForResult(
                                    this@MainActivity,
                                    14
                                )
                        } catch (e: IntentSender.SendIntentException) {
                            Log.e("error",e.stackTrace.toString())
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        }
                    }
                }
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==14 && resultCode == RESULT_OK){
            getSearchList()
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }

    }


}
