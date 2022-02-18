package com.vsn.utilslibrary

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.*
import android.provider.Settings
import androidx.core.app.ActivityCompat
import java.util.*
import kotlin.math.roundToInt

class LocationUtils(context: Context, requireFine: Boolean, passive: Boolean, interval: Long, requireNewLocation: Boolean) {

    class Pointer : Parcelable {
        val latitude: Double
        val longitude: Double

        constructor(lat: Double, lng: Double) {
            latitude = lat
            longitude = lng
        }

        override fun toString(): String = "$latitude, $longitude"

        override fun describeContents(): Int = 0

        override fun writeToParcel(out: Parcel, flags: Int) {
            out.writeDouble(latitude)
            out.writeDouble(longitude)
        }

        private constructor(`in`: Parcel) {
            latitude = `in`.readDouble()
            longitude = `in`.readDouble()
        }

        companion object {
            @JvmField val CREATOR: Parcelable.Creator<Pointer?> = object : Parcelable.Creator<Pointer?> {
                override fun createFromParcel(`in`: Parcel): Pointer = Pointer(`in`)

                override fun newArray(size: Int): Array<Pointer?> = arrayOfNulls(size)
            }
        }
        
    }

    interface Listener {
        fun onPositionChanged()
    }

    private val mLocationManager: LocationManager
    private val mRequireFine: Boolean
    private val mPassive: Boolean
    private val mInterval: Long
    private val mRequireNewLocation: Boolean
    private var mBlurRadius = 0
    private var mLocationListener: LocationListener? = null
    private var mPosition: Location? = null
    private var mListener: Listener? = null

    // ALL CONSTRUCTOR
    constructor(context: Context) : this(context, false) {
        Companion.context = context
    }

    constructor(context: Context, requireFine: Boolean) : this(context, requireFine, false) {
        Companion.context = context
    }

    constructor(context: Context, requireFine: Boolean, passive: Boolean) : this(context, requireFine, passive, INTERVAL_DEFAULT) {
        Companion.context = context
    }

    constructor(context: Context, requireFine: Boolean, passive: Boolean, interval: Long) : this(context, requireFine, passive, interval, false) {
        Companion.context = context
    }

    fun setListener(listener: Listener?) { mListener = listener }

    // IS LOCATION ENABLE OR NOT
    fun hasLocationEnabled(): Boolean = hasLocationEnabled(getProviderName())

    private fun hasLocationEnabled(providerName: String): Boolean {
        return try {
            mLocationManager.isProviderEnabled(providerName)
        } catch (e: Exception) {
            false
        }
    }

