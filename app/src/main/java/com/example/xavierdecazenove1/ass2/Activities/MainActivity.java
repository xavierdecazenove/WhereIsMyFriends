package com.example.xavierdecazenove1.ass2.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xavierdecazenove1.ass2.Controller;
import com.example.xavierdecazenove1.ass2.R;

public class MainActivity extends AppCompatActivity {

    private Controller controller;
    private Button login;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button)findViewById(R.id.login);
        name = (EditText)findViewById(R.id.name);

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);

        controller = (Controller)getApplication();

        /** Check du SharedPreference : Pass à l'activité suivante */
        if (!sharedPreferences.getString("name","").equals("")){
            controller.creatUser(sharedPreferences.getString("name",""));
            Intent otherActivity = new Intent(getApplicationContext(), ManagerActivity.class);
            startActivity(otherActivity);
        }

        /** Lance l'activity Manager */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(!name.getText().toString().equals("")) {
                    saveData();
                    Intent otherActivity = new Intent(getApplicationContext(), ManagerActivity.class);
                    startActivity(otherActivity);
                }
            }
        });
    }

    protected void onDestroy() {
        controller.disconnectClicked();
        super.onDestroy();
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("name", name.getText().toString());
        editor.apply();
    }
}
