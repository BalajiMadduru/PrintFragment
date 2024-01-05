package com.pax.demo.modules.cashdrawer;

import com.pax.dal.ICashDrawer;
import com.pax.demo.base.DemoApp;
import com.pax.demo.util.BaseTester;

public class CashDrawerTest extends BaseTester {

    private ICashDrawer cashDrawer;

    private CashDrawerTest() {
        cashDrawer = DemoApp.getDal().getCashDrawer();
    }

    private static class Holder {
        private static final CashDrawerTest instance = new CashDrawerTest();
    }

    public static CashDrawerTest getInstance() {
        return Holder.instance;
    }

    public int open() {
        int ret = cashDrawer.open();
        if (ret == 0) {
            logTrue("open");
        } else {
            logErr("open", "open failed . errCode:" + ret);
        }
        return ret;
    }

}
