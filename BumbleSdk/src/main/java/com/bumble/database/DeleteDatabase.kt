package com.hippoagent.database

import android.os.AsyncTask
import com.bumble.BumbleConfig

/**
 * Created by gurmail on 01/05/19.
 * @author gurmail
 */
class DeleteDatabase : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        val database : AppDatabase = AppDatabase.getAppDataBase(BumbleConfig.getInstance().context)!!
        database.clearAllTables()
        AppDatabase.destroyDataBase()
        return null
    }
}