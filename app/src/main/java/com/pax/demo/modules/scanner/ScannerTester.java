package com.pax.demo.modules.scanner;

import android.os.Handler;
import android.os.Message;

import com.pax.dal.IScanner;
import com.pax.dal.IScanner.IScanListener;
import com.pax.dal.entity.EScannerType;
import com.pax.demo.base.DemoApp;
import com.pax.demo.util.BaseTester;

public class ScannerTester extends BaseTester {
    private static ScannerTester cameraTester;

    private static EScannerType scannerType;

    private IScanner scanner;

    private ScannerTester(EScannerType type) {
        ScannerTester.scannerType = type;
        logTrue(scannerType.name());
        scanner = DemoApp.getDal().getScanner(scannerType);
    }

    public static ScannerTester getInstance(EScannerType type) {
        if (cameraTester == null || type != scannerType) {
            cameraTester = new ScannerTester(type);
        }
        return cameraTester;
    }

    public void scan(final Handler handler,int timeout) {
        scanner.open();
        logTrue("open");
        setTimeOut(timeout);
        scanner.setContinuousTimes(1);
        scanner.setContinuousInterval(1000);
        scanner.start(new IScanListener() {

            @Override
            public void onRead(String arg0) {
                logTrue("read:" + arg0);
                Message message = Message.obtain();
                message.what = 0;
                message.obj = arg0;
                handler.sendMessage(message);
            }

            @Override
            public void onFinish() {
                logTrue("onFinish");
                close();
            }

            @Override
            public void onCancel() {
                logTrue("onCancel");
                close();
            }
        });

        logTrue("start");
    }

    public void close() {
        scanner.close();
        logTrue("close");
    }
    
    public void setTimeOut(int timeout){
        scanner.setTimeOut(timeout);
        logTrue("setTimeOut");
    }

}
