package com.example.btdozimetr.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.telephony.IccOpenLogicalChannelResponse;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ReceiveThread extends Thread{
    private BluetoothSocket socket;
    private InputStream inputS;
    private OutputStream outputS;
    private byte[] rBuffer;

    public TextView outText;//

    //*******************************************************************
    public ReceiveThread(BluetoothSocket socket){
        this.socket = socket;

        //this.outText = findViewByID(R.Id.outZiverText);//

        try{
            inputS = socket.getInputStream();
        }catch (IOException e){

        }
    }

    @Override
    public void run() {
        rBuffer = new byte[100];
        while(true){
            try{
                int size = inputS.read(rBuffer);
                String message = new String(rBuffer, 0, size);
                String temp = "   " + message + " Р.";//*******************************************
                outText.setText(temp);//
                Log.d("MyLog", "Message: " + message);
            } catch (IOException e){
                break;
            }
        }
    }

    public void sendMessage(byte[] byteArray){
       try{
           outputS.write(byteArray);
       } catch (IOException e){

       }
    }
}
