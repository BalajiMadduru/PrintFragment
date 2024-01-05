package com.pax.demo.modules.ped;

import android.os.Bundle;
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

public class SM3Fragment extends BasePedFragment {
    private TextView resultText;
    private Button button;
    private EditText editText;
    private TextView textview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_base_layout, container, false);
        resultText = (TextView) view.findViewById(R.id.fragment_ped_base_result);
        textview = (TextView) view.findViewById(R.id.fragment_ped_base_title);
        button = (Button) view.findViewById(R.id.fragment_ped_base_action);
        editText = (EditText) view.findViewById(R.id.fragment_ped_base_edit_1);

        textview.setText("Use SM3 algorithm to calculate Hash:");
        editText.setEms(20);
        editText.setText("01020304050607080910111213141516");
        view.findViewById(R.id.fragment_ped_base_edit_2).setVisibility(View.GONE);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText() == null) {
                    editText.setText("01020304050607080910111213141516");
                }
                byte[] data = Convert.getInstance().strToBcd(editText.getText().toString(),
                        EPaddingPosition.PADDING_LEFT);
                byte[] res = PedTester.getInstance(pedType).SM3(data);
                if (res != null) {
                    resultText.setText("result:" + Convert.getInstance().bcdToStr(res));
                }
            }
        });

        return view;

    }

}
