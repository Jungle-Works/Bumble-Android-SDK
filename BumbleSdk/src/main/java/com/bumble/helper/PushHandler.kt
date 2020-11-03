package com.bumble.helper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import android.text.Html
import android.text.TextUtils
import android.view.View
import com.bumble.Utils.DateUtils
import com.bumble.constants.BumbleAppConstants
import com.bumble.database.CommonData
import com.bumble.service.BumblePushIntentService
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by gurmail on 03/05/19.
 * @author gurmail
 */
class PushHandler : BumbleAppConstants {

    val NOTIFICATION_CHANNEL_ID = "12001"
    val CHANNEL_ONE_NAME = "Default notification"

    fun notificationMissedCall(context: Context, data: JSONObject, icon: Int) {
        showMissedCall(context, data, icon)
    }


    private fun showMissedCall(context: Context, data: JSONObject, icon: Int) {
        val message : String = data.optString("message")
        var title : String = data.optString("title")
        val timeStamp = data.optString("date_time")
        val channelId = data.optLong("channel_id", -1)

        val notificationIntent = Intent(context, BumblePushIntentService::class.java)
        notificationIntent.putExtra("channelId", channelId)
        notificationIntent.putExtra("json", data.toString())
        notificationIntent.putExtra("is_from_push", true)
        notificationIntent.putExtra("en_user_id", CommonData.getUserDetails().data.en_user_id)
        notificationIntent.putExtra("userId", CommonData.getUserDetails().data.userId)

        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pi = PendingIntent.getService(context, 112, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        var mBuilder: NotificationCompat.Builder
        mBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)


        mBuilder.setContentTitle(title)
        mBuilder.setContentIntent(pi)
        mBuilder.setWhen(getTimeMilliSec(DateUtils.getInstance().convertToLocal(timeStamp)))
        mBuilder.setSmallIcon(icon)
        mBuilder.setDefaults(Notification.DEFAULT_SOUND)
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.resources, icon))
        mBuilder.setContentText(Html.fromHtml(message))
        mBuilder.priority = getPriority()
        mBuilder.setAutoCancel(true)


        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, CHANNEL_ONE_NAME, importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            //notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            assert(notificationManager != null)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notification = mBuilder.build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val smallIconViewId = context.getResources().getIdentifier("right_icon", "id", android.R::class.java.getPackage()!!.name)

            try {
                if (smallIconViewId != 0) {
                    if (notification.contentIntent != null)
                        notification.contentView.setViewVisibility(smallIconViewId, View.INVISIBLE)

                    if (notification.headsUpContentView != null)
                        notification.headsUpContentView.setViewVisibility(smallIconViewId, View.INVISIBLE)

                    if (notification.bigContentView != null)
                        notification.bigContentView.setViewVisibility(smallIconViewId, View.INVISIBLE)
                }
            } catch (e: Exception) {

            }

        }
        val id = System.currentTimeMillis().toInt()
        notificationManager.notify(id, notification)
    }

    /**
     * Set notification priority as per API level of device
     */
    private fun getPriority(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_MAX
        } else {
            NotificationCompat.PRIORITY_HIGH
        }
    }

    private fun getTimeMilliSec(timeStamp: String): Long {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        try {
            if (!TextUtils.isEmpty(timeStamp)) {
                val date = format.parse(timeStamp)
                return date.time
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return 0
    }

}