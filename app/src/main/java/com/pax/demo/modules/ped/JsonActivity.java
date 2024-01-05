package com.pax.demo.modules.ped;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pax.dal.IDAL;
import com.pax.dal.IPed;
import com.pax.dal.entity.EFuncKeyMode;
import com.pax.dal.entity.EKeyCode;
import com.pax.dal.entity.EPedType;
import com.pax.dal.exceptions.PedDevException;
import com.pax.demo.R;
import com.pax.demo.base.DemoApp;

public class JsonActivity extends Activity {
    private static final String PASSWORD_ADD = "ADD";
    private static final String PASSWORD_DELETE = "delete";
    private ImageView iv0, iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, ivEnter, ivClear;
    private TextView tvCancel;
    private LinearLayout dotLayout;
    private IDAL mIdal;
    private byte[] seq;
    private int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ped_setkeyboardlayout);
        // init key
        initView();
        // init idal
        initIdal();
        // setCustomKeyLayout
        setKeyLayout();
        // setInputListener
        setInputListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // showKey
        showKeyboard();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // showPinBlock
        showPinBlock();
    }

    private void showPinBlock() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mIdal.getPed(EPedType.INTERNAL)
                            .setFunctionKey(EFuncKeyMode.CLEAR_LAST);
                    mIdal.getPed(EPedType.INTERNAL)
                            .getPinBlock((byte) 1, "4,6",
                                    "0000682502342834".getBytes(), (byte) 0, 1000 * 10);
                } catch (PedDevException e) {
                    e.printStackTrace();
                } finally {
                    // pin input over
                    finish();
                }
            }
        });
        thread.start();
    }

    public void showKeyboard() {
        try {
            iv1.setImageResource(getResourceId(seq[0]));
            iv2.setImageResource(getResourceId(seq[1]));
            iv3.setImageResource(getResourceId(seq[2]));
            iv4.setImageResource(getResourceId(seq[3]));
            iv5.setImageResource(getResourceId(seq[4]));
            iv6.setImageResource(getResourceId(seq[5]));
            iv7.setImageResource(getResourceId(seq[6]));
            iv8.setImageResource(getResourceId(seq[7]));
            iv9.setImageResource(getResourceId(seq[8]));
            iv0.setImageResource(getResourceId(seq[9]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getResourceId(byte num) {
        int id = R.mipmap.pw_zero;
        switch (num) {
            case 48:
                id = R.mipmap.pw_zero;
                break;
            case 49:
                id = R.mipmap.pw1;
                break;
            case 50:
                id = R.mipmap.pw2;
                break;
            case 51:
                id = R.mipmap.pw3;
                break;
            case 52:
                id = R.mipmap.pw4;
                break;
            case 53:
                id = R.mipmap.pw5;
                break;
            case 54:
                id = R.mipmap.pw6;
                break;
            case 55:
                id = R.mipmap.pw7;
                break;
            case 56:
                id = R.mipmap.pw8;
                break;
            case 57:
                id = R.mipmap.pw9;
                break;
            default:
                break;
        }
        return id;
    }


    private void setInputListener() {
        mIdal.getPed(EPedType.INTERNAL)
                .setInputPinListener(new IPed.IPedInputPinListener() {
                    @Override
                    public void onKeyEvent(EKeyCode eKeyCode) {
                        switch (eKeyCode) {
                            case KEY_STAR:
                                // input key
                                showPassword(PASSWORD_ADD);
                                break;
                            case KEY_CLEAR:
                                // clear key
                                showPassword(PASSWORD_DELETE);
                                break;
                            case KEY_CANCEL:
                                // input cancel
                                finish();
                                break;
                            default:
                                break;
                        }
                    }
                });
    }

    private void showPassword(String s) {
        // 加小圆点操作
        if (PASSWORD_ADD.equals(s)) {
            ImageView dot = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);
            params.gravity = Gravity.CENTER_VERTICAL;
            dot.setLayoutParams(params);
            dot.setBackgroundResource(R.mipmap.dot);
            dotLayout.addView(dot);
            sum = sum + 1;
        } else if (PASSWORD_DELETE.equals(s)) {
            if (sum > 0) {
                sum = sum - 1;
                dotLayout.removeViewAt(sum);
            }
        }
    }

    private void setKeyLayout() {
        String keyLayout = "{\n" +
                "\t\"areas\": [{\n" +
                "\t\t\"type\": \"NUM\",\n" +
                "\t\t\"geometry\": \"28,624,165,185\"\n" +
                "\t}, {\n" +
                "\t\t\"type\": \"NUM\",\n" +
                "\t\t\"geometry\": \"196,624,165,185\"\n" +
                "\t}, {\n" +
                "\t\t\"type\": \"NUM\",\n" +
                "\t\t\"geometry\": \"360,624,165,185\"\n" +
                "\t}, {\n" +
                "\t\t\"type\": \"CLEAR\",\n" +
                "\t\t\"geometry\": \"526,624,165,185\"\n" +
                "\t}, {\n" +
                "\t\t\"type\": \"NUM\",\n" +
                "\t\t\"geometry\": \"28,810,165,185\"\n" +
                "\t}, {\n" +
                "\t\t\"type\": \"NUM\",\n" +
                "\t\t\"geometry\": \"184,810,165,185\"\n" +
                "\t}, {\n" +
                "\t\t\"type\": \"NUM\",\n" +
                "\t\t\"geometry\": \"360,810,165,185\"\n" +
                "\t}, {\n" +
                "\t\t\"type\": \"NUM\",\n" +
                "\t\t\"geometry\": \"526,810,165,185\"\n" +
                "\t}, {\n" +
                "\t\t\"type\": \"NUM\",\n" +
                "\t\t\"geometry\": \"28,996,165,185\"\n" +
                "\t}, {\n" +
                "\t\t\"type\": \"NUM\",\n" +
                "\t\t\"geometry\": \"194,996,165,185\"\n" +
                "\t}, {\n" +
                "\t\t\"type\": \"NUM\",\n" +
                "\t\t\"geometry\": \"360,996,165,185\"\n" +
                "\t}, {\n" +
                "\t\t\"type\": \"ENTER\",\n" +
                "\t\t\"geometry\": \"526,996,165,185\"\n" +
                "\t}, {\n" +
                "\t\t\"type\": \"CANCEL\",\n" +
                "\t\t\"geometry\": \"0,60,70,70\"\n" +
                "\t}]\n" +
                "}";
        //try {
  //          seq = mIdal.getPed(EPedType.INTERNAL)
    //                .setKeyBoardLayout(true, keyLayout);
   //     } catch (PedDevException e) {
     //       e.printStackTrace();
       // }
    }

    private void initIdal() {
        try {
            mIdal = DemoApp.getDal();
            mIdal.getSys().showNavigationBar(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        // init key
        iv0 = findViewById(R.id.btn_zero);
        iv1 = findViewById(R.id.btn_one);
        iv2 = findViewById(R.id.btn_two);
        iv3 = findViewById(R.id.btn_three);
        iv4 = findViewById(R.id.btn_four);
        iv5 = findViewById(R.id.btn_five);
        iv6 = findViewById(R.id.btn_six);
        iv7 = findViewById(R.id.btn_seven);
        iv8 = findViewById(R.id.btn_eight);
        iv9 = findViewById(R.id.btn_nine);
        ivEnter = findViewById(R.id.btn_sure);
        ivClear = findViewById(R.id.btn_clear);
        tvCancel = findViewById(R.id.crosshairs_back);
        dotLayout = findViewById(R.id.dot_layout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mIdal) {
            mIdal.getSys().showNavigationBar(true);
        }
    }
}
