package com.hippo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by gurmail on 01/05/19.
 * @author gurmail
 */
@Entity
data class Tags(
        @PrimaryKey
        var tag_id: Int,
        var tagName: String?,
        var tagColor: String?,
        var status: Int,
        var tagType: Int?
)