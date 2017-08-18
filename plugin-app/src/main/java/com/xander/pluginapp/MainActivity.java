package com.xander.pluginapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.socks.library.KLog;
import com.xander.pluginapp.bean.PluginBean;
import com.xander.pluginapp.utils.Utils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KLog.init(BuildConfig.LOG_DEBUG, "xander");
        ArrayList<PluginBean> apps = Utils.getInstallPlugins(this);
//        KLog.debug(apps);
        Utils.loadPluginDrawable(this, apps.get(0), "ic_launcher");
    }
}
