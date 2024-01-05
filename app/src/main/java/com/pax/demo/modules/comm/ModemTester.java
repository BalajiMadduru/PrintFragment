package com.pax.demo.modules.comm;

import com.pax.dal.IComm;
import com.pax.dal.IComm.EConnectStatus;
import com.pax.dal.entity.ModemParam;
import com.pax.dal.exceptions.CommException;
import com.pax.demo.base.DemoApp;
import com.pax.demo.util.BaseTester;

public class ModemTester extends BaseTester {

    private static ModemTester modemTester;

    private IComm modemComm;

    ModemParam modemParam = new ModemParam();

    private ModemTester() {
        String model = android.os.Build.MODEL.toString();

        logTrue(model + " Modem");

        // 0755926619314
        modemParam.setExtNum("0");
        modemParam.setDelayTime(1);

        modemParam.setTelNo1("4008200358");// ("02150361793");
        modemParam.setAsyncMode(0x00);
        modemParam.setSsetup(0x04); // sync, 1200

        // modemParam.setTelNo1("0755926619314");
        // modemParam.setAsyncMode(0x00); // 8,n,1
        // modemParam.setSsetup(0x84); // async, 1200
        //
        modemParam.setTimeout(10);
        //
        // modemParam.setTelNo1("18667066870");
        // modemParam.setAsyncMode(0x04); // 7,e,1
        // modemParam.setSsetup(0x84); // async, 1200
        //

        modemComm = DemoApp.getDal().getCommManager().getModemComm(modemParam);
        logTrue("getModemComm" + modemParam.getTelNo1().toString());
        // comm = IppUser.getInstance().getDal().getCommManager().getUartComm(uartParam);

    };

    public static ModemTester getInstance() {
        if (modemTester == null) {
            modemTester = new ModemTester();
        }
        return modemTester;
    }

    public void connect() {
        try {
            if (modemComm.getConnectStatus() == EConnectStatus.DISCONNECTED) {
                modemComm.connect();
                logTrue("Connect");
            } else {
                logTrue("have connected");
            }
        } catch (CommException e) {
            logErr("Connect", e.getMessage());
            e.printStackTrace();
        }
    }

    public void send(byte[] data) {
        try {
            if (modemComm.getConnectStatus() == EConnectStatus.CONNECTED) {
                modemComm.send(data);
                logTrue("Send");
            } else {
                logErr("Send", "please connect first");
            }
        } catch (CommException e) {
            logErr("Send", e.getMessage());
            e.printStackTrace();
        }
    }

    public byte[] recv(int len) {
        try {
            if (modemComm.getConnectStatus() == EConnectStatus.CONNECTED) {
                byte[] result = modemComm.recv(len);
                logTrue("Recv");
                return result;
            } else {
                logErr("Send", "please connect first");
                return null;
            }
        } catch (CommException e) {
            e.printStackTrace();
            logErr("Recv", e.getMessage());
            return null;
        }

    }

    public byte[] recvNonBlocking() {
        try {
            if (modemComm.getConnectStatus() == EConnectStatus.CONNECTED) {
                byte[] result = modemComm.recvNonBlocking();
                logTrue("recvNonBlocking");
                return result;
            } else {
                logErr("recvNonBlocking", "please connect first");
                return null;
            }
        } catch (CommException e) {
            e.printStackTrace();
            logErr("recvNonBlocking", e.getMessage());
            return null;
        }
    }

    public void disConnect() {
        try {
            if (modemComm.getConnectStatus() == EConnectStatus.CONNECTED)
                modemComm.disconnect();
            logTrue("DisConnect");
            modemTester = null;
            modemComm = null;
        } catch (CommException e) {
            e.printStackTrace();
            logErr("DisConnect", e.getMessage());
        }

    }

    public void setConnectTimeout(int timeout) {
        modemComm.setConnectTimeout(timeout);
        logTrue("setConnectTimeout");
    }

    public void cancelRecv() {
        modemComm.cancelRecv();
        logTrue("cancelRecv");
        disConnect();
    }

    public void reset() {
        modemComm.reset();
        logTrue("reset");
    }

    public void setSendTimeout(int timeout) {
        modemComm.setSendTimeout(timeout);
        logTrue("setSendTimeout");
    }

    public void setRecvTimeout(int timeout) {
        modemComm.setRecvTimeout(timeout);
        logTrue("setRecvTimeout");
    }

}
