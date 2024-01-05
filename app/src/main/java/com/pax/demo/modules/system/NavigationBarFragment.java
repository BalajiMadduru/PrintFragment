package com.pax.demo.modules.system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.pax.dal.entity.ENavigationKey;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class NavigationBarFragment extends BaseFragment implements OnClickListener, OnItemSelectedListener {
    private Spinner spinner;
    private Button enableBt, visibleBt;

    private String navigationKey;
    private boolean enaleB = true, visibleB = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sys_bar_key, container, false);

        spinner = (Spinner) view.findViewById(R.id.fragment_sys_bar_key_spinner);
        enableBt = (Button) view.findViewById(R.id.fragment_sys_bar_key_enable_bt);
        visibleBt = (Button) view.findViewById(R.id.fragment_sys_bar_key_visible_bt);

        enaleB = SysTester.getInstance().isNavigationBarEnabled();
        visibleB = SysTester.getInstance().isNavigationBarVisible();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.navigation));
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
        enableBt.setOnClickListener(this);
        visibleBt.setOnClickListener(this);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                navigationKey = "NavigationBar";
                enaleB = SysTester.getInstance().isNavigationBarEnabled();
                enableBt.setText(enaleB == true ? "disable" : "enable");
                visibleB = SysTester.getInstance().isNavigationBarVisible();
                visibleBt.setText(visibleB == true ? "unvisible" : "visible");
                break;
            case 1:
                navigationKey = ENavigationKey.BACK.name();
                enaleB = SysTester.getInstance().isNavigationKeyEnabled(ENavigationKey.BACK);
                enableBt.setText(enaleB == true ? "disable" : "enable");
                visibleBt.setText("not support");
                break;
            case 2:
                navigationKey = ENavigationKey.HOME.name();
                enaleB = SysTester.getInstance().isNavigationKeyEnabled(ENavigationKey.HOME);
                enableBt.setText(enaleB == true ? "disable" : "enable");
                visibleBt.setText("not support");
                break;
            case 3:
                navigationKey = ENavigationKey.RECENT.name();
                enaleB = SysTester.getInstance().isNavigationKeyEnabled(ENavigationKey.RECENT);
                enableBt.setText(enaleB == true ? "disable" : "enable");
                visibleBt.setText("not support");
                break;

            default:
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_sys_bar_key_enable_bt:
                if (navigationKey.equals("NavigationBar")) {
                    SysTester.getInstance().enableNavigationBar(!enaleB);
                } else {
                    ENavigationKey eNavigationKey = null;
                    if (navigationKey.equals(ENavigationKey.BACK.name())) {
                        eNavigationKey = ENavigationKey.BACK;
                    } else if (navigationKey.equals(ENavigationKey.HOME.name())) {
                        eNavigationKey = ENavigationKey.HOME;
                    } else {
                        eNavigationKey = ENavigationKey.RECENT;
                    }
                    SysTester.getInstance().enableNavigationKey(eNavigationKey, !enaleB);
                }
                enaleB = !enaleB;
                enableBt.setText(enaleB == true ? "disable" : "enable");
                break;

            case R.id.fragment_sys_bar_key_visible_bt:
                if (!visibleBt.getText().equals("not support")) {
                    SysTester.getInstance().showNavigationBar(!visibleB);
                    visibleB = !visibleB;
                    visibleBt.setText(visibleB == true ? "unvisible" : "visible");
                }
                break;

            default:
                break;
        }

    }
}
