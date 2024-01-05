package com.pax.demo.modules.picc;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.pax.dal.IPicc;
import com.pax.dal.entity.ApduSendInfo;
import com.pax.dal.entity.EBeepMode;
import com.pax.dal.entity.EDetectMode;
import com.pax.dal.entity.EM1KeyType;
import com.pax.dal.entity.EPiccRemoveMode;
import com.pax.dal.entity.EPiccType;
import com.pax.dal.entity.PiccCardInfo;
import com.pax.dal.entity.PiccPara;
import com.pax.dal.exceptions.EPiccDevException;
import com.pax.dal.exceptions.PiccDevException;
import com.pax.demo.base.DemoApp;
import com.pax.demo.modules.system.SysTester;
import com.pax.demo.util.BaseTester;
import com.pax.demo.util.Convert;
import com.pax.demo.util.IApdu;
import com.pax.demo.util.IApdu.IApduReq;
import com.pax.demo.util.Packer;

public class PiccTester extends BaseTester {
    private static PiccTester piccTester;
    private IPicc picc;
    private static EPiccType piccType;

    private PiccTester(EPiccType type) {
        piccType = type;
        picc = DemoApp.getDal().getPicc(piccType);
    }

    public static PiccTester getInstance(EPiccType type) {
        if (piccTester == null || type != piccType) {
            piccTester = new PiccTester(type);
        }
        return piccTester;
    }

    // 读取当前参数设置
    public PiccPara setUp() {
        try {
            PiccPara readParam = picc.readParam();
            logTrue("readParam");
            return readParam;
        } catch (PiccDevException e) {
            e.printStackTrace();
            logErr("readParam", e.toString());
            return null;
        }
    }

    public void open() {
        try {
            picc.open();
            logTrue("open");
        } catch (PiccDevException e) {
            e.printStackTrace();
            logErr("open", e.toString());
        }
    }

    public PiccCardInfo detect(EDetectMode mode) {
        try {
            PiccCardInfo cardInfo = picc.detect(mode);
            logTrue("detect");
            return cardInfo;
        } catch (PiccDevException e) {
            e.printStackTrace();
            logErr("detect", e.toString());
            return null;
        }
    }

    public byte[] isoCommand(byte cid, byte[] send) {
        try {
            byte[] isoCommand = picc.isoCommand(cid, send);
            logTrue("isoCommand");
            return isoCommand;
        } catch (PiccDevException e) {
            e.printStackTrace();
            logErr("isoCommand", e.toString());
            return null;
        }
    }

    public void remove(EPiccRemoveMode mode, byte cid) {
        try {
            picc.remove(mode, cid);
            logTrue("remove");
        } catch (PiccDevException e) {
            e.printStackTrace();
            logErr("remove", e.toString());
        }
    }

    public void setLed(byte led) {
        try {
            picc.setLed(led);
            logTrue("setLed");
        } catch (PiccDevException e) {
            e.printStackTrace();
            logErr("setLed", e.getMessage());
        }
    }

    public void close() {
        try {
            picc.close();
            logTrue("close");
        } catch (PiccDevException e) {
            e.printStackTrace();
            logErr("close", e.toString());
        }
    }

    public void m1Auth(EM1KeyType type, byte blkNo, byte[] pwd, byte[] serialNo) {
        try {
            picc.m1Auth(type, blkNo, pwd, serialNo);
            logTrue("m1Auth");
        } catch (PiccDevException e) {
            e.printStackTrace();
            logErr("m1Auth", e.toString());
        }
    }

    public byte[] m1Read(byte blkNo) {
        try {
            byte[] result = picc.m1Read(blkNo);
            logTrue("m1Read");
            return result;
        } catch (PiccDevException e) {
            e.printStackTrace();
            logErr("m1Read", e.toString());
            return null;
        }

    }

