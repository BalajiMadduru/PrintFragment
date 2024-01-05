package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pax.dal.entity.EAesCheckMode;
import com.pax.dal.entity.ECryptOperate;
import com.pax.dal.entity.ECryptOpt;
import com.pax.dal.entity.EPedKeyType;
import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;
import com.pax.demo.util.Convert;

public class AesFragment extends BasePedFragment implements OnClickListener {

    private TextView aesText, resultText;
    private EditText aesEdit, resEdit;
    private Button writeBt, calcBt, setBt;

    private byte[] aes = { (byte) 0xE5, (byte) 0x38, (byte) 0xA1, (byte) 0x0E, (byte) 0xE5, (byte) 0x98, (byte) 0xA2,
            (byte) 0x1F, (byte) 0xC2, (byte) 0x58, (byte) 0x31, (byte) 0x40, (byte) 0x2F, (byte) 0x23, (byte) 0xE0,
            (byte) 0x08 };

    byte putIV[] = { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10 };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_getpinblock, container, false);
        aesText = (TextView) view.findViewById(R.id.fragment_ped_base_title);
        resultText = (TextView) view.findViewById(R.id.fragment_ped_base_result);
        writeBt = (Button) view.findViewById(R.id.fragment_ped_base_action);
        setBt = (Button) view.findViewById(R.id.fragment_ped_getpinblock_setlandscape);
        aesEdit = (EditText) view.findViewById(R.id.fragment_ped_base_edit_1);
        view.findViewById(R.id.fragment_ped_base_edit_2).setVisibility(View.GONE);
        view.findViewById(R.id.fragment_ped_getpinblock_setrandom).setVisibility(View.GONE);

        calcBt = (Button) view.findViewById(R.id.fragment_ped_getpinblock_showbox);
        resEdit = (EditText) view.findViewById(R.id.fragment_ped_getpinblock_ped_title);

        setBt.setVisibility(View.GONE);
        aesText.setText("AesKey");
        writeBt.setText("writeAesKey");
        calcBt.setText("calcAes");
        resEdit.setHint("Aes result");
        aesEdit.setText(Convert.getInstance().bcdToStr(aes));

        writeBt.setOnClickListener(this);
        calcBt.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragment_ped_base_action:
                boolean flag = PedTester.getInstance(pedType).writeAesKey(EPedKeyType.TMK, (byte) 0, (byte) 2, aes,
                        EAesCheckMode.KCV_NONE, null);
                if (flag) {
                    resultText.setText("writeAesKey success");
                } else {
                    resultText.setText("writeAesKey failed");
                }
                break;
            case R.id.fragment_ped_getpinblock_showbox:
                byte[] dataIn = { 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11, 0x11,
                        0x11, 0x11 };
                byte[] res = PedTester.getInstance(pedType).calcAes((byte) 2, putIV, dataIn, ECryptOperate.ENCRYPT,
                        ECryptOpt.CBC);
                String encryptStr = "";
                if (res != null) {
                    encryptStr = "ENCRYPT:" + Convert.getInstance().bcdToStr(res);
                }
                Log.i("Test", encryptStr);
                byte[] resD = PedTester.getInstance(pedType).calcAes((byte) 2, putIV, res, ECryptOperate.DECRYPT,
                        ECryptOpt.CBC);
                String decryptStr = "";
                if (resD != null) {
                    decryptStr = "DECRYPT:" + Convert.getInstance().bcdToStr(resD);
                }
                Log.i("Test", decryptStr);
                resEdit.setText(encryptStr + "\n" + decryptStr);
                break;

            default:
                break;
        }
    }

}
