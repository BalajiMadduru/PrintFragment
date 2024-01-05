package com.pax.demo.modules.keyboard;

import com.pax.dal.IKeyBoard;
import com.pax.dal.entity.EKeyCode;
import com.pax.demo.base.DemoApp;
import com.pax.demo.util.BaseTester;

public class KeyBoardTester extends BaseTester {

    private IKeyBoard keyBoard;
    private static KeyBoardTester cameraTester;

    private KeyBoardTester() {
        keyBoard = DemoApp.getDal().getKeyBoard();
    }

    public static KeyBoardTester getInstance() {
        if (cameraTester == null) {
            cameraTester = new KeyBoardTester();
        }
        return cameraTester;
    }

    public boolean isHit() {
        boolean hit = keyBoard.isHit();
        logTrue("isHit");
        return hit;
    }

    public void clear() {
        keyBoard.clear();
        logTrue("clear");
    }

    public EKeyCode getKey() {
        EKeyCode keyCode = keyBoard.getKey();
        logTrue("getKey");
        return keyCode;
    }

    public void setMute(boolean isMute) {
        keyBoard.setMute(isMute);
        logTrue("setMute");
    }

}
