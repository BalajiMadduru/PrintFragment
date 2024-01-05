package com.pax.demo.modules.comm;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;
import com.pax.demo.util.BaseTester;
import com.pax.demo.util.Convert;
import com.pax.demo.util.Convert.EPaddingPosition;

public class ModemFragment extends BaseFragment implements OnClickListener {

    private Button connectBt, sendBt, receiveBt, disconnectBt, recvNonBlockingBt, resetBt, cancelRecvBt;
    private EditText editText, lenEdit, connectTimeoutEdit, sendTimeoutEdit, recvTimeoutEdit;
    static ReceiveThread receiveThread;

    private boolean RECEIVE_FLAG = false;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    editText.setText(msg.obj.toString());
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_comm_icomm, container, false);
        connectBt = (Button) view.findViewById(R.id.fragment_comm_connect);
        sendBt = (Button) view.findViewById(R.id.fragment_comm_send);
        receiveBt = (Button) view.findViewById(R.id.fragment_comm_receive);
        disconnectBt = (Button) view.findViewById(R.id.fragment_comm_disconnect);
        editText = (EditText) view.findViewById(R.id.fragment_comm_result_text);
        lenEdit = (EditText) view.findViewById(R.id.fragment_comm_recv_len);
        recvTimeoutEdit = (EditText) view.findViewById(R.id.fragment_comm_recv_timeout);
        connectTimeoutEdit = (EditText) view.findViewById(R.id.fragment_comm_connect_timeout);
        sendTimeoutEdit = (EditText) view.findViewById(R.id.fragment_comm_send_timeout);
        recvNonBlockingBt = (Button) view.findViewById(R.id.fragment_comm_recvNonBlocking);
        resetBt = (Button) view.findViewById(R.id.fragment_comm_reset);
        cancelRecvBt = (Button) view.findViewById(R.id.fragment_comm_cancelRecv);

        lenEdit.setText("10");
        connectBt.setOnClickListener(this);
        sendBt.setOnClickListener(this);
        receiveBt.setOnClickListener(this);
        disconnectBt.setOnClickListener(this);
        recvNonBlockingBt.setOnClickListener(this);
        resetBt.setOnClickListener(this);
        cancelRecvBt.setOnClickListener(this);

        connectTimeoutEdit.setVisibility(View.INVISIBLE);
        sendTimeoutEdit.setVisibility(View.INVISIBLE);
        recvTimeoutEdit.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_comm_connect:
                ModemTester.getInstance().connect();
                break;
            case R.id.fragment_comm_send:
                // CUP String data =
                // "005B600222000060310031100508000020000000C0001600129630303030303038323938373635343332313031313131310011000000010030002953657175656E6365204E6F3136333039345358582D324B3537363535320003303120";
                String data = "005C600401000060310031317608000020000000c0001600114030303030303032323130323331303036303531303030320011000000010030003053657175656e6365204e6f313730303030443830302d36303030313833320003303120";
                // editText.setText(data);
                byte[] b = Convert.getInstance().strToBcd(data, EPaddingPosition.PADDING_LEFT);
                ModemTester.getInstance().send(b);
                break;
            case R.id.fragment_comm_receive:
                // receive();
                byte[] recv = ModemTester.getInstance().recv(10);
                Log.d("Test", "recv len " + recv.length + " data: " + Convert.getInstance().bcdToStr(recv));

                break;
            case R.id.fragment_comm_disconnect:
                ModemTester.getInstance().disConnect();
                break;
            case R.id.fragment_comm_recvNonBlocking:
                byte[] result = ModemTester.getInstance().recvNonBlocking();
                // editText.setText(IppUser.getInstance(MainActivity.context).getGl().getConvert().bcdToStr(result));
                editText.setText(Convert.getInstance().bcdToStr(result));
                break;
            case R.id.fragment_comm_reset:
                ModemTester.getInstance().reset();
                break;
            case R.id.fragment_comm_cancelRecv:
                ModemTester.getInstance().cancelRecv();
                RECEIVE_FLAG = false;
                break;

            default:
                break;
        }
    }

    private void receive() {
        if (!RECEIVE_FLAG) {
            receiveBt.setText("cancelReceive");
            if (receiveThread == null) {
                receiveThread = new ReceiveThread();
                receiveThread.start();
            }
            RECEIVE_FLAG = true;
            new Thread(new Runnable() {

                @Override
                public void run() {
                    long startTime = SystemClock.currentThreadTimeMillis();
                    long timeOutMs = Long.parseLong(recvTimeoutEdit.getText().toString());
                    while (SystemClock.currentThreadTimeMillis() - startTime < timeOutMs) {
                        if (RECEIVE_FLAG != true) {
                            break;
                        }
                        continue;
                    }
                    if (receiveThread != null) {
                        receiveThread.interrupt();
                        receiveThread = null;
                        ModemTester.getInstance().disConnect();
                        RECEIVE_FLAG = false;
                    }
                    // cancelReceive();
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            receiveBt.setText("receive(auto connect)");
                        }
                    });
                }
            }).start();
        } else {
            receiveBt.setText("receive");
            ModemTester.getInstance().cancelRecv();
            if (receiveThread != null) {
                receiveThread.interrupt();
                receiveThread = null;
                ModemTester.getInstance().disConnect();
                RECEIVE_FLAG = false;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiveThread != null) {
            receiveThread.interrupt();
            receiveThread = null;
        }

    }

    class ReceiveThread extends Thread {
        @Override
        public void run() {
            super.run();
            String len = lenEdit.getText().toString();
            if (len.equals("")) {
                len = "4";
            }

            byte[] data = ModemTester.getInstance().recv(Integer.parseInt(len));
            if (null != data) {
                // String res = IppUser.getInstance().getGl().getConvert().bcdToStr(data);
                String res = Convert.getInstance().bcdToStr(data);
                new BaseTester().logTrue("bcdToStr");
                if (res != null) {
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = res;
                    handler.sendMessage(message);
                    Log.i("Test", "Modem接收到的消息：" + res);
                }

            } else {
                Log.i("Test", "Modem接收到的消息为空");
            }

        }
    }
}