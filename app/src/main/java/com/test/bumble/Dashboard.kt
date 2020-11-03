package com.test.bumble

import acom.test.bumble.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumble.BumbleConfig
import com.bumble.MobileCampaignBuilderBumble
import com.bumble.NotificationListener
import com.bumble.Utils.ToastUtil
import com.bumble.model.promotional.PromotionResponse
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_dashboard.*


/**
 * Created by gurmail on 11/06/19.
 * @author gurmail
 */
class Dashboard : Fragment(), NotificationListener {

    var activity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity() as MainActivity?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnCampaign.setOnClickListener {
            val attributes: MobileCampaignBuilderBumble = MobileCampaignBuilderBumble.Builder()
                    .setParseFormat("dd, MMM h:mm a")
                    .build()
            BumbleConfig.getInstance().openMobileCampaigns(activity, attributes)
        }
        btnLogout.setOnClickListener {
            Paper.book("demo").destroy()
            BumbleConfig.clearBumbleData(activity)
            activity?.startActivity(Intent(activity, InitActivity::class.java))
            activity?.finish()
        }
    }

    override fun onFailureListener() {
        TODO("Not yet implemented")
    }

    override fun onSucessListener(response: PromotionResponse?) {
        TODO("Not yet implemented")
    }


    override fun onItemClickListener(data: String?) {
        ToastUtil.getInstance(activity).showToast("Clicked: $data")
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }





}
