package com.affinitymapper.affinitymapper.activities;

import android.widget.TabHost;
import android.app.TabActivity;
import android.widget.TabHost.OnTabChangeListener;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import com.affinitymapper.affinitymapper.R;

public class TabBar extends TabActivity implements OnTabChangeListener{

    TabHost tabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_am);

        // Get TabHost Reference
        tabHost = getTabHost();

        // Set TabChangeListener called when tab changed
        tabHost.setOnTabChangedListener(this);

        TabHost.TabSpec spec;
        Intent intent;

        //Interests
        // Create  Intents to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, Interests.class);
        spec = tabHost.newTabSpec("Interests").setIndicator("")
                .setContent(intent);

        //Add intent to tab
        tabHost.addTab(spec);

        /** TO DO
        intent = new Intent().setClass(this, EditProfile.class);
        spec = tabHost.newTabSpec("Edit Profile").setIndicator("")
                .setContent(intent);
        tabHost.addTab(spec);
         **/

        // Set drawable images to tab
        tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.settings_not_selected);

        // Set Interests as Default tab and change image
        tabHost.getTabWidget().setCurrentTab(0);
        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.interests_selected);

    }


    @Override
    public void onTabChanged(String tabId) {

        /************ Called when tab changed *************/

        //********* Check current selected tab and change according images *******/

        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            if(i==0)
                tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.interests_selected);
            else if(i==1)
                tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.settings_selected);
        }
    }
}