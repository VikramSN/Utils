package com.vsn.utilslibrary

import android.content.Context
import android.content.Intent


class AppNavigator {

    companion object {

        fun goTo(context : Context, activity : Class<*>) :Intent {
            return Intent(context, activity)
        }

        fun goToActivity(context: Context, activity : Class<*>) {
            context.startActivity(Intent(context,activity))
        }
    }
}