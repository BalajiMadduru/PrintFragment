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

import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;
import com.pax.demo.util.Convert;
import com.pax.demo.util.Convert.EPaddingPosition;

public class SM2SignAndVerifyFragment extends BasePedFragment implements OnClickListener {

    private TextView resultText, titleText;
    private Button button;
    private EditText editText;

    private Button verifyBt;
    private EditText pedTitleEt;

    private byte[] defaultUid = new byte[] { 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x31, 0x32, 0x33, 0x34,
            0x35, 0x36, 0x37, 0x38 };
    private byte[] data = null;
    private byte[] res = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_getpinblock, container, false);

        titleText = (TextView) view.findViewById(R.id.fragment_ped_base_title);
        resultText = (TextView) view.findViewById(R.id.fragment_ped_base_result);
        button = (Button) view.findViewById(R.id.fragment_ped_base_action);
        editText = (EditText) view.findViewById(R.id.fragment_ped_base_edit_1);
        view.findViewById(R.id.fragment_ped_base_edit_2).setVisibility(View.GONE);
        view.findViewById(R.id.fragment_ped_getpinblock_setrandom).setVisibility(View.GONE);
        view.findViewById(R.id.fragment_ped_getpinblock_setlandscape).setVisibility(View.GONE);
        verifyBt = (Button) view.findViewById(R.id.fragment_ped_getpinblock_showbox);
        pedTitleEt = (EditText) view.findViewById(R.id.fragment_ped_getpinblock_ped_title);

        titleText.setText("Use SM2 algorithm to calculate the signature data:");
        verifyBt.setText("Verify");
        resultText.setMovementMethod(ScrollingMovementMethod.getInstance());
        pedTitleEt.setText("verify result:");

        button.setOnClickListener(this);
        verifyBt.setOnClickListener(this);
        if (editText.getText() == null || editText.getText().length() == 0) {
            editText.setText("123456789");
        }
        data = Convert.getInstance().strToBcd(editText.getText().toString(), EPaddingPosition.PADDING_LEFT);
        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_ped_base_action:

                if (data != null) {
                    res = PedTester.getInstance(pedType).SM2Sign((byte) 2, (byte) 3, defaultUid, data);
                    if(res != null){
                    resultText.setText("result:" + Convert.getInstance().bcdToStr(res));
                    }
                }
                break;
            case R.id.fragment_ped_getpinblock_showbox:

                boolean resB = PedTester.getInstance(pedType).SM2Verify((byte) 2, defaultUid, data, res);
                if (resB) {
                    pedTitleEt.setText("verify result is true");
                } else {
                    pedTitleEt.setText("verify result is false");
                }
                break;

            default:
                break;
        }
    }
}
