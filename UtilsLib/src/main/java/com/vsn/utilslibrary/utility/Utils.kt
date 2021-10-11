@file:Suppress("DEPRECATION")
package com.vsn.utilslibrary.utility

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.provider.Settings
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.text.InputType
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Base64
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.text.bold
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.model.LatLng
import com.vsn.utilslibrary.R
import java.io.ByteArrayOutputStream
import java.lang.StringBuilder
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable

import android.graphics.drawable.Drawable
import android.provider.AlarmClock
import android.telephony.PhoneNumberUtils
import android.text.TextUtils
import android.view.*
import com.google.android.gms.tasks.Task
import java.lang.NullPointerException


class Utils {

        companion object {

            private var toast: Toast? = null
            private var snack: Snackbar? = null
            private var cal: Calendar = Calendar.getInstance()
            var customDialog: Dialog? = null
            var dialog: Dialog? = null
            var progressDialog:ProgressDialog? = null

            // TOAST =========================================================================================================================
            fun getShortToast(context: Context, message: String) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
                toast!!.show()
            }

            fun getLongToast(context: Context, message: String) {
                toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
                toast!!.show()
            }

            fun getCustomToast(
                context: Context,
                message: String,
                duration: Int,
                horizontalMargin: Float,
                verticalMargin: Float,
                gravity: Int,
                xOffSet: Int,
                yOffSet: Int
            ) {
                toast = Toast.makeText(context, message, duration)
                toast!!.setMargin(horizontalMargin, verticalMargin)
                toast!!.setGravity(gravity, xOffSet, yOffSet)
                toast!!.show()
            }

            fun cancelToast() {
                toast!!.view!!.visibility = View.GONE
                toast!!.cancel()
            }

            @SuppressLint("SetTextI18n", "UseCompatLoadingForColorStateLists")
            fun getErrorToast(context: Context, message: String) {
                val inflater:LayoutInflater = (context as Activity).layoutInflater
                val layout = inflater.inflate(
                    R.layout.custom_toast,
                    context.findViewById(R.id.toastLayout)
                )
                val toastLayout :LinearLayout = layout.findViewById(R.id.toastLayout)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    toastLayout.backgroundTintList = context.resources.getColorStateList(R.color.error)
                }
                val image : ImageView = layout.findViewById(R.id.toastImg)
                image.setImageResource(R.drawable.tick_wrong)
                val text : TextView = layout.findViewById(R.id.toastLabel)
                text.text = message
                val toast = Toast(context)
                toast.duration = Toast.LENGTH_LONG
                toast.view = layout
                toast.show()
            }

            @SuppressLint("SetTextI18n", "UseCompatLoadingForColorStateLists")
            fun getSuccessToast(context: Context, message: String) {
                val inflater:LayoutInflater = (context as Activity).layoutInflater
                val layout = inflater.inflate(
                    R.layout.custom_toast,
                    context.findViewById(R.id.toastLayout)
                )
                val toastLayout :LinearLayout = layout.findViewById(R.id.toastLayout)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    toastLayout.backgroundTintList = context.resources.getColorStateList(R.color.success)
                }
                val image : ImageView = layout.findViewById(R.id.toastImg)
                image.setImageResource(R.drawable.tick_right)
                val text : TextView = layout.findViewById(R.id.toastLabel)
                text.text = message
                val toast = Toast(context)
                toast.duration = Toast.LENGTH_LONG
                toast.view = layout
                toast.show()
            }

            @SuppressLint("SetTextI18n", "UseCompatLoadingForColorStateLists")
            fun getInfoToast(context: Context, message: String) {
                val inflater:LayoutInflater = (context as Activity).layoutInflater
                val layout = inflater.inflate(
                    R.layout.custom_toast,
                    context.findViewById(R.id.toastLayout)
                )
                val toastLayout :LinearLayout = layout.findViewById(R.id.toastLayout)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    toastLayout.backgroundTintList = context.resources.getColorStateList(R.color.info)
                }
                val image : ImageView = layout.findViewById(R.id.toastImg)
                image.setImageResource(R.drawable.tick_info)
                val text : TextView = layout.findViewById(R.id.toastLabel)
                text.text = message
                val toast = Toast(context)
                toast.duration = Toast.LENGTH_LONG
                toast.view = layout
                toast.show()
            }

            @SuppressLint("SetTextI18n", "UseCompatLoadingForColorStateLists")
            fun getWarningToast(context: Context, message: String) {
                val inflater:LayoutInflater = (context as Activity).layoutInflater
                val layout = inflater.inflate(
                    R.layout.custom_toast,
                    context.findViewById(R.id.toastLayout)
                )
                val toastLayout :LinearLayout = layout.findViewById(R.id.toastLayout)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    toastLayout.backgroundTintList = context.resources.getColorStateList(R.color.warning)
                }
                val image : ImageView = layout.findViewById(R.id.toastImg)
                image.setImageResource(R.drawable.tick_warn)
                val text : TextView = layout.findViewById(R.id.toastLabel)
                text.text = message
                val toast = Toast(context)
                toast.duration = Toast.LENGTH_LONG
                toast.view = layout
                toast.show()
            }

            //SNACK BAR=========================================================================================================================
            fun getShortSnack(view: View, message: String) {
                snack = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                snack!!.show()
            }

            fun getLongSnack(view: View, message: String) {
                snack = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                snack!!.show()
            }

            fun getSnackWithAction(
                view: View,
                message: String,
                actionText: String,
                listener: View.OnClickListener
            ) {
                snack = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                snack!!.setAction(actionText, listener)
                snack!!.show()
            }

            fun getCustomSnack(
                view: View,
                message: String,
                duration: Int,
                gravity: Int,
                actionText: String,
                actionTextColor: Int,
                backgroundTintColor: Int,
                listener: View.OnClickListener
            ) {

                val snackView = snack!!.view
                val param :FrameLayout.LayoutParams = snackView.layoutParams as FrameLayout.LayoutParams
                param.gravity = gravity
                snack = Snackbar.make(view, message, duration)
                snack!!.setAction(actionText, listener)
                snack!!.setActionTextColor(actionTextColor)
                snack!!.setBackgroundTint(backgroundTintColor)
                snack!!.show()
            }

            fun getSnackOnTop(view: View, message: String) {
                snack = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                val viewSnack = snack!!.view
                val params = viewSnack.layoutParams as FrameLayout.LayoutParams
                params.gravity = Gravity.TOP
                snack!!.show()
            }

            fun getSnackOnCenter(view: View, message: String) {
                snack = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                val viewSnack = snack!!.view
                val params = viewSnack.layoutParams as FrameLayout.LayoutParams
                params.gravity = Gravity.CENTER
                snack!!.show()
            }

            fun cancelSnack(){
                snack!!.dismiss()
            }

            // PROGRESS DIALOG =========================================================================================================================
            fun getProgressDialogCircular(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean
            ) {
                progressDialog = ProgressDialog(context)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.show()
            }

            fun getProgressDialogCircularDefaultDark(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.show()
            }

            fun getProgressDialogCircularHoloLight(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.show()
            }

            fun getProgressDialogCircularHoloDark(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.show()
            }

            fun getProgressDialogCircularTraditional(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_TRADITIONAL)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.show()
            }

            // circular end.

            fun getProgressDialogCircularPositive(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(DialogInterface.BUTTON_POSITIVE, positiveText, listener)
                progressDialog!!.show()
            }

            fun getProgressDialogCircularPositiveDefaultDark(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(DialogInterface.BUTTON_POSITIVE, positiveText, listener)
                progressDialog!!.show()
            }

            fun getProgressDialogCircularPositiveHoloLight(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(DialogInterface.BUTTON_POSITIVE, positiveText, listener)
                progressDialog!!.show()
            }

            fun getProgressDialogCircularPositiveHoloDark(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(DialogInterface.BUTTON_POSITIVE, positiveText, listener)
                progressDialog!!.show()
            }

            fun getProgressDialogCircularPositiveTraditional(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_TRADITIONAL)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(DialogInterface.BUTTON_POSITIVE, positiveText, listener)
                progressDialog!!.show()
            }

            // circular positive end.

            fun getProgressDialogCircularNegative(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                negativeText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEGATIVE, negativeText, listener)
                progressDialog!!.show()
            }

            fun getProgressDialogCircularNegativeDefaultDark(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                negativeText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEGATIVE, negativeText, listener)
                progressDialog!!.show()
            }

            fun getProgressDialogCircularNegativeHoloLight(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                negativeText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEGATIVE, negativeText, listener)
                progressDialog!!.show()
            }

            fun getProgressDialogCircularNegativeHoloDark(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                negativeText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEGATIVE, negativeText, listener)
                progressDialog!!.show()
            }

            fun getProgressDialogCircularNegativeTraditional(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                negativeText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_TRADITIONAL)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEGATIVE, negativeText, listener)
                progressDialog!!.show()
            }

