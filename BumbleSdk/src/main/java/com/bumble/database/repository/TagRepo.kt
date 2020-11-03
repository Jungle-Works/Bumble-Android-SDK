package com.hippo.database.repository

import android.content.Context
import android.os.AsyncTask

/**
 * Created by gurmail on 01/05/19.
 * @author gurmail
 */
class TagRepo(context: Context, getData: Boolean) {

    /*private val tagDao: TagDao
    private var tagList: List<Tags>? = null

    init {
        val database: AppDatabase = AppDatabase.getAppDataBase(context)!!
        tagDao = database.tagDao()
        if(getData)
            tagList = tagDao.getAllTags()
    }

    fun getTagsList(): List<Tags>? {
        return tagList
    }

    fun insertTags(tags: ArrayList<Tag>) {
        insertAsync(tagDao).execute(tags)
    }

    class insertAsync internal constructor(val tagDao: TagDao): AsyncTask<ArrayList<Tag>, Void, Void>() {
        override fun doInBackground(vararg params: ArrayList<Tag>?): Void? {
            val tagsList: ArrayList<Tag> = params[0]!!
            for(tag: Tag in tagsList) {
               val tag: Tags = Tags(tag_id = tag.tagId, tagName = tag.tagName,
                       tagColor = tag.colorCode, status = tag.status, tagType = 0)
                tagDao.insert(tag)
            }
            return null
        }
    }*/

}