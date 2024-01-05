package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pax.dal.entity.RSARecoverInfo;
import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;
import com.pax.demo.util.Convert;

import java.util.Random;

public class RSARecoverFragment extends BasePedFragment {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private TextView resultText;
    private byte[] bytes = new byte[512/8];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_writekey, container, false);
        //if (null != PedTester.modulus1 || PedTester.isGenRsaKey) {
        do {
            Random random = new Random();
            random.nextBytes(bytes);
            if (null == PedTester.modulus1) {
                break;
            }
        } while (byteComp(bytes, PedTester.modulus1));
        //}
        Log.i("Test", "RSA random data:" + Convert.getInstance().bcdToStr(bytes));
        button1 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt1);
        button1.setVisibility(View.GONE);
        button2 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt2);
        button2.setVisibility(View.GONE);
        button3 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt3);
        button3.setText("RsakeyRecover(public)");
        button4 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt4);
        button4.setText("RsakeyRecover(private)");
        resultText = (TextView) view.findViewById(R.id.fragment_ped_writeKey_result_text);
        resultText.setMovementMethod(ScrollingMovementMethod.getInstance());
        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RSARecoverInfo info = PedTester.getInstance(pedType).RSARecoverPublic(bytes);
                if (info != null) {
                    resultText.setText(getString(R.string.ped_rsa_public) + "\n"
                            + Convert.getInstance().bcdToStr(bytes) + "\n RSA(public key) "
                            + getString(R.string.ped_result_success) + "\n"
                            + Convert.getInstance().bcdToStr(info.getData()));
                } else {
                    resultText.setText(getString(R.string.ped_rsa_public_fail));
                }
            }
        });
        button4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RSARecoverInfo info = PedTester.getInstance(pedType).RSARecoverPrivate(bytes);
                if (info != null) {
                    resultText.setText(getString(R.string.ped_rsa_private) + "\n"
                            + Convert.getInstance().bcdToStr(bytes) + "\n RSA(private key) "
                            + getString(R.string.ped_result_success) + "\n"
                            + Convert.getInstance().bcdToStr(info.getData()));
                } else {
                    resultText.setText(getString(R.string.ped_rsa_private_fail));
                }
            }
        });
        return view;
    }

    public boolean byteComp(byte[] b1, byte[] b2) {
        if (b1.length > b2.length) {
            return true;
        }
        if (b1.length == b2.length) {
            for (int i = 0; i < b1.length; i++) {
                if (b1[i] == b2[i]) {
                    continue;
                }
                return b1[i] > b2[i] ? true : false;
            }
        }
        return false;

    }
}
