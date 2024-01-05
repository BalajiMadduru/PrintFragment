package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.pax.dal.entity.ECheckMode;
import com.pax.dal.entity.EPedKeyType;
import com.pax.dal.entity.EPedType;
import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;
import com.pax.demo.util.Convert;
import com.pax.demo.util.Convert.EPaddingPosition;

public class WriteKeyFragment extends BasePedFragment implements OnClickListener {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private TextView resultText;
    private CheckBox checkBox;

    // private byte[] byte_TMK = new byte[] { 0x54, (byte) 0xDC, (byte) 0xBF, 0x79, (byte) 0xAE, (byte) 0xB9, 0x70,
    // 0x32,
    // (byte) 0x9E, (byte) 0x97, (byte) 0xB9, (byte) 0x86, 0x51, (byte) 0xE6, 0x19, (byte) 0xCE };
    // private byte[] byte_TPK = new byte[] { (byte) 0x9D, (byte) 0x8D, 0x00, (byte) 0xD7, (byte) 0xDB, 0x16, (byte)
    // 0xD3,
    // (byte) 0xF8, (byte) 0xC1, (byte) 0xC4, 0x75, (byte) 0xF1, (byte) 0x92, (byte) 0xB2, 0x27, 0x51 };
    // private byte[] byte_TAK = new byte[] { (byte) 0xB5, (byte) 0xDD, (byte) 0xF9, 0x52, (byte) 0xB2, 0x70, 0x7E,
    // 0x67,
    // 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08 };
    // private byte[] byte_TDK = new byte[] { 0x76, (byte) 0x8A, 0x5B, 0x4E, (byte) 0xFB, 0x48, (byte) 0xBC, 0x30,
    // (byte) 0xC1, (byte) 0xCC, 0x3F, (byte) 0xD2, 0x25, 0x22, 0x71, 0x4D };

    // private byte[] byte_TPK_KCV = new byte[] { 0x50, 0x29, 0x74 , 0x29};//502974295D2E049E
    // private byte[] byte_TAK_KCV = new byte[] { 0x57, 0x04, 0x75, (byte) 0xB3 };
    // private byte[] byte_TDK_KCV = new byte[] { (byte) 0x92, (byte) 0xA6, 0x5B, (byte) 0xC2 };

    private byte[] byte_TMK = Convert.getInstance()
            .strToBcd("54DCBF79AEB970329E97B98651E619CE", EPaddingPosition.PADDING_LEFT);
    private byte[] byte_TPK = Convert.getInstance()
            .strToBcd("9D8D00D7DB16D3F8C1C475F192B22751", EPaddingPosition.PADDING_LEFT);//明文：07E6D931EA75734AA4E00483ADF2134F
    private byte[] byte_TAK = Convert.getInstance()
            .strToBcd("B5DDF952B2707E670102030405060708", EPaddingPosition.PADDING_LEFT);
    private byte[] byte_TDK = Convert.getInstance()
            .strToBcd("768A5B4EFB48BC30C1CC3FD22522714D", EPaddingPosition.PADDING_LEFT);//67BC0E979825972CC729FE6246E0F7AB
    private byte[] byte_TDK_plain = Convert.getInstance()
            .strToBcd("67BC0E979825972CC729FE6246E0F7AB", EPaddingPosition.PADDING_LEFT);
    
