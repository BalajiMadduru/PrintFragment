package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.pax.dal.entity.EPedType;
import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;

public class PedSelectFragment extends BasePedFragment {

    private Button internalBt;
    private Button externalBt;
    private CheckBox checkBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_select_type, container, false);

        internalBt = (Button) view.findViewById(R.id.fragment_select_bt1);
        externalBt = (Button) view.findViewById(R.id.fragment_select_bt2);

        checkBox = (CheckBox) view.findViewById(R.id.fragment_select_checkbox);

        internalBt.setText(getString(R.string.ped_type_INTERNAL));
        internalBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                PedTester.PEDMODE = checkBox.isChecked() ? 1 : 0;
                fragmentSelect(new PedFragment(), EPedType.INTERNAL);
            }
        });

        externalBt.setText(getString(R.string.ped_type_EXTERNAL));
        externalBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                PedTester.PEDMODE = checkBox.isChecked() ? 1 : 0;
                fragmentSelect(new PedFragment(), EPedType.EXTERNAL_TYPEA);
            }
        });
        return view;
    }

}
