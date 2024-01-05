package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;
import com.pax.demo.util.Convert;
import com.pax.demo.util.Convert.EPaddingPosition;

public class CalcDUKPTDesFragment extends BasePedFragment {

    private TextView writeview;
    private Button button;
    private EditText editText;
    private TextView textview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_base_layout, container, false);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.fragment_ped_base_linerlayout);
        Button button2 = new Button(getActivity());
        button2.setText(getString(R.string.ped_des_mac));
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.weight = 1.0f;
        button2.setLayoutParams(params);
        writeview = (TextView) view.findViewById(R.id.fragment_ped_base_result);
        textview = (TextView) view.findViewById(R.id.fragment_ped_base_title);
        textview.setText(R.string.ped_input);
        button = (Button) view.findViewById(R.id.fragment_ped_base_action);
        button.setLayoutParams(params);
        editText = (EditText) view.findViewById(R.id.fragment_ped_base_edit_1);
        editText.setEms(20);
        editText.setText("01020304050607080910111213141516");
        view.findViewById(R.id.fragment_ped_base_edit_2).setVisibility(View.GONE);
        button.setText(R.string.ped_des_des);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String str = editText.getText().toString().trim();
                byte[] bytes = PedTester.getInstance(pedType).calcDUKPTDesDes(
                        Convert.getInstance().strToBcd(str, EPaddingPosition.PADDING_RIGHT));
                if (bytes != null) {
                    writeview.setText(getString(R.string.ped_des_result) + Convert.getInstance().bcdToStr(bytes));
                } else {
                    writeview.setText(getString(R.string.ped_result_fail));
                }

            }
        });
        button2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String str = editText.getText().toString().trim();
                byte[] bytes = PedTester.getInstance(pedType).calcDUKPTDesMac(
                        Convert.getInstance().strToBcd(str, EPaddingPosition.PADDING_RIGHT));
                if (bytes != null) {
                    writeview.setText(getString(R.string.ped_mac_result) + Convert.getInstance().bcdToStr(bytes));
                } else {
                    writeview.setText(getString(R.string.ped_result_fail));
                }

            }
        });
        layout.addView(button2);
        return view;
    }
}
