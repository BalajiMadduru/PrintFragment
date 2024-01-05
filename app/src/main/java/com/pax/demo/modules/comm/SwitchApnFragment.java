package com.pax.demo.modules.comm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class SwitchApnFragment extends BaseFragment {

    private EditText aliasEt, nameEt, userNameEt, passwordEt, authTypeEt;
    private Button switchBtn;
    private TextView resTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comm_switch_apn_layout, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        aliasEt = (EditText) view.findViewById(R.id.fragment_comm_switch_apn_alias_et);
        nameEt = (EditText) view.findViewById(R.id.fragment_comm_switch_apn_name_et);
        userNameEt = (EditText) view.findViewById(R.id.fragment_comm_switch_apn_username_et);
        passwordEt = (EditText) view.findViewById(R.id.fragment_comm_switch_apn_password_et);
        authTypeEt = (EditText) view.findViewById(R.id.fragment_comm_switch_apn_authtype_et);
        resTv = (TextView) view.findViewById(R.id.fragment_comm_switch_apn_res_tx);

        switchBtn = (Button) view.findViewById(R.id.fragment_comm_switch_apn_btn);
        switchBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = aliasEt.getText().toString();
                String newApn = nameEt.getText().toString();
                String username = userNameEt.getText().toString();
                String password = passwordEt.getText().toString();
                int authType = Integer.parseInt(authTypeEt.getText().toString());
                int res = CommTester.getInstance().switchApn(name, newApn, username, password, authType);
                if (res == 1) {
                    resTv.setText("switchApn successful!");
                } else {
                    resTv.setText("switchApn failed!");
                }
            }
        });
    }
}
