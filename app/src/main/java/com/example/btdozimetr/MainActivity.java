package com.example.btdozimetr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;







import com.example.btdozimetr.adapter.BtConsts;
import com.example.btdozimetr.bluetooth.BtConnection;

import java.util.Arrays;





public class MainActivity extends AppCompatActivity {

    private MenuItem menuItem;
    private BluetoothAdapter btAdapter;
    private final int ENABLE_REQUEST = 15;
    private SharedPreferences pref;
    private BtConnection btConnection;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       init();//
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menuItem = menu.findItem(R.id.id_bt_button);
        setBtIcon();//

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.id_bt_button){
            if(!btAdapter.isEnabled()){
                enableBt();
            } else {
                btAdapter.disable();
                menuItem.setIcon(R.drawable.bt_enable);
            }
        } else if(item.getItemId() == R.id.id_menu){

            if(btAdapter.isEnabled()){
                Intent i = new Intent(MainActivity.this, BtListActivity.class);
                startActivity(i);
            } else{
                Toast.makeText(this, "Включите bluetooth для перехода на этот экран", Toast.LENGTH_SHORT).show();
            }
        } else if(item.getItemId() == R.id.id_bt_connect){
            btConnection.connect();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ENABLE_REQUEST){
            if(requestCode == RESULT_OK){
                setBtIcon();
            }
        }
    }

    private void setBtIcon(){
        if(btAdapter.isEnabled()){
            menuItem.setIcon(R.drawable.bt_disabled);
        } else{
            menuItem.setIcon(R.drawable.bt_enable);
        }
    }

    private void init(){
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        pref = getSharedPreferences(BtConsts.MY_PREF, Context.MODE_PRIVATE);
        btConnection = new BtConnection(this);
        Log.d("MyLog", "Bt Mac address : " + pref.getString(BtConsts.MAC_KEY, "no bt selected"));
    }

    private void enableBt(){
        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(i, ENABLE_REQUEST);
    }

    public void startNewAct(View v){
        Intent intent = new Intent(this, Polimaster.class);
        startActivity(intent);
    }

}