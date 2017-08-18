package com.xander.pluginapp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.socks.library.KLog;
import com.socks.library.KLogUtil;
import com.xander.pluginapp.bean.PluginBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dalvik.system.PathClassLoader;

/**
 * Created by xanderwang on 2017/8/18.
 */

public class Utils {

    private static HashMap<String, Context> contextMap = new HashMap<>();
    private static HashMap<String, PathClassLoader> pathClassLoaderMap = new HashMap<>();

    public static ArrayList<PluginBean> getInstallPlugins(Context context) {
        ArrayList<PluginBean> pluginBeans = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packageInfoList = pm.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES);
        for (PackageInfo packageInfo : packageInfoList) {
            PluginBean pluginBean = new PluginBean();
            pluginBean.setPkg(packageInfo.packageName);
            pluginBean.setName(packageInfo.applicationInfo.name);
            pluginBeans.add(pluginBean);
        }
        return pluginBeans;
    }

    public static Drawable loadPluginDrawable(Context context, PluginBean pluginBean, String resName) {
        return loadPluginDrawable(context, pluginBean.getPkg(), resName);
    }

    private static Drawable loadPluginDrawable(Context context, String packageName, String resName) {
        Context pluginContext = contextMap.get(packageName);
        if (null == pluginContext) {
            try {
                pluginContext = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
                contextMap.put(packageName, pluginContext);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (null != pluginContext) {
            int resId = getResourceId(pluginContext, packageName, "minmap", "ic_launcher");
            KLog.d("resId:" + resId);
            return pluginContext.getResources().getDrawable(resId);
        }
        return null;
    }

    private static int getResourceId(Context pluginContext, String packageName, String type, String resName) {
        // 第一个参数为包含dex的apk或者jar的路径，第二个参数为父加载器
        PathClassLoader pathClassLoader = pathClassLoaderMap.get(packageName);
        if (null == pathClassLoader) {
            pathClassLoader = new PathClassLoader(pluginContext.getPackageResourcePath(), ClassLoader.getSystemClassLoader());
            pathClassLoaderMap.put(packageName, pathClassLoader);
        }
        try {
            Class<?> clazz = null;
            if (true) {
                //通过使用自身的加载器反射出mipmap类进而使用该类的功能
                clazz = pathClassLoader.loadClass(packageName + ".R$" + type);
            } else {
                //参数：1、类的全名，2、是否初始化类，3、加载时使用的类加载器
                clazz = Class.forName(packageName + ".R$" + type, true, pathClassLoader);
            }
            Field field = clazz.getDeclaredField(resName);
            int resourceId = field.getInt(clazz);
            //使用上述两种方式都可以，这里我们得到R类中的内部类mipmap，通过它得到对应的图片id，进而给我们使用
            return resourceId;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