//            circular negative end.

            fun getProgressDialogCircularNeutral(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                neutralText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEUTRAL, neutralText, listener)
                progressDialog!!.show()
            }

            fun getProgressDialogCircularNeutralDefaultDark(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                neutralText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEUTRAL, neutralText, listener)
                progressDialog!!.show()
            }

            fun getProgressDialogCircularNeutralHoloLight(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                neutralText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEUTRAL, neutralText, listener)
                progressDialog!!.show()
            }

            fun getProgressDialogCircularNeutralHoloDark(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                neutralText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEUTRAL, neutralText, listener)
                progressDialog!!.show()
            }

            fun getProgressDialogCircularNeutralTraditional(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                neutralText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_TRADITIONAL)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEUTRAL, neutralText, listener)
                progressDialog!!.show()
            }

            // circular neutral end.

            fun getProgressDialogCircularPositiveNegative(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                negativeText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeListener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_POSITIVE,
                    positiveText,
                    positiveListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEGATIVE,
                    negativeText,
                    negativeListener
                )
                progressDialog!!.show()
            }

            fun getProgressDialogCircularPositiveNegativeDefaultDark(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                negativeText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeListener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_POSITIVE,
                    positiveText,
                    positiveListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEGATIVE,
                    negativeText,
                    negativeListener
                )
                progressDialog!!.show()
            }

            fun getProgressDialogCircularPositiveNegativeHoloLight(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                negativeText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeListener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_POSITIVE,
                    positiveText,
                    positiveListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEGATIVE,
                    negativeText,
                    negativeListener
                )
                progressDialog!!.show()
            }

            fun getProgressDialogCircularPositiveNegativeHoloDark(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                negativeText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeListener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_POSITIVE,
                    positiveText,
                    positiveListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEGATIVE,
                    negativeText,
                    negativeListener
                )
                progressDialog!!.show()
            }

            fun getProgressDialogCircularPositiveNegativeTraditional(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                negativeText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeListener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_TRADITIONAL)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_POSITIVE,
                    positiveText,
                    positiveListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEGATIVE,
                    negativeText,
                    negativeListener
                )
                progressDialog!!.show()
            }

            // circular positive negative end.

            fun getProgressDialogCircularPositiveNeutral(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                negativeText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeListener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_POSITIVE,
                    positiveText,
                    positiveListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEUTRAL,
                    negativeText,
                    negativeListener
                )
                progressDialog!!.show()
            }

            fun getProgressDialogCircularPositiveNeutralDefaultDark(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                negativeText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeListener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_POSITIVE,
                    positiveText,
                    positiveListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEUTRAL,
                    negativeText,
                    negativeListener
                )
                progressDialog!!.show()
            }

            fun getProgressDialogCircularPositiveNeutralHoloLight(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                negativeText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeListener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_POSITIVE,
                    positiveText,
                    positiveListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEUTRAL,
                    negativeText,
                    negativeListener
                )
                progressDialog!!.show()
            }

            fun getProgressDialogCircularPositiveNeutralHoloDark(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                negativeText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeListener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_POSITIVE,
                    positiveText,
                    positiveListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEUTRAL,
                    negativeText,
                    negativeListener
                )
                progressDialog!!.show()
            }

            fun getProgressDialogCircularPositiveNeutralTraditional(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                negativeText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeListener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_TRADITIONAL)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_POSITIVE,
                    positiveText,
                    positiveListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEUTRAL,
                    negativeText,
                    negativeListener
                )
                progressDialog!!.show()
            }

            // circular positive neutral end.

            fun getProgressDialogCircularPositiveNegativeNeutral(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                negativeText: String,
                neutralText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeListener: DialogInterface.OnClickListener,
                neutralListner: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_POSITIVE,
                    positiveText,
                    positiveListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEGATIVE,
                    negativeText,
                    negativeListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEUTRAL,
                    neutralText,
                    neutralListner
                )
                progressDialog!!.show()
            }

            fun getProgressDialogCircularPositiveNegativeNeutralDefaultDark(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                negativeText: String,
                neutralText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeListener: DialogInterface.OnClickListener,
                neutralListner: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_POSITIVE,
                    positiveText,
                    positiveListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEGATIVE,
                    negativeText,
                    negativeListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEUTRAL,
                    neutralText,
                    neutralListner
                )
                progressDialog!!.show()
            }

            fun getProgressDialogCircularPositiveNegativeNeutralHoloLight(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                negativeText: String,
                neutralText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeListener: DialogInterface.OnClickListener,
                neutralListner: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_POSITIVE,
                    positiveText,
                    positiveListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEGATIVE,
                    negativeText,
                    negativeListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEUTRAL,
                    neutralText,
                    neutralListner
                )
                progressDialog!!.show()
            }

            fun getProgressDialogCircularPositiveNegativeNeutralHoloDark(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                negativeText: String,
                neutralText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeListener: DialogInterface.OnClickListener,
                neutralListner: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_POSITIVE,
                    positiveText,
                    positiveListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEGATIVE,
                    negativeText,
                    negativeListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEUTRAL,
                    neutralText,
                    neutralListner
                )
                progressDialog!!.show()
            }

            fun getProgressDialogCircularPositiveNegativeNeutralTraditional(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                positiveText: String,
                negativeText: String,
                neutralText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeListener: DialogInterface.OnClickListener,
                neutralListner: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_TRADITIONAL)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_POSITIVE,
                    positiveText,
                    positiveListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEGATIVE,
                    negativeText,
                    negativeListener
                )
                progressDialog!!.setButton(
                    DialogInterface.BUTTON_NEUTRAL,
                    neutralText,
                    neutralListner
                )
                progressDialog!!.show()
            }

            // circular positive negative neutral end.

            fun getProgressDialogHorizontal(
                context: Context,
                title: String,
                message: String,
                maxValue: Int,
                incrementValue: Int,
                sleep: Long,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean
            ) {
                progressDialog = ProgressDialog(context)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.max = maxValue
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                val handler:Handler = object : Handler() {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        progressDialog!!.incrementProgressBy(incrementValue)
                    }
                }

                Thread {
                    try {
                        while (progressDialog!!.progress <= progressDialog!!.max) {
                            Thread.sleep(sleep)
                            handler.sendMessage(handler.obtainMessage())
                            if(progressDialog!!.progress == progressDialog!!.max) {
                                progressDialog!!.dismiss()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()

                progressDialog!!.show()
            }

            fun getProgressDialogHorizontalDefaultDark(
                context: Context,
                title: String,
                message: String,
                maxValue: Int,
                incrementValue: Int,
                sleep: Long,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.max = maxValue
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                val handler:Handler = object : Handler() {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        progressDialog!!.incrementProgressBy(incrementValue)
                    }
                }

                Thread {
                    try {
                        while (progressDialog!!.progress <= progressDialog!!.max) {
                            Thread.sleep(sleep)
                            handler.sendMessage(handler.obtainMessage())
                            if(progressDialog!!.progress == progressDialog!!.max) {
                                progressDialog!!.dismiss()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()

                progressDialog!!.show()
            }

            fun getProgressDialogHorizontalHoloLight(
                context: Context,
                title: String,
                message: String,
                maxValue: Int,
                incrementValue: Int,
                sleep: Long,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.max = maxValue
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                val handler:Handler = object : Handler() {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        progressDialog!!.incrementProgressBy(incrementValue)
                    }
                }

                Thread {
                    try {
                        while (progressDialog!!.progress <= progressDialog!!.max) {
                            Thread.sleep(sleep)
                            handler.sendMessage(handler.obtainMessage())
                            if(progressDialog!!.progress == progressDialog!!.max) {
                                progressDialog!!.dismiss()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()

                progressDialog!!.show()
            }

            fun getProgressDialogHorizontalHoloDark(
                context: Context,
                title: String,
                message: String,
                maxValue: Int,
                incrementValue: Int,
                sleep: Long,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.max = maxValue
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                val handler:Handler = object : Handler() {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        progressDialog!!.incrementProgressBy(incrementValue)
                    }
                }

                Thread {
                    try {
                        while (progressDialog!!.progress <= progressDialog!!.max) {
                            Thread.sleep(sleep)
                            handler.sendMessage(handler.obtainMessage())
                            if(progressDialog!!.progress == progressDialog!!.max) {
                                progressDialog!!.dismiss()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()

                progressDialog!!.show()
            }

            fun getProgressDialogHorizontalTraditional(
                context: Context,
                title: String,
                message: String,
                maxValue: Int,
                incrementValue: Int,
                sleep: Long,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_TRADITIONAL)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.max = maxValue
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                val handler:Handler = object : Handler() {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        progressDialog!!.incrementProgressBy(incrementValue)
                    }
                }

                Thread {
                    try {
                        while (progressDialog!!.progress <= progressDialog!!.max) {
                            Thread.sleep(sleep)
                            handler.sendMessage(handler.obtainMessage())
                            if(progressDialog!!.progress == progressDialog!!.max) {
                                progressDialog!!.dismiss()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()

                progressDialog!!.show()
            }

            // horizontal end.

            fun getProgressDialogHorizontalNegative(
                context: Context,
                title: String,
                message: String,
                maxValue: Int,
                incrementValue: Int,
                sleep: Long,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                negativeText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.max = maxValue
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEGATIVE, negativeText, listener)
                val handler:Handler = object : Handler() {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        progressDialog!!.incrementProgressBy(incrementValue)
                    }
                }

                Thread {
                    try {
                        while (progressDialog!!.progress <= progressDialog!!.max) {
                            Thread.sleep(sleep)
                            handler.sendMessage(handler.obtainMessage())
                            if(progressDialog!!.progress == progressDialog!!.max) {
                                progressDialog!!.dismiss()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()

                progressDialog!!.show()
            }

            fun getProgressDialogHorizontalNegativeDefaultDark(
                context: Context,
                title: String,
                message: String,
                maxValue: Int,
                incrementValue: Int,
                sleep: Long,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                negativeText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.max = maxValue
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEGATIVE, negativeText, listener)
                val handler:Handler = object : Handler() {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        progressDialog!!.incrementProgressBy(incrementValue)
                    }
                }

                Thread {
                    try {
                        while (progressDialog!!.progress <= progressDialog!!.max) {
                            Thread.sleep(sleep)
                            handler.sendMessage(handler.obtainMessage())
                            if(progressDialog!!.progress == progressDialog!!.max) {
                                progressDialog!!.dismiss()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()

                progressDialog!!.show()
            }

            fun getProgressDialogHorizontalNegativeHoloLight(
                context: Context,
                title: String,
                message: String,
                maxValue: Int,
                incrementValue: Int,
                sleep: Long,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                negativeText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.max = maxValue
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEGATIVE, negativeText, listener)
                val handler:Handler = object : Handler() {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        progressDialog!!.incrementProgressBy(incrementValue)
                    }
                }

                Thread {
                    try {
                        while (progressDialog!!.progress <= progressDialog!!.max) {
                            Thread.sleep(sleep)
                            handler.sendMessage(handler.obtainMessage())
                            if(progressDialog!!.progress == progressDialog!!.max) {
                                progressDialog!!.dismiss()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()

                progressDialog!!.show()
            }

            fun getProgressDialogHorizontalNegativeHoloDark(
                context: Context,
                title: String,
                message: String,
                maxValue: Int,
                incrementValue: Int,
                sleep: Long,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                negativeText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.max = maxValue
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEGATIVE, negativeText, listener)
                val handler:Handler = object : Handler() {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        progressDialog!!.incrementProgressBy(incrementValue)
                    }
                }

                Thread {
                    try {
                        while (progressDialog!!.progress <= progressDialog!!.max) {
                            Thread.sleep(sleep)
                            handler.sendMessage(handler.obtainMessage())
                            if(progressDialog!!.progress == progressDialog!!.max) {
                                progressDialog!!.dismiss()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()

                progressDialog!!.show()
            }

            fun getProgressDialogHorizontalNegativeTraditional(
                context: Context,
                title: String,
                message: String,
                maxValue: Int,
                incrementValue: Int,
                sleep: Long,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                negativeText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_TRADITIONAL)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.max = maxValue
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEGATIVE, negativeText, listener)
                val handler:Handler = object : Handler() {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        progressDialog!!.incrementProgressBy(incrementValue)
                    }
                }

                Thread {
                    try {
                        while (progressDialog!!.progress <= progressDialog!!.max) {
                            Thread.sleep(sleep)
                            handler.sendMessage(handler.obtainMessage())
                            if(progressDialog!!.progress == progressDialog!!.max) {
                                progressDialog!!.dismiss()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()

                progressDialog!!.show()
            }

            // horizontal negative end.

            fun getProgressDialogHorizontalNeutral(
                context: Context,
                title: String,
                message: String,
                maxValue: Int,
                incrementValue: Int,
                sleep: Long,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                neutralText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.max = maxValue
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEUTRAL, neutralText, listener)
                val handler:Handler = object : Handler() {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        progressDialog!!.incrementProgressBy(incrementValue)
                    }
                }

                Thread {
                    try {
                        while (progressDialog!!.progress <= progressDialog!!.max) {
                            Thread.sleep(sleep)
                            handler.sendMessage(handler.obtainMessage())
                            if(progressDialog!!.progress == progressDialog!!.max) {
                                progressDialog!!.dismiss()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()

                progressDialog!!.show()
            }

            fun getProgressDialogHorizontalNeutralDefaultDark(
                context: Context,
                title: String,
                message: String,
                maxValue: Int,
                incrementValue: Int,
                sleep: Long,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                neutralText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.max = maxValue
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEUTRAL, neutralText, listener)
                val handler:Handler = object : Handler() {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        progressDialog!!.incrementProgressBy(incrementValue)
                    }
                }

                Thread {
                    try {
                        while (progressDialog!!.progress <= progressDialog!!.max) {
                            Thread.sleep(sleep)
                            handler.sendMessage(handler.obtainMessage())
                            if(progressDialog!!.progress == progressDialog!!.max) {
                                progressDialog!!.dismiss()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()

                progressDialog!!.show()
            }

            fun getProgressDialogHorizontalNeutralHoloLight(
                context: Context,
                title: String,
                message: String,
                maxValue: Int,
                incrementValue: Int,
                sleep: Long,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                neutralText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.max = maxValue
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEUTRAL, neutralText, listener)
                val handler:Handler = object : Handler() {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        progressDialog!!.incrementProgressBy(incrementValue)
                    }
                }

                Thread {
                    try {
                        while (progressDialog!!.progress <= progressDialog!!.max) {
                            Thread.sleep(sleep)
                            handler.sendMessage(handler.obtainMessage())
                            if(progressDialog!!.progress == progressDialog!!.max) {
                                progressDialog!!.dismiss()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()

                progressDialog!!.show()
            }

            fun getProgressDialogHorizontalNeutralHoloDark(
                context: Context,
                title: String,
                message: String,
                maxValue: Int,
                incrementValue: Int,
                sleep: Long,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                neutralText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_HOLO_DARK)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.max = maxValue
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEUTRAL, neutralText, listener)
                val handler:Handler = object : Handler() {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        progressDialog!!.incrementProgressBy(incrementValue)
                    }
                }

                Thread {
                    try {
                        while (progressDialog!!.progress <= progressDialog!!.max) {
                            Thread.sleep(sleep)
                            handler.sendMessage(handler.obtainMessage())
                            if(progressDialog!!.progress == progressDialog!!.max) {
                                progressDialog!!.dismiss()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()

                progressDialog!!.show()
            }

            fun getProgressDialogHorizontalNeutralTraditional(
                context: Context,
                title: String,
                message: String,
                maxValue: Int,
                incrementValue: Int,
                sleep: Long,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                neutralText: String,
                listener: DialogInterface.OnClickListener
            ) {
                progressDialog = ProgressDialog(context, AlertDialog.THEME_TRADITIONAL)
                progressDialog!!.setTitle(title)
                progressDialog!!.setMessage(message)
                progressDialog!!.max = maxValue
                progressDialog!!.setCancelable(isCancel)
                progressDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                progressDialog!!.setButton(DialogInterface.BUTTON_NEUTRAL, neutralText, listener)
                val handler:Handler = object : Handler() {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        progressDialog!!.incrementProgressBy(incrementValue)
                    }
                }

                Thread {
                    try {
                        while (progressDialog!!.progress <= progressDialog!!.max) {
                            Thread.sleep(sleep)
                            handler.sendMessage(handler.obtainMessage())
                            if(progressDialog!!.progress == progressDialog!!.max) {
                                progressDialog!!.dismiss()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()

                progressDialog!!.show()
            }

            // horizontal neutral end.

            fun cancelProgressDialog() {
                if (progressDialog != null && progressDialog!!.isShowing) {
                    progressDialog!!.dismiss()
                }
            }

            // DIALOG =============================================================================================================================
            fun getDialog(
                context: Context,
                colorCode: String,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean
            ) {
                dialog = Dialog(context, R.style.ThemeDialogCustom)
                dialog!!.setContentView(R.layout.dialog_progress)
                val prog = dialog!!.findViewById<ProgressBar>(R.id.progressBar)
                prog.indeterminateDrawable.setColorFilter(
                    Color.parseColor(colorCode),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                )
                dialog!!.setCancelable(isCancel)
                dialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                dialog!!.show()
            }

            fun cancelDialog() {
                if (dialog != null && dialog!!.isShowing) {
                    dialog!!.dismiss()
                }
            }

            // CUSTOM DIALOG =============================================================================================================================

            fun getCustomDialogPositive(
                context: Context,
                title: String,
                titleColorCode: String,
                message: String,
                messageColorCode: String,
                layoutBackground: Int,
                positiveText: String,
                positiveTextColor: String,
                positiveBackground: Int,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                listener: View.OnClickListener
            ) {
                customDialog = Dialog(context)
                customDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                customDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                customDialog!!.setCancelable(isCancel)
                customDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                customDialog!!.setContentView(R.layout.dialog_alert)
                val dialogLayout = customDialog!!.findViewById<LinearLayout>(R.id.alertRoot)
                dialogLayout.setBackgroundResource(layoutBackground)

                customDialog!!.findViewById<ImageView>(R.id.alertIcon).visibility = View.GONE

                val dialogTitle = customDialog!!.findViewById<TextView>(R.id.alertTitle)
                dialogTitle.text = title
                dialogTitle.setTextColor(Color.parseColor(titleColorCode))

                val dialogMessage = customDialog!!.findViewById<TextView>(R.id.alertMsg)
                dialogMessage.text = message
                dialogMessage.setTextColor(Color.parseColor(messageColorCode))

                val dialogPositive = customDialog!!.findViewById<TextView>(R.id.alertPositive)
                dialogPositive.text = positiveText
                dialogPositive.setTextColor(Color.parseColor(positiveTextColor))
                dialogPositive.setBackgroundResource(positiveBackground)
                dialogPositive.setOnClickListener(listener)

                customDialog!!.findViewById<TextView>(R.id.alertNegative).visibility = View.GONE

                customDialog!!.show()
            }

            fun getCustomDialogPositiveWithIcon(
                context: Context,
                title: String,
                titleColorCode: String,
                message: String,
                messageColorCode: String,
                icon: Int,
                layoutBackground: Int,
                positiveText: String,
                positiveTextColor: String,
                positiveBackground: Int,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                listener: View.OnClickListener
            ) {
                customDialog = Dialog(context)
                customDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                customDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                customDialog!!.setCancelable(isCancel)
                customDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                customDialog!!.setContentView(R.layout.dialog_alert)
                val dialogLayout = customDialog!!.findViewById<LinearLayout>(R.id.alertRoot)
                dialogLayout.setBackgroundResource(layoutBackground)

                val dialogIcon = customDialog!!.findViewById<ImageView>(R.id.alertIcon)
                dialogIcon.setImageResource(icon)

                val dialogTitle = customDialog!!.findViewById<TextView>(R.id.alertTitle)
                dialogTitle.text = title
                dialogTitle.setTextColor(Color.parseColor(titleColorCode))

                val dialogMessage = customDialog!!.findViewById<TextView>(R.id.alertMsg)
                dialogMessage.text = message
                dialogMessage.setTextColor(Color.parseColor(messageColorCode))

                val dialogPositive = customDialog!!.findViewById<TextView>(R.id.alertPositive)
                dialogPositive.text = positiveText
                dialogPositive.setTextColor(Color.parseColor(positiveTextColor))
                dialogPositive.setBackgroundResource(positiveBackground)
                dialogPositive.setOnClickListener(listener)

                customDialog!!.findViewById<TextView>(R.id.alertNegative).visibility = View.GONE

                customDialog!!.show()
            }

            fun getCustomDialogPositiveNegative(
                context: Context,
                title: String,
                titleColorCode: String,
                message: String,
                messageColorCode: String,
                layoutBackground: Int,
                positiveText: String,
                positiveTextColor: String,
                positiveBackground: Int,
                negativeText: String,
                negativeTextColor: String,
                negativeBackground: Int,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                listener: View.OnClickListener
            ) {
                customDialog = Dialog(context)
                customDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                customDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                customDialog!!.setCancelable(isCancel)
                customDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                customDialog!!.setContentView(R.layout.dialog_alert)
                val dialogLayout = customDialog!!.findViewById<LinearLayout>(R.id.alertRoot)
                dialogLayout.setBackgroundResource(layoutBackground)

                customDialog!!.findViewById<ImageView>(R.id.alertIcon).visibility = View.GONE

                val dialogTitle = customDialog!!.findViewById<TextView>(R.id.alertTitle)
                dialogTitle.text = title
                dialogTitle.setTextColor(Color.parseColor(titleColorCode))

                val dialogMessage = customDialog!!.findViewById<TextView>(R.id.alertMsg)
                dialogMessage.text = message
                dialogMessage.setTextColor(Color.parseColor(messageColorCode))

                val dialogPositive = customDialog!!.findViewById<TextView>(R.id.alertPositive)
                dialogPositive.text = positiveText
                dialogPositive.setTextColor(Color.parseColor(positiveTextColor))
                dialogPositive.setBackgroundResource(positiveBackground)
                dialogPositive.setOnClickListener(listener)

                val dialogNegative = customDialog!!.findViewById<TextView>(R.id.alertNegative)
                dialogNegative.text = negativeText
                dialogNegative.setTextColor(Color.parseColor(negativeTextColor))
                dialogNegative.setBackgroundResource(negativeBackground)
                dialogNegative.setOnClickListener { customDialog!!.dismiss() }

                customDialog!!.show()
            }

            fun getCustomDialogPositiveNegativeWithIcon(
                context: Context,
                title: String,
                titleColorCode: String,
                message: String,
                messageColorCode: String,
                icon: Int,
                layoutBackground: Int,
                positiveText: String,
                positiveTextColor: String,
                positiveBackground: Int,
                negativeText: String,
                negativeTextColor: String,
                negativeBackground: Int,
                isCancel: Boolean,
                isCanceledOnTouchOutside: Boolean,
                listener: View.OnClickListener
            ) {
                customDialog = Dialog(context)
                customDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                customDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                customDialog!!.setCancelable(isCancel)
                customDialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
                customDialog!!.setContentView(R.layout.dialog_alert)
                val dialogLayout = customDialog!!.findViewById<LinearLayout>(R.id.alertRoot)
                dialogLayout.setBackgroundResource(layoutBackground)

                val dialogIcon = customDialog!!.findViewById<ImageView>(R.id.alertIcon)
                dialogIcon.setImageResource(icon)

                val dialogTitle = customDialog!!.findViewById<TextView>(R.id.alertTitle)
                dialogTitle.text = title
                dialogTitle.setTextColor(Color.parseColor(titleColorCode))

                val dialogMessage = customDialog!!.findViewById<TextView>(R.id.alertMsg)
                dialogMessage.text = message
                dialogMessage.setTextColor(Color.parseColor(messageColorCode))

                val dialogPositive = customDialog!!.findViewById<TextView>(R.id.alertPositive)
                dialogPositive.text = positiveText
                dialogPositive.setTextColor(Color.parseColor(positiveTextColor))
                dialogPositive.setBackgroundResource(positiveBackground)
                dialogPositive.setOnClickListener(listener)

                val dialogNegative = customDialog!!.findViewById<TextView>(R.id.alertNegative)
                dialogNegative.text = negativeText
                dialogNegative.setTextColor(Color.parseColor(negativeTextColor))
                dialogNegative.setBackgroundResource(negativeBackground)
                dialogNegative.setOnClickListener { customDialog!!.dismiss() }

                customDialog!!.show()
            }

            fun cancelCustomDialog() {
                customDialog!!.dismiss()
            }

            // ALERT BOX ===========================================================================================================================
            fun getAlert(context: Context, title: String, msg: String, isCancel: Boolean) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setCancelable(isCancel)
                builder.show()
            }

            fun getAlertPositive(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                positiveText: String,
                listener: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(title)
                builder.setMessage(message)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(positiveText, listener)
                builder.show()
            }

            fun getAlertPositiveDefaultDark(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                positiveText: String,
                listener: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                builder.setTitle(title)
                builder.setMessage(message)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(positiveText, listener)
                builder.show()
            }

            fun getAlertPositiveHoloLight(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                positiveText: String,
                listener: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                builder.setTitle(title)
                builder.setMessage(message)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(positiveText, listener)
                builder.show()
            }

            fun getAlertPositiveHoloDark(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                positiveText: String,
                listener: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK)
                builder.setTitle(title)
                builder.setMessage(message)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(positiveText, listener)
                builder.show()
            }

            fun getAlertPositiveTraditional(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                positiveText: String,
                listener: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context, AlertDialog.THEME_TRADITIONAL)
                builder.setTitle(title)
                builder.setMessage(message)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(positiveText, listener)
                builder.show()
            }

            // alert positive end.

            fun getAlertPositiveWithIcon(
                context: Context,
                title: String,
                msg: String,
                icon: Int,
                isCancel: Boolean,
                positiveText: String,
                listener: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setIcon(icon)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(positiveText, listener)
                builder.show()
            }

            fun getAlertNegative(
                context: Context,
                title: String,
                msg: String,
                isCancel: Boolean,
                negativeText: String,
                listener: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(negativeText, listener)
                builder.show()
            }

            fun getAlertNegativeDefaultDark(
                context: Context,
                title: String,
                msg: String,
                isCancel: Boolean,
                negativeText: String,
                listener: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(negativeText, listener)
                builder.show()
            }

            fun getAlertNegativeHoloLight(
                context: Context,
                title: String,
                msg: String,
                isCancel: Boolean,
                negativeText: String,
                listener: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(negativeText, listener)
                builder.show()
            }

            fun getAlertNegativeHoloDark(
                context: Context,
                title: String,
                msg: String,
                isCancel: Boolean,
                negativeText: String,
                listener: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(negativeText, listener)
                builder.show()
            }

            fun getAlertNegativeTraditional(
                context: Context,
                title: String,
                msg: String,
                isCancel: Boolean,
                negativeText: String,
                listener: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context, AlertDialog.THEME_TRADITIONAL)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(negativeText, listener)
                builder.show()
            }

            // alert negative end.

            fun getAlertPositiveNegative(
                context: Context,
                title: String,
                msg: String,
                isCancel: Boolean,
                positiveText: String,
                listenerPositive: DialogInterface.OnClickListener,
                negativeText: String,
                listenerNegative: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(positiveText, listenerPositive)
                builder.setNegativeButton(negativeText, listenerNegative)
                builder.show()
            }

            fun getAlertPositiveNegativeDefaultDark(
                context: Context,
                title: String,
                msg: String,
                isCancel: Boolean,
                positiveText: String,
                listenerPositive: DialogInterface.OnClickListener,
                negativeText: String,
                listenerNegative: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(positiveText, listenerPositive)
                builder.setNegativeButton(negativeText, listenerNegative)
                builder.show()
            }

            fun getAlertPositiveNegativeHoloLight(
                context: Context,
                title: String,
                msg: String,
                isCancel: Boolean,
                positiveText: String,
                listenerPositive: DialogInterface.OnClickListener,
                negativeText: String,
                listenerNegative: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(positiveText, listenerPositive)
                builder.setNegativeButton(negativeText, listenerNegative)
                builder.show()
            }

            fun getAlertPositiveNegativeHoloDark(
                context: Context,
                title: String,
                msg: String,
                isCancel: Boolean,
                positiveText: String,
                listenerPositive: DialogInterface.OnClickListener,
                negativeText: String,
                listenerNegative: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(positiveText, listenerPositive)
                builder.setNegativeButton(negativeText, listenerNegative)
                builder.show()
            }

            fun getAlertPositiveNegativeTraditional(
                context: Context,
                title: String,
                msg: String,
                isCancel: Boolean,
                positiveText: String,
                listenerPositive: DialogInterface.OnClickListener,
                negativeText: String,
                listenerNegative: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context, AlertDialog.THEME_TRADITIONAL)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(positiveText, listenerPositive)
                builder.setNegativeButton(negativeText, listenerNegative)
                builder.show()
            }

            // alert positive negative end.

            fun getAlertPositiveNegativeNeutral(
                context: Context,
                title: String,
                msg: String,
                isCancel: Boolean,
                positiveText: String,
                listenerPositive: DialogInterface.OnClickListener,
                negativeText: String,
                listenerNegative: DialogInterface.OnClickListener,
                neutralText: String,
                listenerNeutral: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(positiveText, listenerPositive)
                builder.setNegativeButton(negativeText, listenerNegative)
                builder.setNeutralButton(neutralText, listenerNeutral)
                builder.show()
            }

            fun getAlertPositiveNegativeNeutralDefaultDark(
                context: Context,
                title: String,
                msg: String,
                isCancel: Boolean,
                positiveText: String,
                listenerPositive: DialogInterface.OnClickListener,
                negativeText: String,
                listenerNegative: DialogInterface.OnClickListener,
                neutralText: String,
                listenerNeutral: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(positiveText, listenerPositive)
                builder.setNegativeButton(negativeText, listenerNegative)
                builder.setNeutralButton(neutralText, listenerNeutral)
                builder.show()
            }

            fun getAlertPositiveNegativeNeutralHoloLight(
                context: Context,
                title: String,
                msg: String,
                isCancel: Boolean,
                positiveText: String,
                listenerPositive: DialogInterface.OnClickListener,
                negativeText: String,
                listenerNegative: DialogInterface.OnClickListener,
                neutralText: String,
                listenerNeutral: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(positiveText, listenerPositive)
                builder.setNegativeButton(negativeText, listenerNegative)
                builder.setNeutralButton(neutralText, listenerNeutral)
                builder.show()
            }

            fun getAlertPositiveNegativeNeutralHoloDark(
                context: Context,
                title: String,
                msg: String,
                isCancel: Boolean,
                positiveText: String,
                listenerPositive: DialogInterface.OnClickListener,
                negativeText: String,
                listenerNegative: DialogInterface.OnClickListener,
                neutralText: String,
                listenerNeutral: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(positiveText, listenerPositive)
                builder.setNegativeButton(negativeText, listenerNegative)
                builder.setNeutralButton(neutralText, listenerNeutral)
                builder.show()
            }

            fun getAlertPositiveNegativeNeutralTraditional(
                context: Context,
                title: String,
                msg: String,
                isCancel: Boolean,
                positiveText: String,
                listenerPositive: DialogInterface.OnClickListener,
                negativeText: String,
                listenerNegative: DialogInterface.OnClickListener,
                neutralText: String,
                listenerNeutral: DialogInterface.OnClickListener
            ) {
                val builder = AlertDialog.Builder(context, AlertDialog.THEME_TRADITIONAL)
                builder.setTitle(title)
                builder.setMessage(msg)
                builder.setCancelable(isCancel)
                builder.setPositiveButton(positiveText, listenerPositive)
                builder.setNegativeButton(negativeText, listenerNegative)
                builder.setNeutralButton(neutralText, listenerNeutral)
                builder.show()
            }

            // alert positive negative neutral end.

            fun getAlertRadioListSingleChoice(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.create().show()
            }

            fun getAlertRadioListSingleChoiceDefaultDark(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(
                    context,
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK
                )
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.create().show()
            }

            fun getAlertRadioListSingleChoiceHoloLight(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.create().show()
            }

            fun getAlertRadioListSingleChoiceHoloDark(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.create().show()
            }

            fun getAlertRadioListSingleChoiceTraditional(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_TRADITIONAL)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.create().show()
            }

            // alert single choice end.

            fun getAlertRadioListSingleChoicePositive(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.create().show()
            }

            fun getAlertRadioListSingleChoicePositiveDefaultDark(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(
                    context,
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK
                )
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.create().show()
            }

            fun getAlertRadioListSingleChoicePositiveHoloLight(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.create().show()
            }

            fun getAlertRadioListSingleChoicePositiveHoloDark(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.create().show()
            }

            fun getAlertRadioListSingleChoicePositiveTraditional(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_TRADITIONAL)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.create().show()
            }

            // alert single choice positive end.

            fun getAlertRadioListSingleChoiceNegative(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                alertDialog.create().show()
            }

            fun getAlertRadioListSingleChoiceNegativeDefaultDark(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(
                    context,
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK
                )
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                alertDialog.create().show()
            }

            fun getAlertRadioListSingleChoiceNegativeHoloLight(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                alertDialog.create().show()
            }

            fun getAlertRadioListSingleChoiceNegativeHoloDark(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                alertDialog.create().show()
            }

            fun getAlertRadioListSingleChoiceNegativeTraditional(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_TRADITIONAL)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                alertDialog.create().show()
            }

            // alert single choice negative end.

            fun getAlertRadioListSingleChoicePositiveNegative(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                alertDialog.create().show()
            }

            fun getAlertRadioListSingleChoicePositiveNegativeDefaultDark(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(
                    context,
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK
                )
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                alertDialog.create().show()
            }

            fun getAlertRadioListSingleChoicePositiveNegativeHoloLight(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                alertDialog.create().show()
            }

            fun getAlertRadioListSingleChoicePositiveNegativeHoloDark(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                alertDialog.create().show()
            }

            fun getAlertRadioListSingleChoicePositiveNegativeTraditional(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: Int,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_TRADITIONAL)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setSingleChoiceItems(list, checkedItem, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                alertDialog.create().show()
            }

            // alert single choice positive negative end.

            fun getAlertRadioListMultiChoice(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: BooleanArray,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                multiChoiceListener: DialogInterface.OnMultiChoiceClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setMultiChoiceItems(list, checkedItem, multiChoiceListener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                alertDialog.create().show()
            }

            fun getAlertRadioListMultiChoiceDefaultDark(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: BooleanArray,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                multiChoiceListener: DialogInterface.OnMultiChoiceClickListener
            ) {
                val alertDialog = AlertDialog.Builder(
                    context,
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK
                )
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setMultiChoiceItems(list, checkedItem, multiChoiceListener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                alertDialog.create().show()
            }

            fun getAlertRadioListMultiChoiceHoloLight(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: BooleanArray,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                multiChoiceListener: DialogInterface.OnMultiChoiceClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setMultiChoiceItems(list, checkedItem, multiChoiceListener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                alertDialog.create().show()
            }

            fun getAlertRadioListMultiChoiceHoloDark(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: BooleanArray,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                multiChoiceListener: DialogInterface.OnMultiChoiceClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setMultiChoiceItems(list, checkedItem, multiChoiceListener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                alertDialog.create().show()
            }

            fun getAlertRadioListMultiChoiceTraditional(
                context: Context,
                title: String,
                list: Array<String>,
                checkedItem: BooleanArray,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                multiChoiceListener: DialogInterface.OnMultiChoiceClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_TRADITIONAL)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setMultiChoiceItems(list, checkedItem, multiChoiceListener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                alertDialog.create().show()
            }


            fun getAlertList(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                val alert = alertDialog.create()
                alert.show()
            }

            fun getAlertListDefaultDark(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(
                    context,
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK
                )
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                val alert = alertDialog.create()
                alert.show()
            }

            fun getAlertListHoloLight(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                val alert = alertDialog.create()
                alert.show()
            }

            fun getAlertListHoloDark(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                val alert = alertDialog.create()
                alert.show()
            }

            fun getAlertListTraditional(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_TRADITIONAL)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                val alert = alertDialog.create()
                alert.show()
            }

            // alert list end

            fun getAlertListPositive(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                val alert = alertDialog.create()
                alert.show()
            }

            fun getAlertListPositiveDefaultDark(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(
                    context,
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK
                )
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                val alert = alertDialog.create()
                alert.show()
            }

            fun getAlertListPositiveHoloLight(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                val alert = alertDialog.create()
                alert.show()
            }

            fun getAlertListPositiveHoloDark(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                val alert = alertDialog.create()
                alert.show()
            }

            fun getAlertListPositiveTraditional(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_TRADITIONAL)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                val alert = alertDialog.create()
                alert.show()
            }

            // alert list positive end

            fun getAlertListNegative(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                val alert = alertDialog.create()
                alert.show()
            }

            fun getAlertListNegativeDefaultDark(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(
                    context,
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK
                )
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                val alert = alertDialog.create()
                alert.show()
            }

            fun getAlertListNegativeHoloLight(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                val alert = alertDialog.create()
                alert.show()
            }

            fun getAlertListNegativeHoloDark(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                val alert = alertDialog.create()
                alert.show()
            }

            fun getAlertListNegativeTraditional(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_TRADITIONAL)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                val alert = alertDialog.create()
                alert.show()
            }

            // alert list negative end

            fun getAlertListPositiveNegative(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                val alert = alertDialog.create()
                alert.show()
            }

            fun getAlertListPositiveNegativeDefaultDark(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(
                    context,
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK
                )
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                val alert = alertDialog.create()
                alert.show()
            }

            fun getAlertListPositiveNegativeHoloLight(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                val alert = alertDialog.create()
                alert.show()
            }

            fun getAlertListPositiveNegativeHoloDark(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                val alert = alertDialog.create()
                alert.show()
            }

            fun getAlertListPositiveNegativeTraditional(
                context: Context,
                title: String,
                list: Array<String>,
                isCancel: Boolean,
                positiveText: String,
                positiveListener: DialogInterface.OnClickListener,
                negativeText: String,
                negativeListener: DialogInterface.OnClickListener,
                listener: DialogInterface.OnClickListener
            ) {
                val alertDialog = AlertDialog.Builder(context, AlertDialog.THEME_TRADITIONAL)
                alertDialog.setTitle(title)
                alertDialog.setCancelable(isCancel)
                alertDialog.setItems(list, listener)
                alertDialog.setPositiveButton(positiveText, positiveListener)
                alertDialog.setNeutralButton(negativeText, negativeListener)
                val alert = alertDialog.create()
                alert.show()
            }

            // alert list positive negative end

            // INPUT VALIDATION =========================================================================================================================
            fun checkInput(input: String?): Boolean {
                return !(input == null || input.isEmpty())
            }

            // PASSWORD VALIDATION =======================================================================================================================
            fun checkPasswordLength(input: String?, length: Int): Boolean {
                return input!!.length >= length
            }

            // EMAIL VALIDATION ============================================================================================================================
            fun checkEmail(input: String?): Boolean {
                return emailPattern.matcher(input!!).matches()
            }

            // EMAIL PATTERN =========================================================================================================================
            private val emailPattern: Pattern = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+")

            // CHECK INTERNET CONNECTION =========================================================================================================================
            fun isInternetAvailable(context: Context): Boolean {
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork = cm.activeNetworkInfo
                return if (null != activeNetwork) {
                    if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                        true
                    } else activeNetwork.type == ConnectivityManager.TYPE_MOBILE
                } else {
                    false
                }
            }

            fun checkInternetWithRetry(
                context: Context,
                title: String,
                message: String,
                isCancel: Boolean,
                btnText: String
            ): Boolean {
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork = cm.activeNetworkInfo
                return if (null != activeNetwork) {
                    if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                        true
                    } else activeNetwork.type == ConnectivityManager.TYPE_MOBILE
                } else {
                    getAlertPositive(context, title, message, isCancel, btnText) { dialog, _ ->
                        checkInternetWithRetry(context, title, message, isCancel, btnText)
                        dialog!!.dismiss()
                    }
                    false
                }
            }

            // RANDOM NUMBER =========================================================================================================================
            fun getRandomNumber(): String {
                val random = Random()
                return String.format("%04d", random.nextInt(10000))
            }

            // TIME ZONE ===========================================================================================================================
            fun getTimeZoneOffSet(timeZoneId: String, timeZoneformat: String): String {
                val timezone: TimeZone = TimeZone.getTimeZone(
                    getTimeZoneName(
                        timeZoneId,
                        timeZoneformat
                    )
                )
                return timezone.getOffset(Calendar.ZONE_OFFSET.toLong()).toString()
            }

            fun getCurrentTimeZone(date: String, format: String) : String {
                val formatter = SimpleDateFormat(format, Locale.getDefault())
                formatter.timeZone = TimeZone.getTimeZone(getCurrentTimeZone())
                val date1 = formatter.parse(date)
                formatter.timeZone = TimeZone.getDefault()
                return formatter.format(date1!!)
            }

            // TIME ZONE ID = "GMT", format = "Z"
            @SuppressLint("SimpleDateFormat")
            fun getTimeZone(timeZoneId: String, timeZoneformat: String): String {
                cal = Calendar.getInstance(TimeZone.getTimeZone(timeZoneId), Locale.getDefault())
                val timeZone = SimpleDateFormat(timeZoneformat).format(cal.time)
                return timeZone.substring(0, 3) + ":" + timeZone.subSequence(3, 5)
            }

            // Time Zone Name such as Asia/Kolkata
            fun getTimeZoneName(timeZoneId: String, timeZoneformat: String): String {
                val timeZoneName = TimeZone.getTimeZone(
                    timeZoneId + getTimeZone(
                        timeZoneId,
                        timeZoneformat
                    )
                )
                val names = TimeZone.getAvailableIDs(timeZoneName.getOffset(System.currentTimeMillis()))
                return names[0]
            }

            fun getCurrentTimeZone() : String = Calendar.getInstance().timeZone.displayName

            // STRING =============================================================================================================================
            fun getStringReplace(input: String, oldValue: String, newValue: String): String {
                return input.replace(oldValue, newValue)
            }

            fun getStringDecimalPlaces(input: String): String {
                val numFormat = DecimalFormat("##.##")
                return numFormat.format(input)
            }

            fun getDoubleDecimalPlaces(input: Double): Double {
                val numFormat = DecimalFormat("##.00")
                return numFormat.format(input).toDouble()
            }

            // ADDRESS FROM LAT, LNG ===============================================================================================================
            fun getAddressFromLatLng(context: Context, lat: Double, lng: Double): String {
                var foundAdd = ""
                try {
                    val geoCoder = Geocoder(context, Locale.getDefault())
                    val addresses: List<Address> = geoCoder.getFromLocation(lat, lng, 1)
                    if (addresses.isNotEmpty()) {
                        foundAdd = addresses[0].getAddressLine(0)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    foundAdd = "Address not found."
                }

                return foundAdd
            }

            fun getAddressFromLatLng(context: Context, lat: Double, lng: Double, maxResult: Int): String {
                var foundAdd = "Address not available"
                var locale = ""
                var thoroughFare = ""
                try {
                    val geoCoder = Geocoder(context, Locale.getDefault())
                    val addresses: List<Address> = geoCoder.getFromLocation(lat, lng, maxResult)
                    if (addresses.isNotEmpty()) {
                        val city = addresses[0].locality
                        val state = addresses[0].adminArea
                        val country = addresses[0].countryName
                        val postalCode = addresses[0].postalCode
                        val knownName = addresses[0].featureName
                        try { locale = addresses[0].getAddressLine(0).split(",")[4] } catch (e: Exception) { e.printStackTrace() }
                        try { thoroughFare = addresses[0].thoroughfare } catch (e: Exception) { e.printStackTrace() }
                        foundAdd = "$knownName $locale $thoroughFare, $city, $state,$country,$postalCode"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    foundAdd = "Address not available"
                    Log.e("UtilsAdd", "Address Exception : ${e.message}")
                }
                return foundAdd
            }

            fun getAddressUsingApi(lat: String, lng: String, googleApiKey: String):String {
                val geoApiCtx = GeoApiContext.Builder()
                    .apiKey(googleApiKey)
                    .build()
                val addresses = GeocodingApi.reverseGeocode(
                    geoApiCtx, LatLng(
                        lat.toDouble(),
                        lng.toDouble()
                    )
                ).await()
                return addresses[0].formattedAddress
            }


            //LAT, LNG FROM ADDRESS ===============================================================================================================

            fun getLatLngFromAddress(context: Context, address: String): String {
                var latlng = ""
                val geocoder = Geocoder(context, Locale.getDefault())
                try {
                    val addList = geocoder.getFromLocationName(address, 5)
                    latlng = addList[0].latitude.toString() + "," + addList[0].longitude
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return latlng
            }

            fun getLatLngFromAddressUsingApi(address: String, googleApiKey: String):String {
                return try {
                    val geoApiCtx = GeoApiContext.Builder()
                        .apiKey(googleApiKey)
                        .build()
                    val result = GeocodingApi.geocode(geoApiCtx, address).await()
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    gson.toJson(result[0].geometry.location)
                } catch (e: Exception) {
                    e.message!!
                }
            }

            fun getLocationUsingFusedLocationClient(context: Context) : String{
                val sb = StringBuilder()
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) { }
                fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener {
                        sb.append(it.latitude.toString()+",")
                        sb.append(it.longitude.toString())
                    }.addOnFailureListener {
                        sb.append(it.message)
                    }
                return sb.toString()
            }

            // CURRENT DATE
            fun getCurrentDate(dateFormat: String): String {
                return SimpleDateFormat(dateFormat, Locale.getDefault()).format(Date())
            }

            // CURRENT TIME
            fun getCurrentTime(timeFormat: String): String {
                return SimpleDateFormat(timeFormat, Locale.getDefault()).format(Calendar.getInstance().time)
            }

            // CURRENT DATE TIME WITH FORMAT
            fun getCurrentDateTime(dateFormat: String, timeFormat: String): String {
                return SimpleDateFormat("$dateFormat $timeFormat", Locale.getDefault()).format(Date())
            }

            // DATE/TIME DIFFERENCE
            @SuppressLint("SimpleDateFormat")
            fun getDateTimeDifference(time1: String, time2: String, format: String): String {
                val simpleDateFormat = SimpleDateFormat(format)
                val date1 = simpleDateFormat.parse(time1)
                val date2 = simpleDateFormat.parse(time2)
                val difference = date2!!.time - date1!!.time
                var days = (difference / (1000 * 60 * 60 * 24)).toInt()
                val yrs = (days/365)
                days %= 365
                val hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toInt()
                val min = (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours).toInt() / (1000 * 60)
                val sec = ((difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours).toInt() / (1000 * 60))/ (1000*60)
                return when {
                    yrs > 0 -> { return if(yrs==1) { "$yrs yr $days days" } else { "$yrs yrs $days days" } }
                    days > 0 -> { return if(days==1) { "$days day" } else { "$days days" } }
                    hours > 0 -> { return if(hours==1) { "$hours hr $min mins" } else { "$hours hrs $min mins" } }
                    min > 0 -> { return if(min==1) { "$min min $sec sec" } else { "$min mins $sec sec" } }
                    sec > 0 -> { return if(sec==1) { "$sec sec" } else { "$sec secs" } }
                    else -> "0"
                }
            }

            // FORMATED DATE TIME
            fun getDateTimeFormated(dateInput: String, fromFormat: String, toFormat: String): String {
                return try {
                    val sdf = SimpleDateFormat(fromFormat, Locale.getDefault())
                    val date = sdf.parse(dateInput)
                    SimpleDateFormat(toFormat, Locale.getDefault()).format(date!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                    dateInput
                }
            }

            // TWO TIMES COMPARISION
            fun isTime2GreaterThanFromTime1(fromTime: String, toTime: String, format: String): Boolean {
                val sdf = SimpleDateFormat(format, Locale.getDefault())
                val time1 = sdf.parse(fromTime)
                val time2 = sdf.parse(toTime)
                return !time2!!.before(time1)
            }

            // TWO TIME ARE EQUAL OR NOT
            fun isTwoTimesEqual(fromTime: String, toTime: String, format: String): Boolean {
                val sdf = SimpleDateFormat(format)
                val time1 = sdf.parse(fromTime)
                val time2 = sdf.parse(toTime)
                return time1 != time2
            }

            // GPS IS ENABLE/DISABLE
            fun isGpsEnabled(context: Context): Boolean {
                val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            }

            // BITMAP TO BASE64
            fun getBitmapToBase64Converted(images: Bitmap): String {
                val stream = ByteArrayOutputStream()
                images.compress(Bitmap.CompressFormat.JPEG, 90, stream)
                val image = stream.toByteArray()
                // Log.e("LOOK", encodedImage);
                return Base64.encodeToString(image, Base64.DEFAULT)
            }

            // PATH TO BITMAP
            fun getPathToBitmapConverted(filePath: String?): Bitmap {
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeFile(filePath, options)
                options.inJustDecodeBounds = false
                return BitmapFactory.decodeFile(filePath, options)
            }

            // DRAWABLE TO BITMAP
            fun drawableToBitmap(drawable: Drawable?): Bitmap? {
                if (drawable == null) {
                    throw NullPointerException("Drawable to convert should NOT be null")
                }
                if (drawable is BitmapDrawable) {
                    return drawable.bitmap
                }
                if (drawable.intrinsicWidth <= 0 && drawable.intrinsicHeight <= 0) {
                    return null
                }
                val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                return bitmap
            }

            // PATH TO BITMAP 2
            fun getPathToBitmapConverted2(context: Context, filePath: Uri): Bitmap {
                return MediaStore.Images.Media.getBitmap(context.contentResolver, filePath)
            }

            // COMPARE TWO STRINGS
            fun getStringComparision(str1: String, str2: String, ignoreCase: Boolean): Boolean {
                return str1.equals(str2, ignoreCase)
            }

            // GET PRE/POST MONTH FROM CURRENT DATE
            fun getPreOrPostMonthDateFromCurrentDate(dateFormat: String, preMonthValue: Int) : String {
                cal = Calendar.getInstance()
                cal.add(Calendar.MONTH, preMonthValue)
                val date = cal.time
                val format = SimpleDateFormat(dateFormat)
                return format.format(date)
            }

            // SHOW SOFT KEYBOARD
            fun showSoftKeyboard(context: Context, editText: EditText) {
                val softKey = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                editText.requestFocus()
                softKey.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
            }

            // HIDE SOFT KEYBOARD
            fun hideSoftKeyboard(context: Context, editText: EditText) {
                val hideKey = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                editText.inputType = InputType.TYPE_NULL
                editText.requestFocus()
                hideKey.hideSoftInputFromWindow(editText.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
            }

            // PARTIAL TEXT BOLD AT START
            fun partialTextBoldStart(normalText: String, boldText: String) : SpannableStringBuilder {
                return SpannableStringBuilder().bold { append(boldText) }.append(normalText)
            }

            // PARTIAL TEXT BOLD AT CENTER
            fun partialTextBoldCenter(startText: String, centerBoldText: String, endText: String) : SpannableStringBuilder {
                return SpannableStringBuilder().append(startText).bold { append(centerBoldText) }.append(
                    endText
                )
            }

            // PARTIAL TEXT BOLD AT END
            fun partialTextBoldEnd(normalText: String, boldText: String) : SpannableStringBuilder {
                return SpannableStringBuilder().append(normalText).bold { append(boldText) }
            }

            // PARTIAL TEXT BOLD AT START & END
            fun partialTextBoldStartEnd(
                startBoldText: String,
                centerText: String,
                endBoldText: String
            ) : SpannableStringBuilder {
                return SpannableStringBuilder().bold { append(startBoldText) }.append(centerText).bold { append(
                    endBoldText
                ) }
            }

            // PARTIAL TEXT BOLD AT ANY POSITION
            fun partialTextCustom(
                text: String,
                color: Int,
                italic: Boolean,
                bold: Boolean,
                boldItalic: Boolean,
                startIndex: Int,
                endIndex: Int
            ) : SpannableStringBuilder {
                val ssb =  SpannableStringBuilder(text)
                val textColor = ForegroundColorSpan(color)
                val textBold = StyleSpan(android.graphics.Typeface.BOLD)
                val textBoldItalic = StyleSpan(android.graphics.Typeface.BOLD_ITALIC)
                val textItalic = StyleSpan(android.graphics.Typeface.ITALIC)
                ssb.setSpan(textColor, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                when {
                    italic -> {
                        ssb.setSpan(
                            textItalic,
                            startIndex,
                            endIndex,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE
                        )
                    }
                    bold -> {
                        ssb.setSpan(
                            textBold,
                            startIndex,
                            endIndex,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE
                        )
                    }
                    boldItalic -> {
                        ssb.setSpan(
                            textBoldItalic,
                            startIndex,
                            endIndex,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE
                        )
                    }
                }

                return ssb
            }


            // DEVICE DETAILS ======================================================================
            fun getDeviceType() = Build.TYPE.toString()

            fun getDeviceBrand()= Build.BRAND.toString()

            fun getDeviceBoard() = Build.BOARD.toString()

            fun getDeviceBootloader() = Build.BOOTLOADER.toString()

            fun getDeviceId() = Build.ID.toString()

            fun getDeviceManufacture() = Build.MANUFACTURER.toString()

            fun getDeviceModel() = Build.MODEL.toString()

            fun getDeviceProduct() = Build.PRODUCT.toString()

            @SuppressLint("HardwareIds")
            fun getDeviceSerialNo() = Build.SERIAL.toString()

            fun getDeviceVersion() = Build.VERSION.RELEASE

            fun getDeviceVersionName() = VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT].name

            @SuppressLint("HardwareIds")
            fun getAndroidUniqueId(context : Context) = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID).toString()


            // CHECK SD CARD MOUNTED OR NOT
            fun isSDCardMounted() = Environment.getExternalStorageState()!=null && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)

            // DENSITY MULTIPLIER
            fun getDensityMultiplier(context : Context) = context.resources.displayMetrics.density

            // PIXEL TO DENSITY INDEPENDENT PIXEL (PX TO DIP)
            fun getDipFromPX(pixel : Int, context: Context) = (pixel*context.resources.displayMetrics.density+0.5F).toInt()

            // CHECK SERVICE RUNNING OR NOT
            fun isServiceRunning(context: Context,serviceName : String) : Boolean {
                val manager = context.applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                for (service in manager.getRunningServices(Int.MAX_VALUE)) {
                    if (service.service.className == serviceName) {
                        return true
                    }
                }
                return false
            }

            // INTENT USESE ==============================================================================================================================

            // DIAL A CALL USING INTENT
            fun dial(context: Context, number : String) {
                context.startActivity(Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:$number")))
            }

            // CALL USING INTENT
            fun call(context: Context, number : String) {
                context.startActivity(Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:$number")))
            }

            // SEND MSG USING INTENT
            fun sendMessage(context: Context, number: String, message : String) {
                val smsIntent = Intent(Intent.ACTION_SEND)
                smsIntent.putExtra("smsto:",Uri.parse(number))
                smsIntent.putExtra("sms_body",message)
                smsIntent.type = "vnd.android-dir/mms-sms"
                context.startActivity(smsIntent)
            }

            // VIEW WEB PAGE USING INTENT
            fun getWebView(context: Context, url : String) {
                context.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
            }

            // SEND MAIL USING INTENT
            fun sendMail(context: Context, to :String, subject: String, message: String, title: String) {
                val sendEmail = Intent(Intent.ACTION_SEND)
                sendEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
                sendEmail.putExtra(Intent.EXTRA_SUBJECT, subject)
                sendEmail.putExtra(Intent.EXTRA_TEXT, message)
                sendEmail.setType("message/rfc822")
                context.startActivity(Intent.createChooser(sendEmail,title))
            }

            // HIDE ACTIONBAR
            fun hideActionBar(context : Context) {
                (context as Activity).window?.requestFeature(Window.FEATURE_ACTION_BAR)
                context.actionBar?.hide()
            }

            // SHOW FULL SCREEN ACTIVITY
            fun getFullScreen(context: Context) {
                (context as Activity).window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }

            //  GET ACITVITY SCREEN SIZE
            fun getScreenSize(context: Context) : IntArray {
                val size = Point()
                val wm = (context as Activity).windowManager
                wm.defaultDisplay.getSize(size)
                return intArrayOf(size.x, size.y)
            }

            // CLEAR WINDOW BACKGROUND
            fun clearWindowBackground(context: Context) = (context as Activity).window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            // GLOBAL PHONE NUMBER VALIDATION
            fun isGlobalPhoneNumber(phoneNumber : String) = !TextUtils.isEmpty(phoneNumber) && PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)

            // INDIAN PHONE NUMEBR VALIDATION
            fun isIndianPhoneNumber(phoneNumber: String) : Boolean {
                var result:Boolean = isGlobalPhoneNumber(phoneNumber)
                if (result) {
                    result = if (phoneNumber.length > 10) {
                        phoneNumber.length == 11 && phoneNumber.startsWith("0")
                                || phoneNumber.length == 13 && phoneNumber.startsWith("+91")
                                || phoneNumber.length == 13 && phoneNumber.startsWith("091")
                                || phoneNumber.length == 14 && phoneNumber.startsWith("0091")
                    } else {
                        phoneNumber.length == 10 && !phoneNumber.startsWith("0")
                    }
                }
                return result
            }

            // APP IN FOREGROUND
            fun isAppInForeground(context: Context) : Boolean {
              val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val appProcesses = activityManager.runningAppProcesses as List<ActivityManager.RunningAppProcessInfo> ?: return false
                val packageName = context.packageName
                for(appProcess in appProcesses) {
                    if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                        return true
                    }
                }
                return false
            }




        }

    }

