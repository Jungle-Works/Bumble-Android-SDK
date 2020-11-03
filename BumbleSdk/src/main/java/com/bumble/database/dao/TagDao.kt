package com.hippo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hippo.database.entity.Tags

/**
 * Created by gurmail on 01/05/19.
 * @author gurmail
 */
@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tags: Tags)

    @Query("select * from Tags")
    fun getAllTags() : List<Tags>

}