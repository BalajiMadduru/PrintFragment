package com.pax.demo.modules.comm;

import com.pax.dal.IDalCommManager;
import com.pax.dal.entity.ERoute;
import com.pax.demo.base.DemoApp;
import com.pax.demo.util.BaseTester;

public class CommTester extends BaseTester {

    private static CommTester tester;
    private IDalCommManager commManager;

    private CommTester() {
        commManager = DemoApp.getDal().getCommManager();
    }

    public synchronized static CommTester getInstance() {
        if (tester == null) {
            tester = new CommTester();
        }
        return tester;
    }

    public boolean enableMultiPath() {
        boolean b = commManager.enableMultiPath();
        logTrue("enableMultiPath");
        return b;
    }

    public boolean disableMultiPath() {
        boolean b = commManager.disableMultiPath();
        logTrue("disableMultiPath");
        return b;
    }

    public boolean setRoute(String ip, ERoute route) {
        boolean b = commManager.setRoute(ip, route);
        logTrue("setRoute");
        return b;
    }

    public int switchApn(String name, String newApn, String username, String password, int authType) {
        int res = commManager.switchAPN(name, newApn, username, password, authType);
        if (res == 1)
            logTrue("switchAPN");
        else
            logErr("switchAPN", "");
        return res;
    }
}
