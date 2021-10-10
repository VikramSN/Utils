package com.vsn.utilspack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vsn.utilslibrary.location.LocationUtils
import com.vsn.utilslibrary.utility.Utils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        demoButton.setOnClickListener {
            val dist = Utils.getAndroidUniqueId(this)
            Utils.getSuccessToast(this,"id : $dist")

        }

    }

}