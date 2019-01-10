package com.example.xavierdecazenove1.ass2.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xavierdecazenove1.ass2.Connection.Message;
import com.example.xavierdecazenove1.ass2.Controller;
import com.example.xavierdecazenove1.ass2.TranslateClass.LocaleHelper;
import com.example.xavierdecazenove1.ass2.R;

public class CreatGroupActivity extends AppCompatActivity {

    private EditText name;
    private Button create;
    private TextView title;

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_group);

        controller = (Controller) getApplication();

        create = (Button)findViewById(R.id.createGroup);
        name = (EditText)findViewById(R.id.nameGroup);
        title = (TextView)findViewById(R.id.titleCrG);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().equals("")){
                    controller.sendMessage(Message.registration(name.getText().toString(),controller.user.getName()));
                    Intent otherActivity = new Intent(getApplicationContext(),ManagerActivity.class);
                    startActivity(otherActivity);
                } else {
                    Toast.makeText(getApplicationContext(),"Please fill up the field", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Translation
        if (LocaleHelper.getLanguage(CreatGroupActivity.this).equalsIgnoreCase("en")) {
            Context context = LocaleHelper.setLocale(CreatGroupActivity.this, "en");
            Resources resources = context.getResources();
            title.setText(resources.getString(R.string.CreateAGroup));
            create.setText(resources.getString(R.string.create));
            name.setHint(resources.getString(R.string.name));
        } else if (LocaleHelper.getLanguage(CreatGroupActivity.this).equalsIgnoreCase("sv")) {
            Context context = LocaleHelper.setLocale(CreatGroupActivity.this, "sv");
            Resources resources = context.getResources();
            title.setText(resources.getString(R.string.CreateAGroup));
            create.setText(resources.getString(R.string.create));
            name.setHint(resources.getString(R.string.name));
        }

    }
}
