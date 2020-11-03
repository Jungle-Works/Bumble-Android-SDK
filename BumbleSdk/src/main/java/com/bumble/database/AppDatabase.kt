package com.hippoagent.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.hippo.database.entity.ChannelFilter
import com.hippo.database.entity.*
import com.hippo.database.entity.Tags
import com.hippo.database.entity.UserData
import com.hippo.database.dao.ChannelInfoDao

/**
 * Created by gurmail on 27/03/19.
 * @author gurmail
 */
@Database(entities = arrayOf(ChannelInfo::class, ChannelList::class, UserData::class, Tags::class, ChannelFilter::class)
        , version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): ChannelInfoDao
    //abstract fun channelListDao(): ChannelListDao
    //abstract fun tagDao(): TagDao

    companion object {
        @Volatile var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "hippo_customer")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}