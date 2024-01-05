package com.pax.demo.modules.wifiprobe;

import java.util.List;

import android.os.Handler;
import android.os.Message;

import com.pax.dal.IWifiProbe;
import com.pax.dal.IWifiProbe.ProbeListener;
import com.pax.demo.base.DemoApp;
import com.pax.demo.util.BaseTester;

public class WifiProbeTester extends BaseTester {

    private static WifiProbeTester probeTester;
    private IWifiProbe wifiProbe;

    private WifiProbeTester() {

    }

    public static synchronized WifiProbeTester getInstance() {
        if (probeTester == null) {
            probeTester = new WifiProbeTester();
        }
        probeTester.wifiProbe = DemoApp.getDal().getWifiProbe();
        return probeTester;
    }

    public void start(final Handler handler) {
        wifiProbe.start(new ProbeListener() {

            @Override
            public void onProbeItem(String probeinfo, String rssi) {
                logTrue("onProbeItem");
                Message.obtain(handler, 0, probeinfo + rssi).sendToTarget();
            }

            @Override
            public void onFinish() {
                logTrue("onFinish");
                Message.obtain(handler, 0, "finished").sendToTarget();
            }

            @Override
            public void onFailure(int reason) {
                logTrue("onFailure");
                Message.obtain(handler, 0, "failed").sendToTarget();
            }
        });
        logTrue("start");
    }

    public void stop() {
        wifiProbe.stop();
        logTrue("stop");
    }

    public int getStatus() {
        int res = wifiProbe.getStatus();
        logTrue("getStatus");
        return res;
    }

    public List<String> getResults() {
        List<String> resList = wifiProbe.getResults();
        logTrue("getResults");
        return resList;
    }
}
