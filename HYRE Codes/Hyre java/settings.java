package com.example.hyreeee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class settings extends AppCompatActivity {
     SwitchCompat switch_1,switch_2,switch_3,switch_4;
     boolean stateswitch1,stateswitch2,stateswitch3,stateswitch4;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sp=getSharedPreferences("PREFS",0);
        stateswitch1=sp.getBoolean("Switch 1",false);
        stateswitch2=sp.getBoolean("Switch 2",false);
        stateswitch3=sp.getBoolean("Switch 3",false);
        stateswitch4=sp.getBoolean("Switch 4",false);

        switch_1=(SwitchCompat)findViewById(R.id.switch_1);
        switch_2=(SwitchCompat)findViewById(R.id.switch_2);
        switch_3=(SwitchCompat)findViewById(R.id.switch_3);
        switch_4=(SwitchCompat)findViewById(R.id.switch_4);
        switch_1.setChecked(stateswitch1);
        switch_2.setChecked(stateswitch2);
        switch_3.setChecked(stateswitch3);
        switch_4.setChecked(stateswitch4);

        switch_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateswitch1= !stateswitch1;
                switch_1.setChecked(stateswitch1);
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("Switch 1",stateswitch1);
                editor.apply();

            }
        });
        switch_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateswitch2= !stateswitch2;
                switch_2.setChecked(stateswitch2);
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("Switch 2",stateswitch2);
                editor.apply();

            }
        });
        switch_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateswitch3= !stateswitch3;
                switch_3.setChecked(stateswitch3);
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("Switch 3",stateswitch3);
                editor.apply();

            }
        });
        switch_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateswitch4= !stateswitch4;
                switch_4.setChecked(stateswitch4);
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("Switch 4",stateswitch4);
                editor.apply();

            }
        });
    }
}