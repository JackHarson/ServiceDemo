package com.jumei.foreground

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

//前台服务
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                (service as ForeGroundService.MyBinder).service.numberLiveData.observe(this@MainActivity,
                    Observer {
                        findViewById<TextView>(R.id.tv_text).text = it.toString()

                    })
            }

            override fun onServiceDisconnected(name: ComponentName?) {

            }
        }
        Intent(this, ForeGroundService::class.java).also {
            bindService(it, connection, BIND_AUTO_CREATE)
        }

    }
}