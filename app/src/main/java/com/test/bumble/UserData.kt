package com.test.bumble

data class UserData @JvmOverloads constructor(
var server: String,
var appType: String?,
var appSecretKey: String?,
var userUniqueKey: String?,
var userName: String?,
var email: String?,
var phoneNumber: String?,
var tag: String?,
var isPayment: Boolean?,
var isManager: Boolean?,
var imagePath: String?,
var lang: String?,
val isAnnouncementCount: Boolean?
) {

    constructor(server: String) : this(
    server, "", "", "", "", "", "",
    "", false, false, "", "", true
    )
}