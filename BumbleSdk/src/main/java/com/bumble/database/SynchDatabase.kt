package com.hippoagent.database

import android.os.AsyncTask
import com.bumble.BumbleConfig
import com.bumble.Utils.BumbleLog
import com.hippo.database.repository.ChannelInfoRepo
import com.google.gson.Gson
import com.hippo.database.entity.ChannelInfo

/**
 * Created by gurmail on 29/04/19.
 * @author gurmail
 */
class SynchDatabase: AsyncTask<Void, Void, Void>() {

    var TAG: String = SynchDatabase::class.java.simpleName
    var channelInfoRepo: ChannelInfoRepo? = null
    var channenInfoList: List<ChannelInfo>? = null
    val timeStamp: Long = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)

    override fun doInBackground(vararg params: Void?): Void? {
        try {
            channelInfoRepo = ChannelInfoRepo(BumbleConfig.getInstance().context, timeStamp)
            channenInfoList = channelInfoRepo?.getChannelInfo()

            BumbleLog.e(TAG, "channenInfoList = "+ Gson().toJson(channenInfoList))
            for(channelInfo:ChannelInfo in channenInfoList!!) {
//                CommonData.removeSentMessageChannel(channelInfo.channelId)
//                CommonData.removeUnsentMessageChannel(channelInfo.channelId)
//                CommonData.removeUnsentMessageMapChannel(channelInfo.channelId)
//                CommonData.removeMessageResponse(channelInfo.channelId)

                channelInfoRepo?.deleteChannel(channelInfo.channelId)
            }

        } catch (e: Exception) {

        }

        /*try {
            val notificationTimestamp : TreeMap<Int, Long> =  NotificationDatabase.getNotificationTime()
            val localTime: Long = (2 * 24 * 60 * 60 * 1000)
            val channels = ArrayList<Int>()
            for ((key, value) in notificationTimestamp) {
                if (System.currentTimeMillis() - value > localTime) {
                    channels.add(key)
                }
            }

            if(channels.size > 0) {
                for(i in channels.indices) {
                    NotificationDatabase.removeNotifications(channels[i])
                    CommonData.removeNotificationChannel(channels[i])
                }
            }
        } catch (e: Exception) {
        }*/
        return null
    }
}