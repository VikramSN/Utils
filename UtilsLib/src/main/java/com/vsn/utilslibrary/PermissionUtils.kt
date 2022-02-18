package com.vsn.utilslibrary

import android.content.Context
import android.util.Log
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class PermissionUtils {

        companion object {
            var isPermissionEnabled = false
            fun setPermission(context: Context, permissionName: String) : Boolean {
                Dexter.withContext(context).withPermissions()

                Dexter.withContext(context)
                    .withPermission(permissionName)
                    .withListener(object : PermissionListener {
                        override fun onPermissionGranted(response: PermissionGrantedResponse?) {
//                            Utils.showToast(context, "Permission Granted")
                            Log.e("permissionUtils","permission grant : $response")
                            isPermissionEnabled =  true
                        }

                        override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                            token!!.continuePermissionRequest()
                            Log.e("permissionUtils","permission tokens :  $permission and $token")
                            isPermissionEnabled =  false
                        }

                        override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                            if(response!!.isPermanentlyDenied) {
                                setPermission(context, permissionName)
                                Log.e("permissionUtils","permission deny : $response")
                                isPermissionEnabled = false
                            }
//                            Utils.showToast(context, "Permission Denied")
//                            Utils.showAlert(context,
//                                "Need Permissions",
//                                "This app needs permission to use the feature. You can grant them in app settings.",
//                                "open",
//                                "close",
//                                DialogInterface.OnClickListener { dialog, _ ->
//                                    openSettings(context)
//                                    dialog.dismiss()
//                                },
//                                DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })
                        }

                    }).check()

                return isPermissionEnabled
            }

//            private fun openSettings(context: Context) {
//                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                val uri = Uri.fromParts("package", context.packageName, null)
//                intent.data = uri
//                context.startActivity(intent)
////        startActivityForResult(intent,101)
//            }
        }


}