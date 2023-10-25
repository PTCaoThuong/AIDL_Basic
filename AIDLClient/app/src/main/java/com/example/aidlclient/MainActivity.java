package com.example.aidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import com.example.aidlserver.IAIDLColorInterface;

public class MainActivity extends AppCompatActivity {
    IAIDLColorInterface Iaidlcolorinterface;

    Button btnchange;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            Iaidlcolorinterface = IAIDLColorInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            Iaidlcolorinterface = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent("AIDLColorService");
        intent.setPackage("com.example.aidlserver");

        bindService(intent,connection,BIND_AUTO_CREATE);

        btnchange = findViewById(R.id.btnchange);
        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int color = Iaidlcolorinterface.getcolor();
                    v.setBackgroundColor(color);
                }catch (RemoteException e){

                }
            }
        });
    }
}