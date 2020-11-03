package com.hippo.database.dao

import androidx.room.*
import androidx.room.Delete
import com.hippo.database.entity.ChannelInfo


/**
 * Created by gurmail on 30/04/19.
 * @author gurmail
 */
@Dao
interface ChannelInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateChannel(channelInfo: ChannelInfo)

    @Delete()
    fun deleteChannels(vararg channelInfo: ChannelInfo)

    @Query("Select * from ChannelInfo Order by updatedAt")
    fun getChannelInfo() : List<ChannelInfo>

    @Query("Select * from channelinfo where updatedAt < :timeStamp")
    fun getChannelInfo(timeStamp: Long) : List<ChannelInfo>

    @Query("Select * from ChannelInfo where channelId = :channelId Order by updatedAt")
    fun getChannelInfo(channelId: Int) : ChannelInfo

    @Query("Delete from ChannelInfo where channelId = :channelId")
    fun deleteChannelInfo(channelId: Int)

    @Query("Update ChannelInfo SET updatedAt =:value where channelId =:id")
    fun updateChannelInfo(value: Long, id: Int)

}