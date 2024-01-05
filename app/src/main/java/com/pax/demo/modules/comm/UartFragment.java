package com.pax.demo.modules.comm;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
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

public class UartFragment extends BaseFragment implements OnClickListener {

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
        setTextWatcher();

        connectBt.setOnClickListener(this);
        sendBt.setOnClickListener(this);
        receiveBt.setOnClickListener(this);
        disconnectBt.setOnClickListener(this);
        recvNonBlockingBt.setOnClickListener(this);
        resetBt.setOnClickListener(this);
        cancelRecvBt.setOnClickListener(this);

        return view;
    }

    private void setTextWatcher() {
        recvTimeoutEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                UartTester.getInstance().setRecvTimeout(Integer.parseInt(s.toString()));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        connectTimeoutEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                UartTester.getInstance().setConnectTimeout(Integer.parseInt(s.toString()));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        sendTimeoutEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                UartTester.getInstance().setSendTimeout(Integer.parseInt(s.toString()));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_comm_connect:
                UartTester.getInstance().connect();
                break;
            case R.id.fragment_comm_send:
                String data = editText.getText().toString();
                if (data.equals("")) {
                     data = "123456";
                    //data = "2015银行卡清算机构准入管务院印发的《关于实施银行卡清算机构准入管理的决定》开务院印发的《关于实施银行卡清算机构准入管理的决定》开始实行。这意味着我国银行卡清算市场将全务院印发的《关于实施银行卡清算机构准入管理的决定》开务院印发的《关于实施银行卡清算机构准入管务院印发的《关于实施银行卡清算机构准入管理的决定》开务院印发的《关于实施银行卡清算机构准入管理的决定》开始实行。这意味着我国银行卡清算市场将全面开放。今后，国际卡组织、国内第三方支付机构、银行等符合条件的境内境外机构都可以参面开放。今后，国际卡组织、国内第三方支付机构、银行等符合条件的境内境外机构都可以参与我国银行卡清算市场，通过市场竞争来提升我国银行卡清算服务水平，加快支付服务市场的改革开放和创新转型。[12] 2016年3月17日央行重拳出击整治银行卡收单市场秩序据悉，为规范银行卡收单外包业务，整肃银行卡收单市场秩序，前期，人民银行在全国范围内开展了银行卡收单外包业务专项抽查。此次抽查历时2个月，抽查对象涵盖57家银行和支付机构。根据“统一部署，分级实施，规范业务，突出重点”的抽查方案，人民银行总行采取直接飞行检查与分支行属地检查同步结合方式，以规范外包、打击二清为核心，对虚假商户入网、违规开放交易接口、违规多次转包业务等严厉打击、严肃查处。人民银行的果断出手让市场违规态势明显收敛，银行卡收单市场秩2015年6月1日起，国务院印发的《关于实施银行卡清算机构准入管理的决定》开始实行。这意味着我国银行卡清算市场将全面开放。今后，国际卡组织、国内第三方支付机构、银行等符合条件的境内境外机构都可以参与我国银行卡清算市场，通过市场竞争来提升我国银行卡清算服务水平，加快支付服务市场的改革开放和创新转型。[12] 2016年3月17日央行重拳出击整治银行卡收单市场秩序据悉，为规范银行卡收单外包业务，整肃银行卡收单市场秩序，前期，人民银行在全国范围内开展了银行卡收单外包业务专项抽查。此次抽查历时2个月，抽查对象涵盖57家银行和支付机构。根据“统一部署，分级实施，规范业务，突出重点”的抽查方案，人民银行总行采取直接飞行检查与分支行属地检查同步结合方式，以规范外包、打击二清为核心，对虚假商户入网、违规开放交易接口、违规多次转包业务等严厉打击、严肃查处。人民银行的果断出手让市场违规态势明显收敛，银行卡收单市场秩2015年6月1日起，国务院印发的《关于实施银行卡清算机构准入管理的决定》开始实行。这意味着我国银行卡清算市场将全面开放。今后，国际卡组织、国内第三方支付机构、银行等符合条件的境内境外机构都可以参与我国银行卡清算市场，通过市场竞争来提升我国银行卡清算服务水平，加快支付服务市场的改革开放和创新转型。[12] 2016年3月17日央行重拳出击整治银行卡收单市场秩序据悉，为规范银行卡收单外包业务，整肃银行卡收单市场秩序，前期，人民银行在全国范围内开展了银行卡收单外包业务专项抽查。此次抽查历时2个月，抽查对象涵盖57家银行和支付机构。根据“统一部署，分级实施，规范业务，突出重点”的抽查方案，人民银行总行采取直接飞行检查与分支行属地检查同步结合方式，以规范外包、打击二清为核心，对虚假商户入网、违规开放交易接口、违规多次转包业务等严厉打击、严肃查处。人民银行的果断出手让市场违规态势明显收敛，银行卡收单市场秩2015年6月1日起，国务院印发的《关于实施银行卡清算机构准入管理的决定》开始实行。这意味着我国银行卡清算市场将全面开放。今后，国际卡组织、国内第三方支付机构、银行等符合条件的境内境外机构都可以参与我国银行卡清算市场，通过市场竞争来提升我国银行卡清算服务水平，加快支付服务市场的改革开放和创新转型。[12] 2016年3月17日央行重拳出击整治银行卡收单市场秩序据悉，为规范银行卡收单外包业务，整肃银行卡收单市场秩序，前期，人民银行在全国范围内开展了银行卡收单外包业务专项抽查。此次抽查历时2个月，抽查对象涵盖57家银行和支付机构。根据“统一部署，分级实施，规范业务，突出重点”的抽查方案，人民银行总行采取直接飞行检查与分支行属地检查同步结合方式，以规范外包、打击二清为核心，对虚假商户入网、违规开放交易接口、违规多次转包业务等严厉打击、严肃查处。人民银行的果断出手让市场违规态势明显收敛，银行卡收单市场秩2015年6月1日起，国务院印发的《关于实施银行卡清算机构准入管理的决定》开始实行。这意味着我国银行卡清算市场将全面开放。今后，国际卡组织、国内第三方支付机构、银行等符合条件的境内境外机构都可以参与我国银行卡清算市场，通过市场竞争来提升我国银行卡清算服务水平，加快支付服务市场的改革开放和创新转型。[12] 2016年3月17日央行重拳出击整治银行卡收单市场秩序据悉，为规范银行卡收单外包业务，整肃银行卡收单市场秩序，前期，人民银行在全国范围内开展了银行卡收单外包业务专项抽查。此次抽查历时2个月，抽查对象涵盖57家银行和支付机构。根据“统一部署，分级实施，规范业务，突出重点”的抽查方案，人民银行总行采取直接飞行检查与分支行属地检查同步结合方式，以规范外包、打击二清为核心，对虚假商户入网、违规开放交易接口、违规多次转包业务等严厉打击、严肃查处。人民银行的果断出手让市场违规态势明显收敛，银行卡收单市场秩2015年6月1日起，国务院印发的《关于实施银行卡清算机构准入管理的决定》开始实行。这意味着我国银行卡清算市场将全面开放。今后，国际卡组织、国内第三方支付机构、银行等符合条件的境内境外机构都可以参与我国银行卡清算市场，通过市场竞争来提升我国银行卡清算服务水平，加快支付服务市场的改革开放和创新转型。[12] 2016年3月17日央行重拳出击整治银行卡收单市场秩序据悉，为规范银行卡收单外包业务，整肃银行卡收单市场秩序，前期，人民银行在全国范围内开展了银行卡收单外包业务专项抽查。此次抽查历时2个月，抽查对象涵盖57家银行和支付机构。根据“统一部署，分级实施，规范业务，突出重点”的抽查方案，人民银行总行采取直接飞行检查与分支行属地检查同步结合方式，以规范外包、打击二清为核心，对虚假商户入网、违规开放交易接口、违规多次转包业务等严厉打击、严肃查处。人民银行的果断出手让市场违规态势明显收敛，银行卡收单市场秩";
                }
                UartTester.getInstance().send(data.getBytes());

                break;
            case R.id.fragment_comm_receive:
                RECEIVE_FLAG = true;
                receive();
                break;
            case R.id.fragment_comm_disconnect:
                UartTester.getInstance().disConnect();
                break;
            case R.id.fragment_comm_recvNonBlocking:
                byte[] result = UartTester.getInstance().recvNonBlocking();
                // editText.setText(IppUser.getInstance(MainActivity.context).getGl().getConvert().bcdToStr(result));
                if(result != null) {
                    editText.setText(Convert.getInstance().bcdToStr(result));
                }
                break;
            case R.id.fragment_comm_reset:
                UartTester.getInstance().reset();
                break;
            case R.id.fragment_comm_cancelRecv:
                UartTester.getInstance().cancelRecv();
                RECEIVE_FLAG = false;
                break;

            default:
                break;
        }
    }

    private void receive() {
        if (RECEIVE_FLAG) {
            receiveBt.setText("cancelReceive");
            if (receiveThread == null) {
                receiveThread = new ReceiveThread();
                receiveThread.start();
            }
            // RECEIVE_FLAG = true;
            // new Thread(new Runnable() {
            //
            // @Override
            // public void run() {
            // long startTime = SystemClock.currentThreadTimeMillis();
            // long timeOutMs = Long.parseLong(recvTimeoutEdit.getText().toString());
            // while (SystemClock.currentThreadTimeMillis() - startTime < timeOutMs) {
            // if (RECEIVE_FLAG != true) {
            // break;
            // }
            // continue;
            // }
            // if (receiveThread != null) {
            // receiveThread.interrupt();
            // receiveThread = null;
            // UartTester.getInstance().disConnect();
            // RECEIVE_FLAG = false;
            // }
            // //cancelReceive();
            // handler.post(new Runnable() {
            //
            // @Override
            // public void run() {
            // receiveBt.setText("receive(auto connect)");
            // }
            // });
            // }
            // }).start();
        } else {
            receiveBt.setText("receive");
            UartTester.getInstance().cancelRecv();
            if (receiveThread != null) {
                receiveThread.interrupt();
                receiveThread = null;
                UartTester.getInstance().disConnect();
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
            String timeOut = recvTimeoutEdit.getText().toString();
            if (len.equals("")) {
                len = "4";
            }
            while (!Thread.interrupted()) {
                byte[] data = UartTester.getInstance().recv(Integer.parseInt(len));
                if (null != data) {
                    // String res = IppUser.getInstance().getGl().getConvert().bcdToStr(data);
                    String res = Convert.getInstance().bcdToStr(data);
                    new BaseTester().logTrue("bcdToStr");
                    if (res != null) {
                        Message message = Message.obtain();
                        message.what = 0;
                        message.obj = res;
                        handler.sendMessage(message);
                        Log.i("TAG", "Uart接收到的消息：" + res);
                    }

                } else {
                    Log.i("TAG", "Uart接收到的消息为空");
                }
            }

        }
    }

}
