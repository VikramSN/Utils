package com.vsn.utilspack

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.vsn.utilslibrary.location.LocationUtils
import com.vsn.utilslibrary.utility.Utils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        demoButton.setOnClickListener {
//            Utils.dial(this,"9876543210")
//            Utils.call(this,"9876543210")
//            Utils.sendMessage(this,"9876543210","Hello World")
//            Utils.sendMail(this,"xyz@gmail.com,abc@gmail.com,def@gmail.com","HW","Hello World","Select Mail App")
        }

    }

}