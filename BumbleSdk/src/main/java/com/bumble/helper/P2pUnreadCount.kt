package com.bumble.helper

import android.text.TextUtils
import com.bumble.BumbleConfig
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by gurmail on 28/08/20.
 * @author gurmail
 */
object P2pUnreadCount {
    var p2pTransactionId = HashMap<String, String>() // tr_otheruseruniqueId <--> tr
    var p2pChannelsCount = HashMap<String, Long>() // tr <-> channelId
    var p2pChannelsCountValues = HashMap<Long, Int>() // channelId <-> count
    var p2pChannelsMuid = HashMap<Long, ArrayList<String>>() // channelId <-> ArrayList<String(MUID)>

    fun getChannelId(transactionId: String, userUniqueKey: String): Long? {
        val trId = p2pTransactionId[transactionId+"_"+userUniqueKey]
        return if(!TextUtils.isEmpty(trId)) {
            getChannelId(trId!!)
        } else {
            null
        }
    }

    fun setLocalTransactionId(transactionId: String, userUniqueKey: String) {
        p2pTransactionId[transactionId+"_"+userUniqueKey] = transactionId
    }

    /**
     * for getting saved channel id from hashmap
     * @transactionId
     */
    fun getChannelId(transactionId: String): Long? {
        return p2pChannelsCount[transactionId]
    }

    /**
     * For getting the
     */
    fun getChannelCount(channelId: Long): Int {
        return p2pChannelsCountValues[channelId] ?: 0
    }


    /**
     * Method used with the response of P2P API response
     */
    fun updateChannelId(transactionId: String, channelId: Long, count: Int, muid: String) {
        p2pChannelsCount[transactionId] = channelId
        p2pChannelsCountValues[channelId] = count
        if(TextUtils.isEmpty(muid)) {
            p2pChannelsMuid[channelId] = ArrayList()
        } else {
            var array = p2pChannelsMuid[channelId]
            if(array != null) {
                array = ArrayList()
            }
            array!!.add(muid)
            p2pChannelsMuid[channelId] = array
        }
    }

    /**
     * This method is used with the notification and faye message received
     */
    @Synchronized
    fun updateChannelId(transactionId: String, channelId: Long, muid: String) {
        if(p2pChannelsCount.containsKey(transactionId)) {
            p2pChannelsCount[transactionId] = channelId
            var array = p2pChannelsMuid[channelId]
            if(array != null && array.contains(muid)) {
                val count = getChannelCount(channelId)
                BumbleConfig.getInstance().updateCount(transactionId, count)
            } else {
                val count = getChannelCount(channelId) + 1
                if(array == null) {
                    array = ArrayList()
                }
                array.add(muid)
                p2pChannelsMuid[channelId] = array
                p2pChannelsCount[transactionId] = channelId
                p2pChannelsCountValues[channelId] = count
                BumbleConfig.getInstance().updateCount(transactionId, count)
            }
        }
    }

    fun clearCount(channelId: Long) {
        p2pChannelsCountValues[channelId] = 0
        p2pChannelsMuid[channelId] = ArrayList()
    }

    fun getTransactionId(channelId: Long): String {
        if(p2pChannelsCount.containsValue(channelId)) {
            val transaction = getKeyByValue(p2pChannelsCount, channelId)
            if(TextUtils.isEmpty(transaction))
                return ""
            else
                return transaction!!
        }
        return ""
    }

    fun <T, E> getKeyByValue(map: Map<T, E>, value: E): T? {
        for ((key, value1) in map) {
            if (value == value1) {
                return key
            }
        }
        return null
    }

    fun removeTransactionId(transactionId: String) {
        try {
            val trOther = getKeyByValue(p2pTransactionId, transactionId)
            if(!TextUtils.isEmpty(trOther)) {
                p2pTransactionId.remove(trOther)
                val channelId = p2pChannelsCount[transactionId]
                p2pChannelsCount.remove(transactionId)
                if(channelId != null) {
                    p2pChannelsCountValues.remove(channelId)
                    p2pChannelsMuid.remove(channelId)
                }
            }
        } catch (e: Exception) {
        }
    }

    fun hasTransactionId(transactionId: String): Boolean {
        return p2pTransactionId.containsKey(transactionId)
    }

}