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

public class WriteKeyVarFragment extends BasePedFragment {
    private TextView writeview;
    private Button button;
    private EditText editText;
    private TextView textview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_base_layout, container, false);
        writeview = (TextView) view.findViewById(R.id.fragment_ped_base_result);
        textview = (TextView) view.findViewById(R.id.fragment_ped_base_title);
        textview.setText(getString(R.string.ped_input_write));
        button = (Button) view.findViewById(R.id.fragment_ped_base_action);
        editText = (EditText) view.findViewById(R.id.fragment_ped_base_edit_1);
        editText.setEms(20);
        editText.setText("01020304050607080910111213141516");
        view.findViewById(R.id.fragment_ped_base_edit_2).setVisibility(View.GONE);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String str = editText.getText().toString().trim();
                if (PedTester.getInstance(pedType).writeKeyVar(
                        Convert.getInstance().strToBcd(str, EPaddingPosition.PADDING_RIGHT))) {
                    writeview.setText(getString(R.string.ped_writekeyvar_success));
                } else {
                    writeview.setText(getString(R.string.ped_writekeyvar_fail));
                }

            }
        });
        return view;
    }

}
