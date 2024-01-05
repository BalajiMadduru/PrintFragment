package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;
import com.pax.demo.util.Convert;
import com.pax.demo.util.Convert.EPaddingPosition;

public class GetMacSMFragment extends BasePedFragment {
    private TextView resultText;
    private Button button;
    private EditText editText;
    private TextView textView;
    private LinearLayout linearLayout;
    private byte[] initVector = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_base_layout, container, false);
        resultText = (TextView) view.findViewById(R.id.fragment_ped_base_result);
        textView = (TextView) view.findViewById(R.id.fragment_ped_base_title);
        linearLayout = (LinearLayout) view.findViewById(R.id.fragment_ped_base_linerlayout);
        button = (Button) view.findViewById(R.id.fragment_ped_base_action);
        editText = (EditText) view.findViewById(R.id.fragment_ped_base_edit_1);
        textView.setText("Use SM4 algorithm to calculate MAC:");
        editText.setEms(20);
        editText.setText("01020304050607080910111213141516");
        button.setText("SM4 CBC");
        Button button2 = new Button(getActivity());
        button2.setText("SM3");
        linearLayout.addView(button2);

        view.findViewById(R.id.fragment_ped_base_edit_2).setVisibility(View.GONE);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (editText.getText() == null) {
                    editText.setText("01020304050607080910111213141516");
                }
                String str = editText.getText().toString().trim();
                byte[] mac = PedTester.getInstance(pedType).getMacSM((byte) 12, initVector,
                        Convert.getInstance().strToBcd(str, EPaddingPosition.PADDING_RIGHT), (byte) 0);
                if (mac != null) {
                    resultText.setText(getString(R.string.ped_macvalue_success)
                            + Convert.getInstance().bcdToStr(mac));
                } else {
                    resultText.setText(getString(R.string.ped_macvalue_fail));
                }
            }
        });

        button2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (editText.getText() == null) {
                    editText.setText("01020304050607080910111213141516");
                }
                String str = editText.getText().toString().trim();
                byte[] mac = PedTester.getInstance(pedType).getMacSM((byte) 12, initVector,
                        Convert.getInstance().strToBcd(str, EPaddingPosition.PADDING_RIGHT), (byte) 1);
                if (mac != null) {
                    resultText.setText(getString(R.string.ped_macvalue_success)
                            + Convert.getInstance().bcdToStr(mac));
                } else {
                    resultText.setText(getString(R.string.ped_macvalue_fail));
                }
            }
        });

        return view;
    }

}
