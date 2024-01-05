package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pax.dal.entity.SM2KeyPair;
import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;
import com.pax.demo.util.Convert;

public class GenSM2KeyPairFragment extends BasePedFragment {

    private TextView titleText, resultText;
    private EditText editText, edit2;
    private Button genBt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_base_layout, container, false);
        titleText = (TextView) view.findViewById(R.id.fragment_ped_base_title);
        resultText = (TextView) view.findViewById(R.id.fragment_ped_base_result);
        editText = (EditText) view.findViewById(R.id.fragment_ped_base_edit_1);
        edit2 = (EditText) view.findViewById(R.id.fragment_ped_base_edit_2);
        genBt = (Button) view.findViewById(R.id.fragment_ped_base_action);
        titleText.setText("Generate one SM2 key-pair(only support 256 bits):");
        resultText.setMovementMethod(ScrollingMovementMethod.getInstance());
        edit2.setVisibility(View.GONE);
        editText.setText("256");

        genBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                short keyLenBit = 0;
                if (editText.getText() != null) {
                    keyLenBit = (short) Integer.parseInt(editText.getText().toString());
                } else {
                    keyLenBit = (short) 256;
                }
                SM2KeyPair genSM2KeyPair = PedTester.getInstance(pedType).genSM2KeyPair(keyLenBit);
                if (genSM2KeyPair != null) {
                    String publicKey = Convert.getInstance().bcdToStr(genSM2KeyPair.getPubKey());
                    String privateKey = Convert.getInstance().bcdToStr(genSM2KeyPair.getPvtKey());
                    resultText.setText("publicKey:" + publicKey + "\nprivateKey:" + privateKey);
                }
            }
        });

        return view;
    }

}
