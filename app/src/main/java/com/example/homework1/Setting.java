package com.example.homework1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner setting_SPI_speed;
    private Button settings_BTN_back;
    private int speed;
    private Switch setting_mute,setting_button;
    private EditText settings_name;
    public static String name;
    private Button settings_BTN_save;

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

        setting_mute = findViewById(R.id.setting_mute);
        setting_mute.setChecked(TheGame.checkIfPlay);
        setting_mute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkSwitch();
            }
        });

        setting_button = findViewById(R.id.setting_button);
        setting_button.setChecked(TheGame.checkIfButtonOrSensor);
        setting_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkButtonSwitch();
            }
        });

        setting_SPI_speed = findViewById(R.id.setting_speed);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Speed,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setting_SPI_speed.setAdapter(adapter);
        setting_SPI_speed.setOnItemSelectedListener(this);

        settings_name = findViewById(R.id.settings_name);

        settings_BTN_save =  findViewById(R.id.settings_BTN_save);
        settings_BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = settings_name.getText().toString();
                Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_SHORT).show();
            }
        });

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
                speed =500;
                break;

            case ("X3"):
                speed =300;
                break;
            default:
                speed =600;
                break;
        }
        Intent intent = new Intent(this,MainApp.class);
        intent.putExtra("SPEED_VALUE", speed);
        finish();
        startActivity(intent);

    }
    public void checkSwitch() {

        if (setting_mute.isChecked())
            TheGame.checkIfPlay = true;

        else
            TheGame.checkIfPlay = false;

    }
    private void checkButtonSwitch() {
        if (setting_button.isChecked())
            TheGame.checkIfButtonOrSensor = true;
        else
            TheGame.checkIfButtonOrSensor = false;
    }
}