    // UPDATE LOCATION
    fun beginUpdates() {
        if (mLocationListener != null) {
            endUpdates()
        }
        if (!mRequireNewLocation) {
            mPosition = getLastPosition()
        }
        mLocationListener = createLocationListener()
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) { return }
        mLocationManager.requestLocationUpdates(getProviderName(), mInterval, 90f, mLocationListener!!)
    }

    // STOP LOCATION UPDATE
    private fun endUpdates() {
        if (mLocationListener != null) {
            mLocationManager.removeUpdates(mLocationListener!!)
            mLocationListener = null
        }
    }

    // BLUR LOCATION
    private fun blurTheLocation(originalLocation: Location): Location {
        return if (mBlurRadius <= 0) { originalLocation } else {
            val newLocation = Location(originalLocation)
            val blurMeterLong = calculateRandomOffset(mBlurRadius) / SQUARE_ROOT_TWO
            val blurMeterLat = calculateRandomOffset(mBlurRadius) / SQUARE_ROOT_TWO
            newLocation.longitude = newLocation.longitude + meterToLongitude(blurMeterLong, newLocation.latitude)
            newLocation.latitude = newLocation.latitude + getMeterToLatitude(blurMeterLat)
            newLocation
        }
    }

    // GET CURRENT POSITION
    fun getPosition(): Pointer? = if (mPosition == null) { null } else {
            val position = blurTheLocation(mPosition!!)
            Pointer(position.latitude, position.longitude)
        }

    // GET LATITUDE
    fun getLatitude(): Double = if (mPosition == null) { 0.0 } else { 
            val position = blurTheLocation(mPosition!!)
            position.latitude
        }
    
    // GET LONGITUDE
    fun getLongitude(): Double = if (mPosition == null) { 0.0 } else {
            val position = blurTheLocation(mPosition!!)
            position.longitude
    }

    // GET CURRENT LOCATION TIMESTAMP
    fun getTimestampInMilliseconds(): Long = if (mPosition == null) { 0L } else { mPosition!!.time }
    
    // GET ELAPSE TIME
    fun getterElapsedTimeInNanoseconds(): Long = if (mPosition == null) { 0L } else {
            if (Build.VERSION.SDK_INT >= 17) {
                mPosition!!.elapsedRealtimeNanos
            } else {
                (SystemClock.elapsedRealtime() + getTimestampInMilliseconds() - System.currentTimeMillis()) * 1000000
            }
    }

    // GET CURRENT SPEED
    fun getSpeed(): Float = if (mPosition == null) { 0.0f } else {
            mPosition!!.speed
    }
    
    // GET CURRENT ALTITUDE
    fun getAltitude(): Double = if (mPosition == null) { 0.0 } else { mPosition!!.altitude}
    
    // SET BLUR RADIUS
    fun setBlurRadius(blurRadius: Int) { mBlurRadius = blurRadius }

    private fun createLocationListener(): LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                mPosition = location
                cachePosition()
                if (mListener != null) {
                    mListener!!.onPositionChanged()
                }
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
    
    // GET PROVIDER NAME
    private fun getProviderName(requireFine: Boolean = mRequireFine): String = if (requireFine) { if (mPassive) { PROVIDER_FINE_PASSIVE } else { PROVIDER_FINE } } else {
            if (hasLocationEnabled(PROVIDER_COARSE)) {
                if (mPassive) { throw RuntimeException("No provider  for coarse location") } else { PROVIDER_COARSE } } else {
                if (hasLocationEnabled(PROVIDER_FINE) || hasLocationEnabled(PROVIDER_FINE_PASSIVE)) { getProviderName(true) } else { PROVIDER_COARSE }
            }}

    // GET LAST POSITION
    private fun getLastPosition(): Location? = if (mCachedPosition != null) { mCachedPosition } else {
            try {
                if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) { }
                mLocationManager.getLastKnownLocation(getProviderName())
            } catch (e: Exception) {
                null
            }
    }

    // CACHE POSITION
    private fun cachePosition() {
        if (mPosition != null) {
            mCachedPosition = mPosition
        }
    }

    companion object {
        var context: Context? = null
        private const val PROVIDER_COARSE = LocationManager.NETWORK_PROVIDER
        private const val PROVIDER_FINE = LocationManager.GPS_PROVIDER
        private const val PROVIDER_FINE_PASSIVE = LocationManager.PASSIVE_PROVIDER
        private const val INTERVAL_DEFAULT = (10 * 60 * 1000).toLong()
        private const val KILOMETER_TO_METER = 1000.0f
        private const val LATITUDE_TO_KILOMETER = 111.133f
        private const val LONGITUDE_TO_KILOMETER_AT_ZERO_LATITUDE = 111.320f
        private val mRandom = Random()
        private val SQUARE_ROOT_TWO = Math.sqrt(2.0)
        private var mCachedPosition: Location? = null

        private fun calculateRandomOffset(radius: Int): Int = mRandom.nextInt((radius + 1) * 2) - radius
        
        fun openSettings(context: Context) { context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }

        // GET LATITUDE IN KILOMETER
        fun getLatitudeToKilometer(latitude: Double): Double = latitude * LATITUDE_TO_KILOMETER

        // GET KILOMETE IN LATITUDE
        fun getKilometerToLatitude(kilometer: Double): Double = kilometer / getLatitudeToKilometer(1.0)

        // GET LATITUDE IN METER
        fun getLatitudeToMeter(latitude: Double): Double = getLatitudeToKilometer(latitude) * KILOMETER_TO_METER

        // GET METER IN LATITUDE
        fun getMeterToLatitude(meter: Double): Double = meter / getLatitudeToMeter(1.0)
        
        // GET LONGITUDE IN KILOMETER
        fun getLongitudeToKilometer(longitude: Double, latitude: Double): Double = longitude * LONGITUDE_TO_KILOMETER_AT_ZERO_LATITUDE * Math.cos(Math.toRadians(latitude))

        // GET KILOMETER IN LONGITUDE
        fun getKilometerToLongitude(kilometer: Double, latitude: Double): Double = kilometer / getLongitudeToKilometer(1.0, latitude)

        // GET LONGITUDE IN METER
        fun getLongitudeToMeter(longitude: Double, latitude: Double): Double = getLongitudeToKilometer(longitude, latitude) * KILOMETER_TO_METER

        // GET METER IN LONGITUDE
        fun meterToLongitude(meter: Double, latitude: Double): Double {
            return meter / getLongitudeToMeter(1.0, latitude)
        }

        // DISTANCE USING POINTER
        fun getDistanceUsingPointers(start: Pointer, end: Pointer): String {
            return getDistanceUsingLatLng(start.latitude, start.longitude, end.latitude, end.longitude)
        }

       // DISTANCE USING LAT,LNG
        fun getDistanceUsingLatLng(startLatitude: Double, startLongitude: Double, endLatitude: Double, endLongitude: Double): String {
           var totalDistance = "0"
            val results = FloatArray(3)
            Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results)
           totalDistance = if(results[0]>=1000) { "${((results[0] / 1000.0) * 100.0).roundToInt() /100.0} km" } else { "${(results[0] * 100.0).roundToInt() /100.0} m" }
            return totalDistance
        }
    }

    init {
        mLocationManager = context.applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mRequireFine = requireFine
        mPassive = passive
        mInterval = interval
        mRequireNewLocation = requireNewLocation
        Companion.context = context
        if (!mRequireNewLocation) {
            mPosition = getLastPosition()
            cachePosition()
        }
    }
}