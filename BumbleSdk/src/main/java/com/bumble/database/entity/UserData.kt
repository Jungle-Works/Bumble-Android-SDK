package com.hippo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by gurmail on 01/05/19.
 * @author gurmail
 */

@Entity
data class UserData(
        @PrimaryKey
        var userId: Long,
        var access_token: String?,
        var app_secret_key: String?,
        var business_name: String?,
        var en_user_id: String,
        var business_id: Int,
        var user_name: String?,
        var full_name: String?,
        var email: String?,
        var phone_number: String?,
        var user_channel: String?,
        var user_image: String?,
        var agent_type: Int?,
        var online_status: String?,
        var business_property: String?,
        var billing_plan: Boolean,
        var is_boarding_complete: Int?,
        var is_boarding_skip: Int?,
        var agent_onboarding_completed: Int?,
        var business_unique_name: String?,
        var line_before_feedback: String?,
        var line_after_feedback_1: String?,
        var line_after_feedback_2: String?,
        var business_email: String?,
        var is_video_call_enabled: Int,
        var is_audio_call_enabled: Int,
        var max_file_size: Long,
        var unsupported_message: String?,
        var version: String?
)