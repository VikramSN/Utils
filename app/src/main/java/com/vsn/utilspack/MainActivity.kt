package com.vsn.utilspack

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.vsn.utilslibrary.AppNavigator
import com.vsn.utilslibrary.Utils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val demoButton:Button = findViewById(R.id.demoButton)

        demoButton.setOnClickListener {
                Utils.dial(this,"9876543210")
//            Utils.call(this,"9876543210")
//            Utils.sendMessage(this,"9876543210","Hello World")
//            Utils.sendMail(this,"xyz@gmail.com,abc@gmail.com,def@gmail.com","HW","Hello World","Select Mail App")
//            Utils.hideActionBar(this)
            startActivity(AppNavigator.goTo(this,MainActivity::class.java))
        }

    }

}