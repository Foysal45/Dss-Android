package com.dss.hrms.view.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.dss.hrms.view.allInterface.OnNetworkStateChangeListener

class NetworkChangeReceiver() : BroadcastReceiver() {

    var listener: OnNetworkStateChangeListener? = null


    constructor(listener: OnNetworkStateChangeListener?) : this() {
        this.listener = listener
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            if (isInternetOnCheck(context!!)) {
                listener?.onChange(true)
            } else {
                listener?.onChange(false)
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    private fun isInternetOnCheck(aContext: Context): Boolean {
        var aResult = false
        val aConnecMan =
            aContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = aConnecMan.activeNetworkInfo
        if (activeNetwork != null) {
            val isConnected = activeNetwork.isConnected
            val isWiFi = activeNetwork.type == ConnectivityManager.TYPE_WIFI
            if (isConnected && isWiFi) {
            }
        }
        if (aConnecMan.getNetworkInfo(0)!!.state == NetworkInfo.State.CONNECTED
            || aConnecMan.getNetworkInfo(0)!!.state == NetworkInfo.State.CONNECTING
            || aConnecMan.getNetworkInfo(1)!!.state == NetworkInfo.State.CONNECTING
            || aConnecMan.getNetworkInfo(1)!!.state == NetworkInfo.State.CONNECTED
        ) {
            aResult = true
        } else if (aConnecMan.getNetworkInfo(0)!!.state == NetworkInfo.State.DISCONNECTED
            || aConnecMan.getNetworkInfo(1)!!.state == NetworkInfo.State.DISCONNECTED
        ) {
            aResult = false
        }
        return aResult
    }
}