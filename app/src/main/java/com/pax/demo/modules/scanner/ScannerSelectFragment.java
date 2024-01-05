package com.pax.demo.modules.scanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.pax.dal.entity.EScannerType;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class ScannerSelectFragment extends BaseFragment {

    private Button internalBt;
    private Button externalBt;
    private CheckBox checkBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_type, container, false);

        internalBt = (Button) view.findViewById(R.id.fragment_select_bt1);
        externalBt = (Button) view.findViewById(R.id.fragment_select_bt2);
        checkBox = (CheckBox)view.findViewById(R.id.fragment_select_checkbox);
        
        checkBox.setVisibility(View.GONE);

        internalBt.setText(getString(R.string.scanner_INTERNAL));
        internalBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragmentSelect(new ScannerFragment(), EScannerType.REAR);
            }
        });

        externalBt.setText(getString(R.string.scanner_EXTERNAL));
        externalBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragmentSelect(new ScannerFragment(), EScannerType.EXTERNAL);
            }
        });
        return view;
    }

    private void fragmentSelect(Fragment fragment, EScannerType scannerType) {
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("scannerType", scannerType.name());
        fragment.setArguments(bundle);
        transaction.replace(R.id.parent_layout, fragment);
        transaction.commit();
    }
}
