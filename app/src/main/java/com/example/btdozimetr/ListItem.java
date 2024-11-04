package com.example.btdozimetr;

import android.bluetooth.BluetoothDevice;

import com.example.btdozimetr.adapter.BtAdapter;

public class ListItem {
    //private String btName;
    //private String btMac;
    private BluetoothDevice btDevice;
    private String itemType = BtAdapter.DEF_ITEM_TYPE;

    public BluetoothDevice getBtDevice() {
        return btDevice;
    }

    public void setBtDevice(BluetoothDevice btDevice) {
        this.btDevice = btDevice;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}
