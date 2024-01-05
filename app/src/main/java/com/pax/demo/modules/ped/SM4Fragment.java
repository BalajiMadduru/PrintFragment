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
import com.pax.dal.entity.ECryptOpt;
import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;
import com.pax.demo.util.Convert;

public class SM4Fragment extends BasePedFragment implements OnClickListener {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private TextView resultText;

    private byte[] initVector = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
    private byte[] testData = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 15, 14, 13, 12, 11,
            10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_writekey, container, false);
        button1 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt1);
        button2 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt2);
        button3 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt3);
        button4 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt4);
        button1.setText("ECB encrypt");
        button2.setText("ECB decrypt");
        button3.setText("CBC encrypt");
        button4.setText("CBC decrypt");

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
        byte[] res = null;
        switch (v.getId()) {
            case R.id.fragment_ped_writeKey_bt1:
                res = PedTester.getInstance(pedType).SM4((byte) 13, null, testData, ECryptOperate.ENCRYPT, ECryptOpt.ECB);
                break;

            case R.id.fragment_ped_writeKey_bt2:
                res = PedTester.getInstance(pedType).SM4((byte) 13, null, testData, ECryptOperate.DECRYPT, ECryptOpt.ECB);
                break;
            case R.id.fragment_ped_writeKey_bt3:
                res = PedTester.getInstance(pedType).SM4((byte) 13, initVector, testData, ECryptOperate.ENCRYPT,
                        ECryptOpt.CBC);
                break;
            case R.id.fragment_ped_writeKey_bt4:
                res = PedTester.getInstance(pedType).SM4((byte) 13, initVector, testData, ECryptOperate.DECRYPT,
                        ECryptOpt.CBC);
                break;
            default:
                break;
        }
        if (res != null) {
            resultText.setText(Convert.getInstance().bcdToStr(res));
        }
    }
}
