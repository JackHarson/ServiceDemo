package com.jumei.servicedemo

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        findViewById<Button>(R.id.btn_binder).setOnClickListener {

            val binderIntent = Intent(this, MyService::class.java)
            val serviceConnection = object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    (service as MyService.MyBinder).service.numberLiveData
                        .observe(this@SecondActivity, Observer {
                            findViewById<TextView>(R.id.tv_text).text = "计数${it}"
                        })
                }

                override fun onServiceDisconnected(name: ComponentName?) {

                }
            }

            //绑定是客户端与服务器端的关系 若客户端的绑定全结束 则该service会结束
            // 若是先startService 接着在binding Service 则会继续之前的状态继续进行
            bindService(binderIntent, serviceConnection, BIND_AUTO_CREATE)


        }
    }
}