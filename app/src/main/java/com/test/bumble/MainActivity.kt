package com.test.bumble

import acom.test.bumble.R
import android.Manifest
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumble.*
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigInteger
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.UnknownHostException
import java.nio.ByteOrder
import java.util.*


class MainActivity : AppCompatActivity() {

    var attributes: BumbleConfigAttributes? = null
    var token: String? = null

    var server: String? = null
    var isManager: Boolean = false
    var app_type: String? = null
    var app_secret_key: String? = null
    var user_unique_key: String? = null
    var user_name: String = ""
    var email: String? = ""
    var phone: String? = null
    var tags: String? = null
    var image: String? = null
    var lang: String? = ""

    var isBroadcastEnabled: Boolean = true
    var isPaymentEnabled: Boolean = true
    var isAnnouncementCount: Boolean = true
    var isForking: Boolean = false
    val authToken: String = "42413.163469c2b50a0f1b1c152d3c10112c4018ecc2ff2f"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (checkLocationPermissions()) {
            setInItBumble()
        } else
            setInItBumble()


    }

    private fun checkLocationPermissions(): Boolean {
        /** Code to check whether the TrackingData Permission is Granted  */
        val permissionsRequired = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        return AppManager.getInstance().askUserToGrantPermission(this, permissionsRequired, "Please grant permission to Location", 123)
    }

    private fun setInItBumble() {
        server = intent.getStringExtra("server")
        isManager = intent.getBooleanExtra("is_manager", false)
        isPaymentEnabled = intent.getBooleanExtra("isPaymentEnabled", true)
        isAnnouncementCount = intent.getBooleanExtra("isAnnouncementCount", true)
        app_type = intent.getStringExtra("app_type")
        app_secret_key = intent.getStringExtra("app_secret_key")
        user_unique_key = intent.getStringExtra("user_unique_key")
        if (intent.hasExtra("user_name"))
            user_name = intent.getStringExtra("user_name").toString()
        if (intent.hasExtra("email"))
            email = intent.getStringExtra("email")
        if (intent.hasExtra("phone"))
            phone = intent.getStringExtra("phone")
        if (intent.hasExtra("tags"))
            tags = intent.getStringExtra("tags")
        if (intent.hasExtra("image_path"))
            image = intent.getStringExtra("lang")
        if (intent.hasExtra("image_path"))
            lang = intent.getStringExtra("lang")


        loadFragment(dashBoardFragment())
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
//                R.id.navigation_home -> loadFragment(homeFragment())
                R.id.navigation_dashboard -> loadFragment(dashBoardFragment())
//                R.id.navigation_details -> loadFragment(detailsFragment())
            }
            return@setOnNavigationItemSelectedListener true
        }

        FirebaseInstanceId.getInstance().instanceId
                .addOnSuccessListener(this@MainActivity) { instanceIdResult ->
                    val newToken = instanceIdResult.token
                    Log.e("newToken", newToken)
                    token = newToken
                    initSDK()
                }
    }


    fun getWifiIPAddress(): String {
        val wifiMgr = getApplicationContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiMgr.connectionInfo
        val ip = wifiInfo.ipAddress
        return android.text.format.Formatter.formatIpAddress(ip);

    }

    fun getMobileIPAddress(): String {
        try {
            val interfaces: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (intf in interfaces) {
                val addrs: List<InetAddress> = Collections.list(intf.getInetAddresses())
                for (addr in addrs) {
                    if (!addr.isLoopbackAddress) {
                        return addr.hostAddress
                    }
                }
            }
        } catch (ex: Exception) {
        } // for now eat exceptions
        return ""
    }

    private fun getLocalWifiIpAddress(): String {
        val wifiManager = getApplicationContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
        var ipAddress = wifiManager.connectionInfo.ipAddress
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress)
        }
        val ipByteArray: ByteArray = BigInteger.valueOf(ipAddress.toLong()).toByteArray()
        val ipAddressString: String
        ipAddressString = try {
            InetAddress.getByAddress(ipByteArray).hostAddress
        } catch (ex: UnknownHostException) {
            "null"
        }
        return ipAddressString
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            123 -> {
                setInItBumble()
                return
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    fun initSDK() {

        //hippoStatusBar

        val fuguColorConfig = HippoColorConfig.Builder()
//            .hippoActionBarBg(FuguColorConfigStrings.FUGU_ACTION_BAR_BG)
//            .hippoStatusBar(FuguColorConfigStrings.FUGU_STATUS_BAR)
//            .hippoActionBarText(FuguColorConfigStrings.FUGU_ACTION_BAR_TEXT)
//
//            .hippoFloatingBtnBg(FuguColorConfigStrings.FUGU_ACTION_BAR_BG)
//            .hippoFloatingBtnText(FuguColorConfigStrings.FUGU_ACTION_BAR_TEXT)

//            .hippoBgMessageYou(FuguColorConfigStrings.FUGU_BG_MESSAGE_YOU)
//            .hippoBgMessageFrom(FuguColorConfigStrings.FUGU_BG_MESSAGE_FROM)
//            .hippoPrimaryTextMsgYou(FuguColorConfigStrings.FUGU_PRIMARY_TEXT_MSG_YOU)
//            .hippoMessageRead(FuguColorConfigStrings.FUGU_MESSAGE_READ)
//            .hippoPrimaryTextMsgFrom(FuguColorConfigStrings.FUGU_PRIMARY_TEXT_MSG_FROM)
//            .hippoSecondaryTextMsgYou(FuguColorConfigStrings.FUGU_SECONDARY_TEXT_MSG_YOU)
//            .hippoSecondaryTextMsgFrom(FuguColorConfigStrings.FUGU_SECONDARY_TEXT_MSG_FROM)
//            .hippoTextColorPrimary(FuguColorConfigStrings.FUGU_TEXT_COLOR_PRIMARY)
//            .hippoChannelDateText(FuguColorConfigStrings.FUGU_CHANNEL_DATE_TEXT)
//            .hippoChatBg(FuguColorConfigStrings.FUGU_CHAT_BG)
//            .hippoBorderColor(FuguColorConfigStrings.FUGU_BORDER_COLOR)
//            .hippoChatDateText(FuguColorConfigStrings.FUGU_CHAT_DATE_TEXT)
//            .hippoThemeColorPrimary(FuguColorConfigStrings.FUGU_THEME_COLOR_PRIMARY)
//            .hippoThemeColorSecondary(FuguColorConfigStrings.FUGU_THEME_COLOR_SECONDARY)
//            .hippoTypeMessageBg(FuguColorConfigStrings.FUGU_TYPE_MESSAGE_BG)
//            .hippoTypeMessageHint(FuguColorConfigStrings.FUGU_TYPE_MESSAGE_HINT)
//            .hippoTypeMessageText(FuguColorConfigStrings.FUGU_TYPE_MESSAGE_TEXT)
//            .hippoChannelBg(FuguColorConfigStrings.FUGU_CHANNEL_BG)
//            .hippoSecondaryTextMsgFromName(FuguColorConfigStrings.FUGU_ACTION_BAR_BG)
                .build()


        val captureUserDataBumble: CaptureUserDataBumble = CaptureUserDataBumble.Builder()
                .userUniqueKey(user_unique_key)
                .fullName(user_name)
                .phoneNumber(phone)
                .email(email)
                .addressLine1("plot no 5")
                .addressLine2("CDCL Building")
                .city("Chandigarh")
                .region("Chandigarh")
                .country("India")
                .zipCode("160019")
                .latitude(30.16)
                .longitude(76.34)
                .fetchBusinessLang(true)
                .setLang(lang)
                .build()

//        image = "https://thumbs.dreamstime.com/b/cute-boy-cartoon-illustration-87282832.jpg"
        //image = "https://rukminim1.flixcart.com/image/704/704/poster/t/g/n/iron-man-paper-poster-12-x18-with-4-acrylic-sticker-free-original-imaej3pmqpcxzgjs.jpeg"


        val additionalInfo = AdditionalInfo.Builder()
                .needDeviceOptimization(true)
                .prePaymentMethodFetched(true)
                .hasChannelPager(true)
                .hasCreateNewChat(false)
                //.setCreateChatBtnText("Consult Now")
                //.setEmptyChannelList("No data found")
                .replyOnFeedback(true)
                .showAgentImage(true)
                .showEmptyChatBtn(true)
                .isAnnouncementCount(isAnnouncementCount)
                .build()


        val attributes = BumbleConfigAttributes.Builder()
                .setAppKey(app_secret_key)
                .setAppType(app_type)
                .setManager(isManager)
                .setAppType(app_type)
                .setCaptureUserData(captureUserDataBumble)
                .setEnvironment(server)
                .setProvider("com.test.hipposdk.provider")
                .setUnreadCount(false)
                .setShowLog(true)
                .setDeviceToken(token)
                .setBroadcastEnabled(isBroadcastEnabled)
                .setPaymentEnabled(isPaymentEnabled)
                .isForking(isForking)
                .setAuthToken(app_secret_key)
//                .setImagePath(image)
                //.setColorConfig(fuguColorConfig)
//                .setAdditionalInfo(additionalInfo)
                .build()


        BumbleConfig.initBumbleConfig(this, attributes, object : BumbleInitCallback {
            override fun onPutUserResponse() {

            }

            override fun onErrorResponse() {

            }

            override fun hasData() {

            }

        })
        BumbleConfig.getInstance().isSetSkipNumber = false
//        BumbleConfig.getInstance().setShowCreateBtn(true)

        BumbleNotificationConfig.handleHippoPushNotification(this@MainActivity, DataClass.getFuguBundle())

        BumbleConfig.getInstance().setApiListener(object : OnApiCallback {
            override fun onSucess() {
                Log.e("TAG", "API sucess")
            }

            override fun onFailure(errorMessage: String?) {
                Log.e("TAG", "API failed " + errorMessage)
            }

            override fun onProcessing() {
                Log.e("TAG", "API in progress")
            }

        })


        questions["How much time it will take to reach?"] = 1
        questions["Are you coming?"] = 2
        questions["Come to location shown in the app"] = 3
        questions["Where are you"] = 4
        questions["I am on my way"] = 5


        suggestions[1] = "How much time it will take to reach?"
        suggestions[2] = "Are you coming?"
        suggestions[3] = "Come to location shown in the app"
        suggestions[4] = "Where are you"
        suggestions[5] = "I am on my way"
        suggestions[6] = "Ok"
        suggestions[7] = "2 mins"
        suggestions[8] = "5 mins"
        suggestions[9] = "10 mins"
        suggestions[10] = "Yes, on my way"


        // for driver
        mapping[-1] = arrayListOf(4, 5)
        mapping[0] = arrayListOf(6)
        mapping[1] = arrayListOf(7, 8, 9)
        mapping[2] = arrayListOf(10)
        mapping[3] = arrayListOf(6)


        BumbleConfig.getInstance().questions = questions
        BumbleConfig.getInstance().suggestions = suggestions
        BumbleConfig.getInstance().mapping = mapping
        BumbleConfig.getInstance()
    }


    private var questions = HashMap<String, Int>()
    private var suggestions = HashMap<Int, String>()
    private var mapping = HashMap<Int, ArrayList<Int>>()

    //    private var homeFragment: HomeFragment? = null
    private var dashBoardFragment: Dashboard? = null

    //    private var detailsFragment: Details? = null
//
//    private fun homeFragment(): Fragment {
//        if (homeFragment == null) {
//            val bdl = Bundle()
//            bdl.putBoolean("isManager", isManager)
//            homeFragment = HomeFragment()
//            homeFragment!!.arguments = bdl
//        }
//        return homeFragment!!
//    }
//
    private fun dashBoardFragment(): Dashboard {
        if (dashBoardFragment == null) {
            val bdl = Bundle()
            bdl.putBoolean("isManager", isManager)
            bdl.putString("user_unique_key", user_unique_key)
            dashBoardFragment = Dashboard()
            dashBoardFragment!!.arguments = bdl
        }
        return dashBoardFragment!!
    }


    //    // load fragment
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commitAllowingStateLoss()
    }


    //==================================== for conversation type apps ==============================//

}