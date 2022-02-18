package com.vsn.utilslibrary

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.tasks.Task

class GpsUtils {

    companion object {
         fun showLocationPrompt(context: Context){
            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
             var response:LocationSettingsResponse? = null

            val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(context).checkLocationSettings(builder.build())
            result.addOnCompleteListener { task ->
                try {
                    response = task.getResult(ApiException::class.java)

                    Log.e("locPromp","response gps : "+response!!.locationSettingsStates!!.isGpsUsable)
                    Log.e("locPromp","response locaiton: "+response!!.locationSettingsStates!!.isLocationUsable)
                    Log.e("locPromp","response network : "+response!!.locationSettingsStates!!.isNetworkLocationUsable)

                } catch (exception: ApiException) {
                    when (exception.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            try {
                                val resolvable: ResolvableApiException = exception as ResolvableApiException
                                resolvable.startResolutionForResult(context as Activity, LocationRequest.PRIORITY_HIGH_ACCURACY)
                            } catch (e: IntentSender.SendIntentException) {
                                Log.e("locPromp","Exception IS $e")
                            } catch (e: ClassCastException) {
                                Log.e("locPromp","Exception CCE $e")
                            }
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            Log.e("locPromp","unavailable")
                        }
                    }
                }
            }
        }
    }

}