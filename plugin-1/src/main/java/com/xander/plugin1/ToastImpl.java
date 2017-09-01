package com.xander.plugin1;

import com.xander.plugin.common.IToast;

/**
 * Created by xanderwang on 2017/9/1.
 */

public class ToastImpl implements IToast {
    @Override
    public String getToast() {
        return "我是 com.xander.plugin1";
    }
}
