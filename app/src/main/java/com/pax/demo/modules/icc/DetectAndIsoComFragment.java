package com.pax.demo.modules.icc;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;
import com.pax.demo.util.Convert;
import com.pax.demo.util.IApdu;
import com.pax.demo.util.IApdu.IApduReq;
import com.pax.demo.util.IApdu.IApduResp;
import com.pax.demo.util.Packer;

import java.io.UnsupportedEncodingException;

public class DetectAndIsoComFragment extends BaseFragment {

    private TextView textView;
    public static IccDectedThread iccDectedThread;
    public static boolean b = false;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    textView.setText(msg.obj.toString());
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        textView = (TextView) view.findViewById(R.id.fragment_textview);
        textView.setTextSize(14);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        if (iccDectedThread == null) {
            iccDectedThread = new IccDectedThread();
            iccDectedThread.start();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        if (iccDectedThread != null) {
            iccDectedThread.interrupt();
            iccDectedThread = null;
            IccTester.getInstance().light(false);
        }
        super.onDestroy();
    }

    public class IccDectedThread extends Thread {
        @Override
        public void run() {
            super.run();
            String resString = "";
            IccTester.getInstance().light(true);
            while (!Thread.interrupted()) {
                b = IccTester.getInstance().detect((byte) 0);
                if (b) {
                    resString = getResources().getString(R.string.icc_detect_havecard);
                    byte[] res = IccTester.getInstance().init((byte) 0);
                    if (res == null) {
                        Log.i("Test", "init ic card,but no response");
                        return;
                    }
                    resString += ("\ninit response：" + Convert.getInstance().bcdToStr(res));
                    IccTester.getInstance().autoResp((byte) 0, true);// 设置iccIsoCommand函数是否自动发送GET RESPONSE指令。

                    IApdu apdu = Packer.getInstance().getApdu();
                    IApduReq apduReq = apdu.createReq((byte) 0x00, (byte) 0xa4, (byte) 0x04, (byte) 0x00,
                            "1PAY.SYS.DDF01".getBytes(), (byte) 0);
                    byte[] req = apduReq.pack();
                    // byte[] req =
                    // getObject.getIGLs().getConvert().strToBcd("00A404000E315041592E5359532E444446303100",
                    // EPaddingPosition.PADDING_LEFT);
                    // new TestLog().logTrue("apduReq"+getObject.getIGLs().getConvert().bcdToStr(apdu.pack(apduReq)));
                    byte[] isoRes = IccTester.getInstance().isoCommand((byte) 0, req);

                    if (isoRes != null) {
                        IApduResp apduResp = apdu.unpack(isoRes);
                        String isoStr = null;
                        try {
                            isoStr = "isocommand response:" + " Data:" + new String(apduResp.getData(), "iso8859-1")
                                    + " Status:" + apduResp.getStatus() + " StatusString:" + apduResp.getStatusString();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        resString += ("\n" + isoStr);
                    }
                    IccTester.getInstance().close((byte) 0);
                    IccTester.getInstance().light(false);
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = resString;
                    handler.sendMessage(message);

                    SystemClock.sleep(2000);
                    break;

                } else {
                    resString = getResources().getString(R.string.icc_detect_nocard);
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = resString;
                    handler.sendMessage(message);

                    SystemClock.sleep(2000);
                }
            }
        }
    }
}
