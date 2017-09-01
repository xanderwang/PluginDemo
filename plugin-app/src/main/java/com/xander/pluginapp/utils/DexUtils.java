package com.xander.pluginapp.utils;

import android.os.Environment;

import java.io.File;
import java.util.HashMap;

import dalvik.system.DexClassLoader;

/**
 * Created by xanderwang on 2017/9/1.
 */

public class DexUtils {

    public static final String SD_CARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String DEX_DIR = SD_CARD + "/plugin";

    private static HashMap<String, DexClassLoader> dexClassLoaderHashMap = new HashMap<>();

    public static DexClassLoader loadPluginApk(String pluginName, ClassLoader classLoader) {
        File dexFile = new File(DEX_DIR, pluginName);
        DexClassLoader dexClassLoader = new DexClassLoader(
            dexFile.getAbsolutePath(),
            DEX_DIR,
            null,
            classLoader
        );
        return dexClassLoader;
    }
}
