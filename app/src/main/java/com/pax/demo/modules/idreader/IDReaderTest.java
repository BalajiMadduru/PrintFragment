package com.pax.demo.modules.idreader;

import android.os.Handler;

import com.pax.dal.IIDReader;
import com.pax.dal.IIDReader.IDReadListener;
import com.pax.dal.entity.IDReadResult;
import com.pax.demo.base.DemoApp;
import com.pax.demo.util.BaseTester;

public class IDReaderTest extends BaseTester {

    private static IDReaderTest idReaderTest;
    private IIDReader reader;

    private IDReaderTest() {
        reader = DemoApp.getDal().getIDReader();
    }

    public synchronized static IDReaderTest getInstance() {
        if (idReaderTest == null) {
            idReaderTest = new IDReaderTest();
        }
        return idReaderTest;
    }

    public void startRead(final Handler handler) {
        if (null != reader){
            reader.start(new IDReadListener() {

                @Override
                public void onRead(IDReadResult result) {
                    handler.obtainMessage(0, result).sendToTarget();
                    logTrue("onRead");
                }

                @Override
                public void onComplete() {
                    //handler.obtainMessage(0, "exit").sendToTarget();
                    logTrue("onComplete");
                }
            });
        }else{
            logTrue("reader is null");
        }
    }
}
