package com.pax.demo.modules.printer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class CutPaperFragment extends BaseFragment implements OnClickListener {

    private Button cutPaperBt, cutModeBt;
    private TextView resultTv;
    private RadioGroup radioGroup;
    private RadioButton fullCut, partialCut;
    private int mode = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_printer_cutpaper, container, false);
        cutPaperBt = (Button) view.findViewById(R.id.fragment_printer_cutpaper_bt);
        cutModeBt = (Button) view.findViewById(R.id.fragment_printer_getcutmode_bt);
        resultTv = (TextView) view.findViewById(R.id.fragment_printer_result_tv);

        radioGroup = (RadioGroup) view.findViewById(R.id.fragment_printer_cutpaper_rg);
        fullCut = (RadioButton) view.findViewById(R.id.fragment_printer_fullpaper_rb);
        partialCut = (RadioButton) view.findViewById(R.id.fragment_printer_partialpaper_rb);

        cutPaperBt.setOnClickListener(this);
        cutModeBt.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.fragment_printer_fullpaper_rb:
                        mode = 0;
                        break;
                    case R.id.fragment_printer_partialpaper_rb:
                        mode = 1;
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragment_printer_cutpaper_bt:
                String res = PrinterTester.getInstance().cutPaper(mode);
                resultTv.setText(res);
                break;
            case R.id.fragment_printer_getcutmode_bt:
               String resultStr = PrinterTester.getInstance().getCutMode();
                resultTv.setText(resultStr);
                break;

            default:
                break;
        }
    }
}