    private byte[] byte_TPK_KCV = Convert.getInstance().strToBcd("50297429", EPaddingPosition.PADDING_LEFT);// 502974295D2E049E
    private byte[] byte_TAK_KCV = Convert.getInstance().strToBcd("570475B3", EPaddingPosition.PADDING_LEFT);
    private byte[] byte_TDK_KCV = Convert.getInstance().strToBcd("92A65BC2", EPaddingPosition.PADDING_LEFT);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_writekey, container, false);
        button1 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt1);
        button2 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt2);
        button3 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt3);
        button4 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt4);
        resultText = (TextView) view.findViewById(R.id.fragment_ped_writeKey_result_text);
        checkBox = (CheckBox) view.findViewById(R.id.fragment_ped_writeKey_checkBox);
        checkBox.setVisibility(View.VISIBLE);

        button1.setText("write TMK");
        button2.setText("write TPK");
        button3.setText("write TAK");
        button4.setText("write TDK");

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        boolean resB = false;
        String resStr = "";
        switch (v.getId()) {
            case R.id.fragment_ped_writeKey_bt1:
                if (!checkBox.isChecked()) {
                    resB = PedTester.getInstance(pedType).writeKey(EPedKeyType.TLK, (byte) 0x00, EPedKeyType.TMK,
                            (byte) 1, byte_TMK, ECheckMode.KCV_NONE, null);
                    resStr = "TMK";
                } else {
                    resB = PedTester.getInstance(pedType).writeKey(EPedKeyType.TLK, (byte) 0x00, EPedKeyType.SM4_TMK,
                            (byte) 11, byte_TMK, ECheckMode.KCV_NONE, null);
                    resStr = "SM4_TMK";
                }

                break;
            case R.id.fragment_ped_writeKey_bt2:
                if (!checkBox.isChecked()) {
                    resB = PedTester.getInstance(pedType).writeKey(EPedKeyType.TMK, (byte) 1, EPedKeyType.TPK, (byte) 1,
                            byte_TPK, ECheckMode.KCV_ENCRYPT_0, byte_TPK_KCV);
                    resStr = "TPK";
                } else {
                    resB = PedTester.getInstance(pedType).writeKey(EPedKeyType.SM4_TMK, (byte) 11, EPedKeyType.SM4_TPK,
                            (byte) 11, byte_TPK, ECheckMode.KCV_NONE, null);
                    resStr = "SM4_TPK";
                }
                break;
            case R.id.fragment_ped_writeKey_bt3:
                if (!checkBox.isChecked()) {
                    resB = PedTester.getInstance(pedType).writeKey(EPedKeyType.TMK, (byte) 1, EPedKeyType.TAK, (byte) 2,
                            byte_TAK, ECheckMode.KCV_ENCRYPT_0, byte_TAK_KCV);
                    resStr = "TAK";
                } else {
                    resB = PedTester.getInstance(pedType).writeKey(EPedKeyType.SM4_TMK, (byte) 11, EPedKeyType.SM4_TAK,
                            (byte) 12, byte_TAK, ECheckMode.KCV_NONE, null);
                    resStr = "SM4_TAK";
                }
                break;
            case R.id.fragment_ped_writeKey_bt4:
                if (!checkBox.isChecked()) {
                    if(pedType == EPedType.INTERNAL){
                        resB = PedTester.getInstance(pedType).writeKey(EPedKeyType.TMK, (byte) 1, EPedKeyType.TDK, (byte) 3,
                                byte_TDK, ECheckMode.KCV_ENCRYPT_0, byte_TDK_KCV);
                        resStr = "TDK";  
                    }else{
                        //外置密码键盘tdk需要明文写入
                        resB = PedTester.getInstance(pedType).writeKey(EPedKeyType.TMK, (byte) 0, EPedKeyType.TDK, (byte) 3,
                                byte_TDK_plain, ECheckMode.KCV_NONE, null);
                        resStr = "TDK";
                    }
                    
                } else {
                    resB = PedTester.getInstance(pedType).writeKey(EPedKeyType.SM4_TMK, (byte) 11, EPedKeyType.SM4_TDK,
                            (byte) 13, byte_TDK, ECheckMode.KCV_NONE, null);
                    resStr = "SM4_TDK";
                }
                break;
            default:
                break;
        }

        if (resB) {
            resultText.setText("write " + resStr + " success");
        } else {
            resultText.setText("write " + resStr + " failed");
        }
    }

}
