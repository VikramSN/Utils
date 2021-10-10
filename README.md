# Utils
It's a utility package, which helps to use many utility features in android, such as logs,toasts,snackbars, alerts,radio alert, list alert,checkboxes alert, progressbars, time difference, lat-lang to address, address to lat-lnt, timezone, random number, current time-date, current time zone, etc...

Step 1. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.VikramSN:Utils:Tag'
	}
