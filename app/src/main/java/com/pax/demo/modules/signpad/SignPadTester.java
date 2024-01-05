package com.pax.demo.modules.signpad;

import com.pax.dal.ISignPad;
import com.pax.dal.entity.SignPadResp;
import com.pax.demo.base.DemoApp;
import com.pax.demo.util.BaseTester;

public class SignPadTester extends BaseTester {

    private static SignPadTester signPadTester;

    private ISignPad signPad;

    private SignPadTester() {
        signPad = DemoApp.getDal().getSignPad();
    }

    public static SignPadTester getInstance() {
        if (signPadTester == null)
            signPadTester = new SignPadTester();
        return signPadTester;
    }

    public SignPadResp signStart(String serial) {
        SignPadResp resp = signPad.signStart(serial);
        logTrue("signStart");
        return resp;

    }

    public void displayWord(int x, int y, byte dataID, byte flag, byte confirm, int displayTime) {
        signPad.displayWord(x, y, dataID, flag, confirm, displayTime);
        logTrue("displayWord");
    }

    public void cancle() {
        signPad.cancel();
        logTrue("cancle");
    }
}
