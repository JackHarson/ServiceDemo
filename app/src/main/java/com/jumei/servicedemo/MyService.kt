package com.jumei.servicedemo

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val TAG = "hello"

class MyService : LifecycleService() {
    private var number = 0
    val numberLiveData = MutableLiveData(0)
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: MyService")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand:  MyService")
        lifecycleScope.launch {
            while (true) {
                delay(1000)
                Log.d(TAG, "onStartCommand:== ${number++}")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    inner class MyBinder : Binder() {
        val service = this@MyService
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        Log.d(TAG, "onBind:== ${number++}")
        lifecycleScope.launch {
            while (true) {
                delay(1000)
                numberLiveData.value = numberLiveData.value?.plus(1)
            }
        }
        return MyBinder()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy:  MyService")

    }
}