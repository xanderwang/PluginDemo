package com.xander.pluginapp.utils;

import android.os.Environment;

import com.socks.library.KLog;
import com.xander.plugin.common.IToast;

import java.io.File;
import java.util.HashMap;

import dalvik.system.DexClassLoader;

/**
 * Created by xanderwang on 2017/9/1.
 */

public class DexUtils {

    public static final String SD_CARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String DEX_DIR = SD_CARD + "/plugin";

    public static String OUT_DEX_DIR = "";

    private static HashMap<String, DexClassLoader> dexClassLoaderHashMap = new HashMap<>();

    public static DexClassLoader loadPluginApk(String pluginName, ClassLoader classLoader) {
        File pluginFile = new File(DEX_DIR, pluginName);
        if (!pluginFile.exists()) {
            return null;
        }
        KLog.d("plugin " + pluginFile.getAbsolutePath());
        DexClassLoader dexClassLoader = dexClassLoaderHashMap.get(pluginName);
        if (dexClassLoader == null) {
            dexClassLoader = new DexClassLoader(
                OUT_DEX_DIR,
                DEX_DIR,
                null,
                classLoader
            );
            dexClassLoaderHashMap.put(pluginName, dexClassLoader);
        }
        return dexClassLoader;
    }

    public static IToast getDexIToast(String pluginName, ClassLoader classLoader) {
        DexClassLoader dexClassLoader = loadPluginApk(pluginName, classLoader);
        if (dexClassLoader == null) {
            return new IToast() {
                @Override
                public String getToast() {
                    return "null dexClassLoader";
                }
            };
        }
        File dexDir = new File(OUT_DEX_DIR);
        printDir(dexDir);

        final Exception exception;
        try {
            Class toast = dexClassLoader.loadClass("com.xander.plugin1.ToastImpl");
            IToast iToast = (IToast) toast.newInstance();
            return iToast;
        } catch (ClassNotFoundException e) {
            exception = e;
            e.printStackTrace();
        } catch (InstantiationException e) {
            exception = e;
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            exception = e;
            e.printStackTrace();
        }
        return new IToast() {
            @Override
            public String getToast() {
                return exception == null ? "unknow error" : exception.getLocalizedMessage();
            }
        };
    }

    private static void printDir(File dir) {
        if (null == dir) {
            KLog.d("dir is null");
            return;
        }
        KLog.d("scan:" + dir.getAbsolutePath());
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if( files.length == 0 ) {
                KLog.d(dir.getAbsolutePath() + " is empty");
            }
            for (File file : files) {
                printDir(file);
            }
        }
    }

}
