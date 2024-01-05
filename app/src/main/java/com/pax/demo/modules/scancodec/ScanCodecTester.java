package com.pax.demo.modules.scancodec;

import android.content.Context;

import com.pax.dal.IScanCodec;
import com.pax.dal.entity.DecodeResult;
import com.pax.demo.base.DemoApp;
import com.pax.demo.util.BaseTester;

public class ScanCodecTester extends BaseTester {

    private static ScanCodecTester codecTester;
    private IScanCodec scanCodec;

    private ScanCodecTester() {

    }

    public static synchronized ScanCodecTester getInstance() {
        if (codecTester == null) {
            codecTester = new ScanCodecTester();
        }
        codecTester.scanCodec = DemoApp.getDal().getScanCodec();
        return codecTester;
    }

    public void disableFormat(int format) {
        scanCodec.disableFormat(format);
        logTrue("disableFormat");
    }

    public void enableFormat(int format) {
        scanCodec.enableFormat(format);
        logTrue("enableFormat");
    }

    public void init(Context context, int width, int height) {
        scanCodec.init(context, width, height);
        logTrue("init");
    }

    public DecodeResult decode(byte[] data) {
        DecodeResult result = scanCodec.decode(data);
        logTrue("decode");
        return result;
    }

    public void release() {
        scanCodec.release();
        logTrue("release");
    }
}
