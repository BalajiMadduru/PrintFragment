package com.pax.demo.modules.mag;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pax.dal.entity.TrackData;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class ReadFragment extends BaseFragment {
    private TextView textView;

    static MagReadThread magReadThread;

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
        textView.setText(getResources().getString(R.string.mag_swipe_card));
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        if (magReadThread == null) {
            magReadThread = new MagReadThread();
            MagTester.getInstance().open();
            MagTester.getInstance().reset();
            magReadThread.start();
        }

        return view;
    }

    @Override
    public void onDestroy() {
        if (magReadThread != null) {
            MagTester.getInstance().close();
            magReadThread.interrupt();
            magReadThread = null;
        }
        super.onDestroy();
    }

    class MagReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!Thread.interrupted()) {
                if (MagTester.getInstance().isSwiped()) {
                    TrackData trackData = MagTester.getInstance().read();
                    if (trackData != null) {
                        String resStr = "";
                        if (trackData.getResultCode() == 0) {
                            resStr = getResources().getString(R.string.mag_card_error);
                            Message.obtain(handler, 0, resStr).sendToTarget();
                            continue;
                        }
                        if ((trackData.getResultCode() & 0x01) == 0x01) {
                            resStr += getResources().getString(R.string.mag_track1_data) + trackData.getTrack1();
                            Message.obtain(handler, 0, resStr).sendToTarget();
                        }
                        if ((trackData.getResultCode() & 0x02) == 0x02) {
                            resStr += getResources().getString(R.string.mag_track2_data) + trackData.getTrack2();
                            Message.obtain(handler, 0, resStr).sendToTarget();
                        }
                        if ((trackData.getResultCode() & 0x04) == 0x04) {
                            resStr += getResources().getString(R.string.mag_track3_data) + trackData.getTrack3();
                            Message.obtain(handler, 0, resStr).sendToTarget();
                        }
                        break;
                    }
                    
                }
                SystemClock.sleep(100);
            }
        }
    }
}
