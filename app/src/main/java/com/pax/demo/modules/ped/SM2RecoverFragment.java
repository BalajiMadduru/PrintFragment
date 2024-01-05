package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pax.dal.entity.ECryptOperate;
import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;
import com.pax.demo.util.Convert;

public class SM2RecoverFragment extends BasePedFragment implements OnClickListener {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private TextView resultText;

    private byte[] testData = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };
    private byte[] resStr = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_writekey, container, false);
        button1 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt1);
        button2 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt2);
        button3 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt3);
        button4 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt4);
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3.setText("publicKey(encrypt)");
        button4.setText("privateKey(decrypt)");

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        resultText = (TextView) view.findViewById(R.id.fragment_ped_writeKey_result_text);
        resultText.setMovementMethod(ScrollingMovementMethod.getInstance());

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_ped_writeKey_bt3:
                resStr = PedTester.getInstance(pedType).SM2Recover((byte) 2, testData, ECryptOperate.ENCRYPT);
                if (resStr != null) {
                    resultText.setText(Convert.getInstance().bcdToStr(resStr));
                }
                break;
            case R.id.fragment_ped_writeKey_bt4:
                if (resStr != null) {
                    resStr = PedTester.getInstance(pedType).SM2Recover((byte) 3, resStr, ECryptOperate.DECRYPT);
                    if (resStr != null) {
                        resultText.setText(Convert.getInstance().bcdToStr(resStr));
                    }
                } else {
                    resultText.setText("please encrypt data first");
                }
                break;
            default:
                break;
        }

    }
}
