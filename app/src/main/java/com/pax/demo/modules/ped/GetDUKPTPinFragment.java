package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pax.dal.entity.DUKPTResult;
import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;
import com.pax.demo.util.Convert;

public class GetDUKPTPinFragment extends BasePedFragment {

    private TextView writeview;
    private Button button;
    private EditText editText;
    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_base_layout, container, false);
        writeview = (TextView) view.findViewById(R.id.fragment_ped_base_result);
        button = (Button) view.findViewById(R.id.fragment_ped_base_action);
        editText = (EditText) view.findViewById(R.id.fragment_ped_base_edit_1);
        view.findViewById(R.id.fragment_ped_base_edit_2).setVisibility(View.GONE);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String num = editText.getText().toString().trim();
                if (num.length() < 13) {
                    writeview.setText(getString(R.string.ped_number_error));
                    return;
                }
                byte[] panArray = num.substring(num.length() - 13, num.length() - 1).getBytes();
                final byte[] dataIn = new byte[16];
                dataIn[0] = 0x00;
                dataIn[1] = 0x00;
                dataIn[2] = 0x00;
                dataIn[3] = 0x00;
                System.arraycopy(panArray, 0, dataIn, 4, panArray.length);
                writeview.setText(getString(R.string.ped_input_password));
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        final DUKPTResult result = PedTester.getInstance(pedType).getDUKPTPin(dataIn);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (result != null) {
                                    Log.i("ss", "ksn:" + Convert.getInstance().bcdToStr(result.getKsn()));
                                    writeview.setText(getString(R.string.ped_dukpin_result_success) + "\n"
                                            + Convert.getInstance().bcdToStr(result.getResult()));
                                } else {
                                    writeview.setText(getString(R.string.ped_dukpin_result_fail));
                                }
                            }
                        });

                    }
                });
                thread.start();
            }
        });

        return view;
    }
}
