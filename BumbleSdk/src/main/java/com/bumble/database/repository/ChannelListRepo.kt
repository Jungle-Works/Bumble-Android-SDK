package com.hippo.database.repository

import android.content.Context
import com.hippoagent.database.AppDatabase

/**
 * Created by gurmail on 01/05/19.
 * @author gurmail
 */
class ChannelListRepo(context: Context)  {

    //private val channelListDao: ChannelListDao

    init {
        val database = AppDatabase.getAppDataBase(context)!!
        //channelListDao = database.channelListDao()
    }

    fun getFirstChannels() {

    }


}