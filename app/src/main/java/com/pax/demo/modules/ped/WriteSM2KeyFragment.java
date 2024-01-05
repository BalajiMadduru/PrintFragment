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

import com.pax.dal.entity.EPedKeyType;
import com.pax.dal.entity.EPedType;
import com.pax.dal.entity.SM2KeyPair;
import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;
import com.pax.demo.util.Convert;

public class WriteSM2KeyFragment extends BasePedFragment implements OnClickListener {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private TextView resultText;

    private TextView titleText, genKeyResult;
    private EditText editText, edit2;
    private Button genBt;

    private byte[] SM2_PublicKey = null;
    private byte[] SM2_PrivateKey = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_write_sm2key, container, false);
        button1 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt1);
        button2 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt2);
        button3 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt3);
        button4 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt4);
        button1.setText("SM2Key(PUB)");
        button2.setText("SM2Key(PVT)");
        button3.setText("SM2CipherKey(PUB)");
        button4.setText("SM2CipherKey(PVT)");

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        resultText = (TextView) view.findViewById(R.id.fragment_ped_writeKey_result_text);

        titleText = (TextView) view.findViewById(R.id.fragment_ped_base_title);
        genKeyResult = (TextView) view.findViewById(R.id.fragment_ped_base_result);
        editText = (EditText) view.findViewById(R.id.fragment_ped_base_edit_1);
        edit2 = (EditText) view.findViewById(R.id.fragment_ped_base_edit_2);
        genBt = (Button) view.findViewById(R.id.fragment_ped_base_action);
        titleText.setText("Generate one SM2 key-pair(only support 256 bits):");
        genKeyResult.setMovementMethod(ScrollingMovementMethod.getInstance());
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
                SM2KeyPair genSM2KeyPair = PedTester.getInstance(EPedType.INTERNAL).genSM2KeyPair(keyLenBit);
                if (genSM2KeyPair != null) {
                    SM2_PublicKey = genSM2KeyPair.getPubKey();
                    SM2_PrivateKey = genSM2KeyPair.getPvtKey();
                    String publicKey = Convert.getInstance().bcdToStr(SM2_PublicKey);
                    String privateKey = Convert.getInstance().bcdToStr(SM2_PrivateKey);
                    genKeyResult.setText("publicKey:" + publicKey + "\nprivateKey:" + privateKey);
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        boolean resB = false;
        String str = "";
        switch (v.getId()) {
            case R.id.fragment_ped_writeKey_bt1:
                resB = PedTester.getInstance(pedType).writeSM2Key((byte) 2, EPedKeyType.SM2_PUB_KEY, SM2_PublicKey);
                str = "SM2Key(PUB)";
                break;
            case R.id.fragment_ped_writeKey_bt2:
                resB = PedTester.getInstance(pedType).writeSM2Key((byte) 3, EPedKeyType.SM2_PVT_KEY, SM2_PrivateKey);
                str = "SM2Key(PVT)";
                break;
            case R.id.fragment_ped_writeKey_bt3:
                resB = PedTester.getInstance(pedType).writeSM2CipherKey(EPedKeyType.SM4_TMK, (byte) 11,
                        EPedKeyType.SM2_PUB_KEY, (byte) 4, SM2_PublicKey);
                str = "SM2CipherKey(PUB)";
                break;
            case R.id.fragment_ped_writeKey_bt4:
                resB = PedTester.getInstance(pedType).writeSM2CipherKey(EPedKeyType.SM4_TMK, (byte) 11,
                        EPedKeyType.SM2_PVT_KEY, (byte) 5, SM2_PrivateKey);
                str = "SM2CipherKey(PVT)";
                break;
            default:
                break;
        }

        if (resB) {
            resultText.setText("write " + str + " success");
        } else {
            resultText.setText("write " + str + " failed");
        }
    }

}
