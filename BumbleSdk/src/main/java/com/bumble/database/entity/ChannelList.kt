package com.hippo.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bumble.Utils.DateUtils
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by gurmail on 01/05/19.
 * @author gurmail
 */

@Entity(indices = arrayOf(Index(value = arrayOf("channelId"), unique = true)))
data class ChannelList(
        @PrimaryKey
        var channelId: Int,
        var channelName: String,
        var status: Int,
        var botChannelName: String? = null,
        var messageId: String,
        var message: String? = null,
        var createdAt: String? = null,
        var userId: Int? = null,
        var label: String? = null,
        var agentId: Int? = null,
        var agentName: String? = null,
        var imageUrl: String? = null,
        var thumbnailUrl: String? = null,
        var unreadCount: Int? = 0,
        var last_sent_by_id: Int? = 0,
        var last_sent_by_full_name: String? = null,
        var default_message: String? = null,
        var last_message_status: Int? = 2,
        var last_sent_by_user_type: Int? = 0,
        var lastUpdatedAt: String? = null,
        var disableReply: Int? = null,
        var messageState: Int? = null,
        var message_type: Int = 0,
        var callType: String? = null,
        var chatType: Int = 0,
        var inRideTime: Int = 0,
        var inRideText: String? = null,
        @ColumnInfo(name = "date_time")
        var dateTime: String = DateUtils.getInstance().convertToUTC(DateUtils.getFormattedDate(Date())),
        @ColumnInfo(name = "modification_date")
        @SerializedName(value = "modification_date")
        var modificationDate: Date = Date(System.currentTimeMillis())

)