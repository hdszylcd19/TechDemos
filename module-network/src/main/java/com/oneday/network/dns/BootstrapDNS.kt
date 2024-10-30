package com.oneday.network.dns

import android.text.TextUtils
import com.oneday.utils.DNSLookUpUtils
import okhttp3.Dns
import java.net.InetAddress

/**
 *
 */
// TODO: 待具体实现
class BootstrapDNS(var forceIp: String = "") : Dns {

//    val realDNS: Dns by lazy {
//        if (XHAppConfigProxy.getInstance().enableExtendDNS()) {
//            XhOkHttpDNS()
//        } else {
//            OptimizeLocalDNS()
//        }
//    }

    companion object {
        const val TAG = "BootstrapDNS"
    }

    override fun lookup(hostname: String): List<InetAddress> {
//        LogUtils.e(TAG, "hostname => $hostname and forceIp => $forceIp")
        return if (!TextUtils.isEmpty(forceIp)) {
            DNSLookUpUtils.loadLocalDNS(forceIp)
        } else {
            listOf()
        }
    }
}