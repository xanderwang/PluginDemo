package com.xander.pluginapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.socks.library.KLog;
import com.xander.plugin.common.IToast;
import com.xander.pluginapp.bean.PluginBean;
import com.xander.pluginapp.utils.DexUtils;
import com.xander.pluginapp.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File dexDir = getDir("outdex", 0);
        File t = new File(dexDir,"text.txt");
        if( !t.exists() ) {
            dexDir.mkdirs();
            try {
                t.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        DexUtils.OUT_DEX_DIR = dexDir.getAbsolutePath();

        setContentView(R.layout.activity_main);
        KLog.init(BuildConfig.LOG_DEBUG, "wxy");
        ArrayList<PluginBean> apps = Utils.getInstallPlugins(this);
//        KLog.debug(apps);
//        Utils.loadPluginDrawable(this, apps.get(0), "ic_launcher");
    }

    @Override
    protected void onResume() {
        super.onResume();
        IToast toast = DexUtils.getDexIToast("plugin-1-debug.apk", getClassLoader());
        KLog.d(toast.getToast());
    }
}
