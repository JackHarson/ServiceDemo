package com.jumei.servicedemo

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private val TAG = "hello"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Activity")
        setContentView(R.layout.activity_main)

    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Activity")
        findViewById<Button>(R.id.btn_start_service).setOnClickListener {
            Intent(this, MyService::class.java).also {
                startService(it)
            }
        }
        findViewById<Button>(R.id.btn_start_activity).setOnClickListener {
            Intent(this, SecondActivity::class.java).also {
                startActivity(it)
            }
        }

        findViewById<Button>(R.id.btn_binder_service).setOnClickListener {

            val binderIntent = Intent(this, MyService::class.java)
            val serviceConnection = object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    (service as MyService.MyBinder).service.numberLiveData
                        .observe(this@MainActivity, Observer {
                            findViewById<TextView>(R.id.textView).text = "计数${it}"
                        })
                }

                override fun onServiceDisconnected(name: ComponentName?) {

                }
            }

            //绑定是客户端与服务器端的关系 若客户端的绑定全结束 则该service会结束
            // 若是先startService 接着在binding Service 则会继续之前的状态继续进行
//            startService(binderIntent)
            bindService(binderIntent, serviceConnection, BIND_AUTO_CREATE)

        }

    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Activity")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Activity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Activity")
//        Intent(this, MyService::class.java).also {
//            stopService(it)
//        }
    }

}