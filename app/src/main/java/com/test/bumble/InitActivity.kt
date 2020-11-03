package com.test.bumble

import acom.test.bumble.R
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.bumble.BumbleConfigAttributes
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_init.*
import kotlinx.android.synthetic.main.activity_main.*

class InitActivity : AppCompatActivity() {

    var server = arrayOf("dev", "dev3003", "dev3004", "test", "live", "beta-live")

    var selectedServer = "dev"
    var app_type = "1"
    //    var app_secret_key = "f58fe228d784dab9157873cd1fd44aa7" // professor
    var app_secret_key = "0e786bbd02b1fab15cf1aed765d638e5"  // g7
//    var app_secret_key = "9e0f69987b39aae01ff258b84132cca3"  // chander



    var user_unique_key = "qwerty"
    var user_name: String? = ""

    var email: String? = ""
    var phone: String? = ""
    var tags: String? = ""
    var image: String? = ""
    var lang: String? = ""

    var isAnnouncementCount: Boolean = true
    var isPaymentEnabled: Boolean = true
    var isBroadCastEnable: Boolean = false
    var isTookan: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)

        Paper.init(this)


        Log.e("Deeplink =", "getIntent().getData()=" + intent.data)
        Log.e("Deeplink =", "getIntent().getExtras()=" + intent.extras)

        //Paper.book("demo").write("data", intent.extras)
        DataClass.setFuguBundle(intent.extras)
        val data: UserData = Paper.book("demo").read("data", UserData(""))
        if(!TextUtils.isEmpty(data.server) && (!TextUtils.isEmpty(data.appSecretKey) && data.appSecretKey!! != et_app_secret_key.hint.toString()
                        || data.userUniqueKey!! != et_user_unique_key.hint.toString())) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("is_manager", data.isManager)
            intent.putExtra("server", data.server)
            intent.putExtra("app_type", data.appType)
            intent.putExtra("app_secret_key", data.appSecretKey)
            intent.putExtra("user_unique_key", data.userUniqueKey)
            intent.putExtra("user_name", data.userName)
            intent.putExtra("email", data.email)
            intent.putExtra("phone", data.phoneNumber)
            intent.putExtra("tags", data.tag)
            intent.putExtra("isPaymentEnabled", data.isPayment)
            intent.putExtra("isManager", data.isManager)
            intent.putExtra("image_path", data.imagePath)
            intent.putExtra("lang", data.lang)
            intent.putExtra("isAnnouncementCount", data.isAnnouncementCount)
            startActivity(intent)
            finish()

        }

        announcement_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            isAnnouncementCount = isChecked
        }

        announcement_switch.isChecked = true

        payment_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            isPaymentEnabled = isChecked
        }

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedServer = server[position]
            }
        }

        broadcast_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            isBroadCastEnable = isChecked
        }

        tooken_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            isTookan = isChecked
        }


        //Paper.book().

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, server)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = aa


        init.setOnClickListener {
            //HippoConfig.getInstance().openDevicePermission(this@InitActivity)
            if(layout_sdk.visibility == View.VISIBLE) {

                if(!TextUtils.isEmpty(et_app_type.text.toString().trim()))
                    app_type = et_app_type.text.toString().trim()

                if(!TextUtils.isEmpty(et_app_secret_key.text.toString().trim()))
                    app_secret_key = et_app_secret_key.text.toString().trim()

                if(!TextUtils.isEmpty(et_user_unique_key.text.toString().trim()))
                    user_unique_key = et_user_unique_key.text.toString().trim()

                if(!TextUtils.isEmpty(et_user_name.text.toString().trim()))
                    user_name = et_user_name.text.toString().trim()

                if(!TextUtils.isEmpty(et_phone.text.toString().trim()))
                    phone = et_phone.text.toString().trim()

                if(!TextUtils.isEmpty(et_email.text.toString().trim()))
                    email = et_email.text.toString().trim()

                if(!TextUtils.isEmpty(et_tags.text.toString().trim()))
                    tags = et_tags.text.toString().trim()

                if(!TextUtils.isEmpty(et_image_path.text.toString().trim()))
                    image = et_image_path.text.toString().trim()

                if(!TextUtils.isEmpty(et_lang.text.toString().trim()))
                    lang = et_lang.text.toString().trim()




                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("is_manager", false)
                intent.putExtra("server", selectedServer)
                intent.putExtra("app_type", app_type)
                intent.putExtra("app_secret_key", app_secret_key)
                intent.putExtra("user_unique_key", user_unique_key)
                intent.putExtra("user_name", user_name)
                intent.putExtra("email", email)
                intent.putExtra("phone", phone)
                intent.putExtra("tags", tags)
                intent.putExtra("isPaymentEnabled", isPaymentEnabled)
                intent.putExtra("image_path", image)
                intent.putExtra("lang", lang)
                intent.putExtra("isAnnouncementCount", isAnnouncementCount)


                val data = UserData(server = selectedServer, appType = app_type, appSecretKey = app_secret_key,
                        userUniqueKey = user_unique_key, userName = user_name, email = email, phoneNumber = phone,
                        tag = tags, isPayment = isPaymentEnabled, isManager = false, imagePath = image, lang = lang,
                        isAnnouncementCount = isAnnouncementCount)


                Paper.book("demo").write("data", data)

                startActivity(intent)
                finish()
            }
        }
    }
}
