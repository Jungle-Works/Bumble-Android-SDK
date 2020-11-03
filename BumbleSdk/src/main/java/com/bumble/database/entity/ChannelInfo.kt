package com.hippo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by gurmail on 30/04/19.
 * @author gurmail
 */

@Entity
data class ChannelInfo(@PrimaryKey var channelId: Int, var updatedAt: Long)