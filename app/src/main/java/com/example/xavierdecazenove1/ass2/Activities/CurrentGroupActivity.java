package com.example.xavierdecazenove1.ass2.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xavierdecazenove1.ass2.Connection.Message;
import com.example.xavierdecazenove1.ass2.Controller;
import com.example.xavierdecazenove1.ass2.TranslateClass.LocaleHelper;
import com.example.xavierdecazenove1.ass2.PackListViewClass.ModelItems;
import com.example.xavierdecazenove1.ass2.PackListViewClass.MyAdapter;
import com.example.xavierdecazenove1.ass2.R;

import java.util.ArrayList;
import java.util.List;

public class CurrentGroupActivity extends AppCompatActivity {

    private TextView title;
    private TextView info;
    private ListView listView;
    List<ModelItems> modelItemsList;
    private Button back;
    private Controller controller;

    public static final String TAG = "CurrentGroupActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_group);

        controller = (Controller) getApplication();

        listView = (ListView) findViewById(R.id.listView);
        back = (Button)findViewById(R.id.back);
        title = (TextView)findViewById(R.id.titleCG);
        info = (TextView)findViewById(R.id.info);

        modelItemsList = new ArrayList<>();


        for (int i=0; i<controller.currentGroups.size(); ++i){
            modelItemsList.add(new ModelItems(controller.currentGroups.get(i)));
            Log.d(TAG, "onCreate: " + controller.currentGroups.get(i));
        }
        listView.setAdapter(new MyAdapter(this,modelItemsList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nameGroup = modelItemsList.get(position).getM_name();
                controller.sendMessage(Message.registration(nameGroup,controller.user.getName()));
                Toast.makeText(getApplicationContext(), "Group selected : " + nameGroup, Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(),ManagerActivity.class);
                startActivity(otherActivity);
            }
        });


        // Translation
        if (LocaleHelper.getLanguage(CurrentGroupActivity.this).equalsIgnoreCase("en")) {
            Context context = LocaleHelper.setLocale(CurrentGroupActivity.this, "en");
            Resources resources = context.getResources();
            title.setText(resources.getString(R.string.CurrentGroups));
            info.setText(resources.getString(R.string.ClickToJoin));
            back.setText(resources.getString(R.string.back));
        } else if (LocaleHelper.getLanguage(CurrentGroupActivity.this).equalsIgnoreCase("sv")) {
            Context context = LocaleHelper.setLocale(CurrentGroupActivity.this, "sv");
            Resources resources = context.getResources();
            title.setText(resources.getString(R.string.CurrentGroups));
            info.setText(resources.getString(R.string.ClickToJoin));
            back.setText(resources.getString(R.string.back));
        }

    }
}
