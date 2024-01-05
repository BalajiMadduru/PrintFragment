package com.pax.demo.modules.comm;

import com.pax.dal.IChannel;
import com.pax.dal.entity.EChannelType;
import com.pax.dal.exceptions.ChannelException;
import com.pax.demo.base.DemoApp;
import com.pax.demo.util.BaseTester;

public class ChannelTester extends BaseTester {

    private static ChannelTester netWorkTester;
    private int i = 0;

    private ChannelTester() {
    }

    public static ChannelTester getInstance() {
        if (netWorkTester == null) {
            netWorkTester = new ChannelTester();
        }
        return netWorkTester;
    }

    public int enableChannelExclusive(EChannelType channelType, int timeout) {
        int res = DemoApp.getDal().getCommManager().enableChannelExclusive(channelType, timeout);
        logTrue("enableChannelExclusive");
        return res;
    }

    public void enableNetWork(EChannelType channelType) {
        try {
            IChannel channel = DemoApp.getDal().getCommManager().getChannel(channelType);
            if (channel != null) {
                channel.enable();
                logTrue("enable");
            }
        } catch (ChannelException e) {
            e.printStackTrace();
            logErr("enable", e.getMessage());
        }
    }

    public void disableNetWork(EChannelType channelType) {
        try {
            IChannel channel = DemoApp.getDal().getCommManager().getChannel(channelType);
            if (channel != null) {
                channel.disable();
                logTrue("disable");
            }
        } catch (ChannelException e) {
            e.printStackTrace();
            logErr("disable", e.getMessage());
        }
    }

    public String getNetWorkStatus(EChannelType channelType) {
        boolean b = false;
        IChannel channel = DemoApp.getDal().getCommManager().getChannel(channelType);
        if (channel != null) {
            b = channel.isEnabled();
            if (i == 0) {
                logTrue("isEnabled");
                i++;
            }
            return channelType.toString() + (b == true ? " open" : " close");
        }
        return "error";
    }
}
