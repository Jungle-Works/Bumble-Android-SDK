package com.bumble.activity;

import android.os.Bundle;

import com.bumble.fragment.CampaignFragment;
import com.bumblesdk.R;


public class CampaignActivity extends FuguBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hippo_activity_campaign);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new CampaignFragment(), CampaignFragment.class.getName())
                .commitAllowingStateLoss();
    }
}