package com.pax.demo.base;

import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pax.demo.R;

public class AboutFragment extends BaseFragment {

    private TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        textView = (TextView) view.findViewById(R.id.fragment_textview);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        
        String aboutText = "";
        try {
            aboutText += ("Demo Version:"+getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName+"\n\n");
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        aboutText += getString(R.string.suggestion);
        
        aboutText += "\n\nLogFloatView Action:\n 1.click:show \n 2.doubleClick:hide \n 3.longClick:delete \n 4.drag:move place";
        textView.setText(aboutText);
        return view;
    }
}
