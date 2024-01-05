package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;

public class WriteRSAkeyFragment extends BasePedFragment {

    private Button writeTMK;
    private Button writeTPK;
    private Button writeTAK;
    private Button writeTDK;
    private TextView resultText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_writekey, container, false);
        writeTMK = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt1);
        writeTMK.setVisibility(View.GONE);
        writeTPK = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt2);
        writeTPK.setVisibility(View.GONE);
        writeTAK = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt3);
        writeTAK.setText("writeRsakey(public)");
        writeTDK = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt4);
        writeTDK.setText("writeRsakey(private)");
        resultText = (TextView) view.findViewById(R.id.fragment_ped_writeKey_result_text);
        writeTAK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = PedTester.getInstance(pedType).writeRSAKeyPublic();
                if (flag) {
                    resultText.setText(getString(R.string.ped_rsapublic_success));
                } else {
                    resultText.setText(getString(R.string.ped_rsapublic_fail));
                }
            }
        });
        writeTDK.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = PedTester.getInstance(pedType).writeRSAKeyPrivate();
                if (flag) {
                    resultText.setText(getString(R.string.ped_rsaprivate_success));
                } else {
                    resultText.setText(getString(R.string.ped_rsaprivate_fail));
                }
            }
        });
        return view;
    }
}
