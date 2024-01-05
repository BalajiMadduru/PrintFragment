package com.pax.demo.modules.system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

import java.util.Locale;

public class SystemLanguageFragment extends BaseFragment implements OnClickListener {

    private EditText editText;
    private TextView textView;
    private Button getButton, setButton;

    private String language;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sys_system_language_layout, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        editText = (EditText) view.findViewById(R.id.fragment_sys_system_language_et);
        textView = (TextView) view.findViewById(R.id.fragment_sys_system_language_tv);
        getButton = (Button) view.findViewById(R.id.fragment_sys_system_language_get_bt);
        setButton = (Button) view.findViewById(R.id.fragment_sys_system_language_set_bt);

        getButton.setOnClickListener(this);
        setButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_sys_system_language_get_bt:
                textView.setText(SysTester.getInstance().getSystemLanguage());
                break;
            case R.id.fragment_sys_system_language_set_bt:
                language = editText.getText().toString();
                if (language != null && language.length() > 0 && language.split("_").length == 2) {
                    Locale locale = new Locale(language.split("_")[0], language.split("_")[1]);
                    SysTester.getInstance().setSystemLanguage(locale);
                }else{
                    Toast.makeText(getActivity(), "input invalid", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }
}
