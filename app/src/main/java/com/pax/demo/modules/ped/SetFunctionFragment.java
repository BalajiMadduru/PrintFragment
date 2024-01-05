package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pax.dal.entity.EFuncKeyMode;
import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;

public class SetFunctionFragment extends BasePedFragment {

    private Button writeTMK;
    private Button writeTPK;
    private Button writeTAK;
    private Button writeTDK;
    private TextView resultText;
    private TextView textview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_writekey, container, false);
        textview = (TextView) view.findViewById(R.id.fragment_ped_writeKey_text);
        textview.setText(getString(R.string.ped_set_key));
        writeTMK = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt1);
        writeTMK.setVisibility(View.GONE);
        writeTPK = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt2);
        writeTPK.setVisibility(View.GONE);
        writeTAK = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt3);
        writeTAK.setText(getString(R.string.ped_set_removeall));
        writeTDK = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt4);
        writeTDK.setText(getString(R.string.ped_set_removelast));
        resultText = (TextView) view.findViewById(R.id.fragment_ped_writeKey_result_text);
        writeTAK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PedTester.getInstance(pedType).setFunctionKey(EFuncKeyMode.CLEAR_ALL)) {
                    resultText.setText(getString(R.string.ped_set_key_success));
                } else {
                    resultText.setText(getString(R.string.ped_set_key_fail));
                }

            }
        });
        writeTDK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PedTester.getInstance(pedType).setFunctionKey(EFuncKeyMode.CLEAR_LAST)) {
                    resultText.setText(getString(R.string.ped_set_key_success));
                } else {
                    resultText.setText(getString(R.string.ped_set_key_fail));
                }

            }
        });
        return view;
    }
}
