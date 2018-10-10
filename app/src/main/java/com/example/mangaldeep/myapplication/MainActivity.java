package com.example.mangaldeep.myapplication;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText name,num;
    private Button bt;
    private String key;
   private Firebase f;
    public String mac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // final FirebaseDatabase database=FirebaseDatabase.getInstance();
        //final DatabaseReference table=database.getReference("User");

        Firebase.setAndroidContext(this);
       f=new Firebase("https://myapplication-47ef7.firebaseio.com/");
        name=(EditText)findViewById(R.id.name);
        num=(EditText)findViewById(R.id.no);
        bt=(Button)findViewById(R.id.button);
       // key=table.push().getKey();
        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                try {
                    List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
                    for (NetworkInterface nif : all) {
                        if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                        byte[] macBytes = nif.getHardwareAddress();
                        if (macBytes == null) {
                            mac="";
                        }

                        StringBuilder res1 = new StringBuilder();
                        for (byte b : macBytes) {
                            res1.append(String.format("%02X:",b));
                        }

                        if (res1.length() > 0) {
                            res1.deleteCharAt(res1.length() - 1);
                        }
                        mac=res1.toString();
                    }
                } catch (Exception ex)
                {
                }
                Firebase m= f.child("temp_db").push();
         //       m.setValue("Nidhi");
                String str=name.getText().toString();
                String str1=num.getText().toString();

                Map<String,Object> map=new HashMap<>();
                map.put("name",str);
                map.put("no_of_person",str1);
                map.put("MAC address",mac);
             //   table.setValue(map);
                     m.setValue(map);

            }
        });
    }

}
