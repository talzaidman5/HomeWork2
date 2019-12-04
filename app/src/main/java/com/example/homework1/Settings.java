package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner setting_SPI_speed;
    private Button settings_BTN_back;
    private int speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settings_BTN_back = findViewById(R.id.settings_BTN_back);
        settings_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToStart();
            }
        });


        setting_SPI_speed = findViewById(R.id.setting_speed);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Speed,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setting_SPI_speed.setAdapter(adapter);
        setting_SPI_speed.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        if(!text.equals("Choose the game speed")) {
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        }
        }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    public void backToStart(){
         switch (setting_SPI_speed.getSelectedItem().toString()){
             case("X1"):
                 speed =800;
                 break;

             case ("X2"):
                 speed =600;
                 break;

             case ("X2.5"):
                 speed =700;
                 break;

             case ("X3"):
                 speed =300;
                 break;
             default:
                 speed =900;
                 break;
         }
         Intent intent = new Intent(this,MainApp.class);
         intent.putExtra("SPEED_VALUE", speed);
         startActivity(intent);

     }
}
