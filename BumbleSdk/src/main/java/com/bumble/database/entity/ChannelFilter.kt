package com.hippo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by gurmail on 01/05/19.
 * @author gurmail
 */

@Entity
data class ChannelFilter(
        @PrimaryKey
        var id: Int,
        var name: String?
)