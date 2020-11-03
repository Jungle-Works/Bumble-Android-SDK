package com.hippo.database.repository

import android.content.Context
import android.os.AsyncTask
import com.bumble.Utils.BumbleLog
import com.hippoagent.database.AppDatabase
import com.hippo.database.dao.ChannelInfoDao
import com.google.gson.Gson
import com.hippo.database.entity.ChannelInfo

/**
 * Created by gurmail on 30/04/19.
 * @author gurmail
 */
class ChannelInfoRepo(application: Context, timeStamp: Long) {

    private val channelInfoDao: ChannelInfoDao
    private var channenInfoList: List<ChannelInfo>? = null

    init {
        val database: AppDatabase = AppDatabase.getAppDataBase(application)!!
        channelInfoDao = database.categoryDao()
        if(timeStamp>0)
            channenInfoList = channelInfoDao.getChannelInfo(timeStamp)
    }

    fun getChannelInfo() : List<ChannelInfo>? {
        BumbleLog.d("DATA", "DATA ~~~~~~~~~~~> "+Gson().toJson(channenInfoList))
        return channenInfoList
    }

    fun deleteChannel(channelId: Int) {
        channelInfoDao.deleteChannelInfo(channelId)
    }

    fun updateChannelInfo(channelId: Int) {
        insertAsyncTask(channelInfoDao).execute(channelId)
    }

    class insertAsyncTask internal constructor(val channelInfoDao: ChannelInfoDao) : AsyncTask<Int, Void, Void>() {
        override fun doInBackground(vararg params: Int?): Void? {
            val channelInfo = ChannelInfo(params[0]!!, System.currentTimeMillis())
            BumbleLog.d("DATA", "channelInfo ~~~~~~~~~~~> "+Gson().toJson(channelInfo))
            channelInfoDao.updateChannel(channelInfo)
            return null
        }
    }

}