    public int detectAorBandCommand(Handler handler) {
        // light init
        // setLed((byte) 0x00);
        // blue light
        // setLed((byte) 0x08);

        PiccCardInfo cardInfo;
        if (null != (cardInfo = detect(EDetectMode.ISO14443_AB))) {
            // open blue and yellow light
            // setLed((byte) 0x0c);

            IApdu apdu = Packer.getInstance().getApdu();
            IApduReq apduReq = null;
            if (piccType == EPiccType.INTERNAL) {
                // 选择文件名为‘1PAY.SYS.DDF01’的支付系统环境
                apduReq = apdu.createReq((byte) 0x00, (byte) 0xa4, (byte) 0x04, (byte) 0x00,
                        "1PAY.SYS.DDF01".getBytes(), (short) 256);
            } else if (piccType == EPiccType.EXTERNAL) {
                apduReq = apdu.createReq((byte) 0x00, (byte) 0xa4, (byte) 0x04, (byte) 0x00,
                        "2PAY.SYS.DDF01".getBytes(), (short) 256);
            }

            // byte[] resp = isoCommand((byte) 0, apduReq.pack());

            ApduSendInfo apduSendInfo = new ApduSendInfo();
            apduSendInfo.setCommand(new byte[] { (byte) 0x00, (byte) 0xa4, (byte) 0x04, (byte) 0x00 });
            apduSendInfo.setDataIn("1PAY.SYS.DDF01".getBytes());
            apduSendInfo.setLc("1PAY.SYS.DDF01".getBytes().length);
            apduSendInfo.setLe(256);
            String cardString = "";
            /*try {
                long startTime = System.currentTimeMillis();
                ApduRespInfo resp = null;
                for (int i = 0; i < 100; i++) {
                    resp = picc.isoCommandByApdu((byte) 0, apduSendInfo);
                }
                Log.i("Test", "Neptune isoCommand cost:" + (System.currentTimeMillis() - startTime));
                cardString += ("DataOut:" + Convert.getInstance().bcdToStr(resp.getDataOut()));
                cardString += (" \nSWA:" + Convert.getInstance().bcdToStr(new byte[] { resp.getSwA() }));
                cardString += (" \nSWB:" + Convert.getInstance().bcdToStr(new byte[] { resp.getSwB() }));
            } catch (PiccDevException e1) {
                e1.printStackTrace();
            }*/
            try {
                byte[] ret = null;
                for (int i = 0; i < 100; i++) {
                    ret = picc.isoCommand((byte) 0, apduReq.pack());
                    if (null != ret && ret.length > 0) {
                        break;
                    }
                }
                if (ret != null && ret.length > 0) {
                    byte[] dataout = new byte[ret.length - 2];
                    System.arraycopy(ret, 0, dataout, 0, dataout.length);
                    cardString += ("DataOut:" + Convert.getInstance().bcdToStr(dataout));
                    cardString += (" \nSWA:" + Convert.getInstance().bcdToStr(new byte[]{ret[ret.length - 2]}));
                    cardString += (" \nSWB:" + Convert.getInstance().bcdToStr(new byte[]{ret[ret.length - 1]}));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // close();
            // byte[] resp = null;
            // try {
            // long startTime = System.currentTimeMillis();
            // for (int i = 0; i < 100; i++) {
            // resp = GetObj.getDal().getPicc(EPiccType.INTERNAL).cmdExchange(apduReq.pack(), 256);
            // }
            // Log.i("Test", "Neptune cmdExchange cost:" + (System.currentTimeMillis() - startTime));
            // } catch (PiccDevException e1) {
            // e1.printStackTrace();
            // }
            // Log.i("Test", "send data:" + Convert.getInstance().bcdToStr(apduReq.pack()));
            // IApduResp apduResp = Packer.getInstance().getApdu().unpack(resp);
            // String cardString = "";// 卡片的信息
            // cardString += (new String(new byte[] { cardInfo.getCardType() }) + "卡\n");
            // cardString += ("SerialInfo：" + Convert.getInstance().bcdToStr(cardInfo.getSerialInfo()) + "\n");
            // // cardString += ("Other:" +GetObj.getGL().getConvert().bcdToStr(cardInfo.getOther())+"\n");
            // cardString += ("return message:" + " Data:" + Convert.getInstance().bcdToStr(apduResp.getData())
            // + " Status:" + apduResp.getStatus() + " StatusString:" + apduResp.getStatusString());

            Message message = Message.obtain();
            message.what = 0;
            message.obj = cardString;
            handler.sendMessage(message);

            while (true) {
                try {
                    picc.remove(EPiccRemoveMode.REMOVE, (byte) 0);
                    logTrue("remove");
                } catch (PiccDevException e) {
                    // e.printStackTrace();
                    // logErr("remove", e.toString());
                    // logTrue("errorCode:" + e.getErrCode());
                    if (e.getErrCode() == EPiccDevException.PICC_ERR_CARD_SENSE.getErrCodeFromBasement()) {
                        continue;
                    } else {
                        break;
                    }
                }
                // NO exception, break;
                // message = Message.obtain();
                // message.what = 0;
                // message.obj = "searching A card and B card...";
                // handler.sendMessage(message);
                break;
            }
            // open blue,yellow,green light
            // setLed((byte) 0x0e);
            SysTester.getInstance().beep(EBeepMode.FREQUENCE_LEVEL_1, 500);
            SystemClock.sleep(1000);
            // setLed((byte) 0x00);
            return 1;
        }
        return 0;
    }

    public void detectM(Handler handler, EM1KeyType type, int blockNum, byte[] password) {
        // byte[] pwd = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff };
        PiccCardInfo cardInfo = null;
        if (null != (cardInfo = detect(EDetectMode.ONLY_M))) {

            logTrue("cardtype:"
                    + cardInfo.getCardType()
                    + " SerialInfo:"
                    + Convert.getInstance().bcdToStr(
                            (cardInfo.getSerialInfo() == null) ? "".getBytes() : cardInfo.getSerialInfo())
                    + " cid:"
                    + cardInfo.getCID()
                    + " Other:"
                    + Convert.getInstance().bcdToStr(
                            (cardInfo.getOther() == null) ? "".getBytes() : cardInfo.getOther()));
            // read
            String str = "";
            String errStr = "";
            // byte[] value = m1Read((byte) blockNum);
            byte[] value = null;
            try {
                Log.i("Test", "keyType:" + type.name() + " blockNum:" + blockNum + " password:"
                        + Convert.getInstance().bcdToStr(password));
                picc.m1Auth(type, (byte) blockNum, password, cardInfo.getSerialInfo());
                value = picc.m1Read((byte) blockNum);
            } catch (PiccDevException e) {
                e.printStackTrace();
                errStr = ("[errCode:" + e.getErrCode() + " errMsg:" + e.getErrMsg() + "]");
            }
            if (value != null) {
                str += (Convert.getInstance().bcdToStr(value) + "\n");
            } else {
                // String errStr = "block " + blockNum + " read null";
                if (errStr == null || errStr.length() == 0)
                    str += "null";
                else
                    str += errStr;
                // logErr("m1Read", errStr);
            }

            String cardString = "";// 卡片的信息
            cardString += ("cardType:" + new String(new byte[] { cardInfo.getCardType() }) + "\n");
            cardString += ("block " + blockNum + " read message:" + str);

            Message message = Message.obtain();
            message.what = 0;
            message.obj = cardString;
            handler.sendMessage(message);

        } else {
            Message.obtain(handler, 0, "can't find card !").sendToTarget();
        }

    }
}
