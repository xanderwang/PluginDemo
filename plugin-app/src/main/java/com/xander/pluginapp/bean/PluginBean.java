package com.xander.pluginapp.bean;

/**
 * Created by xanderwang on 2017/8/18.
 */

public class PluginBean {
    private String name;
    private String path;
    private String pkg;

    public PluginBean() {
    }

    public PluginBean(String name, String path, String pkg) {
        this.name = name;
        this.path = path;
        this.pkg = pkg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }
}
